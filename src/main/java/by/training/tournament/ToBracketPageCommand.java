package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.game.ComplexGameDto;
import by.training.game.GameService;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ToBracketPageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToBracketPageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_BRACKET_PAGE;
    private final GameService gameService;


    public ToBracketPageCommand(GameService gameService) {
        this.gameService = gameService;
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

            List<ComplexGameDto> games = gameService.findAllOfTournament(id);
            request.setAttribute(AttributesContainer.GAMES.toString(), games);

            if (games.isEmpty()) {
                throw new ActionCommandExecutionException("Unable to get tournament's games.");
            }

            return routeDependOnSize(games.get(0).getTournament());

        } catch (ServiceException e) {
            LOGGER.error("Unable to get tournament with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get tournament with " + id + " id.", e);
        }
    }


    private Optional<HttpRouter> routeDependOnSize(PlainTournamentDto tournament) {

        switch (tournament.getPlayersNumber()) {
            case 2:
                return Optional.of(new HttpForwarder(PathsContainer.FILE_ONE_GAME_BRACKET_PAGE));
            case 4:
                return Optional.of(new HttpForwarder(PathsContainer.FILE_THREE_GAME_BRACKET_PAGE));
            case 8:
                /* For future impls */
                return Optional.of(new HttpForwarder(PathsContainer.FILE_SEVEN_GAME_BRACKET_PAGE));
            default:
                throw new IllegalStateException("There is no option of "
                        + tournament.getPlayersNumber() + " games bracket.");
        }

    }


}
