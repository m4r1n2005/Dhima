package org.example.domain;

import java.util.List;

public class Serie extends Media{

    public Serie(int mediaId, String title, String description, int releaseYear, List<String> genres, int ageRestriction) {
        super(mediaId, title, description, "Serie", releaseYear, genres, ageRestriction);
    }
}
