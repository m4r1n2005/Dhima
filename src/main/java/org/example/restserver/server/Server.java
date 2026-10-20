package org.example.restserver.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private final HttpServer server;

    public Server(int port) throws IOException{
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/users", new UserHandler());

        server.createContext("/api/media", new MediaHandler());

        server.createContext("/api/ratings", new RatingHandler());

        server.createContext("/api/leaderboard", new LeaderboardHandler());
    }


    public void start(){
        server.start();
    }


    public void stop(){
        server.stop(0);
    }
}
