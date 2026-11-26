package org.example.app.data;

import org.example.app.domain.Rating;

public interface RatingWriter {

    void save(Rating rating);

    void delete(int ratingId);
}