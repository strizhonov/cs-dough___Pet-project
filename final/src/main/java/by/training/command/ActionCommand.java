package by.training.command;

import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {

    HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException;

    ActionCommandType getType();
    
}
