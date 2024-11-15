package com.filmflicks.controllers;

import com.filmflicks.models.TableMetadata;
import com.filmflicks.models.Genre;
import com.filmflicks.models.Movie;
import com.filmflicks.services.MetadataService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
    public List<Movie> getTopRatedMovies(@RequestParam(defaultValue = "20") int size) {
        return metadataService.getTopRatedMovies(size);
    }

    @GetMapping("/session")
    public Map<String, Object> getSessionContents(HttpSession session) {
        Map<String, Object> sessionContents = new HashMap<>();

        session.getAttributeNames().asIterator().forEachRemaining(attributeName -> {
            Object attributeValue = session.getAttribute(attributeName);
            sessionContents.put(attributeName, attributeValue);
        });

        return sessionContents;
    }

    @GetMapping("/database-metadata")
    public List<TableMetadata> getDatabaseMetadata() {
        return metadataService.getDatabaseMetadata();
    }
}
