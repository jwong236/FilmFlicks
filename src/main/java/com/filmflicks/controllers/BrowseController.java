package com.filmflicks.controllers;

import com.filmflicks.models.Movie;
import com.filmflicks.services.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrowseController {

    @Autowired
    private BrowseService browseService;

    @GetMapping("/browse/character")
    public List<Movie> browseCharacter(
            @RequestParam String character,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String sortRule) {

        return browseService.browseMoviesByCharacter(character, page, pageSize, sortRule);
    }

    @GetMapping("/browse/genre")
    public List<Movie> browseGenre(
            @RequestParam String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String sortRule) {

        return browseService.browseMoviesByGenre(genre, page, pageSize, sortRule);
    }
}
