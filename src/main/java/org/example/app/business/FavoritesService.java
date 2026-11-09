package org.example.app.business;

import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;

public interface FavoritesService {

    List<Media> getFavoritesByUser(int userId);

    boolean addFavorite(int userId, int mediaId) throws MediaNotFoundException;

    boolean removeFavorite(int userId, int mediaId)  throws MediaNotFoundException;

    int getFavoriteCount(int mediaId);

}
