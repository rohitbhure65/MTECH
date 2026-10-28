package com.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** include: output of another resource is inserted INTO this response; control comes back. */
@WebServlet("/include-demo")
public class IncludeDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        request.getRequestDispatcher("/header").include(request, response);   // part 1 from HeaderServlet
        response.getWriter().println("<p>This text is from IncludeDemoServlet.</p>");   // we continue after include
        response.getWriter().println("</body></html>");
    }
}
