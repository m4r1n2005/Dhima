
package org.example.app.data.dbncore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UnitOfWork implements AutoCloseable {

    private final Connection connection;
    private boolean finished = false;

    public UnitOfWork() {
        connection = DatabaseManager.INSTANCE.getConnection();

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException closeError) {
                e.addSuppressed(closeError);
            }

            throw new DataAccessException(
                    "Could not disable AutoCommit", e
            );
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Could not create PreparedStatement", e
            );
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
            finished = true;
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Transaction commit failed", e
            );
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
            finished = true;
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Transaction rollback failed", e
            );
        }
    }

    @Override
    public void close() {
        try {
            if (!finished) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Automatic rollback failed", e
            );
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException(
                        "Could not close connection", e
                );
            }
        }
    }
}
