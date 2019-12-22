package by.training.command;

import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public class ChangeLanguageToRuCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.CHANGE_LANGUAGE_TO_RU;


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute(AttributesContainer.LANGUAGE.toString(),
                new Locale(AttributesContainer.RU.toString()));

        return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_INDEX));

    }


    @Override
    public ActionCommandType getType() {
        return type;
    }
}
