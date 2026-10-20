package org.example.restserver.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.example.app.business.UserService;
import org.example.app.business.UserServiceImpl;
import org.example.app.data.UserRepository;
import org.example.app.data.UserRepositoryImpl;
import org.example.app.presentation.UserController;
import org.example.app.presentation.UserControllerImpl;
import org.example.restserver.handler.UserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private final HttpServer server;

    public Server(int port) throws IOException{
        server = HttpServer.create(new InetSocketAddress(port), 0);


        server.createContext("/api/users", UserHandler.getInstance(UserControllerImpl.getInstance(UserServiceImpl.getInstance(UserRepositoryImpl.getInstance()))));

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
