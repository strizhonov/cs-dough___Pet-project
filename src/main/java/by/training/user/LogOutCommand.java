package by.training.user;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LogOutCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.LOG_OUT;


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        request.getSession().setAttribute(AttributesContainer.USER.toString(), null);

        return Optional.of(new HttpForwarder(PathsContainer.FILE_HOME));
    }

}
