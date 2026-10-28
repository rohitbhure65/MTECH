package com.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

/** Sets UTF-8 on every request so Hindi / special characters in forms are not garbled. */
public class EncodingFilter implements Filter {
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig config) throws ServletException {
        String e = config.getInitParameter("encoding");     // <init-param> in web.xml
        if (e != null) encoding = e;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);              // BEFORE the servlet reads parameters
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);                   // pass control to the next filter / servlet
    }
}
