package by.training.game;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToGamePageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToGamePageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_GAME_PAGE;

    private final GameService gameService;

    public ToGamePageCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {

        String sId = request.getParameter(AttributesContainer.ID.toString());
        long gameId = Long.parseLong(sId);
        try {
            GameDto gameDto = gameService.find(gameId);
            request.setAttribute(AttributesContainer.GAME.toString(), gameDto);
            return new HttpForwarder(PathsContainer.GAME_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournament with " + gameId + " id.", e);
            throw new ActionCommandExecutionException("Unable to get tournament with " + gameId + " id.", e);
        }
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
