package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.ValuesContainer;
import by.training.servlet.AbsolutePathRedirector;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChangeLanguageToEnCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.CHANGE_LANGUAGE_TO_EN;

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(AttributesContainer.LANGUAGE.toString(), ValuesContainer.EN);
        return new AbsolutePathRedirector(request.getHeader(ValuesContainer.REFERER));
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
