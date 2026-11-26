package org.example.app.business;

import org.example.app.data.MediaReader;
import org.example.app.data.RatingReader;
import org.example.app.data.UserReader;
import org.example.app.domain.Media;

import java.util.List;

public class RecommendationServiceImpl implements RecommendationService {

    private final UserReader userRepository;
    private final MediaReader mediaRepository;
    private final RatingReader ratingRepository;

    private static RecommendationService instance;

    private RecommendationServiceImpl(
            UserReader userRepository,
            MediaReader mediaRepository,
            RatingReader ratingRepository) {

        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.ratingRepository = ratingRepository;
    }

    public static RecommendationService getInstance(
            UserReader userRepository,
            MediaReader mediaRepository,
            RatingReader ratingRepository) {

        if (instance == null) {
            instance = new RecommendationServiceImpl(
                    userRepository,
                    mediaRepository,
                    ratingRepository
            );
        }

        return instance;
    }

    @Override
    public List<Media> getRecommendations(int userId, String type){
        if(type.equals("genre")){
            String favoriteGenre = userRepository.findById(userId).getFavoriteGenre();

            return mediaRepository.findAll().stream().filter(media -> media.getGenres().contains(favoriteGenre)).toList();
        } else if(type.equals("content")){


            return ratingRepository.findByUserId(userId).stream().filter(rating -> rating.getStars() > 3).map(rating -> mediaRepository.findById(rating.getMediaId())).toList();
        }


        return null;
    }
}