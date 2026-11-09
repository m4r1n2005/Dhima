package org.example.app.business;

import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;

public interface MediaService {

    boolean createMedia(Media media);

    Media getMedia(int mediaId);

    boolean updateMedia(Media media);

    void deleteMedia(int mediaId) throws MediaNotFoundException;

    List<Media> getAllMedia();

    List<Media> searchByTitle(String title);
}
