package by.training.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardRouterState implements ServletRouterState {

    private String path;

    public ForwardRouterState(String path) {
        this.path = path;
    }

    @Override
    public void dispatch(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = servlet.getServletContext();
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
