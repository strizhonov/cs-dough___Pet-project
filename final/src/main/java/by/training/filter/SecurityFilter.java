package by.training.filter;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandProvider;
import by.training.command.ActionCommandProviderImpl;
import by.training.core.ApplicationContext;
import by.training.resourse.AttributesContainer;
import by.training.security.SecurityDirector;
import by.training.security.SecurityProvider;
import by.training.security.SecurityProviderImpl;
import by.training.servlet.HttpRouter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter extends HttpFilter {


    @Override
    public void init(FilterConfig config) {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getParameter(AttributesContainer.COMMAND.toString()) != null) {

            ActionCommandProvider commandProvider
                    = (ActionCommandProvider) ApplicationContext.getInstance().get(ActionCommandProviderImpl.class);
            ActionCommand command = commandProvider.get((HttpServletRequest) request);


            SecurityProvider securityProvider
                    = (SecurityProvider) ApplicationContext.getInstance().get(SecurityProviderImpl.class);
            SecurityDirector securityDirector = securityProvider.get(command);


            Optional<HttpRouter> router = securityDirector.direct((HttpServletRequest) request,
                    (HttpServletResponse) response);

            if (router.isPresent()) {
                router.get().dispatch((HttpServletRequest) request, (HttpServletResponse) response);
                return;
            }
        }

        chain.doFilter(request, response);

    }


    @Override
    public void destroy() {

    }


}
