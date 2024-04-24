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
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;


    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
            System.out.println("LOGIN");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder requestBody = new StringBuilder();

        String email = "";
        String password = "";

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
        String requestString = requestBody.toString();
        System.out.println("request string " + requestString);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(requestString, User.class);

        PrintWriter out = response.getWriter();
        // Set response mime type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        try {

            Connection connection = dataSource.getConnection();
            // prepare query
            String query = "SELECT c.id FROM customers c WHERE c.email = ? AND c.password = ?";
            // declare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());


            // execute query
            ResultSet resultSet = preparedStatement.executeQuery();

//            System.out.println("result set " + resultSet);
            //null means failure
            if (resultSet.next()){
                HttpSession session = request.getSession(false);
                System.out.println("http session: " + session);

                //System.out.println(sessionEmail);
                String customerId = resultSet.getString("id");



                if (session == null){
                    System.out.println("SESSION IS NULL IN LOGIN JAVA");
//                    request.getSession().setAttribute("email", new Email(user.getEmail()));
//                    response.setHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; Path=/fabFlix");
                    session = request.getSession();
//                    response.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/fabFlix");
                    session.setAttribute("email", new Email(customerId, user.getEmail()));

                    Email emailObj = (Email)session.getAttribute("email");

                    System.out.println("new email is used for login: " + emailObj.emailGetter());
                }else{
                    System.out.println("SESSION IS NOT NULL IN LOGIN JAVA");
                    Email emailObj = (Email)session.getAttribute("email");
                    System.out.println("recurring email is used : " + emailObj.emailGetter());
                }

                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                System.out.println("CHECKING IF EMAIL/PW WRONG");
                String emailQuery = "SELECT * FROM customers c WHERE c.email = ?";
                PreparedStatement emailPrepStatement = connection.prepareStatement(emailQuery);

                emailPrepStatement.setString(1, user.getEmail());

                ResultSet emailResult = emailPrepStatement.executeQuery();

                System.out.println("EMAIL RESULT: " + emailResult);
                //wrong pw
                if(emailResult.next()){
                    System.out.println("WRONG PASSWORD");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }else{
                    System.out.println("WRONG EMAIL");
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }


            //flush out the buffer just in case
            out.flush();
            connection.close();



        } catch (Exception e) {

            request.getServletContext().log("Error: ", e);



            out.print(e.getMessage());
            out.flush();
        }



    }



}
