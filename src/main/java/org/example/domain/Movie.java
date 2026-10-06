package org.example.domain;

import java.util.List;

public class Movie extends Media{


    public Movie(int mediaId, String title, String description, int releaseYear, List<String> genres, int ageRestriction) {
        super(mediaId, title, description, "Movie", releaseYear, genres, ageRestriction);
    }
}
