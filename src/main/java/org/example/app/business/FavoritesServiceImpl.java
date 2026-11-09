package org.example.app.business;

import org.example.app.data.FavoritesRepository;
import org.example.app.data.MediaRepository;
import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FavoritesServiceImpl implements FavoritesService{

    private final FavoritesRepository favoritesRepository;

    private final MediaRepository mediaRepository;

    private static FavoritesService instance;

    private FavoritesServiceImpl(FavoritesRepository favoritesRepository, MediaRepository mediaRepository){
        this.favoritesRepository = favoritesRepository;
        this.mediaRepository = mediaRepository;
    }

    public static FavoritesService getInstance(FavoritesRepository favoritesRepository, MediaRepository mediaRepository){
        if(instance == null){
            instance = new FavoritesServiceImpl(favoritesRepository, mediaRepository);
        }

        return instance;
    }


    @Override
    public List<Media> getFavoritesByUser(int userId){
        //Set
        Set<Integer> set = favoritesRepository.getFavoritesByUser(userId);

        return set.stream().map(media -> mediaRepository.findById(media)).toList();

    }

    @Override
    public boolean addFavorite(int userId, int mediaId)
            throws MediaNotFoundException {

        if (mediaRepository.findById(mediaId) == null) {
            throw new MediaNotFoundException("Media not found");
        }

        return favoritesRepository.addFavorite(userId, mediaId);
    }

    @Override
    public boolean removeFavorite(int userId, int mediaId)  throws MediaNotFoundException{

        if (mediaRepository.findById(mediaId) == null) {
            throw new MediaNotFoundException("Media not found");
        }

        return favoritesRepository.removeFavorite(userId, mediaId);
    }

    @Override
    public int getFavoriteCount(int mediaId){
        return favoritesRepository.getFavoriteCount(mediaId);
    }




}
