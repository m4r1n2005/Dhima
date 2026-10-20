package org.example.restserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.app.domain.User;
import org.example.app.presentation.UserController;
import org.example.app.presentation.UserControllerImpl;
import org.example.restserver.http.ContentType;
import org.example.restserver.http.HttpStatus;
import org.example.restserver.server.Request;
import org.example.restserver.server.Response;

import java.io.IOException;

public class UserHandler implements HttpHandler {


    private final UserController userController;


    private static HttpHandler instance;


    private UserHandler(UserController userController){
        this.userController = userController;
    }


    public static HttpHandler getInstance(UserController userController){
        if(instance == null){
            instance = new UserHandler(userController);
        }

        return instance;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        Request request = new Request(exchange);

        switch (request.getMethod()){
            case POST:
                if(request.getPath().equals("/api/users/register")){

                    ObjectMapper mapper = new ObjectMapper();


                    User user = mapper.readValue(request.getBody(), User.class);

                    boolean registered = userController.register(user);

                    if(registered){
                        Response response = new Response(HttpStatus.CREATED, ContentType.JSON, null);
                        response.send(exchange);
                    }



                } else if(request.getPath().equals("/api/users/login")){
                    userController.login(user.getUsername(), user.getPassword());
                } else{

                }
        }
    }
}
