package org.example.app.business;

import org.example.app.domain.Media;

import java.util.List;

public interface MediaService {

    boolean createMedia(Media media);

    Media getMedia(int mediaId);

    boolean updateMedia(Media media);

    boolean deleteMedia(int mediaId);

    List<Media> getAllMedia();

    List<Media> searchByTitle(String title);
}
