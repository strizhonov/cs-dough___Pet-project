package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.organizer.OrganizerDto;
import by.training.organizer.OrganizerService;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ToTournamentPageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToTournamentPageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_TOURNAMENT_PAGE;
    private final TournamentService tournamentService;
    private final OrganizerService organizerService;


    public ToTournamentPageCommand(TournamentService tournamentService, OrganizerService organizerService) {
        this.tournamentService = tournamentService;
        this.organizerService = organizerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {

            TournamentDto tournamentDto = tournamentService.find(id);
            request.setAttribute(AttributesContainer.TOURNAMENT.toString(), tournamentDto);

            OrganizerDto organizer = organizerService.find(tournamentDto.getOrganizerId());
            request.setAttribute(AttributesContainer.ORGANIZER.toString(), organizer);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_TOURNAMENT_PAGE));

        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournament with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get tournament with " + id + " id.", e);
        }
    }

}
