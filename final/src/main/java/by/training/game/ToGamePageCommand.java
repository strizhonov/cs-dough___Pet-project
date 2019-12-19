package by.training.game;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ToGamePageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToGamePageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_GAME_PAGE;
    private final GameService gameService;


    public ToGamePageCommand(GameService gameService) {
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
        long gameId = Long.parseLong(sId);

        try {

            ComplexGameDto game = gameService.find(gameId);
            request.setAttribute(AttributesContainer.GAME.toString(), game);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_GAME_PAGE));

        } catch (ServiceException e) {
            LOGGER.error("Unable to execute command.", e);
            throw new ActionCommandExecutionException("Unable to execute command.", e);
        }
    }

}
