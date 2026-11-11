package org.example.app;

import org.example.app.business.*;
import org.example.app.data.*;
import org.example.app.data.dbncore.DataAccessException;
import org.example.app.data.dbncore.UnitOfWork;
import org.example.app.domain.*;
import org.example.app.presentation.*;
import org.example.app.restserver.server.Server;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main{




    public static void main(String[] args) throws IOException {
        try (UnitOfWork uow = new UnitOfWork()) {

            try (PreparedStatement stmt = uow.prepareStatement("SELECT COUNT(*) FROM users")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Users in database: " + rs.getInt(1));
                    }
                }
            }

            uow.commitTransaction();

            System.out.println("Database test successful!");
        } catch (SQLException e) {
            throw new DataAccessException("Database test failed", e);
        }

        ApplicationConfig appConfig = new ApplicationConfig();
        Server server = new Server(appConfig, 5000);
        server.start();
    }

}