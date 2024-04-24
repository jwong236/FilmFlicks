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
@WebServlet("/totalPrice")
public class TotalPrice extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("TOTAL PRICE");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //get the session, check if it exists, if it is on this section it exists
        HttpSession session = request.getSession(false);

        //if the session exists, get the movieMap
        if (session != null){
            System.out.println("session exists");
            HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");

            //if a map exists already decrease the quantity
            if (movieMap != null){
                System.out.println("map already exists");

                double total = 0;

                System.out.println( "MOVIE MAP ENTRY SET " + movieMap);
                //iterate through the movieMap calculating the total price from getPrice, getQuantity
                for (String title : movieMap.keySet()) {
                    MovieSession movieObj = movieMap.get(title);

                    System.out.println("movie object "  + movieObj);
                    double temp = movieObj.getTotal();
                    total+=temp;
                }

                System.out.println("total for movie " + total);
                HashMap<String, Double> responseMap = new HashMap<>();
                responseMap.put("total", total);

                // Convert the responseMap to JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(responseMap);

                out.print(jsonResponse);

            }else{
                System.out.println("movie map doesnt exist");
            }


        }else{

            //should not happen
            System.out.println("NOT AUTHENTICATED : ADD.JAVA");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }


        out.flush();





    }



}
