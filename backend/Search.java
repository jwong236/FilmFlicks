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
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/search")
public class Search extends HttpServlet {
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

        String title = request.getParameter("title");
        String director = request.getParameter("director");
        String star = request.getParameter("star");
        String year = request.getParameter("year");

        try (Connection connection = dataSource.getConnection()) {
            List<Movie> movies = searchMovies(connection, title, director, star, year);

            response.setStatus(HttpServletResponse.SC_OK);
            out.println(new ObjectMapper().writeValueAsString(movies));
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

    private List<Movie> searchMovies(Connection connection, String title, String director, String star, String year) throws SQLException {
        // Main GET logic
        List<String> queryParts = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        // Used LIKE predicate here to enable substring search for titles directors and stars
        if (title != null && !title.isEmpty()) {
            queryParts.add("LOWER(m.title) LIKE ?");
            parameters.add("%" + title.toLowerCase() + "%");
        }
        if (director != null && !director.isEmpty()) {
            queryParts.add("LOWER(m.director) LIKE ?");
            parameters.add("%" + director.toLowerCase() + "%");
        }
        if (star != null && !star.isEmpty()) {
            queryParts.add("LOWER(s.name) LIKE ?");
            parameters.add("%" + star.toLowerCase() + "%");
        }
        if (year != null && !year.isEmpty()) {
            queryParts.add("m.year = ?");
            parameters.add(Integer.parseInt(year));
        }

        if (queryParts.isEmpty()) {
            throw new IllegalArgumentException("No search criteria provided");
        }

        String whereClause = String.join(" AND ", queryParts);

        String fullQuery = buildFullQuery(whereClause);

        return executeSearchQuery(connection, fullQuery, parameters);
    }

    private String buildFullQuery(String whereClause) {
        return "SELECT " +
                "    m.title AS title, " +
                "    m.year AS year, " +
                "    m.director AS director, " +
                "    GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS Genres, " +
                "    GROUP_CONCAT(DISTINCT s.name ORDER BY s.name SEPARATOR ', ') AS Stars, " +
                "    r.rating AS rating, " +
                "    r.numVotes AS numvotes " +
                "FROM " +
                "    movies m " +
                "LEFT JOIN " +
                "    genres_in_movies gm ON m.id = gm.movieId " +
                "LEFT JOIN " +
                "    genres g ON g.id = gm.genreId " +
                "LEFT JOIN " +
                "    stars_in_movies sm ON m.id = sm.movieId " +
                "LEFT JOIN " +
                "    stars s ON s.id = sm.starId " +
                "LEFT JOIN " +
                "    ratings r ON m.id = r.movieId " +
                "WHERE " + whereClause +
                " GROUP BY " +
                "    m.id, m.title, m.year, m.director, r.rating, r.numVotes " +
                "ORDER BY " +
                "    m.title ASC;";
    }


    private List<Movie> executeSearchQuery(Connection connection, String query, List<Object> parameters) throws SQLException {
        // Executes the full query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof String) {
                    preparedStatement.setString(i + 1, (String) parameters.get(i));
                } else if (parameters.get(i) instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) parameters.get(i));
                }
            }
            return getMoviesFromResultSet(preparedStatement.executeQuery());
        }
    }


    private List<Movie> getMoviesFromResultSet(ResultSet resultSet) throws SQLException {
        // Extracts data from query results
        List<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            Movie movie = new Movie();
            movie.setTitle(resultSet.getString("title"));
            movie.setYear(resultSet.getInt("year"));
            movie.setDirector(resultSet.getString("director"));
            movie.setGenres(Arrays.asList(resultSet.getString("genres").split(", ")));
            movie.setStars(Arrays.asList(resultSet.getString("stars").split(", ")));
            movie.setRating(resultSet.getDouble("rating"));
            movie.setNumVotes(resultSet.getInt("numvotes"));
            movies.add(movie);
        }
        return movies;
    }
}
