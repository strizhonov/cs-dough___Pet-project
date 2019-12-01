package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class ForAllAccessSupervisor implements SecuritySupervisor {
    
    @Override
    public boolean isAccessAllowed(ActionCommand actionCommand, HttpServletRequest request) {
        return true;
    }
    
    @Override
    public Optional<BaseRedirector> direct(ActionCommand actionCommand, HttpServletRequest request, HttpServletResponse response) {
        return Optional.empty();
    }
}
