package com.yourpackage.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/EM";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void executeInTransaction(TransactionOperation operation) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); 
            try {
                operation.run(conn); 
                conn.commit(); 
            } catch (Exception e) {
                conn.rollback(); 
                throw new RuntimeException("Transaction failed and rolled back", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to manage the transaction", e);
        }
    }

    @FunctionalInterface
    public interface TransactionOperation {
        void run(Connection conn) throws Exception;
    }
}
