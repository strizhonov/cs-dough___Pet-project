package by.training.security.supervisorimpl;

import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ForAnyPlayerAccessDirector extends BaseSecurityDirector {

    private static final String REDIRECT_USER_TO = PathsContainer.PLAYER_CREATION;
    private static final String REDIRECT_NON_USER_TO = PathsContainer.LOGIN;
    private final AccessAllowedForType type = AccessAllowedForType.PLAYER;

    @Override
    public AccessAllowedForType getType() {
        return type;
    }

    @Override
    protected boolean isAccessAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null && user.getPlayerAccountId() != 0;
    }

    @Override
    protected HttpRouter getHttpRouter(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null
                ? new HttpRedirector(request.getContextPath() + REDIRECT_USER_TO)
                : new HttpRedirector(request.getContextPath() + REDIRECT_NON_USER_TO);
    }

}