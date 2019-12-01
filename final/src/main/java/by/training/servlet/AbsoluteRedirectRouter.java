package by.training.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbsoluteRedirectRouter implements HttpRouter {

    private String path;

    public AbsoluteRedirectRouterState(String path) {
        this.path = path;
    }

    @Override
    public void proceed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(path);
    }
}
