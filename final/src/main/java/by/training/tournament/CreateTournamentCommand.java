package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.util.ServletUtil;
import by.training.util.TournamentUtil;
import by.training.validation.InputDataValidator;
import by.training.validation.TournamentDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class CreateTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateTournamentCommand.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final ActionCommandType type = ActionCommandType.CREATE_TOURNAMENT;
    private final TournamentService tournamentService;


    public CreateTournamentCommand(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            List<FileItem> items = ServletUtil.parseRequest(request);

            TournamentValidationDto validationDto = compile(items);


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    request.getLocale());

            InputDataValidator<TournamentValidationDto> validator
                    = new TournamentDataValidator(tournamentService, manager);

            ValidationResult result = validator.validate(validationDto);
            if (!result.isValid()) {
                setErrorMessage(result, request);
                return Optional.of(new HttpForwarder(PathsContainer.FILE_TOURNAMENT_CREATION_PAGE));
            }


            HttpSession httpSession = request.getSession();
            UserDto user = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());


            TournamentDto genericDto = convert(user, items, validationDto, request);
            long tournamentId = tournamentService.create(genericDto);


            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.COMMAND_TO_TOURNAMENT_PAGE + tournamentId));

        } catch (ServiceException | FileUploadException | IOException | ValidationException e) {
            LOGGER.error("Tournament creation failed.", e);
            throw new ActionCommandExecutionException("Tournament creation failed.", e);
        }

    }


    private TournamentValidationDto compile(List<FileItem> items) throws IOException {

        int i = -1;
        long logoSize = items.get(++i).getSize();

        String name = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sReward = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sBonus = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sBuyIn = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sPlayersNumber = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        return new TournamentValidationDto(logoSize, name, sReward, sBonus, sBuyIn, sPlayersNumber);
    }


    private void setErrorMessage(ValidationResult result, HttpServletRequest request) {
        LocalizationManager manager
                = new LocalizationManager(AttributesContainer.I18N.toString(), request.getLocale());


        request.setAttribute(AttributesContainer.MESSAGE.toString(),
                manager.getValue(result.getFirstValue()));

    }


    private TournamentDto convert(UserDto user, List<FileItem> items, TournamentValidationDto validationDto,
                                  HttpServletRequest request) throws IOException {

        int i = -1;
        byte[] logo = items.get(++i).get();
        if (logo == null || logo.length == 0) {
            File file = new File(request.getServletContext().getRealPath(PathsContainer.FILE_DEF_TOURNAMENT_LOGO));
            InputStream is = new FileInputStream(file);
            logo = IOUtils.toByteArray(is);
        }

        String name = validationDto.getName();
        double reward = Double.parseDouble(validationDto.getOrganizerRewardPercentage());
        double bonus = Double.parseDouble(validationDto.getFromOrganizerBonus());
        double buyIn = Double.parseDouble(validationDto.getBuyIn());
        int playersNumber = Integer.parseInt(validationDto.getPlayersNumber());


        double prizePool = TournamentUtil.calculatePrizePool(reward, bonus, buyIn, playersNumber);


        return new TournamentDto.Builder()
                .logo(logo)
                .name(name)
                .prizePool(prizePool)
                .buyIn(buyIn)
                .reward(reward)
                .playersNumber(playersNumber)
                .organizerId(user.getOrganizerId())
                .build();
    }

}
