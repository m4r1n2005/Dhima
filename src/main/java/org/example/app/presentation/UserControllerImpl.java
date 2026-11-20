package org.example.app.presentation;

import org.example.app.business.RatingService;
import org.example.app.business.RecommendationService;
import org.example.app.business.UserService;
import org.example.app.domain.*;

import java.util.List;

public class UserControllerImpl implements UserController{
    private final UserService userService;

    private final RecommendationService recommendationService;


    private final RatingService ratingService;

    private static UserController instance;

    private UserControllerImpl(UserService userService, RatingService ratingService, RecommendationService recommendationService){
        this.userService = userService;
        this.ratingService = ratingService;
        this.recommendationService = recommendationService;
    }


    public static UserController getInstance(UserService userService, RatingService ratingService, RecommendationService recommendationService){
        if(instance == null){
            instance = new UserControllerImpl(userService, ratingService, recommendationService);
        }

        return instance;
    }


    @Override
    public boolean register(String username, String password) {
        return userService.register(username, password);
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
        return recommendationService.getRecommendations(userId, type);
    }
}
