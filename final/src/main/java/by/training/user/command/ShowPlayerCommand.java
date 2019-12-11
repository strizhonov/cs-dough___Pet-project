package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.common.ServiceException;
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

public class ShowPlayerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowPlayerCommand.class);
    private final ActionCommandType type = ActionCommandType.SHOW_PLAYER;
    private final UserService userService;

    public ShowPlayerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            PlayerDto playerDto = userService.findPlayer(id);
            request.setAttribute(AttributesContainer.PLAYER.toString(), playerDto);
            return new HttpForwarder(PathsContainer.PLAYER_PROFILE_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get player with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get player with " + id + " id.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
