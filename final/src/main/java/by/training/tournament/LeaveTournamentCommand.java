package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LeaveTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LeaveTournamentCommand.class);
    private final ActionCommandType type = ActionCommandType.LEAVE_TOURNAMENT;
    private final TournamentService tournamentService;


    public LeaveTournamentCommand(TournamentService tournamentService) {
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
        long playerId = user.getPlayerAccountId();

        String sTournamentId = request.getParameter(AttributesContainer.ID.toString());
        long tournamentId = Long.parseLong(sTournamentId);

        ParticipantDto dto = new ParticipantDto(playerId, tournamentId);

        try {

            if (tournamentService.leaveTournament(dto)) {

                return Optional.of(new HttpForwarder(PathsContainer.COMMAND_TO_TOURNAMENT_PAGE + tournamentId));

            } else {


                return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_ERROR_PAGE));
            }

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform tournament leaving.", e);
            throw new ActionCommandExecutionException("Unable to perform tournament leaving.", e);
        }

    }


}
