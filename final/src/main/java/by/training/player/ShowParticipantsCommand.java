package by.training.player;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.game.GameService;
import by.training.organizer.ShowOrganizerCommand;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.tournament.TournamentDto;
import by.training.tournament.TournamentPlacement;
import by.training.tournament.TournamentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ShowParticipantsCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.SHOW_PARTICIPANTS;
    private final TournamentService tournamentService;
    private final GameService gameService;


    public ShowParticipantsCommand(GameService gameService, TournamentService tournamentService) {
        this.gameService = gameService;
        this.tournamentService = tournamentService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            String sTournamentId = request.getParameter(AttributesContainer.ID.toString());
            TournamentDto tournament = tournamentService.find(Long.parseLong(sTournamentId));

            List<TournamentPlacement> placements = gameService.findTournamentPlacements(tournament);

            placements.sort(Comparator.comparingInt(TournamentPlacement::getPlacementNumber));
            request.setAttribute(AttributesContainer.PLACEMENTS.toString(), placements);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_PLACEMENTS));

        } catch (ServiceException e) {
            LOGGER.error("Unable to get players' list.", e);
            throw new ActionCommandExecutionException("Unable to get players' list.", e);
        }
    }

}
