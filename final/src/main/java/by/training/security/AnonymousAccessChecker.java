package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class AnonymousAccessChecker implements AccessChecker {
@Override
    public boolean isAccessAllowed(ActionCommand actionCommand, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
        return user == null;
    }

}