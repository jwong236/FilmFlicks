package com.filmflicks.services;

import com.filmflicks.models.Movie;
import com.filmflicks.models.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.ResultSet;

@Service
public class DatabaseService {

    @Autowired
    private DataSource dataSource;

    /**
     * Inserts a movie using a stored procedure.
     *
     * @param movie The movie object containing details to insert.
     * @return Success or error message.
     */
    public String insertMovie(Movie movie) {
        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            cstmt.setString(1, movie.getTitle());
            cstmt.setInt(2, movie.getYear());
            cstmt.setString(3, movie.getDirector());

            String mainStar = movie.getStars().isEmpty() ? null : movie.getStars().get(0);
            cstmt.setString(4, mainStar);
            cstmt.setNull(5, Types.INTEGER);
            String mainGenre = movie.getGenres().isEmpty() ? null : movie.getGenres().get(0);
            cstmt.setString(6, mainGenre);

            cstmt.registerOutParameter(7, Types.CHAR, 9);
            cstmt.registerOutParameter(8, Types.CHAR, 9);
            cstmt.registerOutParameter(9, Types.INTEGER);
            cstmt.registerOutParameter(10, Types.VARCHAR);

            cstmt.execute();

            String newMovieId = cstmt.getString(7);
            String newStarId = cstmt.getString(8);
            int newGenreId = cstmt.getInt(9);
            String outputMessage = cstmt.getString(10);

            return "{\"status\": \"success\", \"newMovieId\": \"" + newMovieId +
                    "\", \"newStarId\": \"" + newStarId +
                    "\", \"newGenreId\": " + newGenreId +
                    ", \"message\": \"" + outputMessage + "\"}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Inserts a star using a stored procedure.
     *
     * @param star The star object containing details to insert.
     * @return Success or error message.
     */
    public String insertStar(Star star) {
        String resultMessage = "";

        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL add_star(?, ?)}")) {

            // Set the star's name
            cstmt.setString(1, star.getName());

            // Check if birthYear is not null or empty, then parse it to an integer
            if (star.getBirthYear() != null && !star.getBirthYear().trim().isEmpty()) {
                try {
                    int birthYear = Integer.parseInt(star.getBirthYear());
                    cstmt.setInt(2, birthYear);
                } catch (NumberFormatException e) {
                    return "Error: Invalid birth year format.";
                }
            } else {
                cstmt.setNull(2, java.sql.Types.INTEGER);
            }

            boolean hasResults = cstmt.execute();

            // Handle results
            if (hasResults) {
                try (ResultSet rs = cstmt.getResultSet()) {
                    if (rs.next()) {
                        resultMessage = "Star added successfully: " + rs.getString("starId");
                    }
                }
            } else {
                resultMessage = "Star added, but no ID returned.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }

        return resultMessage;
    }

    /**
     * Deletes a movie by its ID.
     *
     * @param movieId The ID of the movie to delete.
     * @return Success or error message.
     */
    public String deleteMovie(String movieId) {
        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL delete_movie(?)}")) {
            cstmt.setString(1, movieId);
            cstmt.execute();
            return "{\"status\": \"success\", \"message\": \"Movie deleted successfully.\"}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Deletes a star by its ID.
     *
     * @param starId The ID of the star to delete.
     * @return Success or error message.
     */
    public String deleteStar(String starId) {
        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL delete_star(?)}")) {
            cstmt.setString(1, starId);
            cstmt.execute();
            return "{\"status\": \"success\", \"message\": \"Star deleted successfully.\"}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }
}
