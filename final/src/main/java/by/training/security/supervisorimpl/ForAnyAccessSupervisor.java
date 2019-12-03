package by.training.security.supervisorimpl;

import by.training.security.AccessAllowedForType;
import by.training.servlet.BaseRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ForAnyAccessSupervisor extends BaseSecuritySupervisor {

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
    protected Optional<BaseRedirector> getOptionalRedirector(HttpServletRequest request, HttpServletResponse response) {
        return Optional.empty();
    }
}
