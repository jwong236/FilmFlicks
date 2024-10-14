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

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>, JpaSpecificationExecutor<Movie> {

    @Procedure(name = "add_movie")
    String addMovie(
            @Param("title") String title,
            @Param("year") int year,
            @Param("director") String director,
            @Param("star_name") String starName,
            @Param("genre_name") String genreName
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
