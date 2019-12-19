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

public class ShowPlayerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowPlayerCommand.class);
    private final ActionCommandType type = ActionCommandType.SHOW_PLAYER;
    private final PlayerService playerService;
    private final TournamentService tournamentService;


    public ShowPlayerCommand(PlayerService playerService, TournamentService tournamentService) {
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

        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            PlayerDto playerDto = playerService.find(id);
            List<TournamentDto> tournaments = tournamentService.findAllOfPlayer(playerDto.getId());

            request.setAttribute(AttributesContainer.PLAYER.toString(), playerDto);
            request.setAttribute(AttributesContainer.TOURNAMENTS.toString(), tournaments);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_PLAYER_PROFILE_PAGE));

        } catch (ServiceException e) {
            LOGGER.error("Unable to get player with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get player with " + id + " id.", e);
        }

    }

}
