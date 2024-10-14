package com.filmflicks.services;

import com.filmflicks.models.Movie;
import com.filmflicks.models.Star;
import com.filmflicks.utils.Utils;
import com.filmflicks.repositories.MovieRepository;
import com.filmflicks.repositories.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;


@Service
public class SearchService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StarRepository starRepository;

    /**
     * Searches for movies based on the provided parameters.
     *
     * @param title The movie title (optional).
     * @param director The movie director (optional).
     * @param star The star in the movie (optional).
     * @param year The movie release year (optional).
     * @param page The page number for pagination.
     * @param pageSize The number of items per page.
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return A list of movies matching the search criteria.
     */
    public List<Movie> searchMovies(String title, String director, String star, String year, int page, int pageSize, String sortRule) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Utils.parseSortRule(sortRule));

        Page<Movie> moviePage = movieRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new java.util.ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%%" + title.toLowerCase() + "%%"));
            }
            if (director != null && !director.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("director")), "%%" + director.toLowerCase() + "%%"));
            }
            if (year != null && !year.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("year"), Integer.parseInt(year)));
            }

            if (star != null && !star.isEmpty()) {
                var joinStars = root.joinSet("stars");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(joinStars.get("name")), "%%" + star.toLowerCase() + "%%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return moviePage.getContent();
    }


    /**
     * Retrieves detailed information about a single movie by its title.
     *
     * @param title The title of the movie.
     * @return Optional containing the Movie object with details if found.
     */
    public Optional<Movie> getSingleMovieByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream().findFirst();
    }

    /**
     * Retrieves information about a star.
     *
     * @param name The name of the star/actor.
     * @return Optional containing star details if found.
     */
    public Optional<Star> getStarDetails(String name) {
        return starRepository.findByNameIgnoreCase(name);
    }
}
