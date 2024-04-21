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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/browse/genre")
public class BrowseGenre extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String genre = request.getParameter("genre");
        if (genre == null || genre.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Genre parameter is required.\"}");
            out.flush();
            return;
        }

        List<Map<String, Object>> movies = new ArrayList<>();

        String query = "SELECT m.title AS Title, m.year AS Year, m.director AS Director, " +
                "GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS Genres, " +
                "GROUP_CONCAT(DISTINCT s.name ORDER BY s.name SEPARATOR ', ') AS Stars, " +
                "r.rating AS Rating, r.numVotes AS NumVotes " +
                "FROM movies m " +
                "JOIN genres_in_movies gm ON m.id = gm.movieId " +
                "JOIN genres g ON gm.genreId = g.id " +
                "LEFT JOIN stars_in_movies sm ON m.id = sm.movieId " +
                "LEFT JOIN stars s ON sm.starId = s.id " +
                "LEFT JOIN ratings r ON m.id = r.movieId " +
                "WHERE g.name = ? " +
                "GROUP BY m.id, m.title, m.year, m.director, r.rating, r.numVotes " +
                "ORDER BY m.title ASC";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, genre);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> movie = new LinkedHashMap<>();
                movie.put("title", rs.getString("Title"));
                movie.put("year", rs.getInt("Year"));
                movie.put("director", rs.getString("Director"));
                movie.put("genres", rs.getString("Genres"));
                movie.put("stars", rs.getString("Stars"));
                movie.put("rating", rs.getDouble("Rating"));
                movie.put("numVotes", rs.getInt("NumVotes"));
                movies.add(movie);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            out.println(objectMapper.writeValueAsString(movies));
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Server error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}
