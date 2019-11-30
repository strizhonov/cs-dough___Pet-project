package by.training.security;

import by.training.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public interface AccessState {

    boolean canExecute(ActionCommand actionCommand, HttpServletRequest request);

}
