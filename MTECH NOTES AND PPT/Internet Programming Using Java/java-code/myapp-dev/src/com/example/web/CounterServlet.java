package com.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Simplest session example: a visit counter that belongs to ONE browser session. */
@WebServlet("/counter")
public class CounterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();              // creates one if needed
        Integer count = (Integer) session.getAttribute("count");
        count = (count == null) ? 1 : count + 1;
        session.setAttribute("count", count);

        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("Session id : " + session.getId());
        response.getWriter().println("New session?: " + session.isNew());
        response.getWriter().println("Visit count: " + count);
        response.getWriter().println("(URL rewriting form: " + response.encodeURL("counter") + ")");
    }
}
