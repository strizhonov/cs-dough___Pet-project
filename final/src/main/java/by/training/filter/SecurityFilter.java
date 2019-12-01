package by.training.filter;

import by.training.appentry.ApplicationLoader;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandProvider;
import by.training.command.ActionCommandProviderImpl;
import by.training.security.SecurityService;
import by.training.security.SecurityServiceImpl;
import by.training.servlet.ServletRouter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (request.getParameter(AttributesContainer.COMMAND) != null) {
            ActionCommandProvider commandProvider
                    = (ActionCommandProvider) ApplicationLoader.getInstance().get(ActionCommandProviderImpl.class);
            ActionCommand command = commandProvider.get((HttpServletRequest) request);
            
            SecuritySupervisor supervisor = command.getType().getSecurityType().getSupervisorClass().getInstance();
            
            if (!supervisor.canExecute(command, request)) {
                BaseRedirectRouter router = supervisor.direct(command, request, response);
                LOGGER.warn("Command execution can not be performed due to the security reasons.");
                router.dispatch(request, response);
            }

        }
        chain.doFilter(request, response);

    }
}
