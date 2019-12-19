package by.training.player;

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
import by.training.validation.InputDataValidator;
import by.training.validation.PlayerDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

public class UpdatePlayerCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.UPDATE_PLAYER;
    private static final Logger LOGGER = LogManager.getLogger(CreatePlayerCommand.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final PlayerService playerService;


    public UpdatePlayerCommand(PlayerService playerService) {
        this.playerService = playerService;

    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            PlayerValidationDto validationDto = compile(request);


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    request.getLocale());


            InputDataValidator<PlayerValidationDto> validator
                    = new PlayerDataValidator(playerService, manager);


            ValidationResult result = validator.validate(validationDto);
            if (!result.isValid()) {
                setErrorMessage(result, request);
                return Optional.of(new HttpForwarder(PathsContainer.FILE_TOURNAMENT_CREATION_PAGE));
            }

            HttpSession session = request.getSession();
            UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());


            PlayerDto genericDto = convert(validationDto, request, user);
            if (playerService.update(genericDto)) {

                return Optional.of(new HttpRedirector( request.getContextPath() +
                        PathsContainer.COMMAND_SHOW_PLAYER + genericDto.getId()));

            } else {

                throw new ActionCommandExecutionException("Unable to perform player updating.");

            }


        } catch (ServiceException | FileUploadException | IOException | ValidationException e) {
            LOGGER.error("Unable to perform player creation.", e);
            throw new ActionCommandExecutionException("Unable to perform player creation.", e);
        }


    }


    private PlayerValidationDto compile(HttpServletRequest request) throws FileUploadException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);

        List<FileItem> items = sfu.parseRequest(request);

        int i = -1;
        long logoSize = items.get(++i).getSize();
        String nickname = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String name = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String surname = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        return new PlayerValidationDto(logoSize, nickname, name, surname);

    }


    private void setErrorMessage(ValidationResult result, HttpServletRequest request) {
        LocalizationManager manager
                = new LocalizationManager(AttributesContainer.I18N.toString(), request.getLocale());


        request.setAttribute(AttributesContainer.MESSAGE.toString(),
                manager.getValue(result.getFirstValue()));
    }


    private PlayerDto convert(PlayerValidationDto validationDto, HttpServletRequest request, UserDto user)
            throws FileUploadException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);

        List<FileItem> items = sfu.parseRequest(request);

        byte[] photo = items.get(0).get();
        if (photo == null || photo.length == 0) {
            File file = new File(request.getServletContext().getRealPath(PathsContainer.FILE_NOBODY));
            InputStream is = new FileInputStream(file);
            photo = IOUtils.toByteArray(is);
        }


        return new PlayerDto.Builder()
                .photo(photo)
                .name(validationDto.getName())
                .surname(validationDto.getSurname())
                .nickname(validationDto.getNickname())
                .userId(user.getId())
                .build();

    }


}
