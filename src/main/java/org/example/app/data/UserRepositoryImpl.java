package org.example.app.data;


import org.example.app.data.dbncore.DataAccessException;
import org.example.app.data.dbncore.UnitOfWork;
import org.example.app.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    private static UserRepository instance;


    private UserRepositoryImpl(){

    }


    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepositoryImpl();
        }

        return instance;
    }


    @Override
    public void save(User user){
        boolean isNew = user.getUserId() == 0;

        String sql;

        if (isNew) {
            sql = """
                INSERT INTO users
                (username, password_hash, email, favorite_genre)
                VALUES (?, ?, ?, ?)
                RETURNING user_id
                """;
        } else {
            sql = """
                UPDATE users
                SET username = ?, email = ?, favorite_genre = ?
                WHERE user_id = ?
                """;
        }

        try (UnitOfWork uow = new UnitOfWork();
             PreparedStatement stmt = uow.prepareStatement(sql)) {

            int generatedId = 0;

            if (isNew) {

                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPasswordHash());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFavoriteGenre());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        generatedId = rs.getInt("user_id");
                    } else {
                        throw new DataAccessException("User ID not returned");
                    }
                }

            } else {

                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getFavoriteGenre());
                stmt.setInt(4, user.getUserId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows != 1) {
                    throw new DataAccessException("User not found");
                }
            }

            uow.commitTransaction();

            if (isNew) {
                user.setUserId(generatedId);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to save user", e);
        }
    }


    @Override
    public User findById(int userId){

        String sql = """
                SELECT user_id, username, password_hash, email, favorite_genre
                FROM users
                WHERE user_id = ?
                """;


        try (UnitOfWork uow = new UnitOfWork();
        PreparedStatement stmt = uow.prepareStatement(sql)) {

            stmt.setInt(1, userId);


            User user = null;

            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("email"),
                            rs.getString("favorite_genre")
                    );
                }
            }


            return user;


        } catch(SQLException e){
            throw new DataAccessException("Failed to find user", e);
        }
    }



    @Override
    public User findByUsername(String username) {

        String sql = """
            SELECT user_id, username, password_hash, email, favorite_genre
            FROM users
            WHERE username = ?
            """;

        try (UnitOfWork uow = new UnitOfWork();
             PreparedStatement stmt = uow.prepareStatement(sql)) {

            stmt.setString(1, username);

            User user = null;

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("email"),
                            rs.getString("favorite_genre")
                    );
                }
            }


            return user;

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Failed to find user by username", e
            );
        }
    }


    @Override
    public User findByEmail(String email){

        String sql = """
                SELECT user_id, username, password_hash, email, favorite_genre
                FROM users
                WHERE email = ? 
                """;

        try (UnitOfWork uow = new UnitOfWork();
        PreparedStatement stmt = uow.prepareStatement(sql)) {

            stmt.setString(1, email);

            User user = null;

            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("email"),
                            rs.getString("favorite_genre")
                    );
                }
            }

            return user;

        } catch (SQLException e){
            throw new DataAccessException("Failed to find user", e);
        }
    }




    @Override
    public List<User> findAll(){
        String sql = """
                SELECT user_id, username, password_hash, email, favorite_genre
                FROM users
                """;
         try (UnitOfWork uow = new UnitOfWork();
         PreparedStatement stmt = uow.prepareStatement(sql)) {

             List<User> userList = new ArrayList<>();

             try(ResultSet rs = stmt.executeQuery()) {
                 while(rs.next()){
                     User user = new User(
                             rs.getInt("user_id"),
                             rs.getString("username"),
                             rs.getString("password_hash"),
                             rs.getString("email"),
                             rs.getString("favorite_genre")
                     );
                     userList.add(user);
                 }
             }

             return userList;

         } catch (SQLException e){
             throw new DataAccessException("Failed to find all users", e);
         }
    }


}
