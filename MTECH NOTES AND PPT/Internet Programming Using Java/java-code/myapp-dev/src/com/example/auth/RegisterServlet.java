package com.example.auth;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.Html;
import com.example.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showForm(response, "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        if (name == null || name.isBlank() || email == null || email.isBlank()
                || password == null || password.length() < 6) {
            showForm(response, "All fields required; password minimum 6 characters.", name, email);
            return;
        }
        if (!password.equals(confirm)) {
            showForm(response, "Passwords do not match.", name, email);
            return;
        }
        try {
            boolean created = new UserDao().insert(new User(name.trim(), email.trim(), PasswordUtil.hash(password)));
            if (!created) {
                showForm(response, "This e-mail is already registered.", name, "");
                return;
            }
            // Post-Redirect-Get: browser reload will not re-submit the form
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
        } catch (SQLException e) {
            throw new ServletException("Database error while registering", e);
        }
    }

    private void showForm(HttpServletResponse response, String error, String name, String email) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String body = (error.isEmpty() ? "" : "<p class='err'>" + Html.esc(error) + "</p>")
            + "<form method='post' action='register'>"
            + "Name:<br><input name='name' value='" + Html.esc(name) + "'><br>"
            + "E-mail:<br><input type='email' name='email' value='" + Html.esc(email) + "'><br>"
            + "Password:<br><input type='password' name='password'><br>"
            + "Confirm password:<br><input type='password' name='confirm'><br>"
            + "<input type='submit' value='Register'></form>"
            + "<p>Already registered? <a href='login'>Login</a></p>";
        response.getWriter().println(Html.page("Register", body));
    }
}
