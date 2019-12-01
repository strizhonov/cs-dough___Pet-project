package by.training.security;

import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForAnyOrganizerAccessTypeSupervisor implements SecuritySupervisor {


    @Override
    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        return user != null && user.getOrganizerId() != 0;
    }
    
    @Override
    public Optional<BaseRedirector> direct(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        if (isAccessAllowed) {
            return Optional.empty();
        }
        if (user != null) {
        String message = ;
        } else if (user.getOrganizerId() != 0;){
         message = 
         return Redirect;
        }
    
}