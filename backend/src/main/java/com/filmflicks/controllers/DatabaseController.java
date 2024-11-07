package com.filmflicks.controllers;

import com.filmflicks.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * Adds a basic movie to the database.
     *
     * @param title    The title of the movie.
     * @param year     The release year of the movie.
     * @param director The director of the movie.
     * @return Success message or error message if the movie already exists.
     */
    @PostMapping("/movie/add-basic")
    public String addMovieBasic(@RequestParam String title, @RequestParam Integer year, @RequestParam String director) {
        return databaseService.addMovieBasic(title, year, director);
    }

    /**
     * Adds stars linked to a movie.
     *
     * @param movieId        The ID of the movie.
     * @param starNames      Comma-separated list of star names.
     * @param starBirthYears Comma-separated list of star birth years matching each star name.
     * @return Success message or error message if any star is not found.
     */
    @PostMapping("/movie/add-star")
    public String addMovieStars(@RequestParam String movieId, @RequestParam String starNames, @RequestParam String starBirthYears) {
        return databaseService.addMovieStars(movieId, starNames, starBirthYears);
    }

    /**
     * Adds genres linked to a movie.
     *
     * @param movieId    The ID of the movie.
     * @param genreNames Comma-separated list of genre names.
     * @return Success message or error message if any genre is not found.
     */
    @PostMapping("/movie/add-genre")
    public String addMovieGenres(@RequestParam String movieId, @RequestParam String genreNames) {
        return databaseService.addMovieGenres(movieId, genreNames);
    }

    /**
     * Deletes a basic movie from the database.
     *
     * @param movieId The ID of the movie to be deleted.
     * @return Success message or error message if the movie does not exist.
     */
    @DeleteMapping("/movie/delete-basic")
    public String deleteMovieBasic(@RequestParam String movieId) {
        return databaseService.deleteMovieBasic(movieId);
    }

    /**
     * Deletes stars linked to a movie.
     *
     * @param movieId The ID of the movie from which stars are to be removed.
     * @return Success message or error message if the movie or stars do not exist.
     */
    @DeleteMapping("/movie/delete-star")
    public String deleteMovieStars(@RequestParam String movieId) {
        return databaseService.deleteMovieStars(movieId);
    }

    /**
     * Deletes genres linked to a movie.
     *
     * @param movieId The ID of the movie from which genres are to be removed.
     * @return Success message or error message if the movie or genres do not exist.
     */
    @DeleteMapping("/movie/delete-genre")
    public String deleteMovieGenres(@RequestParam String movieId) {
        return databaseService.deleteMovieGenres(movieId);
    }

    /**
     * Adds a star to the database.
     *
     * @param name      The name of the star.
     * @param birthYear The birth year of the star.
     * @return Success message or error message if the star already exists.
     */
    @PostMapping("/star/add")
    public String addStar(@RequestParam String name, @RequestParam int birthYear) {
        return databaseService.addStar(name, birthYear);
    }

    /**
     * Deletes a star from the database by their ID.
     *
     * @param starId The ID of the star to delete.
     * @return Success message or error message if the star does not exist.
     */
    @DeleteMapping("/star/delete")
    public String deleteStar(@RequestParam String starId) {
        return databaseService.deleteStar(starId);
    }

}
