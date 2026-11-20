package org.example.app.business;

import org.example.app.data.MediaRepository;
import org.example.app.data.RatingRepository;
import org.example.app.data.UserRepository;
import org.example.app.domain.Media;

import java.util.List;

public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final RatingRepository ratingRepository;

    private static RecommendationService instance;

    private RecommendationServiceImpl(
            UserRepository userRepository,
            MediaRepository mediaRepository,
            RatingRepository ratingRepository) {

        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.ratingRepository = ratingRepository;
    }

    public static RecommendationService getInstance(
            UserRepository userRepository,
            MediaRepository mediaRepository,
            RatingRepository ratingRepository) {

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