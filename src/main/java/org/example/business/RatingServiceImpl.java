package org.example.business;

import org.example.data.RatingRepository;
import org.example.domain.Rating;

import java.util.List;

public class RatingServiceImpl implements RatingService{


    private final RatingRepository ratingRepository;


    private static RatingService instance;


    private RatingServiceImpl(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }


    public static RatingService getInstance(RatingRepository ratingRepository){
        if(instance == null){
            instance = new RatingServiceImpl(ratingRepository);
        }

        return instance;
    }


    @Override
    public boolean createRating(Rating rating){
        Rating search = ratingRepository.findById(rating.getRatingId());

        if(search == null){
            ratingRepository.save(rating);
            return true;
        }
        return false;
    }


    @Override
    public Rating getRating(int ratingId){
        return ratingRepository.findById(ratingId);
    }


    @Override
    public boolean updateRating(Rating rating){
        Rating exists = ratingRepository.findById(rating.getRatingId());

        if(exists != null){
            ratingRepository.save(rating);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteRating(int ratingId){
        Rating exists = ratingRepository.findById(ratingId);

        if(exists != null){
            ratingRepository.delete(ratingId);
            return true;
        }

        return false;
    }


    @Override
    public List<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }


    @Override
    public List<Rating> getRatingsByUser(int userId){
        return ratingRepository.findByUserId(userId);
    }


    @Override
    public List<Rating> getRatingsByMedia(int mediaId){
        return ratingRepository.findByMediaId(mediaId);
    }

}
