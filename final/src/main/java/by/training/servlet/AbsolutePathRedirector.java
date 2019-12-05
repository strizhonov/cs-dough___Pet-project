package by.training.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbsolutePathRedirector extends BaseRedirector {

    public AbsolutePathRedirector(String path) {
        super(path);
    }

    @Override
    public void dispatchIfNeed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }
}
