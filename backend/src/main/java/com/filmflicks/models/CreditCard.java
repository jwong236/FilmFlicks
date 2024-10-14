package com.filmflicks.models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    private Date expiration;

    public CreditCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
