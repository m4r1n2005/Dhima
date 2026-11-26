package org.example.app.data;

import org.example.app.domain.Rating;
import java.util.List;

public interface RatingReader {

    Rating findById(int ratingId);

    List<Rating> findAll();

    List<Rating> findByUserId(int userId);

    List<Rating> findByMediaId(int mediaId);

    List<Rating> findByStars(int stars);
}