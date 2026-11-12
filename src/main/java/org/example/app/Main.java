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

        ApplicationConfig appConfig = new ApplicationConfig();
        Server server = new Server(appConfig, 5000);
        server.start();
    }

}