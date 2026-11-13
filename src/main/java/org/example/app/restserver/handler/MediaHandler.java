
package org.example.app.restserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.example.app.business.AuthService;
import org.example.app.domain.*;
import org.example.app.presentation.MediaController;
import org.example.app.presentation.dto.MediaRequest;
import org.example.app.restserver.http.ContentType;
import org.example.app.restserver.http.HttpStatus;
import org.example.app.restserver.server.Request;
import org.example.app.restserver.server.Response;

import java.io.IOException;
import java.util.List;

public class MediaHandler implements HttpHandler {

    private final MediaController mediaController;
    private final AuthService authService;

    private static HttpHandler instance;

    private MediaHandler(MediaController mediaController,
                         AuthService authService) {
        this.mediaController = mediaController;
        this.authService = authService;
    }

    public static HttpHandler getInstance(MediaController mediaController,
                                          AuthService authService) {
        if (instance == null) {
            instance = new MediaHandler(mediaController, authService);
        }

        return instance;
    }

    private Integer getAuthenticatedUserId(Request request) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {
            return null;
        }

        String token = authorization.substring(7).trim();

        if (token.isEmpty()) {
            return null;
        }

        return authService.validateToken(token);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Request request = new Request(exchange);

        ObjectMapper mapper = new ObjectMapper();

        List<String> parts = request.getPathParts();

