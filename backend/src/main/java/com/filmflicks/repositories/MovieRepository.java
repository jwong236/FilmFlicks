package com.filmflicks.repositories;

import com.filmflicks.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>, JpaSpecificationExecutor<Movie> {

    @Procedure(procedureName = "add_movie_basic")
    String addMovieBasic(
            @Param("movie_title") String title,
            @Param("movie_year") int year,
            @Param("movie_director") String director
    );

    @Procedure(procedureName = "add_movie_stars")
    String addMovieStars(
            @Param("movie_id") String movieId,
            @Param("star_names") String starNames,  // Comma-separated list of star names
            @Param("star_birth_years") String starBirthYears  // Comma-separated list of birth years
    );

    @Procedure(procedureName = "add_movie_genres")
    String addMovieGenres(
            @Param("movie_id") String movieId,
            @Param("genre_names") String genreNames  // Comma-separated list of genre names
    );

    @Procedure(procedureName = "delete_movie_basic")
    String deleteMovieBasic(
            @Param("movie_id") String movieId
    );

    @Procedure(procedureName = "delete_movie_stars")
    String deleteMovieStars(
            @Param("movie_id") String movieId
    );

    @Procedure(procedureName = "delete_movie_genres")
    String deleteMovieGenres(
            @Param("movie_id") String movieId
    );

    // ====================================
    // Query Derivation
    // ====================================

    // Find movies where title starts with a specific character with pagination
    Page<Movie> findByTitleStartingWith(String character, Pageable pageable);

    // Find movies containing a specific title (case-insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);


    // ====================================
    // Complex Queries
    // ====================================

    // Find movies by genre name
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.name = :genre_name")
    Page<Movie> findByGenreName(@Param("genre_name") String genre_name, Pageable pageable);

    // Find top-rated movies
    @Query("SELECT m FROM Movie m JOIN m.rating r ORDER BY r.rating DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);

    // Find distinct directors
    @Query("SELECT DISTINCT m.director FROM Movie m")
    List<String> findDistinctDirectors();
}
