package com.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/** Logs "METHOD /uri  (time ms)" for every request. Mapped to /* in web.xml. */
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        long start = System.currentTimeMillis();

        chain.doFilter(request, response);                   // ---- servlet runs here ----

        long took = System.currentTimeMillis() - start;      // code AFTER chain = response path
        System.out.println(req.getMethod() + " " + req.getRequestURI() + "  (" + took + " ms)");
    }
}
