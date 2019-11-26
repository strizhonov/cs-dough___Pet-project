package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.entity.TournamentDto;
import by.training.service.ServiceException;
import by.training.service.TournamentService;
import by.training.servlet.ServletRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<TournamentDto> tournaments = service.getAll();
            request.setAttribute(ApplicationConstantsContainer.TOURNAMENTS, tournaments);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournaments' list.", e);
        }
        return new ServletRouter("/jsp/tournaments.jsp");
    }

    public ActionCommandType getType() {
        return type;
    }
}
