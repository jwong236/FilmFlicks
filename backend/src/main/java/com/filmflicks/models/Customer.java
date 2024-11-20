package com.filmflicks.models;

import jakarta.persistence.*;

@Entity
@Table(name = "customers") // Matches the table name in your database
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Integer id;

    @Column(name = "first_name", nullable = false) // Maps to the "first_name" column in the table
    private String firstName;

    @Column(name = "last_name", nullable = false) // Maps to the "last_name" column in the table
    private String lastName;

    @Column(name = "cc_id", nullable = false) // Maps to the "cc_id" column in the table
    private String ccId;

    @Column(nullable = false) // Maps to the "address" column (defaults to the field name)
    private String address;

    @Column(nullable = false, unique = true) // Email must be unique
    private String email;

    @Column(nullable = false) // Password cannot be null
    private String password;

    public Customer() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
