package com.filmflicks.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @Column(name = "movie_id")
    private String movie_id;

    private double rating;

    private int num_votes;

    // One-to-One relationship with Movie
    @OneToOne
    @MapsId
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public Rating() {}

    public Rating(double rating, int num_votes) {
        this.rating = rating;
        this.num_votes = num_votes;
    }

    // Getters and Setters

    public String getMovieId() {
        return movie_id;
    }

    public void setMovieId(String movie_id) {
        this.movie_id = movie_id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumVotes() {
        return num_votes;
    }

    public void setNumVotes(int num_votes) {
        this.num_votes = num_votes;
    }
}
