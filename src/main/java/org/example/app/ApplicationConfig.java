package org.example.app;

import com.sun.net.httpserver.HttpHandler;
import org.example.app.business.*;
import org.example.app.data.*;
import org.example.app.presentation.*;
//import org.example.app.restserver.handler.LeaderboardHandler;
//import org.example.app.restserver.handler.MediaHandler;
//import org.example.app.restserver.handler.RatingHandler;
import org.example.app.restserver.handler.MediaHandler;
import org.example.app.restserver.handler.UserHandler;

import java.util.Map;

public class ApplicationConfig {
    // Repositories
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final RatingRepository ratingRepository;
    private final FavoritesRepository favoritesRepository;

    // Services
    private final AuthService authService;
    private final UserService userService;
    private final MediaService mediaService;
    private final RatingService ratingService;
    private final FavoritesService favoritesService;
    //private final LeaderboardService leaderboardService;

    // Controllers
    private final UserController userController;
    private final MediaController mediaController;
    private final RatingController ratingController;
    private final FavoritesController favoritesController;
    //private final LeaderboardController leaderboardController;

    // HTTP Handlers
    private final HttpHandler userHandler;
    private final HttpHandler mediaHandler;
    //private final HttpHandler ratingHandler;
    //private final HttpHandler leaderboardHandler;





    public ApplicationConfig(){

        // Repositories

        userRepository = UserRepositoryImpl.getInstance();

        mediaRepository = MediaRepositoryImpl.getInstance();

        ratingRepository = RatingRepositoryImpl.getInstance();

        favoritesRepository = FavoritesRepositoryImpl.getInstance();

        // Services
        authService = AuthServiceImpl.getInstance();

        userService = UserServiceImpl.getInstance(userRepository, mediaRepository, ratingRepository, authService);


        mediaService = MediaServiceImpl.getInstance(mediaRepository, favoritesRepository);


        ratingService = RatingServiceImpl.getInstance(ratingRepository);


        favoritesService = FavoritesServiceImpl.getInstance(favoritesRepository, mediaRepository);


        //Controllers

        userController = UserControllerImpl.getInstance(userService, ratingService);


        mediaController = MediaControllerImpl.getInstance(mediaService);


        ratingController = RatingControllerImpl.getInstance(ratingService);


        favoritesController = FavoritesControllerImpl.getInstance(favoritesService);



        //Handlers
        userHandler = UserHandler.getInstance(userController, authService);

        mediaHandler = MediaHandler.getInstance(mediaController, authService);

    }


    public HttpHandler getUserHandler() {
        return userHandler;
    }


    public HttpHandler getMediaHandler() { return mediaHandler; }
}
