package org.example.app.domain;

import java.util.List;

public class Movie extends Media{


    public Movie(int mediaId, String title, String description, int releaseYear, List<String> genres, int ageRestriction, int creatorUserId) {
        super(mediaId, title, description, "Movie", releaseYear, genres, ageRestriction, creatorUserId);
    }
}
