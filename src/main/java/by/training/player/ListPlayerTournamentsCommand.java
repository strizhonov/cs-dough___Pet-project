package by.training.player;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ListPlayerTournamentsCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListPlayerTournamentsCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_PLAYER_TOURNAMENTS;
    private final TournamentService tournamentService;
    private final PlayerService playerService;


    public ListPlayerTournamentsCommand(TournamentService tournamentService, PlayerService playerService) {
        this.tournamentService = tournamentService;
        this.playerService = playerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {

        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);

        try {
            PlayerDto player = playerService.find(id);
            request.setAttribute(AttributesContainer.PLAYER.toString(), player);

            List<TournamentDto> tournaments = tournamentService.findAllOfPlayer(id);
            request.setAttribute(AttributesContainer.TOURNAMENTS.toString(), tournaments);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_PLAYER_TOURNAMENTS));

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform tournaments listing.", e);
            throw new ActionCommandExecutionException("Unable to perform tournaments listing.", e);
        }

    }


}
