package com.filmflicks.services;

import com.filmflicks.models.Movie;
import com.filmflicks.models.Star;
import com.filmflicks.repositories.MovieRepository;
import com.filmflicks.repositories.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StarRepository starRepository;

    /**
     * Inserts a movie using the stored procedure in MovieRepository.
     *
     * @param movie The movie object containing details to insert.
     * @return Success or error message.
     */
    public String insertMovie(Movie movie) {
        try {
            // Call the stored procedure in the repository
            String result = movieRepository.addMovie(
                    movie.getTitle(),
                    movie.getYear(),
                    movie.getDirector(),
                    movie.getStars().isEmpty() ? null : movie.getStars().iterator().next().getName(),
                    movie.getGenres().isEmpty() ? null : movie.getGenres().iterator().next().getName()
            );
            return "{\"status\": \"success\", \"message\": \"" + result + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Inserts a star using the stored procedure in StarRepository.
     *
     * @param star The star object containing details to insert.
     * @return Success or error message.
     */
    public String insertStar(Star star) {
        try {
            Integer birthYear = star.getBirthYear() != null ? star.getBirthYear() : 0;
            String result = starRepository.addStar(star.getName(), birthYear);
            return "{\"status\": \"success\", \"message\": \"" + result + "\"}";
        } catch (NumberFormatException e) {
            return "{\"status\": \"error\", \"message\": \"Invalid birth year format.\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Deletes a movie using JPA.
     *
     * @param movieId The ID of the movie to delete.
     * @return Success or error message.
     */
    public String deleteMovie(String movieId) {
        try {
            movieRepository.deleteById(movieId);
            return "{\"status\": \"success\", \"message\": \"Movie deleted successfully.\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Deletes a star using JPA.
     *
     * @param starId The ID of the star to delete.
     * @return Success or error message.
     */
    public String deleteStar(String starId) {
        try {
            starRepository.deleteById(starId);
            return "{\"status\": \"success\", \"message\": \"Star deleted successfully.\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}";
        }
    }
}
