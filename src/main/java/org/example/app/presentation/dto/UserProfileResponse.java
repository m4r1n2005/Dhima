package org.example.app.presentation.dto;

public class UserProfileResponse {

    private int userId;
    private String username;
    private String email;
    private String favoriteGenre;


    public UserProfileResponse(int userId, String username, String email, String favoriteGenre) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.favoriteGenre = favoriteGenre;
    }

    UserProfileResponse(){
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }
}
