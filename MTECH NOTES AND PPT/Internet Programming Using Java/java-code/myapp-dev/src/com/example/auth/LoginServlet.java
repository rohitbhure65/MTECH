package com.example.auth;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.Html;
import com.example.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // READ a cookie: pre-fill the e-mail if "Remember me" was ticked last time
        String remembered = "";
        Cookie[] cookies = request.getCookies();                 // can be null!
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("rememberEmail".equals(c.getName())) {
                    remembered = c.getValue();
                }
            }
        }
        String msg = "";
        if ("1".equals(request.getParameter("registered"))) msg = "<p class='ok'>Registered! Please login.</p>";
        if ("1".equals(request.getParameter("loggedout")))  msg = "<p class='ok'>You have been logged out.</p>";
        showForm(response, msg, remembered);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean remember = request.getParameter("remember") != null;   // checkbox: null if unticked

        try {
            User user = (email == null || password == null) ? null : new UserDao().findByEmail(email.trim());
            if (user == null || !PasswordUtil.verify(password, user.getPasswordHash())) {
                showForm(response, "<p class='err'>Wrong e-mail or password.</p>", email == null ? "" : email);
                return;
            }

            HttpSession old = request.getSession(false);
            if (old != null) old.invalidate();                     // new session id after login (security)
            HttpSession session = request.getSession(true);        // create fresh session
            session.setAttribute("user", user);                    // login = put user in session
            session.setMaxInactiveInterval(30 * 60);               // 30 minutes of inactivity

            Cookie cookie = new Cookie("rememberEmail", remember ? user.getEmail() : "");
            cookie.setMaxAge(remember ? 7 * 24 * 60 * 60 : 0);     // 7 days, or 0 = delete now
            cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            cookie.setHttpOnly(true);                              // JavaScript cannot read it
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (SQLException e) {
            throw new ServletException("Database error while logging in", e);
        }
    }

    private void showForm(HttpServletResponse response, String msg, String email) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String body = msg
            + "<form method='post' action='login'>"
            + "E-mail:<br><input type='email' name='email' value='" + Html.esc(email) + "'><br>"
            + "Password:<br><input type='password' name='password'><br>"
            + "<label><input type='checkbox' name='remember'> Remember my e-mail</label><br>"
            + "<input type='submit' value='Login'></form>"
            + "<p>New user? <a href='register'>Register</a></p>";
        response.getWriter().println(Html.page("Login", body));
    }
}
