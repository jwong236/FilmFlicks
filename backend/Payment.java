//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.transform.Result;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


// This annotation maps this Java Servlet Class to a URL
@WebServlet("/payment")
public class Payment extends HttpServlet {
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

        String firstName = "";
        String lastName = "";
        String creditCardNumber = "";
        Date expiration = null;


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
        CreditCard creditCard = objectMapper.readValue(requestString, CreditCard.class);

        firstName = creditCard.getFirstName();
        lastName = creditCard.getLastName();
        creditCardNumber = creditCard.getCreditCardNumber();
        expiration = creditCard.getExpirationDate();


        PrintWriter out = response.getWriter();
        // Set response mime type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        try {

            Connection connection = dataSource.getConnection();
            // prepare query
            String query = "SELECT * FROM creditcards c WHERE c.firstName = ? AND c.lastName = ? AND c.id = ? AND c.expiration = ?";
            // declare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, creditCardNumber);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String expirationString = dateFormat.format(expiration);


            preparedStatement.setString(4, expirationString);


            // execute query
            ResultSet resultSet = preparedStatement.executeQuery();


            //if result set is not null that means the credit card info is valid
            if (resultSet.next()){
                HttpSession session = request.getSession(false);
                System.out.println("http session: " + session);

                //if null, then you cant add into the cart bc no shopping cart
                if (session != null){

                    Email emailObj = (Email)session.getAttribute("email");

                    String email = emailObj.emailGetter();

                    String customerId = emailObj.customerIdGetter();

                    System.out.println("email: " + email + " customer id : " + customerId);

                    //add the items into the sales table
                    //customerId -> email.java, but it will stay the same because one customer per shopping cart
                    //movieId, SaleDate -> movieSession.java
                    String insertQuery = "INSERT INTO sales (customerId, movieId, saleDate) VALUES (?,?,?)";

                    //iterate throughout the session and get the items in the shopping cart
                    // and add them individually into the sales table
                    HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");

                    //map has to exist for the shopping cart to be added into the sales table
                    if (movieMap != null){
                        System.out.println("INSERTING ALL ITEMS IN SHOPPING CART INTO THE DB");
                        for (String title : movieMap.keySet()) {
                            MovieSession movieObj = movieMap.get(title);
                            int quantity = movieObj.getQuantity();
                            String movieId = movieObj.getId();
                            LocalDate saleDate = movieObj.getSaleDate();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                            String formattedSaleDate = saleDate.format(formatter);

                            //quantity of item will be distinguished by same customerId and movieId but diff sale id

                            if(quantity > 1){
                                for (int i = 0; i < quantity; i++){
                                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                                    insertStatement.setString(1, customerId);
                                    insertStatement.setString(2, movieId);
                                    insertStatement.setString(3, formattedSaleDate);

                                    int rowsInserted = insertStatement.executeUpdate();

                                    if (rowsInserted > 0) {
                                        System.out.println("INSERT SUCCESS (id) + " + (i+1) + ": " + movieId);
                                    } else {
                                        System.out.println("INSERT FAIL: " + movieId);
                                    }
                                }
                            }

                        }
                    }else{
                        System.out.println("no movie map exists : no inserting into the sales table");
                    }

                }

                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("credit card info is invalid");
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
