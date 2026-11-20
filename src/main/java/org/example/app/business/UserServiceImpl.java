package org.example.app.business;

import org.example.app.data.MediaRepository;
import org.example.app.data.RatingRepository;
import org.example.app.data.UserRepository;
import org.example.app.domain.*;

import java.util.*;


public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;



    private final AuthService authService;



    private static UserService instance;

    private UserServiceImpl(UserRepository userRepository, AuthService authService){
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public static UserService getInstance(UserRepository userRepository, AuthService authService){
        if(instance == null){
            instance = new UserServiceImpl(userRepository, authService);
        }

        return instance;
    }



    @Override
    public boolean register(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        User existing = userRepository.findByUsername(username);

        if (existing != null) {
            return false;
        }

        String hash = PasswordHasher.hashPassword(password);

        User user = new User(0, username, hash, null, null);

        userRepository.save(user);

        return true;
    }


    @Override
    public String login(String username, String password) throws InvalidCredentialsException {
        User searchedByUsername = userRepository.findByUsername(username);

        if(searchedByUsername != null){
            boolean found = PasswordHasher.verifyPassword(password, searchedByUsername.getPasswordHash());
            if(found){
                return authService.generateToken(searchedByUsername.getUserId());
            }
        }



        throw new InvalidCredentialsException("Invalid username or password");

    }


    @Override
    public User getProfile(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId);
        if(user != null){
            return user;
        }
        throw new UserNotFoundException("User does not exist!");
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
