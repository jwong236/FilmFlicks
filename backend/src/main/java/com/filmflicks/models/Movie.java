package com.filmflicks.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private String id;
    private String title;
    private Integer year;
    private String director;

    // Many-to-Many relationship with Star
    @ManyToMany
    @JoinTable(
            name = "stars_in_movies",
            joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "starId")
    )
    @JsonManagedReference
    private Set<Star> stars = new HashSet<>();

    // Many-to-Many relationship with Genre
    @ManyToMany
    @JoinTable(
            name = "genres_in_movies",
            joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    @JsonManagedReference
    private Set<Genre> genres = new HashSet<>();

    // One-to-One relationship with Rating
    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private Rating rating;

    // Constructors
    public Movie() {
    }

    public Movie(String id, String title, Integer year, String director) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Set<Star> getStars() {
        return stars;
    }

    public void setStars(Set<Star> stars) {
        this.stars = stars;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
