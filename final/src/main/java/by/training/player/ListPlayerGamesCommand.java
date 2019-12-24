package by.training.player;

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

public class ListPlayerGamesCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListPlayerGamesCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_PLAYER_GAMES;
    private final GameService gameService;
    private final PlayerService playerService;


    public ListPlayerGamesCommand(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
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

            PlayerDto player = playerService.find(id);
            request.setAttribute(AttributesContainer.PLAYER.toString(), player);

            List<ComplexGameDto> games = gameService.findAllOfPlayer(id);
            request.setAttribute(AttributesContainer.GAMES.toString(), games);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_PLAYER_GAMES));

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform games listing.", e);
            throw new ActionCommandExecutionException("Unable to perform games listing.", e);
        }


    }

}
