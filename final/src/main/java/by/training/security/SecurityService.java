package by.training.security;


import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AccessManager {

    boolean canExecute(ActionCommand actionCommand, HttpServletRequest request);

    ServletRouter route(ActionCommand command, HttpServletRequest request, HttpServletResponse response);

}
