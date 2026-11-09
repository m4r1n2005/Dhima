package org.example.app.business;

import org.example.app.data.FavoritesRepository;
import org.example.app.data.MediaRepository;
import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;

public class MediaServiceImpl implements MediaService{


    private final MediaRepository mediaRepository;

    private final FavoritesRepository favoritesRepository;

    private static MediaService instance;

    private MediaServiceImpl(MediaRepository mediaRepository, FavoritesRepository favoritesRepository){
        this.mediaRepository = mediaRepository;
        this.favoritesRepository = favoritesRepository;
    }

    public static MediaService getInstance(MediaRepository mediaRepository, FavoritesRepository favoritesRepository){
        if(instance == null){
            instance = new MediaServiceImpl(mediaRepository, favoritesRepository);
        }

        return instance;
    }

    @Override
    public boolean createMedia(Media media){
        Media search = mediaRepository.findById(media.getMediaId());

        if(search == null){
            mediaRepository.save(media);
            return true;
        }
        return false;
    }

    @Override
    public Media getMedia(int mediaId){
        return mediaRepository.findById(mediaId);
    }


    @Override
    public boolean updateMedia(Media media){
        Media exists = mediaRepository.findById(media.getMediaId());

        if(exists != null){
            mediaRepository.save(media);
            return true;
        }
        return false;
    }


    @Override
    public void deleteMedia(int mediaId) throws MediaNotFoundException {
        Media media = mediaRepository.findById(mediaId);

        if (media == null) {
            throw new MediaNotFoundException("Media not found");
        }

        // Keep your existing authorization checks here.

        mediaRepository.delete(mediaId);
        favoritesRepository.removeFavoritesByMediaId(mediaId);
    }


    @Override
    public List<Media> getAllMedia(){
        return mediaRepository.findAll();
    }


    @Override
    public List<Media> searchByTitle(String title){
        return mediaRepository.findTitle(title);
    }
}
