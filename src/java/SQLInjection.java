package java;

import java.sql.*;

/**
 * This class contains SQL Injection vulnerabilities.
 * Used for testing SAST tools like Checkmarx, Fortify, SonarQube.
 */
public class SQLInjection {

    // Hardcoded credentials - detectable by most SAST tools
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "password123";

    /**
     * Vulnerable SQL query with string concatenation
     * SAST tools should flag this as SQL Injection
     */
    public ResultSet getUserData(String userId) throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test", DB_USER, DB_PASSWORD);

        // VULNERABLE: Direct string concatenation
        String query = "SELECT * FROM users WHERE id = '" + userId + "'";
        Statement stmt = conn.createStatement();

        // VULNERABLE: Execute query with user input
        return stmt.executeQuery(query);
    }

    /**
     * Another SQL injection example with multiple parameters
     */
    public ResultSet searchUsers(String name, String email) throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test", DB_USER, DB_PASSWORD);

        // VULNERABLE: Multiple parameters concatenated
        String query = "SELECT * FROM users WHERE name = '" + name
                     + "' AND email = '" + email + "'";
        Statement stmt = conn.createStatement();

        return stmt.executeQuery(query);
    }

    /**
     * SQL injection with ORDER BY clause
     */
    public ResultSet getUsersSorted(String sortColumn) throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test", DB_USER, DB_PASSWORD);

        // VULNERABLE: User input in ORDER BY
        String query = "SELECT * FROM users ORDER BY " + sortColumn;
        Statement stmt = conn.createStatement();

        return stmt.executeQuery(query);
    }

    /**
     * Prepared statement example (SAFE) - for comparison
     */
    public ResultSet getUserDataSafe(String userId) throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test", DB_USER, DB_PASSWORD);

        // SAFE: Using prepared statement
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, userId);

        return stmt.executeQuery();
    }
}