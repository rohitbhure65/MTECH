package com.example.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/** Runs once at application start/stop, and whenever a session is created/destroyed. */
@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {

    public static final AtomicInteger ACTIVE_SESSIONS = new AtomicInteger();

    @Override
    public void contextInitialized(ServletContextEvent e) {
        e.getServletContext().setAttribute("appName", "College Portal");
        System.out.println("[AppListener] application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        System.out.println("[AppListener] application stopped");
    }

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        ACTIVE_SESSIONS.incrementAndGet();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        ACTIVE_SESSIONS.decrementAndGet();
    }
}
