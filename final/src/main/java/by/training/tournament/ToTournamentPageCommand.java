package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.common.ServiceException;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.OrganizerDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToTournamentPageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToTournamentPageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_TOURNAMENT_PAGE;

    private final TournamentService tournamentService;
    private final UserService userService;

    public ToTournamentPageCommand(TournamentService tournamentService, UserService userService) {
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            TournamentDto tournamentDto = tournamentService.find(id);
            request.setAttribute(AttributesContainer.TOURNAMENT.toString(), tournamentDto);
            OrganizerDto organizer = userService.findOrganizer(tournamentDto.getOrganizerId());
            request.setAttribute(AttributesContainer.ORGANIZER.toString(), organizer);
            return new HttpForwarder(PathsContainer.TOURNAMENT_PAGE);
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
