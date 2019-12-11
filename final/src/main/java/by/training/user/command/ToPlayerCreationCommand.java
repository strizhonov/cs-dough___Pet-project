package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToPlayerCreationCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_PLAYER_CREATION;

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        return new HttpForwarder(PathsContainer.PLAYER_CREATION);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
