package by.training.security.supervisorimpl;

import by.training.security.SecurityDirector;
import by.training.servlet.HttpRespondent;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseSecurityDirector implements SecurityDirector {

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) {
        return isAccessAllowed(request) ? new HttpRespondent() : getHttpRouter(request, response);
    }

    protected abstract boolean isAccessAllowed(HttpServletRequest request);

    protected abstract HttpRouter getHttpRouter(HttpServletRequest request, HttpServletResponse response);

}
