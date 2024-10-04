package com.filmflicks.services;

import com.filmflicks.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BrowseService {

    @Autowired
    private DataSource dataSource;

    /**
     * Retrieves a list of movies that belong to a specified genre.
     *
     * @param genre The genre to filter movies by.
     * @param page The page number for pagination.
     * @param pageSize The number of results per page.
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return A list of movies matching the genre.
     */
    public List<Movie> browseMoviesByGenre(String genre, int page, int pageSize, String sortRule) {
        String query = "SELECT m.title AS Title, m.year AS Year, m.director AS Director, " +
                "GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS Genres, " +
                "GROUP_CONCAT(DISTINCT s.name ORDER BY movie_count DESC, s.name SEPARATOR ', ') AS Stars, " +
                "r.rating AS Rating, r.numVotes AS NumVotes " +
                "FROM movies m " +
                "JOIN genres_in_movies gm ON m.id = gm.movieId " +
                "JOIN genres g ON gm.genreId = g.id " +
                "LEFT JOIN stars_in_movies sm ON m.id = sm.movieId " +
                "LEFT JOIN stars s ON sm.starId = s.id " +
                "LEFT JOIN ratings r ON m.id = r.movieId " +
                "LEFT JOIN ( " +
                "    SELECT starId, COUNT(movieId) AS movie_count " +
                "    FROM stars_in_movies " +
                "    GROUP BY starId " +
                ") AS movie_counts ON s.id = movie_counts.starId " +
                "WHERE g.name = ? " +
                "GROUP BY m.id, m.title, m.year, m.director, r.rating, r.numVotes " +
                getOrderByClause(sortRule) + " LIMIT ? OFFSET ?";
        return executeMovieQuery(query, genre, page, pageSize);
    }

    /**
     * Retrieves a list of movies that have a title starting with a given character.
     * If the character is "*", it retrieves movies whose titles start with a non-alphanumeric character.
     *
     * @param character The character to filter movies by.
     * @param page The page number for pagination.
     * @param pageSize The number of results per page.
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return A list of movies matching the character.
     */
    public List<Movie> browseMoviesByCharacter(String character, int page, int pageSize, String sortRule) {
        String query = character.equals("*") ?
                getWildcardQuery(getOrderByClause(sortRule)) :
                getCharacterQuery(getOrderByClause(sortRule));

        return executeMovieQuery(query, character, page, pageSize);
    }

    /**
     * Executes the SQL query to retrieve movies from the database.
     *
     * @param query The SQL query to execute.
     * @param searchParam The search parameter (e.g., genre or character).
     * @param page The page number for pagination.
     * @param pageSize The number of results per page.
     * @return A list of movies retrieved from the database.
     */
    private List<Movie> executeMovieQuery(String query, String searchParam, int page, int pageSize) {
        List<Movie> movies = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, searchParam);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setTitle(resultSet.getString("Title"));
                movie.setYear(resultSet.getInt("Year"));
                movie.setDirector(resultSet.getString("Director"));
                movie.setGenres(Arrays.asList(resultSet.getString("Genres").split(", ")));
                movie.setStars(Arrays.asList(resultSet.getString("Stars").split(", ")));
                movie.setRating(resultSet.getDouble("Rating"));
                movie.setNumVotes(resultSet.getInt("NumVotes"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }

        return movies;
    }

    /**
     * Constructs the SQL query to retrieve movies whose titles start with a non-alphanumeric character.
     *
     * @param orderByClause The SQL ORDER BY clause for sorting.
     * @return The SQL query to execute.
     */
    private String getWildcardQuery(String orderByClause) {
        return "SELECT m.title AS Title, m.year AS Year, m.director AS Director, " +
                "GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS Genres, " +
                "GROUP_CONCAT(DISTINCT s.name ORDER BY movie_count DESC, s.name SEPARATOR ', ') AS Stars, " +
                "r.rating AS Rating, r.numVotes AS NumVotes " +
                "FROM movies m " +
                "LEFT JOIN genres_in_movies gm ON m.id = gm.movieId " +
                "LEFT JOIN genres g ON gm.genreId = g.id " +
                "LEFT JOIN stars_in_movies sm ON m.id = sm.movieId " +
                "LEFT JOIN stars s ON sm.starId = s.id " +
                "LEFT JOIN ratings r ON m.id = r.movieId " +
                "LEFT JOIN ( " +
                "    SELECT starId, COUNT(movieId) AS movie_count " +
                "    FROM stars_in_movies " +
                "    GROUP BY starId " +
                ") AS movie_counts ON s.id = movie_counts.starId " +
                "WHERE m.title REGEXP '^[^a-zA-Z0-9]' " +
                "GROUP BY m.id, m.title, m.year, m.director, r.rating, r.numVotes " + orderByClause + " LIMIT ? OFFSET ?";
    }

    /**
     * Constructs the SQL query to retrieve movies whose titles start with a specific character.
     *
     * @param orderByClause The SQL ORDER BY clause for sorting.
     * @return The SQL query to execute.
     */
    private String getCharacterQuery(String orderByClause) {
        return "SELECT m.title AS Title, m.year AS Year, m.director AS Director, " +
                "GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS Genres, " +
                "GROUP_CONCAT(DISTINCT s.name ORDER BY movie_count DESC, s.name SEPARATOR ', ') AS Stars, " +
                "r.rating AS Rating, r.numVotes AS NumVotes " +
                "FROM movies m " +
                "LEFT JOIN genres_in_movies gm ON m.id = gm.movieId " +
                "LEFT JOIN genres g ON gm.genreId = g.id " +
                "LEFT JOIN stars_in_movies sm ON m.id = sm.movieId " +
                "LEFT JOIN stars s ON sm.starId = s.id " +
                "LEFT JOIN ratings r ON m.id = r.movieId " +
                "LEFT JOIN ( " +
                "    SELECT starId, COUNT(movieId) AS movie_count " +
                "    FROM stars_in_movies " +
                "    GROUP BY starId " +
                ") AS movie_counts ON s.id = movie_counts.starId " +
                "WHERE m.title LIKE CONCAT(?, '%') " +
                "GROUP BY m.id, m.title, m.year, m.director, r.rating, r.numVotes " + orderByClause + " LIMIT ? OFFSET ?";
    }

    /**
     * Constructs the SQL ORDER BY clause based on the provided sorting rule.
     *
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return The SQL ORDER BY clause.
     */
    private String getOrderByClause(String sortRule) {
        String[] parts = sortRule.split("_");
        String field1 = mapField(parts[0]);
        String direction1 = mapDirection(parts[1]);
        String field2 = mapField(parts[2]);
        String direction2 = mapDirection(parts[3]);

        return String.format("ORDER BY %s %s, %s %s", field1, direction1, field2, direction2);
    }

    /**
     * Maps the provided field (e.g., "title", "rating") to the corresponding SQL field.
     *
     * @param field The field to map.
     * @return The corresponding SQL field (e.g., "m.title", "r.rating").
     */
    private String mapField(String field) {
        switch (field) {
            case "title":
                return "m.title";
            case "rating":
                return "r.rating";
            default:
                throw new IllegalArgumentException("Invalid sorting field: " + field);
        }
    }

    /**
     * Maps the provided sorting direction (e.g., "asc", "desc") to the corresponding SQL keyword.
     *
     * @param direction The sorting direction to map.
     * @return The corresponding SQL keyword ("ASC" or "DESC").
     */
    private String mapDirection(String direction) {
        switch (direction) {
            case "asc":
                return "ASC";
            case "desc":
                return "DESC";
            default:
                throw new IllegalArgumentException("Invalid sorting direction: " + direction);
        }
    }
}
