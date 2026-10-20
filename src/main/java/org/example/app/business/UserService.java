package org.example.app.business;

import org.example.app.domain.User;

public interface UserService {

    boolean register(User user);

    boolean login(String username, String password);

    User getProfile(int userId);

    boolean updateProfile(User user);

}
