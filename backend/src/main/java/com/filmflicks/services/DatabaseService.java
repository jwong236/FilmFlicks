package com.filmflicks.services;

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

    /**
     * Adds a star to the database.
     *
     * @param name      The name of the star.
     * @param birthYear The birth year of the star.
     * @return Success message or error.
     */
    public String addStar(String name, int birthYear) {
        return starRepository.addStar(name, birthYear);
    }

    /**
     * Deletes a star from the database by their ID.
     *
     * @param starId The ID of the star to delete.
     * @return Success message or error.
     */
    public String deleteStar(String starId) {
        return starRepository.deleteStar(starId);
    }


}
