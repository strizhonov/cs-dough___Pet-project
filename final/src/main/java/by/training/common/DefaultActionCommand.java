package by.training.common;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultActionCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.DEFAULT_COMMAND;

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        return new HttpForwarder(PathsContainer.INDEX);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
