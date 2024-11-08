package com.filmflicks.controllers;

import com.filmflicks.models.Movie;
import com.filmflicks.models.Star;
import com.filmflicks.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * Handles search requests for movies based on title, director, star, and year.
     *
     * @param title Movie title (optional).
     * @param director Movie director (optional).
     * @param star Actor/actress name (optional).
     * @param year Movie release year (optional).
     * @param page Page number for pagination (default is 1).
     * @param pageSize Number of items per page (default is 10).
     * @param sortRule Sorting rule (e.g., "title_asc_rating_desc").
     * @return A list of movies that match the search criteria.
     */
    @GetMapping("/search")
    public List<Movie> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String star,
            @RequestParam(required = false) String year,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String sortRule) {

        return searchService.searchMovies(title, director, star, year, page, pageSize, sortRule);
    }

    /**
     * Retrieves detailed information about a single movie by its title.
     *
     * @param title Title of the movie.
     * @return Optional containing the Movie object with details if found.
     */
    @GetMapping("/single-movie")
    public Optional<Movie> getSingleMovie(@RequestParam String title) {
        return searchService.getSingleMovieByTitle(title);
    }

    /**
     * Retrieves detailed information about a single star by name and the movies they've appeared in.
     *
     * @param name Name of the star/actor/actress.
     * @return Optional containing star details if found.
     */
    @GetMapping("/single-star")
    public Optional<Star> getSingleStar(@RequestParam String name) {
        return searchService.getStarDetails(name);
    }
}
