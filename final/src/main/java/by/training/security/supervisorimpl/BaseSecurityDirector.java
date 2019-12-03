package by.training.security.supervisorimpl;

import by.training.security.AccessAllowedForType;
import by.training.security.SecurityDirector;
import by.training.servlet.BaseRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public abstract class BaseSecurityDirector implements SecurityDirector {

    @Override
    public Optional<BaseRedirector> direct(HttpServletRequest request, HttpServletResponse response) {
        return isAccessAllowed(request) ? Optional.empty() : getOptionalRedirector(request, response);
    }

    protected abstract boolean isAccessAllowed(HttpServletRequest request);

    protected abstract Optional<BaseRedirector> getOptionalRedirector(HttpServletRequest request, HttpServletResponse response);

}
