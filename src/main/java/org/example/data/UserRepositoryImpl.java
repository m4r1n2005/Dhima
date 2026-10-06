package org.example.data;


import org.example.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository{
    private final Map<Integer, User> users;

    private static UserRepository instance;


    private UserRepositoryImpl(){
        users = new HashMap<>();
    }


    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepositoryImpl();
        }

        return instance;
    }


    @Override
    public void save(User user){
        users.put(user.getUserId(), user);
    }


    @Override
    public User findById(int userId){
        return users.get(userId);
    }


    @Override
    public User findByUsername(String username){
        for(User user : users.values()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }

        return null;
    }

    @Override
    public User findByEmail(String email){
        for(User user : users.values()){
            if(user.getEmail().equals(email)){
                return user;
            }
        }

        return null;
    }




    @Override
    public List<User> findAll(){
        return new ArrayList<>(users.values());
    }


}
