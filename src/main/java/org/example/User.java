package org.example;

import java.util.List;

public class User {

    private int userId;
    private String username;
    private String password;
    private String email;
    private String favoriteGenre;

    public User(int userId, String username, String password, String email, String favoriteGenre){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoriteGenre = favoriteGenre;
    }


    //getters

    public int getUserId(){ return userId; }

    public String getUsername(){ return username; }

    public String getPassword(){ return password; }

    public String getEmail(){ return email; }

    public String getFavoriteGenre(){ return favoriteGenre; }



}
