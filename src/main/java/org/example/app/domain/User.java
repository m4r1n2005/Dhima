package org.example.app.domain;

public class User {

    private int userId;
    private String username;
    private String password_hash;
    private String email;
    private String favoriteGenre;

    public User(int userId, String username, String password_hash, String email, String favoriteGenre){
        this.userId = userId;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.favoriteGenre = favoriteGenre;
    }

    public User(){

    }


    //getters

    public int getUserId(){ return userId; }

    public String getUsername(){ return username; }

    public String getPasswordHash(){ return password_hash; }

    public String getEmail(){ return email; }

    public String getFavoriteGenre(){ return favoriteGenre; }


    //setters


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }


    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;

        return userId == user.userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", favoriteGenre='" + favoriteGenre + '\'' +
                '}';
    }


}
