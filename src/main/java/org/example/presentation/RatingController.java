package org.example.presentation;

import org.example.domain.Rating;

import java.util.List;

public interface RatingController {


    boolean createRating(Rating rating);

    Rating getRating(int ratingId);

    boolean updateRating(Rating rating);

    boolean deleteRating(int ratingId);

    List<Rating> getAllRatings();

    List<Rating> getRatingsByUser(int userId);

    List<Rating> getRatingsByMedia(int mediaId);
}
