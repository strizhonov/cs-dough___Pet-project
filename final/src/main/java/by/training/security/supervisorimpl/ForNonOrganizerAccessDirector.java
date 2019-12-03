package by.training.security.supervisorimpl;

import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.dto.UserDto;
import by.training.security.AccessAllowedForType;
import by.training.servlet.BaseRedirector;
import by.training.servlet.RelativePathRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ForNonOrganizerAccessDirector extends BaseSecurityDirector {

    private static final String REDIRECT_USER_TO = PathsContainer.ACCESS_DENIED;
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
    protected Optional<BaseRedirector> getOptionalRedirector(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null
                ? Optional.of(new RelativePathRedirector(REDIRECT_USER_TO))
                : Optional.of(new RelativePathRedirector(REDIRECT_NON_USER_TO));
    }

}