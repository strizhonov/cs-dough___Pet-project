package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToLoginPageCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_LOGIN_PAGE;

    public ActionCommandType getType() {
        return type;
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        return new ServletRouter("/jsp/login.jsp");
    }

}
