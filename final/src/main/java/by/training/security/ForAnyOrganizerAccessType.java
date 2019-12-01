package by.training.security;

import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForAnyOragnizerTypeSupervisor implements SecuritySupervisor {


    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        if (user != null && user.getOrganizerId != 0) {
            return true;
        } 
        return false;
        
        
    @Override
    public Optional<BaseRedirector>(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        if (isAccessAllowed) {
            return Optional.empty();
        }
        if (user == null) {
        String alertMessage = 
        
        } else if (user.getOrganizerId == 0) {
        alertMessage = ;
        }
        
        return Optional.of(new Redirector("referer"));
    
}
