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
import java.util.Arrays;

@WebServlet("/singlemovie")
public class SingleMovie extends HttpServlet {
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

        // Get parameters
        String title = request.getParameter("title");

        // Prepare the SQL query
        String query = "SELECT \n" +
                "    m.title, \n" +
                "    m.year, \n" +
                "    m.director,\n" +
                "    GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS genres,\n" +
                "    GROUP_CONCAT(DISTINCT s.name ORDER BY s.name SEPARATOR ', ') AS stars,\n" +
                "    r.rating\n" +
                "FROM \n" +
                "    movies m\n" +
                "LEFT JOIN \n" +
                "    genres_in_movies gm ON m.id = gm.movieId\n" +
                "LEFT JOIN \n" +
                "    genres g ON gm.genreId = g.id\n" +
                "LEFT JOIN \n" +
                "    stars_in_movies sm ON m.id = sm.movieId\n" +
                "LEFT JOIN \n" +
                "    stars s ON sm.starId = s.id\n" +
                "LEFT JOIN\n" +
                "    ratings r ON m.id = r.movieId\n" +
                "WHERE \n" +
                "    m.title = ?\n" +
                "GROUP BY \n" +
                "    m.id, r.rating;";




        // Connect to db and query to get results
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            ResultSet queryResults = preparedStatement.executeQuery();
            List<Map<String, Object>> data = new ArrayList<>();

            // Parse through the rows of the results and extract data
            while (queryResults.next()) {
                Map<String, Object> extractedRows = new LinkedHashMap<>();
                extractedRows.put("title", queryResults.getString("title"));
                extractedRows.put("year", queryResults.getString("year"));
                extractedRows.put("director", queryResults.getString("director"));

                String genres = queryResults.getString("genres");
                String stars = queryResults.getString("stars");
                List<String> genreList = (genres != null && !genres.isEmpty()) ? Arrays.asList(genres.split(", ")) : new ArrayList<>();
                List<String> starList = (stars != null && !stars.isEmpty()) ? Arrays.asList(stars.split(", ")) : new ArrayList<>();
                extractedRows.put("genres", genreList);
                extractedRows.put("stars", starList);
                extractedRows.put("rating", queryResults.getString("rating"));
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
            queryResults.close();
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
