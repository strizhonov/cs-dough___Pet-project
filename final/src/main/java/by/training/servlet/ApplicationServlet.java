package by.training.servlet;

import by.training.appentry.ApplicationLoader;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandProvider;
import by.training.command.ActionCommandProviderImpl;
import by.training.security.SecurityService;
import by.training.security.SecurityServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommandProvider commandProvider
                = (ActionCommandProvider) ApplicationLoader.getInstance().get(ActionCommandProviderImpl.class);
        ActionCommand command = commandProvider.get(request);

        SecurityService securityService
                = (SecurityService) ApplicationLoader.getInstance().get(SecurityServiceImpl.class);



        try {
            ServletRouter router = command.execute(request, response);
            router.getState().dispatch(this, request, response);
        } catch (ActionCommandExecutionException e) {
            LOGGER.error("Unable to execute command " + command.getClass().getSimpleName() + ".", e);
            throw new IllegalStateException("Unable to execute command " + command.getClass().getSimpleName() + ".", e);
        }
    }

}
