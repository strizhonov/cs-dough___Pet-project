package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.PlayerDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListPlayersCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListPlayersCommand.class);
    private final ActionCommandType type = ActionCommandType.LIST_PLAYERS;
    private UserService service;

    public ListPlayersCommand(UserService service) {
        this.service = service;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        try {
            List<PlayerDto> players = service.findAllPlayers();
            request.setAttribute(AttributesContainer.PLAYERS.toString(), players);

            return new HttpForwarder(PathsContainer.PLAYERS);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get players' list.", e);
            throw new ActionCommandExecutionException("Unable to get players' list.", e);
        }

    }

    public ActionCommandType getType() {
        return type;
    }
}
