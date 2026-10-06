package org.example.presentation;

import org.example.domain.User;

public interface UserController {


    boolean register(User user);


    boolean login(String username, String password);


    User getProfile(int userId);


    boolean updateProfile(User user);

}
