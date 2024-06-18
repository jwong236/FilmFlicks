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
@WebServlet("/subtract")
public class Subtract extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;


    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("SUBTRACT");
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
        System.out.println("request string " + requestString);
        ObjectMapper objectMapper = new ObjectMapper();
        MovieObject movieObject = objectMapper.readValue(requestString, MovieObject.class);

        String title =  movieObject.getMovieTitle();
        //get the movie title
        System.out.println("movieTitle " + title);


        double price = 0.0;
        //get the session, check if it exists, if it is on this section it exists
        HttpSession session = request.getSession(false);
        boolean validDecrement = false;

        //if the session exists, get the movieMap
        if (session != null){
            System.out.println("session exists");
            HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");

            //if a map exists already decrease the quantity
            if (movieMap != null){
                System.out.println("map already exists subtract");
                //map exists but the title is in increase
                if (movieMap.containsKey(title)){
                    if (movieMap.get(title).getQuantity() > 1){
                        System.out.println("quantity decreases: " + movieMap.get(title).getQuantity());
                        movieMap.get(title).decrease();
                        validDecrement = true;
                    }else{
                        //you cant decremement when it's 1
                        System.out.println("you cant decremement when it's 1: " + movieMap.get(title).getQuantity());
                    }
                }
            }else{
                System.out.println("movie map doesnt exist");
            }

            //either user is trying to decrement quantity 1 or the map doeesnt exist already
            if (validDecrement == false){
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }else{
                response.setStatus(HttpServletResponse.SC_OK);
            }

            //this is how we know we are adding properly into the shopping cart
            System.out.println("quantity after decreasing: " + movieMap.get(title).getQuantity());
            //return in the response the hashmap as json
            System.out.println(movieMap);


            //return in the response the hashmap as json
//            String jsonResponse = objectMapper.writeValueAsString(movieMap);
//            out.write(jsonResponse);
        }else{

            //should not happen
            System.out.println("NOT AUTHENTICATED : ADD.JAVA");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }








    }



}
