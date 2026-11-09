package org.example.app.data;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class FavoritesRepositoryImpl implements FavoritesRepository{

    private final Map<Integer, Set<Integer>> favoritesByUser;

    private static FavoritesRepository instance;

    private FavoritesRepositoryImpl(){
        this.favoritesByUser = new HashMap<>();
    }

    public static FavoritesRepository getInstance(){
        if(instance == null){
            instance = new FavoritesRepositoryImpl();
        }

        return instance;
    }


    @Override
    public Set<Integer> getFavoritesByUser(int userId){
        Set<Integer> favorites = favoritesByUser.get(userId);

        if (favorites == null) {
            return new HashSet<>();
        }

        return new HashSet<>(favorites);
    }

    @Override
    public boolean addFavorite(int userId, int mediaId){
        Set<Integer> set = favoritesByUser.get(userId);

        if (set == null) {
            set = new HashSet<>();
            favoritesByUser.put(userId, set);
        }

        return set.add(mediaId);
    }

    @Override
    public boolean removeFavorite(int userId, int mediaId){
        Set<Integer> set = favoritesByUser.get(userId);

        if(set == null){
            return false;
        }

        return set.remove(mediaId);
    }

    @Override
    public int getFavoriteCount(int mediaId){
        int count = 0;
        for(Set<Integer> set : favoritesByUser.values()){
            if(set.contains(mediaId)){
                ++count;
            }
        }
        return count;
    }


    @Override
    public void removeFavoritesByMediaId(int mediaId) {
        for (Set<Integer> set : favoritesByUser.values()) {
            set.remove(mediaId);
        }
    }
}
