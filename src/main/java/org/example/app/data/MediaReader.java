package org.example.app.data;



import org.example.app.domain.Media;
import java.util.List;

public interface MediaReader {

    Media findById(int mediaId);

    List<Media> findAll();

    List<Media> findTitle(String title);
}
