package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.dto.TournamentDto;
import by.training.service.ServiceException;
import by.training.service.TournamentService;
import by.training.servlet.HttpRouter;
import by.training.servlet.ServletForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListTournamentsCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListTournamentsCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_TOURNAMENTS;
    private TournamentService service;

    public ListTournamentsCommand(TournamentService service) {
        this.service = service;
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        try {
            List<TournamentDto> tournaments = service.findAll();
            request.setAttribute(AttributesContainer.TOURNAMENTS.toString(), tournaments);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournaments' list.", e);
            throw new ActionCommandExecutionException("Unable to get tournaments' list.", e);
        }
        return new ServletForwarder(servlet, "/jsp/tournaments.jsp");
    }

    public ActionCommandType getType() {
        return type;
    }
}
