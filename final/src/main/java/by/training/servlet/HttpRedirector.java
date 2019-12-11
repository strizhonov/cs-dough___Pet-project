package by.training.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpRedirector implements HttpRouter {

    private final String path;

    public HttpRedirector(String path) {
        this.path = path;
    }

    @Override
    public void dispatchIfNeed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);

    }
}


