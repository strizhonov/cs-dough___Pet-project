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

public class ToTournamentPageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToTournamentPageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_TOURNAMENT_PAGE;

    private TournamentService tournamentService;

    public ToTournamentPageCommand(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            TournamentDto tournamentDto = tournamentService.find(id);
            request.setAttribute(AttributesContainer.TOURNAMENT.toString(), tournamentDto);
            return new ServletForwarder(servlet, "/jsp/tournament-page.jsp");
        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournament with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get tournament with " + id + " id.", e);
        }
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
