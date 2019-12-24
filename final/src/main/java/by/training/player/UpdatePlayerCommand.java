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
import by.training.util.CommandMapper;
import by.training.util.ServletUtil;
import by.training.validation.*;
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
import java.util.Locale;
import java.util.Optional;

public class UpdatePlayerCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.UPDATE_PLAYER;
    private static final Logger LOGGER = LogManager.getLogger(CreatePlayerCommand.class);
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

            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));
            UpdatedDataValidator<PlayerDto, PlayerValidationDto> validator
                    = new PlayerDataValidator(playerService, manager);

            HttpSession session = request.getSession();
            UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
            PlayerDto player = playerService.findOfUser(user.getId());

            List<FileItem> items = ServletUtil.parseRequest(request);
            PlayerValidationDto validationDto = CommandMapper.mapPlayerValidationDto(items);

            ValidationResult result = validator.validate(player, validationDto);
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(), manager.getValue(result.getFirstKey()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_PLAYER_EDITING));
            }


            CommandMapper.merge(player, validationDto);
            if (playerService.update(player)) {

                return Optional.of(new HttpRedirector(request.getContextPath() +
                        PathsContainer.COMMAND_SHOW_PLAYER + player.getId()));

            } else {

                throw new ActionCommandExecutionException("Unable to perform player updating.");

            }


        } catch (ServiceException | FileUploadException | IOException | ValidationException e) {
            LOGGER.error("Unable to perform player creation.", e);
            throw new ActionCommandExecutionException("Unable to perform player creation.", e);
        }

    }




}
