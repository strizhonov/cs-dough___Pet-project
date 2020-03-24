package by.training.security.directorimpl;

import by.training.security.SecurityDirector;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public abstract class BaseSecurityDirector implements SecurityDirector {

    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response) {
        return isAccessAllowed(request) ? Optional.empty() : getHttpRouter(request, response);
    }

    protected abstract boolean isAccessAllowed(HttpServletRequest request);


    protected abstract Optional<HttpRouter> getHttpRouter(HttpServletRequest request, HttpServletResponse response);

}
