package by.training.organizer;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.tournament.TournamentDto;
import by.training.tournament.TournamentService;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ToOrganizerEditingCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToOrganizerEditingCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_ORGANIZER_EDITING;
    private OrganizerService organizerService;
    private TournamentService tournamentService;


    public ToOrganizerEditingCommand(OrganizerService organizerService, TournamentService tournamentService) {
        this.organizerService = organizerService;
        this.tournamentService = tournamentService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());
        try {

            OrganizerDto organizer = organizerService.find(user.getOrganizerId());
            request.setAttribute(AttributesContainer.ORGANIZER.toString(), organizer);

            List<TournamentDto> tournaments = tournamentService.findAllOfOrganizer(organizer.getId());
            request.setAttribute(AttributesContainer.TOURNAMENTS.toString(), tournaments);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_ORGANIZER_EDITING));

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform command logic.", e);
            throw new ActionCommandExecutionException("Unable to perform command logic.", e);
        }


    }

}
