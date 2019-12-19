package by.training.organizer;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DeleteOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.DELETE_ORGANIZER;
    private final OrganizerService organizerService;


    public DeleteOrganizerCommand(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());

        try {
            if (organizerService.delete(user.getOrganizerId())) {
                user.setOrganizerId(0);

                return Optional.of(new HttpRedirector(request.getContextPath()
                        + PathsContainer.FILE_USER_PROFILE_PAGE));
            } else {

                LocalizationManager manager
                        = new LocalizationManager(AttributesContainer.I18N.toString(), request.getLocale());


                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(AttributesContainer.ORGANIZER_DELETING_ERROR.toString()));

                return Optional.of(new HttpRedirector(request.getContextPath()
                        + PathsContainer.COMMAND_SHOW_ORGANIZER + user.getOrganizerId()));

            }

            } catch (ServiceException e) {
            LOGGER.error("Unable to perform organizer deleting.", e);
            throw new ActionCommandExecutionException("Unable to perform organizer deleting.", e);
        }

    }

}
