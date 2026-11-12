package org.example.app.business;

import org.example.app.domain.*;

import java.util.List;

public interface UserService {

    boolean register(String username, String password);

    String login(String username, String password) throws InvalidCredentialsException;

    User getProfile(int userId) throws UserNotFoundException;

    boolean updateProfile(User user);


    List<Media> getRecommendations(int userId, String type);


}
