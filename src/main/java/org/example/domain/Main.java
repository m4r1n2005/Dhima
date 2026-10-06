package org.example.domain;

import org.example.business.*;
import org.example.data.*;
import org.example.presentation.*;

import java.time.Instant;
import java.util.List;

public class Main{






    public static void main(String[] args){

        //Data
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        MediaRepository mediaRepository = MediaRepositoryImpl.getInstance();
        RatingRepository ratingRepository = RatingRepositoryImpl.getInstance();


        //Business
        UserService userService = UserServiceImpl.getInstance(userRepository);
        MediaService mediaService = MediaServiceImpl.getInstance(mediaRepository);
        RatingService ratingService = RatingServiceImpl.getInstance(ratingRepository);

        //Presentation
        UserController userController = UserControllerImpl.getInstance(userService);
        MediaController mediaController = MediaControllerImpl.getInstance(mediaService);
        RatingController ratingController = RatingControllerImpl.getInstance(ratingService);



        User user1 = new User(
                1,
                "martin_dhima",
                "12345678",
                "if25b002@technikum-wien.at",
                "thriller"

        );


        Media movie = new Movie(
                1,
                "The Dark Knight",
                "Batman fights the Joker in Gotham City.",
                2008,
                List.of("Action", "Crime", "Drama"),
                16
        );


        Media serie = new Serie(
                2,
                "Breaking Bad",
                "A chemistry teacher starts making drugs to earn money for his family",
                2008,
                List.of("crime", "drama", "thriller"),
                18
        );



        Media game = new Game(
                3,
                "Red Dead Redemption 2",
                "An open-world western action-adventure game.",
                2018,
                List.of("Action", "Adventure"),
                18
        );


        Rating rating = new Rating(
                1,
                user1.getUserId(),
                movie.getMediaId(),
                5,
                "Best movie ever",
                Instant.now()
        );




        //USER

        System.out.println("\n===== USER =====");

// Register
        System.out.println("Register user:");
        System.out.println(userController.register(user1));

// Try duplicate registration
        System.out.println("\nRegister same user again:");
        System.out.println(userController.register(user1));

// Login - correct password
        System.out.println("\nLogin with correct password:");
        System.out.println(
                userController.login("martin_dhima", "12345678")
        );

// Login - wrong password
        System.out.println("\nLogin with wrong password:");
        System.out.println(
                userController.login("martin_dhima", "wrongPassword")
        );

// Get profile
        System.out.println("\nGet profile:");
        System.out.println(
                userController.getProfile(user1.getUserId())
        );

// Update profile
        System.out.println("\nUpdate profile:");

        user1.setFavoriteGenre("action");

        System.out.println(
                userController.updateProfile(user1)
        );

        System.out.println("Profile after update:");
        System.out.println(
                userController.getProfile(user1.getUserId())
        );


        //MEDIA

        System.out.println("\n===== MEDIA =====");

// Create media
        System.out.println("Create movie:");
        System.out.println(
                mediaController.createMedia(movie)
        );

        System.out.println("Create serie:");
        System.out.println(
                mediaController.createMedia(serie)
        );

        System.out.println("Create game:");
        System.out.println(
                mediaController.createMedia(game)
        );

// Try duplicate
        System.out.println("\nCreate same movie again:");
        System.out.println(
                mediaController.createMedia(movie)
        );

// Get one media
        System.out.println("\nGet media with ID 1:");
        System.out.println(
                mediaController.getMedia(1)
        );

// Get all
        System.out.println("\nAll media:");
        System.out.println(
                mediaController.getAllMedia()
        );

// Search by title
        System.out.println("\nSearch for The Dark Knight:");
        System.out.println(
                mediaController.searchByTitle("The Dark Knight")
        );

// Update media
        System.out.println("\nUpdate movie:");

        movie.setDescription("Updated Batman description.");

        System.out.println(
                mediaController.updateMedia(movie)
        );

        System.out.println("Movie after update:");
        System.out.println(
                mediaController.getMedia(movie.getMediaId())
        );


        //RATING

        System.out.println("\n===== RATING =====");

// Create rating
        System.out.println("Create rating:");
        System.out.println(
                ratingController.createRating(rating)
        );

// Try duplicate
        System.out.println("\nCreate same rating again:");
        System.out.println(
                ratingController.createRating(rating)
        );

// Get rating
        System.out.println("\nGet rating:");
        System.out.println(
                ratingController.getRating(rating.getRatingId())
        );

// Get all ratings
        System.out.println("\nAll ratings:");
        System.out.println(
                ratingController.getAllRatings()
        );

// Ratings by user
        System.out.println("\nRatings by user:");
        System.out.println(
                ratingController.getRatingsByUser(user1.getUserId())
        );

// Ratings by media
        System.out.println("\nRatings for movie:");
        System.out.println(
                ratingController.getRatingsByMedia(movie.getMediaId())
        );

// Update rating
        System.out.println("\nUpdate rating:");

        rating.setStars(4);
        rating.setComment("Still a very good movie.");

        System.out.println(
                ratingController.updateRating(rating)
        );

        System.out.println("Rating after update:");
        System.out.println(
                ratingController.getRating(rating.getRatingId())
        );


        //DELETE

        System.out.println("\n===== DELETE =====");

        System.out.println("Delete rating:");
        System.out.println(
                ratingController.deleteRating(rating.getRatingId())
        );

        System.out.println("Ratings after deletion:");
        System.out.println(
                ratingController.getAllRatings()
        );

        System.out.println("\nDelete game:");
        System.out.println(
                mediaController.deleteMedia(game.getMediaId())
        );

        System.out.println("Media after deletion:");
        System.out.println(
                mediaController.getAllMedia()
        );


    }

}