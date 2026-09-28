package org.example;

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
}
