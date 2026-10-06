package org.example.data;

import org.example.domain.Rating;

import java.util.List;

public interface RatingRepository {


    void save(Rating rating);

    Rating findById(int ratingId);

    List<Rating> findAll();

    List<Rating> findByUserId(int userId);

    List<Rating> findByMediaId(int mediaId);

    void delete(int ratingId);

    List<Rating> findByStars(int stars);


}
