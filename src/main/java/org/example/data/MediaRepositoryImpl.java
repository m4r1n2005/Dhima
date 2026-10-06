package org.example.data;

import org.example.domain.Media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaRepositoryImpl implements MediaRepository{
    private final Map<Integer, Media> medias;


    private static MediaRepository instance;

    private MediaRepositoryImpl(){
        medias = new HashMap<>();
    }


    public static MediaRepository getInstance(){
        if(instance == null){
            instance = new MediaRepositoryImpl();
        }
        return instance;
    }


    @Override
    public void save(Media media){
        medias.put(media.getMediaId(), media);
    }


    @Override
    public Media findById(int mediaId){ return medias.get(mediaId); }

    @Override
    public List<Media> findAll(){
        return new ArrayList<>(medias.values());
    }

    @Override
    public void delete(int mediaId){
        medias.remove(mediaId);
    }

    @Override
    public List<Media> findTitle(String title){
        List<Media> list = new ArrayList<>();
        for(Media media : medias.values()){
            if(media.getTitle().equals(title)){
                list.add(media);
            }
        }
        return list;
    }

}
