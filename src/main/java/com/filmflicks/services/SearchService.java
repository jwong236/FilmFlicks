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
public class SearchService {

    @Autowired
    private DataSource dataSource;

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
        try (Connection connection = dataSource.getConnection()) {
            List<String> queryParts = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                queryParts.add("LOWER(m.title) LIKE ?");
                parameters.add("%" + title.toLowerCase() + "%");
            }
            if (director != null && !director.isEmpty()) {
                queryParts.add("LOWER(m.director) LIKE ?");
                parameters.add("%" + director.toLowerCase() + "%");
            }
            if (star != null && !star.isEmpty()) {
                queryParts.add("LOWER(s.name) LIKE ?");
                parameters.add("%" + star.toLowerCase() + "%");
            }
            if (year != null && !year.isEmpty()) {
                queryParts.add("m.year = ?");
                parameters.add(Integer.parseInt(year));
            }

            if (queryParts.isEmpty()) {
                throw new IllegalArgumentException("No search criteria provided.");
            }

            String whereClause = String.join(" AND ", queryParts);
            int offset = (page - 1) * pageSize;

            String fullQuery = buildFullQuery(whereClause, pageSize, offset, sortRule);
            System.out.println("WHERE CLAUSE: " + whereClause);
            System.out.println("Full Query: " + fullQuery);
            System.out.println("Parameters: " + parameters);

            return executeSearchQuery(connection, fullQuery, parameters);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    /**
     * Builds the full SQL query based on the search criteria.
     *
     * @param whereClause The WHERE clause for filtering the movies.
     * @param pageSize The number of items per page.
     * @param offset The offset for pagination.
     * @param sortRule The sorting rule.
     * @return The full SQL query.
     */
    private String buildFullQuery(String whereClause, int pageSize, int offset, String sortRule) {
        String orderByClause = parseSortRule(sortRule);
        return "SELECT m.title AS title, m.year AS year, m.director AS director, " +
                "GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS genres, " +
                "GROUP_CONCAT(DISTINCT s.name ORDER BY movie_count DESC, s.name SEPARATOR ', ') AS stars, " +
                "r.rating AS rating, r.numVotes AS numvotes " +
                "FROM movies m " +
                "LEFT JOIN genres_in_movies gm ON m.id = gm.movieId " +
                "LEFT JOIN genres g ON gm.genreId = g.id " +
                "LEFT JOIN stars_in_movies sm ON m.id = sm.movieId " +
                "LEFT JOIN stars s ON s.id = sm.starId " +
                "LEFT JOIN ratings r ON m.id = r.movieId " +
                "LEFT JOIN ( " +
                "    SELECT starId, COUNT(movieId) AS movie_count " +
                "    FROM stars_in_movies " +
                "    GROUP BY starId " +
                ") AS movie_counts ON s.id = movie_counts.starId " +
                "WHERE " + whereClause + " " +
                "GROUP BY m.id, m.title, m.year, m.director, r.rating, r.numVotes " +
                orderByClause + " " +
                "LIMIT " + pageSize + " OFFSET " + offset;
    }

    /**
     * Parses the sorting rule to generate the SQL ORDER BY clause.
     *
     * @param sortRule The sorting rule (e.g., "title_asc_rating_desc").
     * @return The SQL ORDER BY clause.
     */
    private String parseSortRule(String sortRule) {
        if (sortRule == null || sortRule.isEmpty()) return "";
        String[] parts = sortRule.split("_");
        if (parts.length != 4) return "";
        String field1 = mapField(parts[0]);
        String direction1 = mapDirection(parts[1]);
        String field2 = mapField(parts[2]);
        String direction2 = mapDirection(parts[3]);

        return String.format("ORDER BY %s %s, %s %s", field1, direction1, field2, direction2);
    }

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

    /**
     * Executes the search query and returns the list of movies.
     *
     * @param connection The database connection.
     * @param query The SQL query.
     * @param parameters The query parameters.
     * @return The list of movies.
     * @throws SQLException If a database error occurs.
     */
    private List<Movie> executeSearchQuery(Connection connection, String query, List<Object> parameters) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof String) {
                    preparedStatement.setString(i + 1, (String) parameters.get(i));
                } else if (parameters.get(i) instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) parameters.get(i));
                }
            }
            return getMoviesFromResultSet(preparedStatement.executeQuery());
        }
    }

    /**
     * Extracts movies from the result set.
     *
     * @param resultSet The result set from the database query.
     * @return The list of movies.
     * @throws SQLException If a database error occurs.
     */
    private List<Movie> getMoviesFromResultSet(ResultSet resultSet) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            Movie movie = new Movie();
            movie.setTitle(resultSet.getString("title"));
            movie.setYear(resultSet.getInt("year"));
            movie.setDirector(resultSet.getString("director"));
            movie.setGenres(Arrays.asList(resultSet.getString("genres").split(", ")));
            movie.setStars(Arrays.asList(resultSet.getString("stars").split(", ")));
            movie.setRating(resultSet.getDouble("rating"));
            movie.setNumVotes(resultSet.getInt("numvotes"));
            movies.add(movie);
        }
        return movies;
    }
}