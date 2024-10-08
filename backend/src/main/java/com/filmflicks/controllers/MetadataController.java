package com.filmflicks.controllers;

import com.filmflicks.models.TableMetadata;
import com.filmflicks.models.Genre;
import com.filmflicks.models.Movie;
import com.filmflicks.services.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return metadataService.getAllGenres();
    }

    @GetMapping("/directors")
    public List<String> getAllDirectors() {
        return metadataService.getAllDirectors();
    }

    @GetMapping("/stars")
    public List<String> getAllStars() {
        return metadataService.getAllStars();
    }

    @GetMapping("/top-rated")
    public List<Movie> getTopRatedMovies() {
        return metadataService.getTopRatedMovies();
    }

    @GetMapping("/database-metadata")
    public List<TableMetadata> getDatabaseMetadata() {
        return metadataService.getDatabaseMetadata();
    }
}
