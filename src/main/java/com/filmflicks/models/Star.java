package com.filmflicks.models;

import java.util.ArrayList;
import java.util.List;

public class Star {

    private String name;
    private String birthYear;
    private List<String> movies;

    public Star(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public void addMovie(String movie) {
        if (movie != null && !movie.trim().isEmpty()) {
            this.movies.add(movie);
        }
    }
}
