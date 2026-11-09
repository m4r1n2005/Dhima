package org.example.app.business;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AuthServiceImpl implements AuthService{

    private final Map<String, Integer> tokens;


    private static AuthService instance;


    private AuthServiceImpl(){
        this.tokens = new HashMap<>();
    }


    public static AuthService getInstance(){
        if(instance == null){
            instance = new AuthServiceImpl();
        }

        return instance;
    }


    @Override
    public String generateToken(int userId){
        String token = UUID.randomUUID().toString();
        tokens.put(token, userId);


        return token;
    }


    @Override
    public Integer validateToken(String token){
        return tokens.get(token);
    }


    @Override
    public void revokeToken(String token){
        tokens.remove(token);
    }
}
