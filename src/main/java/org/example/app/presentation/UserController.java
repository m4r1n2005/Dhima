package org.example.app.presentation;

import org.example.app.domain.User;

public interface UserController {


    boolean register(User user);


    boolean login(String username, String password);


    User getProfile(int userId);


    boolean updateProfile(User user);

}
