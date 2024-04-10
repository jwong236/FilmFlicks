//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// This annotation maps this Java Servlet Class to a URL
@WebServlet("/singlestar")
public class SingleStar extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



        // Get the PrintWriter for writing response
        PrintWriter out = response.getWriter();

        // Set response mime type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        String name = request.getParameter("name");

        try (Connection connection = dataSource.getConnection()){


            // prepare query
            String query =  "SELECT s.name,  s.birthYear, GROUP_CONCAT(m.title SEPARATOR ', ') AS movieTitles FROM stars s JOIN stars_in_movies sm ON sm.starId = s.id JOIN movies m ON m.id = sm.movieId WHERE s.name= ? GROUP BY s.name, s.birthYear" ;


            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1,name);


            // execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(resultSet);

            //have to move it past the header
            resultSet.next();


            //holds the json string hashmaps
            List<Map<String, String>> jsonArr = new ArrayList<>();

            String starName = resultSet.getString("name");
            String birthYear = resultSet.getString("birthYear");
            String movieTitles = resultSet.getString("movieTitles");



            //used to map the strings to their keys to create a json structure
            Map<String, String> data = new LinkedHashMap<>();


            //key is left "____" : val is the right column
            //ex: "title" : movieTitle
            data.put("name", starName);
            data.put("birthYear", birthYear);
            data.put("movieTitles", movieTitles);



            //adds each map into an array
            jsonArr.add(data);
            //jackson api used for turn the hashmap of strings into a string structured like json
            ObjectMapper objMap = new ObjectMapper();

            //structures json so that each object is seperated like normal json struct
            objMap.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectWriter objWrite = objMap.writerWithDefaultPrettyPrinter();

            //turns into json string
            String jsonString = objWrite.writeValueAsString(jsonArr);


            //close database connection when done
            resultSet.close();
            preparedStatement.close();

            //the json string will be our json response
            out.print(jsonString);

            response.setStatus(200);

            //flush out the buffer just in case
            out.flush();





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