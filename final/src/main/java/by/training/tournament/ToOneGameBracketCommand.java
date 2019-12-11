package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.game.GameDto;
import by.training.game.GameService;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ToOneGameBracketCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToOneGameBracketCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_ONE_GAME_BRACKET;
    private final TournamentService tournamentService;
    private final GameService gameService;

    public ToOneGameBracketCommand(TournamentService tournamentService, GameService gameService) {
        this.tournamentService = tournamentService;
        this.gameService = gameService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            TournamentDto tournamentDto = tournamentService.find(id);
            request.setAttribute(AttributesContainer.TOURNAMENT.toString(), tournamentDto);
            List<GameDto> games = gameService.getAllOfTournament(tournamentDto.getId());
            GameDto finalGame = games.get(0);
            request.setAttribute(AttributesContainer.FINAL_GAME.toString(), finalGame);
            return new HttpForwarder(PathsContainer.ONE_GAME_BRACKET_PAGE);
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
