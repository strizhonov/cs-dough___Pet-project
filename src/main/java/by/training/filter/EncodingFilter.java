package by.training.filter;

import by.training.core.ApplicationContext;
import by.training.resourse.AppSetting;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class EncodingFilter extends HttpFilter {

    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);


    @Override
    public void init(FilterConfig fConfig) {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String standardCharset = setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME);

        String requestEncoding = request.getCharacterEncoding();
        if (!standardCharset.equalsIgnoreCase(requestEncoding)) {
            request.setCharacterEncoding(standardCharset);
        }

        String responseEncoding = response.getCharacterEncoding();
        if (!standardCharset.equalsIgnoreCase(responseEncoding)) {
            response.setCharacterEncoding(standardCharset);
        }

        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }

}