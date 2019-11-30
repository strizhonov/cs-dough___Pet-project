package by.training.security;

import by.training.appentry.Bean;
import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Bean
public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean canExecute(ActionCommand actionCommand, HttpServletRequest request) {
        AccessState state = createFromCommand(actionCommand);
        return state.canExecute(actionCommand, request);
    }

    @Override
    public ServletRouter route(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private AccessState createFromCommand(ActionCommand actionCommand) {
        switch (actionCommand.getType().getSecurityType()) {
            case ALL:
                return new AllAccessState();
            default:
                return new AllAccessState();
        }
    }
}
