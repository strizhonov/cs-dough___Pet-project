package by.training.command;

import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DefaultActionCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.DEFAULT_COMMAND;


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_INDEX));

    }


    @Override
    public ActionCommandType getType() {
        return type;
    }
}
