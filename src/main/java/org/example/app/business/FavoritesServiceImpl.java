package org.example.app.business;

import org.example.app.data.FavoritesReader;
import org.example.app.data.FavoritesWriter;
import org.example.app.data.MediaReader;
import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;
import java.util.Set;

public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesReader favoritesReader;
    private final FavoritesWriter favoritesWriter;
    private final MediaReader mediaRepository;

    private static FavoritesService instance;

    private FavoritesServiceImpl(
            FavoritesReader favoritesReader,
            FavoritesWriter favoritesWriter,
            MediaReader mediaRepository) {

        this.favoritesReader = favoritesReader;
        this.favoritesWriter = favoritesWriter;
        this.mediaRepository = mediaRepository;
    }

    public static FavoritesService getInstance(
            FavoritesReader favoritesReader,
            FavoritesWriter favoritesWriter,
            MediaReader mediaRepository) {

        if (instance == null) {
            instance = new FavoritesServiceImpl(
                    favoritesReader,
                    favoritesWriter,
                    mediaRepository);
        }

        return instance;
    }

    @Override
    public List<Media> getFavoritesByUser(int userId) {

        Set<Integer> set = favoritesReader.getFavoritesByUser(userId);

        return set.stream()
                .map(media -> mediaRepository.findById(media))
                .toList();
    }

    @Override
    public boolean addFavorite(int userId, int mediaId)
            throws MediaNotFoundException {

        if (mediaRepository.findById(mediaId) == null) {
            throw new MediaNotFoundException("Media not found");
        }

        return favoritesWriter.addFavorite(userId, mediaId);
    }

    @Override
    public boolean removeFavorite(int userId, int mediaId)
            throws MediaNotFoundException {

        if (mediaRepository.findById(mediaId) == null) {
            throw new MediaNotFoundException("Media not found");
        }

        return favoritesWriter.removeFavorite(userId, mediaId);
    }

    @Override
    public int getFavoriteCount(int mediaId) {

        return favoritesReader.getFavoriteCount(mediaId);
    }
}