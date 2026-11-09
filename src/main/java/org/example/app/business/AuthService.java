package org.example.app.business;

public interface AuthService {

    String generateToken(int userId);


    Integer validateToken(String token);


    void revokeToken(String token);
}
