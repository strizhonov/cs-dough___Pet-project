package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class AllAccessState implements AccessState {
    @Override
    public boolean canExecute(ActionCommand actionCommand, HttpServletRequest request) {
        return true;
    }
}
