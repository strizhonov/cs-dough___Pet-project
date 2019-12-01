package by.training.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface RouterState {
    void proceed(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
