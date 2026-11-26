package org.example.app.data;

import java.util.Set;

public interface FavoritesReader {

    Set<Integer> getFavoritesByUser(int userId);

    int getFavoriteCount(int mediaId);
}