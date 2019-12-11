package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpRedirector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.LOG_OUT;

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(AttributesContainer.USER.toString(), null);
        return new HttpForwarder(PathsContainer.HOME);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
