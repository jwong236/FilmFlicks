//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

// This annotation maps this Java Servlet Class to a URL
@WebServlet("/movielist")
public class MovieList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Change this to your own mysql username and password
        String loginUser = "root";
        String loginPasswd = "andy";
        String loginUrl = "jdbc:mysql://localhost:3307/moviedb";


        // Get the PrintWriter for writing response
        PrintWriter out = response.getWriter();

        // Set response mime type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // create database connection
            Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // declare statement
            Statement statement = connection.createStatement();
            // prepare query
            String query = "SELECT m.title, m.year, m.director, r.rating, GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS genres,        GROUP_CONCAT(DISTINCT s.name ORDER BY s.name SEPARATOR ', ') AS stars FROM movies m JOIN ratings r ON r.movieId = m.id JOIN genres_in_movies gm ON gm.movieId = m.id JOIN genres g ON gm.genreId = g.id JOIN stars_in_movies sm ON sm.movieId = m.id JOIN stars s ON sm.starId = s.id GROUP BY m.id, m.title, m.year, m.director, r.rating ORDER BY r.rating DESC LIMIT 20";
            // execute query
            ResultSet resultSet = statement.executeQuery(query);

            //holds the json strings
            List<Map<String, String>> jsonArr = new ArrayList<>();

            while (resultSet.next()) {
                //gets the individual columns for each row and makes them individual strings
                String movieTitle = resultSet.getString("title");
                String movieRating = resultSet.getString("rating");
                String director = resultSet.getString("director");
                String genres = resultSet.getString("genres");
                String stars = resultSet.getString("stars");

                //used to map the strings to their keys to create a json structure
                Map<String, String> data = new LinkedHashMap<>();


                //key is left "____" : val is the right column
                //ex: "title" : movieTitle
                data.put("title", movieTitle);
                data.put("rating", movieRating);
                data.put("director", director);
                data.put("genres", genres);
                data.put("stars", stars);



                //adds each map into an array
                jsonArr.add(data);

            }

            //jackson api used for turn the hashmap of strings into a string structured like json
            ObjectMapper objMap = new ObjectMapper();

            //structures json so that each object is seperated like normal json struct
            objMap.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectWriter objWrite = objMap.writerWithDefaultPrettyPrinter();

            //turns into json string
            String jsonString = objWrite.writeValueAsString(jsonArr);

            //adds the json array to the response body to send back to client that requested it

            out.print(jsonString);
//            String contentType = response.getContentType();
//            if (contentType != null && contentType.startsWith("application/json")) {
//                System.out.println("Response is JSON");
//            } else {
//                System.out.println("Content type: " + contentType);
//                System.out.println("Response is not JSON");
//            }
            //out.flush();



        } catch (Exception e) {
            /*
             * After you deploy the WAR file through tomcat manager webpage,
             *   there's no console to see the print messages.
             * Tomcat append all the print messages to the file: tomcat_directory/logs/catalina.out
             *
             * To view the last n lines (for example, 100 lines) of messages you can use:
             *   tail -100 catalina.out
             * This can help you debug your program after deploying it on AWS.
             */
            request.getServletContext().log("Error: ", e);

            out.print(e.getMessage());
            out.flush();
        }



    }



}