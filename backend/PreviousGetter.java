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


//Checks the session and looks for the key = "previous" if true then there is a previous,
//if false then there isn't and create a previous based on the new end point and param
@WebServlet("/previousGetter")
public class PreviousGetter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private boolean hashMapComparison(HashMap<String, HashMap<String, String>> prev, HashMap<String, HashMap<String, String>> curr){
        if (prev.size() != curr.size()) {
            return false;
        }

        for (Map.Entry<String, HashMap<String, String>> entry : prev.entrySet()) {
            String key = entry.getKey();
            HashMap<String, String> prevInnerMap = entry.getValue();
            HashMap<String, String> currInnerMap = curr.get(key);
            if (currInnerMap == null) {
                return false;
            }

            for (Map.Entry<String, String> innerEntry : prevInnerMap.entrySet()) {
                String innerKey = innerEntry.getKey();
                String prevValue = innerEntry.getValue();
                String currValue = currInnerMap.get(innerKey);
                if (!Objects.equals(prevValue, currValue)) {
                    return false;
                }
            }
        }
        return true;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean prevSessionFlag = false;
        System.out.println("inside of the previous getter");

        BufferedReader reader = request.getReader();

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        System.out.println("http session: " + session);

        HashMap<String, HashMap<String, String>> prev = (HashMap<String, HashMap<String, String>>) session.getAttribute("prev");


        if (request.getParameter("endpoint").isEmpty()){
            System.out.println("endpoint is empty");
            prevSessionFlag = true;
        }

        //checks what's inside of the request body
        if (!prevSessionFlag || reader != null) {
            System.out.println("Request Body:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.print(line);
            }
        } else {
            //if the body is empty than send the prev session as a response for the frontend to use
            prevSessionFlag = true;
            System.out.println("Request body is null");
        }


        //get the new updated current session if the request body contains content or there is no prev
        if(!prevSessionFlag){
            System.out.println("USE THE CURRENT PARAMS");
            String userPage = request.getParameter("page");
            String userPageSize = request.getParameter("pageSize");
            String sortRule = request.getParameter("sortRule");
            System.out.println("userPage " + userPage + " userPageSize " + userPageSize + " sort rule " + sortRule);
            String title = "";
            String director = "";
            String star = "";
            String year = "";
            String genre = "";
            String character = "";

            ArrayList<String> searchArr = new ArrayList<>();
            searchArr.add("title");
            searchArr.add("director");
            searchArr.add("star");
            searchArr.add("year");



            //if end point is search, browse/genre, or browse/character
            String endpoint = request.getParameter("endpoint");
            HashMap <String, HashMap<String, String>> searchMap = new HashMap<String, HashMap<String, String>>();
            System.out.println("endpoint " + endpoint);


            if (endpoint.equals("search")) {
                //holds title director star and year not all of them will be included though
                HashMap<String, String> searchParams = new HashMap<>();
                for (String searchElem : searchArr){
                    String temp = request.getParameter(searchElem);

                    //if it is a valid param, put it in the hashmap
                    if (temp != null && !temp.isEmpty()){
                        searchParams.put(searchElem, temp);
                    }
                }

                searchParams.put("userPage", userPage);
                searchParams.put("userPageSize", userPageSize);
                searchParams.put("sortRule", sortRule);

                searchMap.put(endpoint, searchParams);
            }
//            else if( endpoint.equals("brose/genre")){
//
//            }


            try {

                //session null means that there is no previous search for movielist
                //which means to store the current one as previous and return status picked up by the frontend
                //which basically means to just continue with original get request
                if (session == null) {
                    //this shouldnt ever happen because a session is created when login
                    //create session and store the current in the session
                    session = request.getSession();
                    System.out.println("search map: " + searchMap);

                    session.setAttribute("prev", searchMap);

                    System.out.println("no previous movie list, using current endpt and params");
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    //check if there is already an attribute for prev
                    System.out.println("checking if there is already an attribute for prev ");

                    //if there is a prev but the body is not null then that means a new search is activated and update the prev
                    if (prev != null && !prev.isEmpty()) {
                        //compare the search map to prev to see if we should upate the session params
                        if (!hashMapComparison(prev, searchMap)) {
                            System.out.println("search params updated, using the current");
                            response.setStatus(HttpServletResponse.SC_CONTINUE);
                        }
                    } else {
                        //session exists but there is no prev from searching just return as if session == null w/o creating new sesh
                        //THIS IS THE DEFAULT BEHAVIOR WHERE THIS IS THE FIRST SEARCH AND NO PREV EXISTS
                        System.out.println("search map: " + searchMap);

                        session.setAttribute("prev", searchMap);
                        System.out.println("no previous movie list, using current endpt and params");
                        response.setStatus(HttpServletResponse.SC_CREATED);
                    }


                }
            }
            catch (Exception e) {

                    request.getServletContext().log("Error: ", e);

                    out.print(e.getMessage());
                    out.flush();
            }
        }else{
                //use previous if when you click on list the body is null because nothing was searched before
                //THIS IS THE ONLY CASE WHERE YOU WOULD USE THE PREVIOUS

                ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
                String prevJson = mapper.writeValueAsString(prev);
                System.out.println("using the previous params" + prev);
                out.print(prevJson);
                response.setStatus(HttpServletResponse.SC_OK);
        }







            //flush out the buffer just in case
            out.flush();







    }



}
