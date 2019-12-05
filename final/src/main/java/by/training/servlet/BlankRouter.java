package by.training.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlankRouter implements HttpRouter {
    @Override
    public void dispatchIfNeed(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
