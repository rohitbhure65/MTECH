package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** One place that knows how to connect to MySQL. */
public final class DBUtil {
    private static final String URL =
        "jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";      // change to YOUR MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");      // load the driver class
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL driver jar missing in WEB-INF/lib: " + e);
        }
    }

    private DBUtil() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
