package by.training.filter;

import by.training.core.ApplicationContext;
import by.training.resourse.AppSetting;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class XSSProtectionFilter extends HttpFilter {

    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);


    @Override
    public void init(FilterConfig config) {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ((HttpServletResponse) response).setHeader(setting.get(AppSetting.SettingName.XSS_PROTECTION_NAME),
                setting.get(AppSetting.SettingName.XSS_PROTECTION_TYPE));

        chain.doFilter(request, response);

    }


    @Override
    public void destroy() {

    }

}