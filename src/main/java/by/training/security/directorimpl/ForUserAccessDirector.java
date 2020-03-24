package by.training.security.directorimpl;

import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

public class ForUserAccessDirector extends BaseSecurityDirector {

    private static final String REDIRECT_TO = PathsContainer.FILE_LOGIN;
    private static final String MESSAGE_KEY = AttributesContainer.LOGIN_FIRST.toString();
    private final AccessAllowedForType type = AccessAllowedForType.USER;


    @Override
    protected boolean isAccessAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null;
    }


    @Override
    protected Optional<HttpRouter> getHttpRouter(HttpServletRequest request, HttpServletResponse response) {
        LocalizationManager localizationManager = new LocalizationManager(AttributesContainer.I18N.toString(),
                (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));

        request.setAttribute(AttributesContainer.MESSAGE.toString(),
                localizationManager.getValue(MESSAGE_KEY));
        return Optional.of(new HttpForwarder(REDIRECT_TO));
    }


    @Override
    public AccessAllowedForType getType() {
        return type;
    }
}