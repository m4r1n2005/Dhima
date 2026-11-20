
package org.example.app.domain;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MediaFactory {

    @FunctionalInterface
    public interface MediaCreator {
        Media create(
                int mediaId,
                String title,
                String description,
                int releaseYear,
                List<String> genres,
                int ageRestriction,
                int creatorUserId
        );
    }

    private static final Map<String, MediaCreator> creators =
            new ConcurrentHashMap<>();

    static {
        register("Movie", Movie::new);
        register("Serie", Serie::new);
        register("Game", Game::new);
    }

    private MediaFactory() {
    }

    public static void register(
            String mediaType,
            MediaCreator creator
    ) {
        creators.put(
                mediaType.toLowerCase(Locale.ROOT),
                creator
        );
    }

    public static Media create(
            String mediaType,
            int mediaId,
            String title,
            String description,
            int releaseYear,
            List<String> genres,
            int ageRestriction,
            int creatorUserId
    ) {
        if (mediaType == null) {
            throw new IllegalArgumentException("Invalid media type");
        }

        MediaCreator creator = creators.get(
                mediaType.toLowerCase(Locale.ROOT)
        );

        if (creator == null) {
            throw new IllegalArgumentException("Invalid media type");
        }

        return creator.create(
                mediaId,
                title,
                description,
                releaseYear,
                genres,
                ageRestriction,
                creatorUserId
        );
    }
}
