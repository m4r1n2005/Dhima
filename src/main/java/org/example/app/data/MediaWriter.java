package org.example.app.data;

import org.example.app.domain.Media;

public interface MediaWriter {

    void save(Media media);

    void delete(int mediaId);
}