package by.training.security;

import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForOrganizerAccessChecker implements AccessChecker {


    @Override
    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        OrganizerDto organizerToOperate = (OrganizerDto) session.getAttribute(AttributesContainer.ORGANIZER_TO_OPERATE.toString());
        return user != null && user.getOrganizerId() == organizerToOperate.getId();
        }
    
}