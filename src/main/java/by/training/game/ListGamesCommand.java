package by.training.game;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.tournament.ListTournamentsCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ListGamesCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListTournamentsCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_GAMES;
    private final GameService service;


    public ListGamesCommand(GameService service) {
        this.service = service;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            List<ComplexGameDto> games = service.findAll();
            request.setAttribute(AttributesContainer.GAMES.toString(), games);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_GAMES));

        } catch (ServiceException e) {
            LOGGER.error("Unable execute games listing.", e);
            throw new ActionCommandExecutionException("Unable execute games listing.", e);
        }
    }

}
