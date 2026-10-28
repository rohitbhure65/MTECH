package com.example.web;

import com.example.model.User;
import com.example.util.Html;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** A reusable page fragment (menu bar). Other servlets INCLUDE it. */
@WebServlet("/header")
public class HeaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ctx = request.getContextPath();
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        String who = user == null ? "Guest" : Html.esc(user.getName());
        response.getWriter().println("<div style='background:#eef;padding:8px'>"
            + "<a href='" + ctx + "/dashboard'>Dashboard</a> | "
            + "<a href='" + ctx + "/students'>Students</a> | "
            + "<a href='" + ctx + "/logout'>Logout</a> &nbsp; (" + who + ")</div>");
    }
}
