package ru.mikaev.auth;

import ru.mikaev.auth.domain.User;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(urlPatterns = {AuthFilter.userPattern + "*", AuthFilter.adminPattern + "*"})
public class AuthFilter implements Filter {
    public static final String userPattern = "/user/";
    public static final String adminPattern = "/admin/";

    @Inject
    private AuthBean authBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        User.Role role = authBean.getRole();
        String uri = request.getRequestURI();

        if(role != null){
            String beginOfAdminUri = request.getContextPath() + adminPattern;
            if(uri.startsWith(beginOfAdminUri) && role != User.Role.ADMIN){
                response.sendRedirect(request.getContextPath() + "/errors.xhtml");
                return;
            }

            filterChain.doFilter(request, response);
            return;
        }

        authBean.setRequestedPage(uri);
        response.sendRedirect(request.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {

    }
}
