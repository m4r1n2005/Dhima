package org.example.domain;

import java.util.List;

public abstract class Media {
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


    //setters


    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return false;

        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return mediaId == media.mediaId;
    }


    @Override
    public String toString() {
        return "Media{" +
                "mediaId=" + mediaId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", releaseYear=" + releaseYear +
                ", genres=" + genres +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}
