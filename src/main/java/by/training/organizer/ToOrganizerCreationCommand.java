package by.training.organizer;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created for security reasons as security service filters only request with "command" parameter
 */
public class ToOrganizerCreationCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_ORGANIZER_CREATION;

    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_ORGANIZER_CREATION));

    }

}
