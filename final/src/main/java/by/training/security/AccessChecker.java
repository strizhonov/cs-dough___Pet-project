package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface AccessChecker {

    boolean isAccessAllowed(ActionCommand actionCommand, HttpServletRequest request);

}
