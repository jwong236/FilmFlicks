import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/write");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();
        Star star = objectMapper.readValue(request.getReader(), Star.class);

        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL add_star(?, ?)}")) {
            cstmt.setString(1, star.getName());
            if (star.getBirthYear() != null && !star.getBirthYear().isEmpty()) {
                cstmt.setInt(2, Integer.parseInt(star.getBirthYear()));
            } else {
                cstmt.setNull(2, Types.INTEGER);
            }

            boolean hasResults = cstmt.execute();
            if (hasResults) {
                try (ResultSet rs = cstmt.getResultSet()) {
                    if (rs.next()) {
                        String message = rs.getString("message");
                        String starId = rs.getString("starId");
                        out.println("{\"status\": \"success\", \"message\": \"" + message + "\", \"starId\": \"" + starId + "\"}");
                    }
                }
            } else {
                out.println("{\"status\": \"success\", \"message\": \"Star added, but no ID returned.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}");
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
