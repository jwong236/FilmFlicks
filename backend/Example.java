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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/example")
public class Example extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Initialize response writer
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Prepare the SQL query
        String query = "SELECT director, COUNT(*) as total_movies, MAX(release_date) AS latest_movie_date " +
                "FROM movies " +
                "GROUP BY director " +
                "ORDER BY total_movies DESC, latest_movie_date DESC " +
                "LIMIT 20";

        // Connect to db and query to get results
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet queryResults = preparedStatement.executeQuery();
            List<Map<String, Object>> data = new ArrayList<>();

            // Parse through the rows of the results and extract data
            while (queryResults.next()) {
                Map<String, Object> extractedRows = new LinkedHashMap<>();
                extractedRows.put("director", queryResults.getString("director"));
                data.add(extractedRows);
            }

            // Convert data to json and write to response stream
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectWriter objWriter = objectMapper.writerWithDefaultPrettyPrinter();
            String dataJson = objWriter.writeValueAsString(data);

            // Log and set status
            out.print(dataJson);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Error fetching director data.\"}");
            request.getServletContext().log("Error: ", e);
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
