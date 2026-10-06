package org.example.presentation;

import org.example.business.UserService;
import org.example.domain.User;

public class UserControllerImpl implements UserController{
    private final UserService userService;

    private static UserController instance;

    private UserControllerImpl(UserService userService){
        this.userService = userService;
    }


    public static UserController getInstance(UserService userService){
        if(instance == null){
            instance = new UserControllerImpl(userService);
        }

        return instance;
    }


    @Override
    public boolean register(User user){
        return userService.register(user);
    }



    @Override
    public boolean login(String username, String password){
        return userService.login(username, password);
    }


    @Override
    public User getProfile(int userId){
        return userService.getProfile(userId);
    }


    @Override
    public boolean updateProfile(User user){
        return userService.updateProfile(user);
    }
}
