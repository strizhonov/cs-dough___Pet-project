package by.training.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletForwarder implements HttpRouter {

    private HttpServlet servlet;
    private String path;

    public ServletForwarder(HttpServlet servlet, String path) {
        this.servlet = servlet;
        this.path = path;
    }

    @Override
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
