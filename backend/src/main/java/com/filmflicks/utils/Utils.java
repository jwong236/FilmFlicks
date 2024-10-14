package com.filmflicks.utils;

import org.springframework.data.domain.Sort;

public class Utils {
    // Maps sorting fields to entity fields
    public static String mapField(String field) {
        switch (field) {
            case "title":
            case "rating":
            case "year":
                return field;
            case "director":
                return "director";
            default:
                throw new IllegalArgumentException("Invalid sorting field: " + field);
        }
    }

    // Maps sorting directions to Sort.Direction
    public static Sort.Direction mapDirection(String direction) {
        switch (direction.toLowerCase()) {
            case "asc":
                return Sort.Direction.ASC;
            case "desc":
                return Sort.Direction.DESC;
            default:
                throw new IllegalArgumentException("Invalid sorting direction: " + direction);
        }
    }

    // Parses the sorting rule to generate the Sort object
    public static Sort parseSortRule(String sortRule) {
        if (sortRule == null || sortRule.isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sortRule.split("_");
        if (parts.length != 4) {
            return Sort.unsorted();
        }

        String field1 = mapField(parts[0]);
        Sort.Direction direction1 = mapDirection(parts[1]);
        String field2 = mapField(parts[2]);
        Sort.Direction direction2 = mapDirection(parts[3]);

        return Sort.by(Sort.Order.by(field1).with(direction1))
                .and(Sort.by(Sort.Order.by(field2).with(direction2)));
    }
}
