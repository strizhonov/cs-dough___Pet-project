package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.ValuesContainer;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChangeLanguageToEnCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.CHANGE_LANGUAGE_TO_EN;

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(AttributesContainer.LANGUAGE.toString(), ValuesContainer.EN);
        return new ServletRouter(request.getHeader(ValuesContainer.REFERER), ServletRouter.RouterType.ABSOLUTE_REDIRECT);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
