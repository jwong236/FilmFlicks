package com.filmflicks.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "movie_prices")
public class MoviePrice {
    @Id
    private String movie_id;
    private BigDecimal price;

    public MoviePrice() {
    }

    public String getMovieId() {
        return movie_id;
    }

    public void setMovieId(String movie_id) {
        this.movie_id = movie_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
