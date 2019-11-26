package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.entity.TournamentDto;
import by.training.service.ServiceException;
import by.training.service.TournamentService;
import by.training.servlet.ServletRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String sId = request.getParameter(ApplicationConstantsContainer.ID);
        long id = Long.parseLong(sId);
        try {
            TournamentDto tournamentDto = tournamentService.get(id);
            request.setAttribute(ApplicationConstantsContainer.TOURNAMENT, tournamentDto);
            return new ServletRouter("/jsp/tournament-page.jsp");
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
