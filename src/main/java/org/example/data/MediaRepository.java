package org.example.data;

import org.example.domain.Media;

import java.util.List;


public interface MediaRepository {

    void save(Media media);

    Media findById(int mediaId);

    List<Media> findAll();

    void delete(int mediaId);

    List<Media> findTitle(String title);
}
