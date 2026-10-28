package com.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Receives the forward / redirect. */
@WebServlet("/target")
public class TargetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Object msg = request.getAttribute("msg");
        response.getWriter().println("URL shown in browser : " + request.getRequestURI());
        response.getWriter().println("request attribute msg: " + (msg == null ? "(null - attribute was lost)" : msg));
    }
}
