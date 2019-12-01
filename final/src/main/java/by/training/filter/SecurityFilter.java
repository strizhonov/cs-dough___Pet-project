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
                    = (ActionCommandProvider) ApplicationLoader.getInstance().get(ActionCommandProvider.class);
            ActionCommand command = commandProvider.get((HttpServletRequest) request);
            
            SecurityProvider securityProvider
                    = (SecurityProvider) ApplicationLoader.getInstance().get(SecurityProvider.class);
            SecuritySupervisor supervisor = securityProvider.get(command);
            
            Optional<BaseRedirectRouter> router = supervisor.direct(command, request, response);
            if (router.isPresent()) {
                LOGGER.warn("Command execution can not be performed due to the security reasons.");
                router.get().dispatch(request, response);
            }
        
        }
        
        chain.doFilter(request, response);

    }
}
