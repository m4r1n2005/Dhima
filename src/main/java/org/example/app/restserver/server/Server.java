package org.example.app.restserver.server;

import com.sun.net.httpserver.HttpServer;
import org.example.app.ApplicationConfig;
import org.example.app.presentation.UserControllerImpl;
import org.example.app.restserver.handler.UserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private final HttpServer server;

    public Server(ApplicationConfig appConfig, int port) throws IOException{
        server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);


        server.createContext("/api/users", appConfig.getUserHandler());


        //server.createContext("/api/media", new MediaHandler());

        //server.createContext("/api/ratings", new RatingHandler());

        //server.createContext("/api/leaderboard", new LeaderboardHandler());
    }


    public void start(){
        server.start();
    }


    public void stop(){
        server.stop(0);
    }
}
