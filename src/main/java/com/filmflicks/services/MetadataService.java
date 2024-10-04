package com.filmflicks.services;

import com.filmflicks.models.TableMetadata;
import com.filmflicks.models.ColumnMetadata;
import com.filmflicks.models.Genre;
import com.filmflicks.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetadataService {

    @Autowired
    private DataSource dataSource;

    /**
     * Retrieves a list of all genres
     *
     * @return A list of all genres
     */
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM genres";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                genres.add(new Genre(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }

    /**
     * Retrieves a list of all directors
     *
     * @return A list of all directors
     */
    public List<String> getAllDirectors() {
        List<String> directors = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT DISTINCT director FROM movies";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String director = resultSet.getString("director");
                directors.add(director);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return directors;
    }

    /**
     * Retrieves a list of all stars
     *
     * @return A list of all stars
     */
    public List<String> getAllStars() {
        List<String> stars = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT DISTINCT s.name FROM stars s";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String star = resultSet.getString("name");
                stars.add(star);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stars;
    }

    /**
     * Retrieves a list of the top 20 highest-rated movies
     *
     * @return A list of top-rated movies
     */
    public List<Movie> getTopRatedMovies() {
        List<Movie> topMovies = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT m.title, m.year, m.director, r.rating FROM movies m " +
                    "JOIN ratings r ON m.id = r.movieId " +
                    "ORDER BY r.rating DESC LIMIT 20";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int year = resultSet.getInt("year");
                String director = resultSet.getString("director");
                double rating = resultSet.getDouble("rating");

                Movie movie = new Movie(title, year, director);
                movie.setRating(rating);
                topMovies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topMovies;
    }

    /**
     * Retrieves database metadata including table names and column details
     *
     * @return A list of table metadata
     */
    public List<TableMetadata> getDatabaseMetadata() {
        List<TableMetadata> metadataList = new ArrayList<>();
        String query = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'moviedb' ORDER BY TABLE_NAME, ORDINAL_POSITION";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            TableMetadata currentTable = null;
            String currentTableName = "";

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (!tableName.equals(currentTableName)) {
                    if (currentTable != null) {
                        metadataList.add(currentTable);
                    }
                    currentTableName = tableName;
                    currentTable = new TableMetadata(tableName);
                }
                currentTable.addColumn(resultSet.getString("COLUMN_NAME"), resultSet.getString("DATA_TYPE"));
            }
            if (currentTable != null) {
                metadataList.add(currentTable);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return metadataList;
    }
}
