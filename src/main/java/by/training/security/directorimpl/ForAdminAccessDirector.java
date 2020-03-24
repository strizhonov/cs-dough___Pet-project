package by.training.security.directorimpl;

import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.User;
import by.training.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ForAdminAccessDirector extends BaseSecurityDirector {

    private static final String REDIRECT_TO = PathsContainer.FILE_ACCESS_DENIED;
    private final AccessAllowedForType type = AccessAllowedForType.ADMIN;

    @Override
    public AccessAllowedForType getType() {
        return type;
    }


    @Override
    protected boolean isAccessAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        return user != null && user.getType() == User.UserType.ADMIN;
    }


    @Override
    protected Optional<HttpRouter> getHttpRouter(HttpServletRequest request, HttpServletResponse response) {
        return Optional.of(new HttpRedirector(request.getContextPath() + REDIRECT_TO));
    }

}