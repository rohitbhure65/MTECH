package com.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Protects private pages: no logged-in user in the session -> redirect to /login. */
@WebFilter(urlPatterns = {"/dashboard", "/students", "/students/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);          // false = do NOT create a new one
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        if (loggedIn) {
            res.setHeader("Cache-Control", "no-store");       // back button after logout shows nothing
            chain.doFilter(request, response);                // allowed -> continue
        } else {
            res.sendRedirect(req.getContextPath() + "/login");  // blocked -> servlet never runs
        }
    }
}
