package com.filmflicks.models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer customer_id;
    private String movie_id;
    private Date sale_date;

    public Sale() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getMovieId() {
        return movie_id;
    }

    public void setMovieId(String movieId) {
        this.movie_id = movieId;
    }

    public Date getSaleDate() {
        return sale_date;
    }

    public void setSaleDate(Date sale_date) {
        this.sale_date = sale_date;
    }
}
