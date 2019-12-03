package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.RelativePathRedirector;
import by.training.servlet.ServletForwarder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultActionCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.DEFAULT_COMMAND;

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        return new RelativePathRedirector(PathsContainer.INDEX);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
