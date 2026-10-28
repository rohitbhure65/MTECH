package com.example.auth;

import com.example.listener.AppListener;
import com.example.model.User;
import com.example.util.Html;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/** Private page. AuthFilter guarantees a user is in the session before we get here. */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Integer visits = (Integer) session.getAttribute("visits");   // per-user counter
        visits = (visits == null) ? 1 : visits + 1;
        session.setAttribute("visits", visits);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Dashboard</title></head><body>");

        request.getRequestDispatcher("/header").include(request, response);   // INCLUDE shared menu

        out.println("<h2>Welcome, " + Html.esc(user.getName()) + "</h2>");
        out.println("<table>");
        out.println("<tr><td>Session id</td><td>" + session.getId() + "</td></tr>");
        out.println("<tr><td>Created</td><td>" + new Date(session.getCreationTime()) + "</td></tr>");
        out.println("<tr><td>Last accessed</td><td>" + new Date(session.getLastAccessedTime()) + "</td></tr>");
        out.println("<tr><td>Timeout (seconds)</td><td>" + session.getMaxInactiveInterval() + "</td></tr>");
        out.println("<tr><td>Your visits this session</td><td>" + visits + "</td></tr>");
        out.println("<tr><td>Active sessions on server</td><td>" + AppListener.ACTIVE_SESSIONS.get() + "</td></tr>");
        out.println("</table></body></html>");
    }
}
