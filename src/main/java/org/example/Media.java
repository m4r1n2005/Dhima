package org.example;

import java.util.List;

public class Media {
    private int mediaId;
    private String title;
    private String description;
    private String mediaType;
    private int releaseYear;
    private List<String> genres;
    private int ageRestriction;


    public Media(int mediaId, String title, String description, String mediaType, int releaseYear, List<String> genres, int ageRestriction){
        this.mediaId = mediaId;
        this.title = title;
        this.description = description;
        this.mediaType = mediaType;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
    }


    //getters

    public int getMediaId(){ return mediaId; }

    public String getTitle(){ return title; }

    public String getDescription(){ return description; }

    public String getMediaType(){ return mediaType; }

    public int getReleaseYear(){ return releaseYear; }

    public List<String> getGenres(){ return genres; }

    public int getAgeRestriction(){ return ageRestriction; }
}
