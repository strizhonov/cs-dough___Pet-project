package by.training.player;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.organizer.ToOrganizerEditingCommand;
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
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class ToPlayerEditingCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToOrganizerEditingCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_PLAYER_EDITING;
    private final PlayerService playerService;
    private final TournamentService tournamentService;


    public ToPlayerEditingCommand(PlayerService playerService, TournamentService tournamentService) {
        this.playerService = playerService;
        this.tournamentService = tournamentService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());

        try {

            PlayerDto player = playerService.find(user.getPlayerAccountId());
            request.setAttribute(AttributesContainer.PLAYER.toString(), player);

            List<TournamentDto> tournaments = tournamentService.findAllOfPlayer(player.getId());
            request.setAttribute(AttributesContainer.TOURNAMENTS.toString(), tournaments);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_PLAYER_EDITING));

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform command logic.", e);
            throw new ActionCommandExecutionException("Unable to perform command logic.", e);
        }

    }

}
