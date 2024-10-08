package com.filmflicks.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private int year;
    private String director;
    private List<String> genres;
    private List<String> stars;
    private double rating;
    private int numVotes;

    public Movie() {
        this.title = "";
        this.year = 0;
        this.director = "";
        this.genres = new ArrayList<>();
        this.stars = new ArrayList<>();
        this.rating = 0.0;
        this.numVotes = 0;
    }

    public Movie(String title, int year, String director) {
        this.title = (title != null) ? title : "";
        this.year = (year > 0) ? year : 0;
        this.director = (director != null) ? director : "";
        this.genres = new ArrayList<>();
        this.stars = new ArrayList<>();
        this.rating = 0.0;
        this.numVotes = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = (title != null) ? title : "";
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = (year > 0) ? year : 0;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = (director != null) ? director : "";
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getStars() {
        return stars;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public void addStar(String star) {
        if (star != null && !star.trim().isEmpty()) {
            this.stars.add(star);
        }
    }

    public void addGenre(String genre) {
        if (genre != null && !genre.trim().isEmpty()) {
            this.genres.add(genre);
        }
    }
}
