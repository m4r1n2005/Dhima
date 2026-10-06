package org.example.business;

import org.example.data.UserRepository;
import org.example.domain.User;

import java.util.*;


public class UserServiceImpl implements UserService{
    private final List<User> loggedInUsers;

    private final UserRepository userRepository;

    private static UserService instance;

    private UserServiceImpl(UserRepository userRepository){
        loggedInUsers = new ArrayList<>();
        this.userRepository = userRepository;
    }

    public static UserService getInstance(UserRepository userRepository){
        if(instance == null){
            instance = new UserServiceImpl(userRepository);
        }

        return instance;
    }



    @Override
    public boolean register(User user){
        User searchedUser = userRepository.findByUsername(user.getUsername());

        if(searchedUser == null){
            userRepository.save(user);
            return true;
        } else{
            return false;
        }
    }


    @Override
    public boolean login(String username, String password){
        User searchedByUsername = userRepository.findByUsername(username);

        if(searchedByUsername != null){
            return searchedByUsername.getPassword().equals(password);
        }

        return false;
    }


    @Override
    public User getProfile(int userId){
        return userRepository.findById(userId);
    }


    @Override
    public boolean updateProfile(User user){
        User exists = userRepository.findById(user.getUserId());

        if (exists != null) {
            userRepository.save(user);
            return true;
        }

        return false;
    }



}
