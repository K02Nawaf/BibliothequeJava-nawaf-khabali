package com.library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nawaf
 */
public class DBConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nawaf-khabali-library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
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