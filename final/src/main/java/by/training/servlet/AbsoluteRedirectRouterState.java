package by.training.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbsoluteRedirectRouterState implements ServletRouterState {

    private String path;

    public AbsoluteRedirectRouterState(String path) {
        this.path = path;
    }

    @Override
    public void dispatch(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(path);
    }
}
