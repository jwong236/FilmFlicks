//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mysql.cj.xdevapi.JsonArray;
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


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpSession;


//whole purpose of this is to return the session's hashmap in valid json format
@WebServlet("/shoppingCart")
public class ShoppingCart extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        //get the session, check if it exists, if it is on this section it exists
        HttpSession session = request.getSession(false);
        ObjectMapper objectMapper = new ObjectMapper();

        //if the session exists, get the movieMap
        if (session != null){
            //System.out.println("session exists");
            HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");
            HashMap<String, Object> movieMapJson = new HashMap<>();

            //if a map exists already increase the quantity
            if (movieMap != null){
                System.out.println("map already exists");

                for (String title : movieMap.keySet()) {
                    //gets all the info for a movie
                    MovieSession movieObj = movieMap.get(title);
                    int quantity = movieObj.getQuantity();
                    String movieId = movieObj.getId();
                    double price = movieObj.getPrice();
                    double totalPrice = price * quantity;

                    //creates a new object containing the totalPrice
                    HashMap<String, Object> movieSessionJson = new HashMap<>();
                    movieSessionJson.put("id", movieId);
                    movieSessionJson.put("quantity", quantity);
                    movieSessionJson.put("price", price);
                    movieSessionJson.put("totalPrice", totalPrice);


                    movieMapJson.put(title, movieSessionJson);
                }
                System.out.println("new movie map " + movieMapJson);
                String jsonResponse = objectMapper.writeValueAsString(movieMapJson);
                out.write(jsonResponse);
                response.setStatus(HttpServletResponse.SC_OK);


            }else{
                System.out.println("session doesnt have anything in the shopping cart");
                response.setStatus(HttpServletResponse.SC_OK);

            }

        }else{

            //should not happen
            System.out.println("NOT AUTHENTICATED : ADD.JAVA");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }









    }



}
