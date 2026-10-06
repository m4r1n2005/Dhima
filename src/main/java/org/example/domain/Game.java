package org.example.domain;

import java.util.List;

public class Game extends Media{

    public Game(int mediaId, String title, String description, int releaseYear, List<String> genres, int ageRestriction) {
        super(mediaId, title, description, "Game", releaseYear, genres, ageRestriction);
    }
}
