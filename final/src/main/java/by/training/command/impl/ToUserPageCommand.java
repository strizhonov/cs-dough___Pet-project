package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.dto.UserDto;
import by.training.service.ServiceException;
import by.training.service.UserService;
import by.training.servlet.HttpRouter;
import by.training.servlet.ServletForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToUserPageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ListTournamentsCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_USER_PAGE;

    private UserService userService;

    public ToUserPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            UserDto toShow = userService.find(id);
            request.getSession().setAttribute(AttributesContainer.USER_TO_PROCESS.toString(), toShow);
            return new ServletForwarder(servlet, "/jsp/game-page.jsp");
        } catch (ServiceException e) {
            LOGGER.error("Unable to get user with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get user with " + id + " id.", e);
        }
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
