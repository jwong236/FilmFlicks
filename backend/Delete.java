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


// This annotation maps this Java Servlet Class to a URL
@WebServlet("/delete")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;


    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("DELETE");
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


        //turn the parameters of the post request into a user class
        //containing email and password
        //set attribute movieMap : hashmap that contains movieTitle -> Movie Objects (price, quantity)
        String requestString = requestBody.toString();
        System.out.println(requestBody);
        System.out.println("delete request string " + requestString);
        String title = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DeleteObject deleteObject = objectMapper.readValue(requestString, DeleteObject.class);
            title = deleteObject.getTitle();
            // Other processing code
        } catch (Exception e) {
            // Handle any exceptions that occur during deserialization
            e.printStackTrace();
        }

        double price = 0.0;
        //get the session, check if it exists, if it is on this section it exists
        HttpSession session = request.getSession(false);
        System.out.println("delete session check: " + session);
        //if the session exists, get the movieMap
        if (session != null){
            //System.out.println("session exists");
            HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");
            System.out.println("movie map in delete " + movieMap);
            //if a map exists already increase the quantity
            if (movieMap != null){
                //System.out.println("map already exists");
                //map exists but the title is in increase
                System.out.println("does movie map contain this title : " + title + "result:  "  + movieMap.containsKey(title));
                if (movieMap.containsKey(title)){
                    //System.out.println("quantity increase");
                    //deletes the key value pair associated with the movie title
                    System.out.println("deleting this movie: " + title);
                    movieMap.remove(title);
                    System.out.println("session check after deletion: " + request.getSession(false));

                    try{
                        //go into database and delete row with the movieId
                        String query = "SELECT id FROM movies WHERE title = ?";

                        Connection connection = dataSource.getConnection();

                        PreparedStatement preparedStatement = connection.prepareStatement(query);

                        preparedStatement.setString(1, title);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        //movie exists in the movie_prices table
                        if (resultSet.next()){

                            String movieId = resultSet.getString("id");

                            String deleteQuery = "DELETE FROM movie_prices WHERE movieId = ?";

                            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);

                            deleteStatement.setString(1, movieId);

                            int rowsAffected = deleteStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("success: movie deleted.");
                            } else {
                                System.out.println("fail: movie was not deleted.");
                            }

                        }
                        connection.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }else{
                    //title doesnt exist then dont do anything
                    System.out.println("cant delete title doesnt exist in session");
                }
            }

            //this is how we know we are adding properly into the shopping cart
//            System.out.println(movieMap.get(title).getQuantity());
//            //return in the response the hashmap as json
//            System.out.println(movieMap);
            //return in the response the hashmap as json
            //should be one less item
//            String jsonResponse = objectMapper.writeValueAsString(movieMap);
//            out.write(jsonResponse);
        }else{

            //should not happen
            System.out.println("NOT AUTHENTICATED : ADD.JAVA");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }








    }



}
