package org.example.app.data;

import org.example.app.domain.Media;

import java.util.Map;
import java.util.Set;

public interface FavoritesRepository {

    Set<Integer> getFavoritesByUser(int userId);


    boolean addFavorite(int userId, int mediaId);


    boolean removeFavorite(int userId, int mediaId);


    int getFavoriteCount(int mediaId);


    void removeFavoritesByMediaId(int mediaId);
}
