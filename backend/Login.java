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
import java.sql.PreparedStatement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletConfig;

// This annotation maps this Java Servlet Class to a URL
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("hello");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        // Get the PrintWriter for writing response
        PrintWriter out = response.getWriter();

        // Set response mime type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        try {

            Connection connection = dataSource.getConnection();
            // prepare query
            String query = "SELECT * FROM customers c WHERE c.email = ? and c.password = ?";
            // declare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);


            // execute query
            ResultSet resultSet = preparedStatement.executeQuery(query);

            ObjectMapper objectMapper = new ObjectMapper();

            Map<String,String> jsonResponse= new HashMap<>();

            //null means failure
            if (resultSet.next()){

                jsonResponse.put("status" , "success");
                jsonResponse.put("message" , "login successful");

                response.setStatus(HttpServletResponse.SC_OK);
            }else{

                //add email into cookie
                jsonResponse.put("status" , "fail");
                jsonResponse.put("message" , "login fail");

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            String jsonString = objectMapper.writeValueAsString(jsonResponse);
            out.print(jsonString);
            //flush out the buffer just in case
            out.flush();
            connection.close();



        } catch (Exception e) {
            /*
             * After you deploy the WAR file through tomcat manager webpage,
             *   there's no console to see the print messages.~~
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