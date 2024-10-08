package com.filmflicks.utils;

public class Utils {
    // Maps sorting fields to database columns
    public static String mapField(String field) {
        switch (field) {
            case "title":
                return "m.title";
            case "rating":
                return "r.rating";
            case "year":
                return "m.year";
            case "director":
                return "m.director";
            default:
                throw new IllegalArgumentException("Invalid sorting field: " + field);
        }
    }

    // Maps sorting directions to SQL clauses
    public static String mapDirection(String direction) {
        switch (direction) {
            case "asc":
                return "ASC";
            case "desc":
                return "DESC";
            default:
                throw new IllegalArgumentException("Invalid sorting direction: " + direction);
        }
    }
}
