package by.training.common;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLanguageToEnCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.CHANGE_LANGUAGE_TO_EN;

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(AttributesContainer.LANGUAGE.toString(), AttributesContainer.EN.toString());
        return new HttpForwarder(PathsContainer.INDEX);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
