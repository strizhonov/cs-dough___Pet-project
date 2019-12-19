package by.training.player;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.organizer.OrganizerService;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DeletePlayerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeletePlayerCommand.class);
    private final ActionCommandType type = ActionCommandType.DELETE_PLAYER;
    private final PlayerService playerService;


    public DeletePlayerCommand(PlayerService playerService) {
        this.playerService = playerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());

        try {
            if (playerService.delete(user.getPlayerAccountId())) {
                user.setPlayerAccountId(0);

                return Optional.of(new HttpRedirector(request.getContextPath()
                        + PathsContainer.FILE_USER_PROFILE_PAGE));
            } else {

                LocalizationManager manager
                        = new LocalizationManager(AttributesContainer.I18N.toString(), request.getLocale());


                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(AttributesContainer.PLAYER_DELETING_ERROR.toString()));

                return Optional.of(new HttpRedirector(request.getContextPath()
                        + PathsContainer.COMMAND_SHOW_PLAYER + user.getPlayerAccountId()));
            }


        } catch (ServiceException e) {
            LOGGER.error("Unable to perform player deleting.", e);
            throw new ActionCommandExecutionException("Unable to perform player deleting.", e);
        }

    }


}
