package org.example.business;

import org.example.domain.User;

public interface UserService {

    boolean register(User user);

    boolean login(String username, String password);

    User getProfile(int userId);

    boolean updateProfile(User user);

}
