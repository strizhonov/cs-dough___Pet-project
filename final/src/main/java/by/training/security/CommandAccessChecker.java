package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface CommandAccessChecker {

    boolean isAccessAllowed(ActionCommand actionCommand, HttpServletRequest request);

}
