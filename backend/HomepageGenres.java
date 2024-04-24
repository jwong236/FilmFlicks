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
import java.sql.Statement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.util.*;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpSession;




// This annotation maps this Java Servlet Class to a URL
@WebServlet("/homepageGenres")
public class HomepageGenres extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;


    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("HOMEPAGE GENRES");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }







    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



        ObjectMapper objectMapper = new ObjectMapper();

        PrintWriter out = response.getWriter();
        // Set response mime type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        try {
            HashMap<String, HashMap<Integer, String>> genreMap = new HashMap<String, HashMap<Integer, String>>();

            Connection connection = dataSource.getConnection();
            String query = "SELECT * FROM genres";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
//            HashMap<Integer, String> g = new HashMap<Integer, String>();
//
//            while (resultSet.next()){
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                g.put(id, name);
//            }
//
//            genreMap.put("genre" , g);
//
//            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
//            String genreJson = mapper.writeValueAsString(genreMap);
//            System.out.println("using the previous params" + genreMap);
//            out.print(genreJson);

            ArrayList<GenreObjects> genreList = new ArrayList<GenreObjects>();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String genreName = resultSet.getString("name");
                GenreObjects genre = new GenreObjects(id, genreName);

                genreList.add(genre);
            }


            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String genreJson = mapper.writeValueAsString(genreList);
            System.out.println("genre list" + genreList);
            out.print(genreJson);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {

            request.getServletContext().log("Error: ", e);



            out.print(e.getMessage());
            out.flush();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }



    }



}
