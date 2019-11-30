package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultActionCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.DEFAULT_COMMAND;

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        return new ServletRouter();
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
