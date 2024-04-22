//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.util.*;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;


// This annotation maps this Java Servlet Class to a URL
@WebServlet("/add")
public class Add extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;


    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("ADD");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        PrintWriter out = response.getWriter();



        String movieTitle = "";

        try(BufferedReader reader = request.getReader()){
            String line ;
            while ((line = reader.readLine()) != null){
                requestBody.append(line);
            }
        }catch(IOException e){
            System.out.println("reading error");
            e.printStackTrace();
        }



        try{
            Connection connection = dataSource.getConnection();
            //turn the parameters of the post request into a user class
            //containing email and password
            //set attribute movieMap : hashmap that contains movieTitle -> Movie Objects (price, quantity)
            String requestString = requestBody.toString();
            System.out.println("request string " + requestString);
            ObjectMapper objectMapper = new ObjectMapper();
            MovieObject movieObject = objectMapper.readValue(requestString, MovieObject.class);

            String title =  movieObject.getMovieTitle();
            //get the movie title
            //System.out.println("movieTitle " + title);


            double price = 0.0;
            //get the session, check if it exists, if it is on this section it exists
            HttpSession session = request.getSession(false);

            //if the session exists, get the movieMap
            if (session != null){
                System.out.println("session exists");
                HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");



                //sql query to get the movieid based on the title
                String movieIdQuery = "SELECT id FROM movies WHERE title = ?";

                PreparedStatement movieIdStatement = connection.prepareStatement(movieIdQuery);

                movieIdStatement.setString(1, title);

                ResultSet idResult = movieIdStatement.executeQuery();
                String movieId = "";

                if (idResult.next()){
                    //System.out.println("id result not null");
                    movieId = idResult.getString("id");
                    //System.out.println("movie title " + title + " movie id : " + movieId);
                }else{
                    System.out.println("id result is null");
                }


                LocalDate currentDate = LocalDate.now();
                System.out.println("current Date " + currentDate);
                //if a map exists already increase the quantity
                if (movieMap != null){
                    System.out.println("map already exists");
                    //map exists but the title is in increase
                    if (movieMap.containsKey(title)){
                        System.out.println("quantity increase");
                        movieMap.get(title).increase();
                    }else{
                        //not in then create new key value pair and make a price
                        System.out.println("new movie added into cart");
                        movieMap.put(title, new MovieSession(movieId, title, 1, currentDate));

                        //add price into db
                        price = movieMap.get(title).getPrice();


                    }
                }else{
                    //if there already isnt a movie map then set one and generate price for
                    System.out.println("empty cart now has 1 item ");
                    movieMap = new HashMap<>();

                    movieMap.put(title, new MovieSession(movieId, title, 1, currentDate));

                    session.setAttribute("movieMap", movieMap);

                    //add price into db
                    price = movieMap.get(title).getPrice();

                }


                //this is how we know we are adding properly into the shopping cart
                System.out.println("quantity after adding : " +movieMap.get(title).getQuantity());
                //return in the response the hashmap as json
                System.out.println(movieMap);
//                String jsonResponse = objectMapper.writeValueAsString(movieMap);
//                out.write(jsonResponse);
            }else{

                //should not happen
                System.out.println("NOT AUTHENTICATED : ADD.JAVA");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }



            //if the price is modified because a new item is added add into db
            if (price != 0.0){
                System.out.println("adding price into db ");
                try {
                    String query = "SELECT id FROM movies WHERE title = ?";


                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, title);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //title exists in movie table
                    if (resultSet.next()) {
                        String movieId = resultSet.getString("id");

                        System.out.println("title exists in movie");
                        //check if the query is already in tthe table
                        String dupeQuery = "SELECT movieId FROM movie_prices WHERE movieId = ?";
                        PreparedStatement dupeStatement = connection.prepareStatement(dupeQuery);
                        dupeStatement.setString(1, movieId);

                        ResultSet dupeResult = dupeStatement.executeQuery();

                        if (dupeResult.next()){
                            System.out.println("movie is already in table, update the price");
                            String updateQuery = "UPDATE movie_prices SET price = ? WHERE movieId = ?";

                            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

                            updateStatement.setDouble(1, price);
                            updateStatement.setString(2, movieId);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                System.out.println("Price Updated for " + movieId);
                            } else {
                                System.out.println("Price Failed to Update for " + movieId);
                            }

                            //close queries
                            dupeStatement.close();
                            dupeResult.close();
                            updateStatement.close();
                        }else{
                            System.out.println("Movie ID for title '" + title + "': " + movieId);
                            String insertQuery = "INSERT INTO movie_prices (movieId, price) VALUES (?, ?)";

                            try {
                                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                                insertStatement.setString(1, movieId);
                                insertStatement.setDouble(2, price);

                                int rowsInserted = insertStatement.executeUpdate();

                                if (rowsInserted > 0) {
                                    System.out.println("INSERT SUCCESS: " + movieId);
                                } else {
                                    System.out.println("INSERT FAIL: " + movieId);
                                }

                                insertStatement.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        //should never happen
                        System.out.println("Movie not found with title '" + title + "'");
                    }

                    resultSet.close();
                    preparedStatement.close();
                } catch (Exception e) {

                    request.getServletContext().log("Error: ", e);

                    out.print(e.getMessage());
                    out.flush();
                }

                out.flush();
            }
        }catch (Exception e) {

            request.getServletContext().log("Error: ", e);

            out.print(e.getMessage());
            out.flush();
        }






    }



}
