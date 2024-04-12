package com.library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nawaf
 */
public class DBConnection {
    private static final String JDBC_URL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11693485";
    private static final String USERNAME = "sql11693485";
    private static final String PASSWORD = "96Ju6AlxHd";
    
    @SuppressWarnings("exports")
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }
    }

    /**
     *
     * @param connection
     */
    public static void closeConnection(@SuppressWarnings("exports") Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Handle connection close exception

            }
        }
    }
}