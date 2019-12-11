package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.OrganizerDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.SHOW_ORGANIZER;
    private final UserService userService;

    public ShowOrganizerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String sId = request.getParameter(AttributesContainer.ID.toString());
        long id = Long.parseLong(sId);
        try {
            OrganizerDto organizerDto = userService.findOrganizer(id);
            request.setAttribute(AttributesContainer.ORGANIZER.toString(), organizerDto);
            return new HttpForwarder(PathsContainer.ORGANIZER_PROFILE_PAGE);
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
