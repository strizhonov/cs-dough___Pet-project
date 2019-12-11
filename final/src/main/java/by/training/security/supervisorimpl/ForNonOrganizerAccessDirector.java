package by.training.security.supervisorimpl;

import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ForNonOrganizerAccessDirector extends BaseSecurityDirector {

    private static final String REDIRECT_USER_TO = PathsContainer.SHOW_ORGANIZER;
    private static final String REDIRECT_NON_USER_TO = PathsContainer.LOGIN;
    private final AccessAllowedForType type = AccessAllowedForType.NON_ORGANIZER_USERS;

    @Override
    public AccessAllowedForType getType() {
        return type;
    }

    @Override
    protected boolean isAccessAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null && user.getOrganizerId() == 0;
    }

    @Override
    protected HttpRouter getHttpRouter(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null
                ? new HttpRedirector(request.getContextPath() + REDIRECT_USER_TO + user.getOrganizerId())
                : new HttpForwarder(REDIRECT_NON_USER_TO);
    }

}