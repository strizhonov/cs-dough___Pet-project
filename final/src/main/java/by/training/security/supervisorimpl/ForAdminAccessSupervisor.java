package by.training.security.supervisorimpl;

import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.dto.UserDto;
import by.training.entity.User;
import by.training.security.AccessAllowedForType;
import by.training.servlet.BaseRedirector;
import by.training.servlet.RelativePathRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ForAdminAccessSupervisor extends BaseSecuritySupervisor {

    private static final String REDIRECT_TO = PathsContainer.ACCESS_DENIED;
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
    protected Optional<BaseRedirector> getOptionalRedirector(HttpServletRequest request, HttpServletResponse response) {
        return Optional.of(new RelativePathRedirector(REDIRECT_TO));
    }

}