package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class ForAnonymousAccessSupervisor implements SecuritySupervisor {
    
    @Override
    public boolean isAccessAllowed(ActionCommand actionCommand, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        return user == null;
    }
    
    @Override
    public Optional<BaseRedirector> direct(ActionCommand actionCommand, HttpServletRequest request) {
        return isAccessAllowed : Optional.empty() : Optional.of(new RelativeRedirector(response.getHeader("Referer")));
    }

}