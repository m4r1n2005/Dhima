package org.example.presentation;

import org.example.domain.Media;

import java.util.List;

public interface MediaController {


    boolean createMedia(Media media);

    Media getMedia(int mediaId);

    boolean updateMedia(Media media);

    boolean deleteMedia(int mediaId);

    List<Media> getAllMedia();

    List<Media> searchByTitle(String title);
}
