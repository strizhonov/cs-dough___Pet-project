package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class AllAccessChecker implements AccessChecker {
    @Override
    public boolean isAccessAllowed(ActionCommand actionCommand, HttpServletRequest request) {
        return true;
    }
}
