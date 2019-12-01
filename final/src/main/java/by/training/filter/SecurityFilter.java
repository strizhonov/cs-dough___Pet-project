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

    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        securityService = new SecurityServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (request.getParameter("command") != null) {
            ActionCommandProvider commandProvider
                    = (ActionCommandProvider) ApplicationLoader.getInstance().get(ActionCommandProviderImpl.class);
            ActionCommand command = commandProvider.get((HttpServletRequest) request);

            if (!securityService.canExecute(command, request)) {
                ServletRouter router = securityService.route(command, request, response);
                LOGGER.warn("Command execution can not be performed due to the security reasons.");
                router.getState().dispatch(this, request, response);
                return;
            }
            if (!securityService.canExecute(command, request)) {
                BaseRedirectRouter router = securityService.route(command, request);
                router.dispatch(request, responce);
            }

        }
        chain.doFilter(request, response);

    }
}
