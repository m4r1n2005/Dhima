package org.example.app.presentation;

import org.example.app.business.RatingService;
import org.example.app.business.UserService;
import org.example.app.domain.*;

import java.util.List;

public class UserControllerImpl implements UserController{
    private final UserService userService;


    private final RatingService ratingService;

    private static UserController instance;

    private UserControllerImpl(UserService userService, RatingService ratingService){
        this.userService = userService;
        this.ratingService = ratingService;
    }


    public static UserController getInstance(UserService userService, RatingService ratingService){
        if(instance == null){
            instance = new UserControllerImpl(userService, ratingService);
        }

        return instance;
    }


    @Override
    public boolean register(User user){
        return userService.register(user);
    }



    @Override
    public String login(String username, String password) throws InvalidCredentialsException {
        return userService.login(username, password);
    }


    @Override
    public User getProfile(int userId) throws UserNotFoundException {
        return userService.getProfile(userId);
    }


    @Override
    public boolean updateProfile(User user){
        return userService.updateProfile(user);
    }


    @Override
    public List<Rating> getRatings(int userId){
        return ratingService.getRatingsByUser(userId);
    }


    @Override
    public List<Media> getRecommendations(int userId, String type){
        return userService.getRecommendations(userId, type);
    }
}
