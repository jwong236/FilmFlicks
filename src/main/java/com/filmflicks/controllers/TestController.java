package com.filmflicks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class TestController {

    @Autowired
    private DataSource dataSource;

    // Endpoint to test if the backend is running
    @GetMapping("/test-backend")
    public String testBackend() {
        return "Backend server is running!";
    }

    // Endpoint to test the MySQL database connection
    @GetMapping("/test-database")
    public String testDatabase() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT table_name" +
                            "FROM information_schema.tables" +
                            "WHERE table_schema = 'your_database_name'");

            StringBuilder tables = new StringBuilder("Tables in the database: ");
            while (resultSet.next()) {
                tables.append(resultSet.getString("table_name")).append(", ");
            }
            return tables.toString();
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }

}
