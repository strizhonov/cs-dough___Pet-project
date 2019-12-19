package by.training.command;

import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ChangeLanguageToEnCommand implements ActionCommand {


    private final ActionCommandType type = ActionCommandType.CHANGE_LANGUAGE_TO_EN;


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(AttributesContainer.LANGUAGE.toString(), AttributesContainer.EN.toString());

        return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_INDEX));
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }
}
