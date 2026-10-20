package org.example.restserver.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.app.presentation.UserController;
import org.example.app.presentation.UserControllerImpl;

import java.io.IOException;

public class UserHandler implements HttpHandler {

    private final UserController userController;


    public UserHandler(){
        this.userController = UserControllerImpl.getInstance();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{

    }
}
