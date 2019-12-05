package by.training.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpRouter {

    void dispatchIfNeed(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    
}
