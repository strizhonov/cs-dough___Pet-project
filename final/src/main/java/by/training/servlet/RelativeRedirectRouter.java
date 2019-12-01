package by.training.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RelativeRedirectRouter implements HttpRouter {

    private String path;

    public RelativeRedirectRouter(String path) {
        this.path = path;
    }

    @Override
    public void dispatch(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }
}


