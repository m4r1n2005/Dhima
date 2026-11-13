package org.example.app.restserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.app.business.AuthService;
import org.example.app.domain.*;
import org.example.app.presentation.FavoritesController;
import org.example.app.presentation.UserController;

import org.example.app.presentation.dto.LoginRequest;
import org.example.app.presentation.dto.UserProfileResponse;
import org.example.app.presentation.dto.UserProfileUpdateRequest;
import org.example.app.restserver.http.ContentType;
import org.example.app.restserver.http.HttpStatus;
import org.example.app.restserver.server.Request;
import org.example.app.restserver.server.Response;

import java.io.IOException;
import java.util.Map;

public class UserHandler implements HttpHandler {


    private final UserController userController;

    private final FavoritesController favoritesController;


    private final AuthService authService;


    private static HttpHandler instance;


    private UserHandler(UserController userController, FavoritesController favoritesController, AuthService authService) {
        this.userController = userController;
        this.favoritesController = favoritesController;
        this.authService = authService;
    }


    public static HttpHandler getInstance(UserController userController, FavoritesController favoritesController, AuthService authService){
        if(instance == null){
            instance = new UserHandler(userController, favoritesController, authService);
        }

        return instance;
    }


    private Integer getAuthenticatedUserId(Request request) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {
            return null;
        }

        String token = authorization.substring(7).trim();

        if (token.isEmpty()) {
            return null;
        }

        return authService.validateToken(token);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        Request request = new Request(exchange);



        switch (request.getMethod()){
            case POST: {
                if (request.getPath().equals("/api/users/register")) {

                    ObjectMapper mapper = new ObjectMapper();


                    LoginRequest credentials = mapper.readValue(request.getBody(), LoginRequest.class);

                    boolean registered = userController.register(credentials.getUsername(), credentials.getPassword());

                    if (registered) {
                        Response response = new Response(HttpStatus.CREATED, ContentType.JSON, null);
                        response.send(exchange);
                    } else {
                        Response badResponse = new Response(HttpStatus.CONFLICT, ContentType.JSON, "Username already exists");
                        badResponse.send(exchange);
                    }


                } else if (request.getPath().equals("/api/users/login")) {

                    ObjectMapper mapper = new ObjectMapper();

                    LoginRequest loginRequest = mapper.readValue(request.getBody(), LoginRequest.class);

//userService returns token, controler returns token to handler
                    try {
                        String token = userController.login(loginRequest.getUsername(), loginRequest.getPassword());

                        String json = mapper.writeValueAsString(Map.of("token", token));

                        Response response = new Response(HttpStatus.OK, ContentType.JSON, json);
                        response.send(exchange);
                    } catch (InvalidCredentialsException e) {
                        String json = "{\"error\":\"Invalid username or password\"}";
                        Response badResponse = new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, json);
                        System.out.println(e.getMessage());
                        badResponse.send(exchange);
                    }

                } else {
                    Response badResponse = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"Endpoint not found\"}");
                    badResponse.send(exchange);
                }
                break;
            }

            case GET: {

                ObjectMapper mapper = new ObjectMapper();


                if (request.getPathParts().size() != 4 ||
                        !request.getPathParts().get(0).equals("api") ||
                        !request.getPathParts().get(1).equals("users")) {

                    new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{\"error\":\"Invalid endpoint\"}"
                    ).send(exchange);

                    break;
                }


                String lastPathPart = request.getPathParts().getLast();

                if (!lastPathPart.equals("profile")) {
                    new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{\"error\":\"Endpoint not found\"}"
                    ).send(exchange);
                    break;
                }

                int userId;

                User user = null;

                try {

                    Integer authenticatedUserId = getAuthenticatedUserId(request);

                    if (authenticatedUserId == null) {
                        new Response(
                                HttpStatus.UNAUTHORIZED,
                                ContentType.JSON,
                                "{\"error\":\"Invalid or missing token\"}"
                        ).send(exchange);

                        break;
                    }

                    userId = Integer.parseInt(request.getPathParts().get(2));

                    if (authenticatedUserId != userId) {
                        new Response(HttpStatus.FORBIDDEN, ContentType.JSON, "{\"error\":\"Access denied\"}"
                        ).send(exchange);
                        break;
                    }

                    user = userController.getProfile(userId);
                } catch (UserNotFoundException e) {
                    Response badResponse = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"User not found\"}");
                    System.out.println(e.getMessage());
                    badResponse.send(exchange);
                    break;
                } catch (NumberFormatException e) {
                    new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Invalid user ID\"}").send(exchange);
                    break;
                }


                UserProfileResponse userProfile = new UserProfileResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getFavoriteGenre());

                String json = mapper.writeValueAsString(userProfile);

                Response response = new Response(HttpStatus.OK, ContentType.JSON, json);

                response.send(exchange);

                break;
            }

            case PUT: {
                if (request.getPathParts().size() != 4 ||
                        !request.getPathParts().get(0).equals("api") ||
                        !request.getPathParts().get(1).equals("users")) {

                    new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{\"error\":\"Invalid endpoint\"}"
                    ).send(exchange);

                    break;
                }


                String lastPathPart = request.getPathParts().getLast();

                if (!lastPathPart.equals("profile")) {
                    new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{\"error\":\"Endpoint not found\"}"
                    ).send(exchange);
                    break;
                }

                int userId;

                User user = null;


                try {

                    Integer authenticatedUserId = getAuthenticatedUserId(request);

                    if (authenticatedUserId == null) {
                        new Response(
                                HttpStatus.UNAUTHORIZED,
                                ContentType.JSON,
                                "{\"error\":\"Invalid or missing token\"}"
                        ).send(exchange);

                        break;
                    }

                    userId = Integer.parseInt(request.getPathParts().get(2));

                    if (authenticatedUserId != userId) {
                        new Response(HttpStatus.FORBIDDEN, ContentType.JSON, "{\"error\":\"Access denied\"}"
                        ).send(exchange);
                        break;
                    }

                    user = userController.getProfile(userId);
                } catch (UserNotFoundException e) {
                    Response badResponse = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"User not found\"}");
                    System.out.println(e.getMessage());
                    badResponse.send(exchange);
                    break;
                } catch (NumberFormatException e) {
                    new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Invalid user ID\"}").send(exchange);
                    break;
                }

                ObjectMapper mapper = new ObjectMapper();


                UserProfileUpdateRequest userProfileUpdateRequest = mapper.readValue(request.getBody(), UserProfileUpdateRequest.class);

                if (userProfileUpdateRequest.getEmail() != null) {
                    user.setEmail(userProfileUpdateRequest.getEmail());
                }

                if (userProfileUpdateRequest.getFavoriteGenre() != null) {
                    user.setFavoriteGenre(userProfileUpdateRequest.getFavoriteGenre());
                }

                userController.updateProfile(user);


                UserProfileResponse userProfile = new UserProfileResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFavoriteGenre()
                );

                String json = mapper.writeValueAsString(userProfile);

                Response response = new Response(HttpStatus.OK, ContentType.JSON, json);

                response.send(exchange);

                break;
            }



            default: {
                Response badResponse = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Invalid path\"}");
                badResponse.send(exchange);
            }
        }

    }
}
