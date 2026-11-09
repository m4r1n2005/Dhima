package org.example.app;

import org.example.app.business.*;
import org.example.app.data.*;
import org.example.app.domain.*;
import org.example.app.presentation.*;
import org.example.app.restserver.server.Server;

import java.io.IOException;


public class Main{




    public static void main(String[] args) throws IOException {
        ApplicationConfig appConfig = new ApplicationConfig();
        Server server = new Server(appConfig, 5000);

        server.start();
    }

}