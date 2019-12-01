package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class ForAdminAccessSupervisor implements SecuritySupervisor {
    
    @Override
    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        return user != null && user.getType == User.UserType.ADMIN;
    }
    
    @Override
    public Optional<BaseRedirectRouter> direct(ActionCommand actionCommand, HttpServletRequest request, HttpServletResponse response) {
        return isAccessAllowed(command, request) ? Optional.of(new RelativeRedirectRouter(denied_page)) : Optional.empty();
    }

}