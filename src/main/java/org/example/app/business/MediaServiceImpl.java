package org.example.app.business;

import org.example.app.data.MediaRepository;
import org.example.app.domain.Media;

import java.util.List;

public class MediaServiceImpl implements MediaService{


    private final MediaRepository mediaRepository;


    private static MediaService instance;

    private MediaServiceImpl(MediaRepository mediaRepository){
        this.mediaRepository = mediaRepository;
    }

    public static MediaService getInstance(MediaRepository mediaRepository){
        if(instance == null){
            instance = new MediaServiceImpl(mediaRepository);
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
    public boolean deleteMedia(int mediaId){
        Media exists = mediaRepository.findById(mediaId);

        if(exists != null){
            mediaRepository.delete(mediaId);
            return true;
        }

        return false;
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
