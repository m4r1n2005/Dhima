package org.example.data;

import org.example.domain.Rating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingRepositoryImpl implements RatingRepository{

    private final Map<Integer, Rating> ratings;


    private static RatingRepository instance;


    private RatingRepositoryImpl(){
        ratings = new HashMap<>();
    }


    public static RatingRepository getInstance(){
        if(instance == null){
            instance = new RatingRepositoryImpl();
        }

        return instance;
    }



    @Override
    public void save(Rating rating){
        ratings.put(rating.getRatingId(), rating);
    }


    @Override
    public Rating findById(int ratingId){
        return ratings.get(ratingId);
    }


    @Override
    public List<Rating> findAll(){
        return new ArrayList<>(ratings.values());
    }


    @Override
    public List<Rating> findByUserId(int userId){
        List<Rating> list = new ArrayList<>();
        for(Rating rating : ratings.values()){
            if(rating.getUserId() == userId){
                list.add(rating);
            }
        }

        return list;
    }


    @Override
    public List<Rating> findByMediaId(int mediaId){
        List<Rating> list = new ArrayList<>();
        for(Rating rating : ratings.values()){
            if(rating.getMediaId() == mediaId){
                list.add(rating);
            }
        }

        return list;
    }


    @Override
    public void delete(int ratingId){
        ratings.remove(ratingId);
    }

    @Override
    public List<Rating> findByStars(int stars){
        List<Rating> list = new ArrayList<>();
        for(Rating rating : ratings.values()){
            if(rating.getStars() == stars){
                list.add(rating);
            }
        }

        return list;
    }

}
