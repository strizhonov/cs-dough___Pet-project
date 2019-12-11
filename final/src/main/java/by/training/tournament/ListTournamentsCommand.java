package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.common.ServiceException;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListTournamentsCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListTournamentsCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_TOURNAMENTS;
    private final TournamentService service;

    public ListTournamentsCommand(TournamentService service) {
        this.service = service;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        try {
            List<TournamentDto> tournaments = service.findAll();
            request.setAttribute(AttributesContainer.TOURNAMENTS.toString(), tournaments);
            return new HttpForwarder(PathsContainer.TOURNAMENTS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournaments' list.", e);
            throw new ActionCommandExecutionException("Unable to get tournaments' list.", e);
        }

    }

    public ActionCommandType getType() {
        return type;
    }
}
