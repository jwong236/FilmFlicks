package com.filmflicks.services;

import com.filmflicks.models.TableMetadata;
import com.filmflicks.models.Genre;
import com.filmflicks.models.Movie;
import com.filmflicks.repositories.GenreRepository;
import com.filmflicks.repositories.MovieRepository;
import com.filmflicks.repositories.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetadataService {

    private static final Logger logger = LoggerFactory.getLogger(MetadataService.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StarRepository starRepository;

    /**
     * Retrieves a list of all genres
     *
     * @return A list of all genres
     */
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * Retrieves a list of all distinct directors
     *
     * @return A list of all directors
     */
    public List<String> getAllDirectors() {
        try {
            return movieRepository.findDistinctDirectors();
        } catch (Exception e) {
            logger.error("Error while fetching directors", e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves a list of all stars
     *
     * @return A list of all stars
     */
    public List<String> getAllStars() {
        try {
            return starRepository.findAllStarNames();
        } catch (Exception e) {
            logger.error("Error while fetching stars", e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves a list of the top 20 highest-rated movies
     *
     * @return A list of top-rated movies
     */
    public List<Movie> getTopRatedMovies() {
        try {
            return movieRepository.findTopRatedMovies(PageRequest.of(0, 20)).getContent();
        } catch (Exception e) {
            logger.error("Error while fetching top-rated movies", e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves database metadata including table names and column details
     *
     * @return A list of table metadata
     */
    public List<TableMetadata> getDatabaseMetadata() {
        List<TableMetadata> metadataList = new ArrayList<>();
        String query = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? ORDER BY TABLE_NAME, ORDINAL_POSITION";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "moviedb"); // Make the schema configurable if needed

            try (ResultSet resultSet = statement.executeQuery()) {
                metadataList = mapResultSetToTableMetadata(resultSet);
            }

        } catch (Exception e) {
            logger.error("Error while fetching database metadata", e);
        }
        return metadataList;
    }

    /**
     * Maps the ResultSet to a list of TableMetadata objects
     *
     * @param resultSet The ResultSet containing table metadata
     * @return A list of TableMetadata objects
     */
    private List<TableMetadata> mapResultSetToTableMetadata(ResultSet resultSet) throws Exception {
        List<TableMetadata> metadataList = new ArrayList<>();
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

        return metadataList;
    }
}
