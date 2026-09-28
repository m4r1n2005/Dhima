package org.example;

import java.time.Instant;
import java.util.List;

public class Main{




    public static void main(String[] args){
        User user1 = new User(
                1,
                "martin_dhima",
                "12345678",
                "if25b002@technikum-wien.at",
                "thriller"

        );

        Media thrillerMovie = new Media(
                1,
                "Breaking Bad",
                "A chemistry teacher starts making drugs to earn money for his family",
                "series",
                2008,
                List.of("crime", "drama", "thriller"),
                18
        );

        Rating rating = new Rating(
                1,
                user1.getUserId(),
                thrillerMovie.getMediaId(),
                5,
                "Best movie ever",
                Instant.now()
        );


        System.out.println("Hello " + user1.getUsername() + "!");


    }

}