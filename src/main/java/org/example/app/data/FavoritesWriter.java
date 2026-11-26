package org.example.app.data;

public interface FavoritesWriter {

    boolean addFavorite(int userId, int mediaId);

    boolean removeFavorite(int userId, int mediaId);
}