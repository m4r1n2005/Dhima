package org.example.app.presentation;

import org.example.app.domain.*;

import java.util.List;

public interface UserController {


    boolean register(String username, String password);


    String login(String username, String password) throws InvalidCredentialsException;


    User getProfile(int userId) throws UserNotFoundException;


    boolean updateProfile(User user);


    List<Rating> getRatings(int userId);


    List<Media> getRecommendations(int userId, String type);

}
