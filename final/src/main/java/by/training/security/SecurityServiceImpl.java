package by.training.security;

import by.training.appentry.Bean;
import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Bean
public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean canExecute(ActionCommand command, HttpServletRequest request) {
        AccessChecker checker = command.getType().getSecurityType().getCheckerClass().getInstance();
        return checker.isAccessAllowed(actionCommand, request);

    }

    @Override
    public ServletRouter route(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private AccessChecker createFromCommand(ActionCommand actionCommand) {

    }
}
