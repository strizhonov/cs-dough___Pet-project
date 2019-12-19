package by.training.game;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class IncreaseFirstPlayerCountCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(IncreaseFirstPlayerCountCommand.class);
    private final ActionCommandType type = ActionCommandType.INCREASE_FIRST_PLAYER_COUNT;
    private final GameService gameService;


    public IncreaseFirstPlayerCountCommand(GameService gameService) {
        this.gameService = gameService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        String sGameId = request.getParameter(AttributesContainer.ID.toString());
        try {

            gameService.increaseFirstPlayerPoints(Long.parseLong(sGameId));

            return Optional.of(new HttpRedirector(request.getContextPath() +
                    PathsContainer.COMMAND_TO_GAME_PAGE + sGameId));

        } catch (ServiceException e) {
            LOGGER.error("Unable execute count increasing.", e);
            throw new ActionCommandExecutionException("Unable execute count increasing.", e);
        }
    }


}
