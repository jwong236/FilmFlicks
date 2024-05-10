import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.ServletConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/insert/star")
public class InsertStar extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            System.out.println("Reading error");
            e.printStackTrace();
        }

        String requestString = requestBody.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Star star = objectMapper.readValue(requestString, Star.class);
        String starName = star.getName();
        String starBirthYear = star.getBirthYear();

        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL add_star(?, ?)}")) {
            cstmt.setString(1, starName);
            if (starBirthYear != null && !starBirthYear.isEmpty()) {
                cstmt.setInt(2, Integer.parseInt(starBirthYear));
            } else {
                cstmt.setNull(2, Types.INTEGER);
            }
            cstmt.execute();

            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("Star inserted successfully.");

            out.println("{\"status\": \"success\", \"message\": \"Star added successfully.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Database error: " + e.getMessage());
            out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    static class Star {
        private String name;
        private String birthYear;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(String birthYear) {
            this.birthYear = birthYear;
        }
    }
}
