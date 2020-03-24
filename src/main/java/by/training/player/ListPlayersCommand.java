package by.training.player;

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
import java.util.List;
import java.util.Optional;

public class ListPlayersCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListPlayersCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_PLAYERS;
    private PlayerService playerService;


    public ListPlayersCommand(PlayerService playerService) {
        this.playerService = playerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {
            List<PlayerDto> players = playerService.findAll();
            request.setAttribute(AttributesContainer.PLAYERS.toString(), players);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_PLAYERS));

        } catch (ServiceException e) {
            LOGGER.error("Unable to get players' list.", e);
            throw new ActionCommandExecutionException("Unable to get players' list.", e);
        }

    }

}
