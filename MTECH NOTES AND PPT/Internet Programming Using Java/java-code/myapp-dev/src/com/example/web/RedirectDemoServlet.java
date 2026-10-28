package com.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** sendRedirect: tells the BROWSER to make a brand-new request. URL changes; request attributes are lost. */
@WebServlet("/redirect-demo")
public class RedirectDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("msg", "This will NOT reach /target after a redirect");
        response.sendRedirect(request.getContextPath() + "/target");   // 302 + Location header
    }
}
