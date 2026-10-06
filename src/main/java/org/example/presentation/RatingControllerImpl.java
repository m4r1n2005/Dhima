package org.example.presentation;

import org.example.business.RatingService;
import org.example.domain.Rating;
import org.w3c.dom.ls.LSOutput;

import java.util.List;

public class RatingControllerImpl implements RatingController{

    private final RatingService ratingService;

    private static RatingController instance;

    private RatingControllerImpl(RatingService ratingService){
        this.ratingService = ratingService;
    }

    public static RatingController getInstance(RatingService ratingService){
        if(instance == null){
            instance = new RatingControllerImpl(ratingService);
        }

        return instance;
    }


    @Override
    public boolean createRating(Rating rating){
        return ratingService.createRating(rating);
    }


    @Override
    public Rating getRating(int ratingId){
        return ratingService.getRating(ratingId);
    }


    @Override
    public boolean updateRating(Rating rating){
        return ratingService.updateRating(rating);
    }


    @Override
    public boolean deleteRating(int ratingId){
        return ratingService.deleteRating(ratingId);
    }


    @Override
    public List<Rating> getAllRatings(){
        return ratingService.getAllRatings();
    }


    @Override
    public List<Rating> getRatingsByUser(int userId){
        return ratingService.getRatingsByUser(userId);
    }


    @Override
    public List<Rating> getRatingsByMedia(int mediaId) {
        return ratingService.getRatingsByMedia(mediaId);
    }



}
