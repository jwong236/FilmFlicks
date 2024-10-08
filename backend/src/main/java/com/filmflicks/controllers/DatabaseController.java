package com.filmflicks.controllers;

import com.filmflicks.models.Movie;
import com.filmflicks.models.Star;
import com.filmflicks.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * Inserts a new movie into the database.
     *
     * @param movie The movie object to be inserted.
     * @return Success message or error.
     */
    @PostMapping("/insert/movie")
    public String insertMovie(@RequestBody Movie movie) {
        return databaseService.insertMovie(movie);
    }

    /**
     * Inserts a new star into the database.
     *
     * @param star The star object to be inserted.
     * @return Success message or error.
     */
    @PostMapping("/insert/star")
    public String insertStar(@RequestBody Star star) {
        return databaseService.insertStar(star);
    }

    /**
     * Deletes a movie from the database.
     *
     * @param movieId The ID of the movie to be deleted.
     * @return Success message or error.
     */
    @DeleteMapping("/delete/movie")
    public String deleteMovie(@RequestParam String movieId) {
        return databaseService.deleteMovie(movieId);
    }

    /**
     * Deletes a star from the database.
     *
     * @param starId The ID of the star to be deleted.
     * @return Success message or error.
     */
    @DeleteMapping("/delete/star")
    public String deleteStar(@RequestParam String starId) {
        return databaseService.deleteStar(starId);
    }
}
