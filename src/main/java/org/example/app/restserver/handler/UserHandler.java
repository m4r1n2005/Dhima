package org.example.app.restserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.app.domain.*;
import org.example.app.presentation.FavoritesController;
import org.example.app.presentation.UserController;

import org.example.app.presentation.dto.LoginRequest;
import org.example.app.presentation.dto.UserProfileResponse;
import org.example.app.restserver.http.ContentType;
import org.example.app.restserver.http.HttpStatus;
import org.example.app.restserver.server.Request;
import org.example.app.restserver.server.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserHandler implements HttpHandler {


    private final UserController userController;

    private final FavoritesController favoritesController;


    private static HttpHandler instance;


    private UserHandler(UserController userController, FavoritesController favoritesController){
        this.userController = userController;
        this.favoritesController = favoritesController;
    }


    public static HttpHandler getInstance(UserController userController, FavoritesController favoritesController){
        if(instance == null){
            instance = new UserHandler(userController, favoritesController);
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
                    } else{
                        Response badResponse = new Response(HttpStatus.CONFLICT, ContentType.JSON, "Username already exists");
                        badResponse.send(exchange);
                    }



                } else if(request.getPath().equals("/api/users/login")){

                    ObjectMapper mapper = new ObjectMapper();

                    LoginRequest loginRequest = mapper.readValue(request.getBody(), LoginRequest.class);

//userService returns token, controler returns token to handler
                    try {
                        String token = userController.login(loginRequest.getUsername(), loginRequest.getPassword());

                        String json = mapper.writeValueAsString(Map.of("token", token));

                        Response response = new Response(HttpStatus.OK, ContentType.JSON, json);
                        response.send(exchange);
                    } catch(InvalidCredentialsException e){
                        String json = "{\"error\":\"Invalid username or password\"}";
                        Response badResponse = new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, json);
                        System.out.println(e.getMessage());
                        badResponse.send(exchange);
                    }

                } else{
                    Response badResponse = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"Endpoint not found\"}");
                    badResponse.send(exchange);
                }
                break;

            case GET:

                ObjectMapper mapper = new ObjectMapper();
                ///winget install --id Docker.DockerDesktop -e


                if(request.getPathParts().size() != 4){
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"Invalid endpoint\"}").send(exchange);
                    break;
                }

                String lastPathPart = request.getPathParts().getLast();

                int userId;

                User user = null;

                try{
                    userId = Integer.parseInt(request.getPathParts().get(2));

                    user = userController.getProfile(userId);
                } catch(UserNotFoundException e){
                    Response badResponse = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"User not found\"}");
                    System.out.println(e.getMessage());
                    badResponse.send(exchange);
                    break;
                } catch(NumberFormatException e){
                    new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Invalid user ID\"}").send(exchange);
                    break;
                }

                if(lastPathPart.equals("profile")){

                    UserProfileResponse userProfile = new UserProfileResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getFavoriteGenre());

                    String json = mapper.writeValueAsString(userProfile);

                    Response response = new Response(HttpStatus.OK, ContentType.JSON, json);

                    response.send(exchange);

                } else if(lastPathPart.equals("ratings")){
                    List<Rating> ratings = userController.getRatings(userId);

                    String json = mapper.writeValueAsString(ratings);

                    Response response = new Response(HttpStatus.OK, ContentType.JSON, json);
                    response.send(exchange);

                } else if(lastPathPart.equals("favorites")){
                    List<Media> list = favoritesController.getFavoritesByUser(userId);

                    String json = mapper.writeValueAsString(list);


                    Response response = new Response(HttpStatus.OK, ContentType.JSON, json);
                    response.send(exchange);

                } else if(lastPathPart.equals("recommendations")){
                    String type = request.getQueryParameters().get("type");

                    if(type == null || (!type.equals("genre") && !type.equals("content"))){
                        Response badResponse = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Empty or non existing reccomendation type\"}");
                        badResponse.send(exchange);
                        break;
                    }

                    List<Media> recommendations = userController.getRecommendations(userId, type);

                    String json = mapper.writeValueAsString(recommendations);

                    Response response = new Response(HttpStatus.OK, ContentType.JSON, json);
                    response.send(exchange);
                } else{
                    Response badResponse = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"Endpoint not found\"}");
                    badResponse.send(exchange);
                }
                break;

            default:
                Response badResponse = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Invalid path\"}");
                badResponse.send(exchange);
        }

    }
}
