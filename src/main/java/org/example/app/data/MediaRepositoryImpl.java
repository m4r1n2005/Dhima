package org.example.app.data;

import org.example.app.data.dbncore.DataAccessException;
import org.example.app.data.dbncore.UnitOfWork;
import org.example.app.domain.Media;
import org.example.app.domain.User;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;


public class MediaRepositoryImpl implements MediaRepository{


    private static MediaRepository instance;

    private MediaRepositoryImpl(){

    }


    public static MediaRepository getInstance(){
        if(instance == null){
            instance = new MediaRepositoryImpl();
        }
        return instance;
    }


    @Override
    @Override
    public void save(Media media) {
        boolean isNew = media.getMediaId() == 0;

        String sql;

        if (isNew) {
            sql = """
                INSERT INTO media
                (title, description, media_type, release_year,
                 age_restriction, creator_user_id, genres)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING media_id
                """;
        } else {
            sql = """
                UPDATE media
                SET title = ?,
                    description = ?,
                    media_type = ?,
                    release_year = ?,
                    age_restriction = ?,
                    genres = ?
                WHERE media_id = ?
                """;
        }

        try (UnitOfWork uow = new UnitOfWork();
             PreparedStatement stmt = uow.prepareStatement(sql)) {

            int generatedId = 0;

            // Convert List<String> to a PostgreSQL TEXT[] array
            Array sqlGenres = null;

            try {
                if (media.getGenres() != null) {
                    sqlGenres = stmt.getConnection().createArrayOf(
                            "text",
                            media.getGenres().toArray(new String[0])
                    );
                }

                if (isNew) {
                    stmt.setString(1, media.getTitle());
                    stmt.setString(2, media.getDescription());
                    stmt.setString(3, media.getMediaType());
                    stmt.setInt(4, media.getReleaseYear());
                    stmt.setInt(5, media.getAgeRestriction());
                    stmt.setInt(6, media.getCreatorUserId());
                    stmt.setArray(7, sqlGenres);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            generatedId = rs.getInt("media_id");
                        } else {
                            throw new DataAccessException("Media ID not returned");
                        }
                    }

                } else {
                    stmt.setString(1, media.getTitle());
                    stmt.setString(2, media.getDescription());
                    stmt.setString(3, media.getMediaType());
                    stmt.setInt(4, media.getReleaseYear());
                    stmt.setInt(5, media.getAgeRestriction());
                    stmt.setArray(6, sqlGenres);
                    stmt.setInt(7, media.getMediaId());

                    int affectedRows = stmt.executeUpdate();

                    if (affectedRows != 1) {
                        throw new DataAccessException("Media not found");
                    }
                }

                uow.commitTransaction();

                if (isNew) {
                    media.setMediaId(generatedId);
                }

            } finally {
                if (sqlGenres != null) {
                    sqlGenres.free();
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to save media", e);
        }
    }


    @Override
    public Media findById(int mediaId) {
        String sql = """
            SELECT * FROM media
            WHERE media_id = ?
            """;

        try (UnitOfWork uow = new UnitOfWork();
             PreparedStatement stmt = uow.prepareStatement(sql)) {

            stmt.setInt(1, mediaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedia(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find media", e);
        }
    }

    @Override
    public List<Media> findAll(){
        return new ArrayList<>(medias.values());
    }

    @Override
    public void delete(int mediaId){
        medias.remove(mediaId);
    }

    @Override
    public List<Media> findTitle(String title){
        List<Media> list = new ArrayList<>();
        for(Media media : medias.values()){
            if(media.getTitle().equals(title)){
                list.add(media);
            }
        }
        return list;
    }

}
