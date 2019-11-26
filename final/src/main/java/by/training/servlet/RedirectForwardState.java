package by.training.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectForwardState implements ServletRouterState {

    private String path;

    public RedirectForwardState(String path) {
        this.path = path;
    }

    @Override
    public void dispatch(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }
}


