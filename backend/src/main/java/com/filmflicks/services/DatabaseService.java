package com.filmflicks.services;

import com.filmflicks.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Adds a basic movie to the database.
     *
     * @param title The title of the movie.
     * @param year The release year of the movie.
     * @param director The director of the movie.
     * @return Success message or error.
     */
    public String addMovieBasic(String title, Integer year, String director) {
        return movieRepository.addMovieBasic(title, year, director);
    }

    /**
     * Adds stars linked to a movie.
     *
     * @param movieId The ID of the movie.
     * @param starNames Comma-separated star names.
     * @param starBirthYears Comma-separated star birth years.
     * @return Success message or error.
     */
    public String addMovieStars(String movieId, String starNames, String starBirthYears) {
        return movieRepository.addMovieStars(movieId, starNames, starBirthYears);
    }

    /**
     * Adds genres linked to a movie.
     *
     * @param movieId The ID of the movie.
     * @param genreNames Comma-separated genre names.
     * @return Success message or error.
     */
    public String addMovieGenres(String movieId, String genreNames) {
        return movieRepository.addMovieGenres(movieId, genreNames);
    }

    /**
     * Deletes a basic movie from the database.
     *
     * @param movieId The ID of the movie to delete.
     * @return Success message or error.
     */
    public String deleteMovieBasic(String movieId) {
        return movieRepository.deleteMovieBasic(movieId);
    }

    /**
     * Deletes stars linked to a movie.
     *
     * @param movieId The ID of the movie.
     * @return Success message or error.
     */
    public String deleteMovieStars(String movieId) {
        return movieRepository.deleteMovieStars(movieId);
    }

    /**
     * Deletes genres linked to a movie.
     *
     * @param movieId The ID of the movie.
     * @return Success message or error.
     */
    public String deleteMovieGenres(String movieId) {
        return movieRepository.deleteMovieGenres(movieId);
    }
}
