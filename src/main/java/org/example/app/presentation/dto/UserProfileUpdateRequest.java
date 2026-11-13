package org.example.app.presentation.dto;

public class UserProfileUpdateRequest {

    private String email;
    private String favoriteGenre;

    public UserProfileUpdateRequest() {
    }


    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
