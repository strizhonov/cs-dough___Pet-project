package by.training.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RelativePathRedirector extends BaseRedirector {

    public RelativePathRedirector(String path) {
        super(path);
    }

    @Override
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }
}


