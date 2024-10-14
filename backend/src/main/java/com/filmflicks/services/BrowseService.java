package com.filmflicks.services;

import com.filmflicks.models.Movie;
import com.filmflicks.repositories.MovieRepository;
import com.filmflicks.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrowseService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Retrieves a paginated list of movies by genre using JPA.
     *
     * @param genre The genre to filter movies by.
     * @param page The page number for pagination.
     * @param pageSize The number of results per page.
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return A list of movies matching the genre.
     */
    public List<Movie> browseMoviesByGenre(String genre, int page, int pageSize, String sortRule) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Utils.parseSortRule(sortRule));
        Page<Movie> moviePage = movieRepository.findByGenreName(genre, pageable);
        return moviePage.getContent();
    }

    /**
     * Retrieves a paginated list of movies starting with a specific character.
     *
     * @param character The character to filter movies by.
     * @param page The page number for pagination.
     * @param pageSize The number of results per page.
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return A list of movies matching the character.
     */
    public List<Movie> browseMoviesByCharacter(String character, int page, int pageSize, String sortRule) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Utils.parseSortRule(sortRule));
        Page<Movie> moviePage = movieRepository.findByTitleStartingWith(character, pageable);
        return moviePage.getContent();
    }
}
