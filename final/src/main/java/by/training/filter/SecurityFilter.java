package by.training.filter;

import by.training.appentry.ApplicationLoader;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandProvider;
import by.training.constant.AttributesContainer;
import by.training.security.AccessAllowedForType;
import by.training.security.SecurityDirector;
import by.training.security.SecurityProvider;
import by.training.servlet.BaseRedirector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter
public class SecurityFilter extends HttpFilter {

    private Logger LOGGER = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getParameter(AttributesContainer.COMMAND.toString()) != null) {
            ActionCommandProvider commandProvider
                    = (ActionCommandProvider) ApplicationLoader.getInstance().get(ActionCommandProvider.class);
            ActionCommand command = commandProvider.get((HttpServletRequest) request);

            SecurityProvider securityProvider = (SecurityProvider) ApplicationLoader.getInstance().get(SecurityProvider.class);

            SecurityDirector securityDirector = securityProvider.get(command);

            Optional<BaseRedirector> router = securityDirector.direct((HttpServletRequest) request, (HttpServletResponse) response);
            if (router.isPresent()) {
                LOGGER.warn("Command execution can not be performed due to the security reasons.");
                router.get().dispatch((HttpServletRequest) request, (HttpServletResponse) response);
            }

        }

        chain.doFilter(request, response);

    }
}
