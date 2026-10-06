package org.example.domain;

import java.time.Instant;

public class Rating {
    private int ratingId;
    private int userId;
    private int mediaId;
    private int stars;
    private String comment;
    private Instant timestamp;


    public Rating(int ratingId, int userId, int mediaId, int stars, String comment, Instant timestamp){
        this.ratingId = ratingId;
        this.userId = userId;
        this.mediaId = mediaId;
        this.stars = stars;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    //getters

    public int getRatingId(){ return ratingId; }
    public int getUserId(){ return userId; }
    public int getMediaId(){ return mediaId; }
    public int getStars(){ return stars; }
    public String getComment(){ return comment; }
    public Instant getTimestamp(){ return timestamp; }

    //setters


    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return ratingId == rating.ratingId;
    }


    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", userId=" + userId +
                ", mediaId=" + mediaId +
                ", stars=" + stars +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }


}
