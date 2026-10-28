package com.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** /cookie?action=set | read | delete */
@WebServlet("/cookie")
public class CookieDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        if (action == null) action = "read";

        switch (action) {
            case "set": {
                Cookie c = new Cookie("theme", "dark");
                c.setMaxAge(60 * 60 * 24);        // seconds; omit/-1 = lives until browser closes
                c.setPath("/");
                c.setHttpOnly(true);
                response.addCookie(c);            // must be called BEFORE the body is committed
                out.println("Cookie 'theme=dark' sent for 1 day.");
                break;
            }
            case "delete": {
                Cookie c = new Cookie("theme", "");
                c.setMaxAge(0);                   // 0 = delete
                c.setPath("/");                   // same path as when it was created
                response.addCookie(c);
                out.println("Cookie 'theme' deleted.");
                break;
            }
            default: {
                Cookie[] all = request.getCookies();
                if (all == null) {
                    out.println("No cookies received.");
                } else {
                    for (Cookie c : all) {
                        out.println(c.getName() + " = " + c.getValue());
                    }
                }
            }
        }
    }
}
