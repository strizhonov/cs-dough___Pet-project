package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.common.ServiceException;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(JoinTournamentCommand.class);
    private final ActionCommandType type = ActionCommandType.JOIN_TOURNAMENT;
    private final UserService userService;

    public JoinTournamentCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());
        long playerId = user.getPlayerAccountId();
        String sTournamentId = request.getParameter(AttributesContainer.TOURNAMENT_ID.toString());
        long tournamentId = Long.parseLong(sTournamentId);
        TournamentJoiningDto dto = new TournamentJoiningDto(playerId, tournamentId);
        try {
            userService.joinTournament(dto);
            return new HttpForwarder(PathsContainer.TOURNAMENT_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Unable to perform tournament joining.", e);
            throw new ActionCommandExecutionException("Unable to perform tournament joining.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
