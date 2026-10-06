package org.example.business;

import org.example.domain.Media;

import java.util.List;

public interface MediaService {

    boolean createMedia(Media media);

    Media getMedia(int mediaId);

    boolean updateMedia(Media media);

    boolean deleteMedia(int mediaId);

    List<Media> getAllMedia();

    List<Media> searchByTitle(String title);
}
