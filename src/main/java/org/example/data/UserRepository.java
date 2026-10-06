package org.example.data;

import org.example.domain.User;

import java.util.List;

public interface UserRepository {

    void save(User user);


    User findById(int userId);


    User findByUsername(String username);


    User findByEmail(String email);


    List<User> findAll();
}
