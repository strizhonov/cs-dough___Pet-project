package by.training.security.supervisorimpl;

import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpRespondent;
import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ForAnyAccessDirector extends BaseSecurityDirector {

    private final AccessAllowedForType type = AccessAllowedForType.ANY;

    @Override
    public AccessAllowedForType getType() {
        return type;
    }

    @Override
    protected boolean isAccessAllowed(HttpServletRequest request) {
        return true;
    }

    @Override
    protected HttpRouter getHttpRouter(HttpServletRequest request, HttpServletResponse response) {
        return new HttpRespondent();
    }
}
