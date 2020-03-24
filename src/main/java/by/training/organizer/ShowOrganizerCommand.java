package by.training.organizer;

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

public class ShowOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.SHOW_ORGANIZER;
    private final OrganizerService organizerService;


    public ShowOrganizerCommand(OrganizerService organizerService) {
        this.organizerService = organizerService;
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

            OrganizerDto organizerDto = organizerService.find(id);
            request.setAttribute(AttributesContainer.ORGANIZER.toString(), organizerDto);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_ORGANIZER_PROFILE_PAGE));

        } catch (ServiceException e) {
            LOGGER.error("Unable to get organizer with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get organizer with " + id + " id.", e);
        }

    }

}
