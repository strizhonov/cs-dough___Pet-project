package by.training.game;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.tournament.ListTournamentsCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListGamesCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListTournamentsCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_GAMES;
    private final GameService service;

    public ListGamesCommand(GameService service) {
        this.service = service;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {
            List<GameDto> games = service.findAll();
            request.setAttribute(AttributesContainer.GAMES.toString(), games);
            return new HttpForwarder(PathsContainer.GAMES);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get games' list.", e);
            throw new ActionCommandExecutionException("Unable to get games' list.", e);
        }

    }

    public ActionCommandType getType() {
        return type;
    }
}
