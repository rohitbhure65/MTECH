package com.example.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** forward: server-side jump. URL in browser stays /forward-demo. Request attributes survive. */
@WebServlet("/forward-demo")
public class ForwardDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("msg", "Data set in ForwardDemoServlet");   // travels with the request
        RequestDispatcher rd = request.getRequestDispatcher("/target");
        rd.forward(request, response);
        // Do NOT write anything to 'response' after forward()
    }
}