        if ((parts.size() != 2 && parts.size() != 3) ||
                !parts.get(0).equals("api") ||
                !parts.get(1).equals("media")) {

            new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                    "{\"error\":\"Endpoint not found\"}").send(exchange);
            return;
        }

        Integer mediaId = null;

        if (parts.size() == 3) {
            try {
                mediaId = Integer.parseInt(parts.get(2));
            } catch (NumberFormatException e) {
                new Response(HttpStatus.BAD_REQUEST, ContentType.JSON,
                        "{\"error\":\"Invalid media ID\"}").send(exchange);
                return;
            }
        }

        switch (request.getMethod()) {

            case GET: {

                if (mediaId == null) {

                    List<Media> mediaList = mediaController.getAllMedia();

                    String json = mapper.writeValueAsString(mediaList);

                    new Response(HttpStatus.OK, ContentType.JSON, json)
                            .send(exchange);

                } else {

                    Media media = mediaController.getMedia(mediaId);

                    if (media == null) {
                        new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                                "{\"error\":\"Media not found\"}")
                                .send(exchange);
                        break;
                    }

                    String json = mapper.writeValueAsString(media);

                    new Response(HttpStatus.OK, ContentType.JSON, json)
                            .send(exchange);
                }

                break;
            }

            case POST: {

                if (mediaId != null) {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Endpoint not found\"}")
                            .send(exchange);
                    break;
                }

                Integer userId = getAuthenticatedUserId(request);

                if (userId == null) {
                    new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON,
                            "{\"error\":\"Invalid or missing token\"}")
                            .send(exchange);
                    break;
                }

                MediaRequest mediaRequest = mapper.readValue(
                        request.getBody(), MediaRequest.class
                );

                Media media = null;

                switch (mediaRequest.getMediaType()) {

                    case "Movie", "movie":
                        media = new Movie(
                                0,
                                mediaRequest.getTitle(),
                                mediaRequest.getDescription(),
                                mediaRequest.getReleaseYear(),
                                mediaRequest.getGenres(),
                                mediaRequest.getAgeRestriction(),
                                userId
                        );
                        break;

                    case "Serie", "serie":
                        media = new Serie(
                                0,
                                mediaRequest.getTitle(),
                                mediaRequest.getDescription(),
                                mediaRequest.getReleaseYear(),
                                mediaRequest.getGenres(),
                                mediaRequest.getAgeRestriction(),
                                userId
                        );
                        break;

                    case "Game", "game":
                        media = new Game(
                                0,
                                mediaRequest.getTitle(),
                                mediaRequest.getDescription(),
                                mediaRequest.getReleaseYear(),
                                mediaRequest.getGenres(),
                                mediaRequest.getAgeRestriction(),
                                userId
                        );
                        break;
                }

                if (media == null) {
                    new Response(HttpStatus.BAD_REQUEST, ContentType.JSON,
                            "{\"error\":\"Invalid media type\"}")
                            .send(exchange);
                    break;
                }

                boolean created = mediaController.createMedia(media);

                if (created) {

                    String json = mapper.writeValueAsString(media);

                    new Response(HttpStatus.CREATED, ContentType.JSON, json)
                            .send(exchange);

                } else {
                    new Response(HttpStatus.CONFLICT, ContentType.JSON,
                            "{\"error\":\"Media already exists\"}")
                            .send(exchange);
                }

                break;
            }

            case PUT: {

                if (mediaId == null) {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Endpoint not found\"}")
                            .send(exchange);
                    break;
                }

                Integer userId = getAuthenticatedUserId(request);

                if (userId == null) {
                    new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON,
                            "{\"error\":\"Invalid or missing token\"}")
                            .send(exchange);
                    break;
                }

                Media media = mediaController.getMedia(mediaId);

                if (media == null) {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Media not found\"}")
                            .send(exchange);
                    break;
                }

                if (media.getCreatorUserId() != userId) {
                    new Response(HttpStatus.FORBIDDEN, ContentType.JSON,
                            "{\"error\":\"Access denied\"}")
                            .send(exchange);
                    break;
                }

                MediaRequest mediaRequest = mapper.readValue(
                        request.getBody(), MediaRequest.class
                );

                if (mediaRequest.getTitle() != null) {
                    media.setTitle(mediaRequest.getTitle());
                }

                if (mediaRequest.getDescription() != null) {
                    media.setDescription(mediaRequest.getDescription());
                }

                if (mediaRequest.getReleaseYear() != null) {
                    media.setReleaseYear(mediaRequest.getReleaseYear());
                }

                if (mediaRequest.getGenres() != null) {
                    media.setGenres(mediaRequest.getGenres());
                }

                if (mediaRequest.getAgeRestriction() != null) {
                    media.setAgeRestriction(mediaRequest.getAgeRestriction());
                }

                boolean updated = mediaController.updateMedia(media);

                if (updated) {

                    String json = mapper.writeValueAsString(media);

                    new Response(HttpStatus.OK, ContentType.JSON, json)
                            .send(exchange);

                } else {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Media not found\"}")
                            .send(exchange);
                }

                break;
            }

            case DELETE: {

                if (mediaId == null) {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Endpoint not found\"}")
                            .send(exchange);
                    break;
                }

                Integer userId = getAuthenticatedUserId(request);

                if (userId == null) {
                    new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON,
                            "{\"error\":\"Invalid or missing token\"}")
                            .send(exchange);
                    break;
                }

                Media media = mediaController.getMedia(mediaId);

                if (media == null) {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Media not found\"}")
                            .send(exchange);
                    break;
                }

                if (media.getCreatorUserId() != userId) {
                    new Response(HttpStatus.FORBIDDEN, ContentType.JSON,
                            "{\"error\":\"Access denied\"}")
                            .send(exchange);
                    break;
                }

                try {

                    mediaController.deleteMedia(mediaId);

                    new Response(HttpStatus.NO_CONTENT, ContentType.JSON,
                            null).send(exchange);

                } catch (MediaNotFoundException e) {
                    new Response(HttpStatus.NOT_FOUND, ContentType.JSON,
                            "{\"error\":\"Media not found\"}")
                            .send(exchange);
                }

                break;
            }

            default: {

                new Response(HttpStatus.BAD_REQUEST, ContentType.JSON,
                        "{\"error\":\"Invalid method\"}")
                        .send(exchange);
            }
        }
    }
}
