package org.example.presentation;

import org.example.business.MediaService;
import org.example.domain.Media;

import java.util.List;


public class MediaControllerImpl implements MediaController {

    private final MediaService mediaService;

    private static MediaController instance;

    private MediaControllerImpl(MediaService mediaService){
        this.mediaService = mediaService;
    }

    public static MediaController getInstance(MediaService mediaService){
        if(instance == null){
            instance = new MediaControllerImpl(mediaService);
        }

        return instance;
    }


    @Override
    public boolean createMedia(Media media){
        return mediaService.createMedia(media);
    }


    @Override
    public Media getMedia(int mediaId){
        return mediaService.getMedia(mediaId);
    }


    @Override
    public boolean updateMedia(Media media){
        return mediaService.updateMedia(media);
    }


    @Override
    public boolean deleteMedia(int mediaId){
        return mediaService.deleteMedia(mediaId);
    }


    @Override
    public List<Media> getAllMedia(){
        return mediaService.getAllMedia();
    }


    @Override
    public List<Media> searchByTitle(String title){
        return mediaService.searchByTitle(title);
    }



}
