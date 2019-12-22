package by.training.game;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class IncreasePlayerCountCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(IncreasePlayerCountCommand.class);
    private final ActionCommandType type = ActionCommandType.INCREASE_PLAYER_COUNT;
    private final GameService gameService;
    private final UserService userService;


    public IncreasePlayerCountCommand(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        String sGameId = request.getParameter(AttributesContainer.ID.toString());
        String sNo = request.getParameter(AttributesContainer.NO.toString());

        try {

            if (gameService.increasePlayerPoints(Long.parseLong(sGameId), Integer.parseInt(sNo))) {

                // Refresh user
                UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());
                user = userService.find(user.getId());
                request.getSession().setAttribute(AttributesContainer.USER.toString(), user);
            }

            return Optional.of(new HttpRedirector(request.getContextPath() +
                    PathsContainer.COMMAND_TO_GAME_PAGE + sGameId));

        } catch (ServiceException e) {
            LOGGER.error("Unable execute count increasing.", e);
            throw new ActionCommandExecutionException("Unable execute count increasing.", e);
        }
    }

}
