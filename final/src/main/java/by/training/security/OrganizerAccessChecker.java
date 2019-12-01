package by.training.security;

import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrganizerAccessChecker implements SecurityService {
    @Override
    public boolean canExecute(ActionCommand actionCommand, HttpServletRequest request) {
        return false;
    }

    @Override
    public ServletRouter route(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
