package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DeleteTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateTournamentCommand.class);
    private final ActionCommandType type = ActionCommandType.DELETE_TOURNAMENT;
    private final TournamentService tournamentService;


    public DeleteTournamentCommand(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        HttpSession httpSession = request.getSession();
        UserDto user = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());

        String sId = request.getParameter(AttributesContainer.ID.toString());
        try {
            tournamentService.delete(Long.parseLong(sId));

            return Optional.of(new HttpForwarder(request.getContextPath()
                    + PathsContainer.COMMAND_SHOW_ORGANIZER + user.getOrganizerId()));

        } catch (ServiceException e) {
            LOGGER.error("Tournament deleting failed.", e);
            throw new ActionCommandExecutionException("Tournament deleting failed.", e);
        }

    }


}
