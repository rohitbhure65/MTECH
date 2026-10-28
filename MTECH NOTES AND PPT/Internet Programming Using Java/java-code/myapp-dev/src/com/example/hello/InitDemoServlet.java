package com.example.hello;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Mapped in web.xml (not by annotation): shows <init-param> and <context-param>
public class InitDemoServlet extends HttpServlet {

    private String author;                       // from <init-param>  (this servlet only)

    @Override
    public void init() throws ServletException {  // runs ONCE, when the servlet is created
        author = getInitParameter("author");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext ctx = getServletContext();
        out.println("init-param    author  = " + author);
        out.println("context-param college = " + ctx.getInitParameter("college"));
        out.println("context path          = " + request.getContextPath());
        out.println("servlet path          = " + request.getServletPath());
        out.println("real path of /        = " + ctx.getRealPath("/"));
    }
}
