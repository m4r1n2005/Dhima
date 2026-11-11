
package org.example.app.data.dbncore;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseManager {
    INSTANCE;

    public Connection getConnection() {
        String password = System.getenv("MRP_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new DataAccessException(
                    "Environment variable MRP_DB_PASSWORD is not set"
            );
        }

        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mrp",
                    "postgres",
                    password
            );
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Failed to connect to PostgreSQL", e
            );
        }
    }
}
