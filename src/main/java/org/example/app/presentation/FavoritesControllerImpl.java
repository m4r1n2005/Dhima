package org.example.app.presentation;

import org.example.app.business.FavoritesService;
import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;

public class FavoritesControllerImpl implements FavoritesController {

    private final FavoritesService favoritesService;


    private static FavoritesController instance;


    private FavoritesControllerImpl(FavoritesService favoritesService){
        this.favoritesService = favoritesService;
    }


    public static FavoritesController getInstance(FavoritesService favoritesService){
        if(instance == null){
            instance = new FavoritesControllerImpl(favoritesService);
        }

        return instance;
    }


    @Override
    public List<Media> getFavoritesByUser(int userId) {
        return favoritesService.getFavoritesByUser(userId);
    }

    @Override
    public boolean addFavorite(int userId, int mediaId) throws MediaNotFoundException{
        return favoritesService.addFavorite(userId, mediaId);
    }

    @Override
    public boolean removeFavorite(int userId, int mediaId) throws MediaNotFoundException{
        return favoritesService.removeFavorite(userId, mediaId);
    }

    @Override
    public int getFavoriteCount(int mediaId){
         return favoritesService.getFavoriteCount(mediaId);
    }

}
