package org.example.app.presentation;

import org.example.app.domain.Media;
import org.example.app.domain.MediaNotFoundException;

import java.util.List;

public interface MediaController {


    boolean createMedia(Media media);

    Media getMedia(int mediaId);

    boolean updateMedia(Media media);

    void deleteMedia(int mediaId) throws MediaNotFoundException;

    List<Media> getAllMedia();

    List<Media> searchByTitle(String title);
}
