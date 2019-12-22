package by.training.security.directorimpl;

import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.User;
import by.training.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

public class ForNonPlayerUserAccessDirector extends BaseSecurityDirector {

    private static final String REDIRECT_USER_TO = PathsContainer.COMMAND_SHOW_PLAYER;
    private static final String REDIRECT_NON_USER_TO = PathsContainer.FILE_LOGIN;
    private static final String MESSAGE_FOR_USER_KEY = AttributesContainer.PLAYER_EXISTS.toString();
    private static final String MESSAGE_FOR_NON_USER_KEY = AttributesContainer.LOGIN_FIRST.toString();


    private final AccessAllowedForType type = AccessAllowedForType.NON_PLAYER_USERS;


    @Override
    public AccessAllowedForType getType() {
        return type;
    }


    @Override
    protected boolean isAccessAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null && user.getPlayerAccountId() == 0 || user != null && user.getType() == User.UserType.ADMIN;
    }


    @Override
    protected Optional<HttpRouter> getHttpRouter(HttpServletRequest request, HttpServletResponse response) {
        LocalizationManager localizationManager = new LocalizationManager(AttributesContainer.I18N.toString(),
                (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));

        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());

        if (user != null) {
            request.setAttribute(AttributesContainer.MESSAGE.toString(),
                    localizationManager.getValue(MESSAGE_FOR_USER_KEY));
            return Optional.of(
                    new HttpRedirector(request.getContextPath() + REDIRECT_USER_TO + user.getPlayerAccountId()));

        } else {
            request.setAttribute(AttributesContainer.MESSAGE.toString(),
                    localizationManager.getValue(MESSAGE_FOR_NON_USER_KEY));
            return Optional.of(new HttpForwarder(REDIRECT_NON_USER_TO));
        }
    }

}