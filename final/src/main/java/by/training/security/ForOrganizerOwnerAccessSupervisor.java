package by.training.security;

import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForOrganizerOwnerAccessSupervisor implements SecuritySupervisor {


    @Override
    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        String sOrganizerId = request.getParameter(AttributesContainer.ORGANIZER_TO_OPERATE_ID.toString());
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        OrganizerDto organizerToOperate = (OrganizerDto) session.getAttribute(AttributesContainer.ORGANIZER_TO_OPERATE.toString());
        long organizerId = sOrganizerId != null ? Long.parse(sOrganizerId) : organizerToOperate != null ? organizerToOperate.getId : 0;              
        return user != null && user.getOrganizerId() == organizerId();
    }
        
    @Override
    public Optional<BaseRedirector> direct(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        String sOrganizerId = request.getParameter(AttributesContainer.ORGANIZER_TO_OPERATE_ID.toString());
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        OrganizerDto organizerToOperate = (OrganizerDto) session.getAttribute(AttributesContainer.ORGANIZER_TO_OPERATE.toString());
        long organizerId = sOrganizerId != null ? Long.parse(sOrganizerId) : organizerToOperate != null ? organizerToOperate.getId : 0;  
        if (isAccessAllowed()) {
            return Optional.empty();
        }
        if (user == null) {
           String message = 
           req.setParameter(message)
        } else if (user.getOrganizerId != organizerId) {
            String message = 
        }
        return Optional.of(new AbsoluteRedirector("Referer"));
    }
    
}