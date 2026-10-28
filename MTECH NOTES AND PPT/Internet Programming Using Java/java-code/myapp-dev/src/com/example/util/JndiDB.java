package com.example.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/** Alternative to DBUtil: use the connection POOL that Tomcat creates from META-INF/context.xml. */
public final class JndiDB {
    private static DataSource ds;

    private JndiDB() { }

    public static Connection getConnection() throws SQLException {
        try {
            if (ds == null) {
                Context ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/college");   // name from context.xml
            }
            return ds.getConnection();        // borrowed from the pool; close() returns it
        } catch (NamingException e) {
            throw new SQLException("DataSource lookup failed", e);
        }
    }
}
