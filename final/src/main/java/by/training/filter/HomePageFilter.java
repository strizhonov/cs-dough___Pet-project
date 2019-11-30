package by.training.filter;

import by.training.constant.ValuesContainer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "protection_name",
        value = ValuesContainer.XSS_PROTECTION_NAME), @WebInitParam(name = "protection_type",
        value = ValuesContainer.XSS_PROTECTION_TYPE)})
public class HomePageFilter implements Filter {

    private String headerName;
    private String headerValue;

    @Override
    public void init(FilterConfig config) {
        headerName = config.getInitParameter("protection_name");
        headerValue = config.getInitParameter("protection_type");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse) response).setHeader(headerName, headerValue);
        String test1 = ((HttpServletRequest) request).getContextPath();

        String test2 = ((HttpServletRequest) request).getRequestURI();

        String test3 = ((HttpServletRequest) request).getPathInfo();



        chain.doFilter(request, response);
    }

}