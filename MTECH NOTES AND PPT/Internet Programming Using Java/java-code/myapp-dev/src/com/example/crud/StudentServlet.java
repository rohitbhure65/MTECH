package com.example.crud;

import com.example.dao.StudentDao;
import com.example.model.Student;
import com.example.util.Html;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 *  GET  /students            -> list all     (Read)
 *  GET  /students?action=new -> empty form
 *  GET  /students?action=edit&id=3 -> form filled with student 3
 *  POST /students            -> action=insert | update | delete
 */
@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private final StudentDao dao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("new".equals(action)) {
                showForm(request, response, new Student(), "insert");
            } else if ("edit".equals(action)) {
                Student s = dao.findById(Integer.parseInt(request.getParameter("id")));
                if (s == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND, "No such student"); return; }
                showForm(request, response, s, "update");
            } else {
                showList(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                dao.delete(Integer.parseInt(request.getParameter("id")));
            } else {
                Student s = new Student();
                s.setName(request.getParameter("name"));
                s.setEmail(request.getParameter("email"));
                s.setCourse(request.getParameter("course"));
                if ("update".equals(action)) {
                    s.setId(Integer.parseInt(request.getParameter("id")));
                    dao.update(s);
                } else {
                    dao.insert(s);
                }
            }
            response.sendRedirect(request.getContextPath() + "/students");   // Post-Redirect-Get
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Student> list = dao.findAll();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Students</title>"
                  + "<style>body{font-family:Arial;margin:30px}table{border-collapse:collapse}"
                  + "td,th{border:1px solid #999;padding:6px 10px}</style></head><body>");
        request.getRequestDispatcher("/header").include(request, response);
        out.println("<h2>Students</h2><p><a href='students?action=new'>+ Add student</a></p>");
        out.println("<table><tr><th>ID</th><th>Name</th><th>E-mail</th><th>Course</th><th>Actions</th></tr>");
        for (Student s : list) {
            out.println("<tr><td>" + s.getId() + "</td><td>" + Html.esc(s.getName()) + "</td><td>"
                + Html.esc(s.getEmail()) + "</td><td>" + Html.esc(s.getCourse()) + "</td><td>"
                + "<a href='students?action=edit&id=" + s.getId() + "'>Edit</a> "
                + "<form style='display:inline' method='post' action='students' onsubmit=\"return confirm('Delete?')\">"
                + "<input type='hidden' name='action' value='delete'><input type='hidden' name='id' value='" + s.getId() + "'>"
                + "<input type='submit' value='Delete'></form></td></tr>");
        }
        out.println("</table></body></html>");
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Student s, String action)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String body = "<form method='post' action='students'>"
            + "<input type='hidden' name='action' value='" + action + "'>"
            + "<input type='hidden' name='id' value='" + s.getId() + "'>"
            + "Name:<br><input name='name' value='" + Html.esc(s.getName()) + "'><br>"
            + "E-mail:<br><input name='email' value='" + Html.esc(s.getEmail()) + "'><br>"
            + "Course:<br><input name='course' value='" + Html.esc(s.getCourse()) + "'><br>"
            + "<input type='submit' value='Save'> <a href='students'>Cancel</a></form>";
        response.getWriter().println(Html.page("update".equals(action) ? "Edit student" : "Add student", body));
    }
}
