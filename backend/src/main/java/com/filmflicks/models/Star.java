package com.filmflicks.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stars")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Star {
    @Id
    private String id;
    private String name;

    private Integer birth_year;

    // Many-to-Many relationship with Movie (inverse side)
    @ManyToMany(mappedBy = "stars")
    @JsonBackReference
    private Set<Movie> movies = new HashSet<>();

    // Constructors
    public Star() {
    }

    public Star(String id, String name, Integer birth_year) {
        this.id = id;
        this.name = name;
        this.birth_year = birth_year;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birth_year;
    }

    public void setBirthYear(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
