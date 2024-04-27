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
        String userPage = request.getParameter("page");
        String userPageSize = request.getParameter("pageSize");
        String sortRule = request.getParameter("sortRule");


        int page = 1;
        int pageSize = 10;
        try {
            if (userPage != null) {
                page = Integer.parseInt(userPage);
            }
            if (userPageSize != null) {
                pageSize = Integer.parseInt(userPageSize);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Invalid pagination parameters.\"}");
            out.flush();
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            List<Movie> movies = searchMovies(connection, title, director, star, year, page, pageSize, sortRule);

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

    private List<Movie> searchMovies(Connection connection, String title, String director, String star, String year, int page, int pageSize, String sortRule) throws SQLException {
        List<String> queryParts = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

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
        int offset = (page - 1) * pageSize;

        String fullQuery = buildFullQuery(whereClause, pageSize, offset, sortRule);


        return executeSearchQuery(connection, fullQuery, parameters);
    }


    private String buildFullQuery(String whereClause, int pageSize, int offset, String sortRule) {
        String orderByClause = parseSortRule(sortRule); // This will generate the ORDER BY clause based on sortRule
        return "SELECT " +
                "    m.title AS title, " +
                "    m.year AS year, " +
                "    m.director AS director, " +
                "    GROUP_CONCAT(DISTINCT g.name ORDER BY g.name SEPARATOR ', ') AS Genres, " +
                "    GROUP_CONCAT(DISTINCT s.name ORDER BY movie_count DESC, s.name SEPARATOR ', ') AS Stars, " +
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
                "LEFT JOIN ( " +
                "    SELECT starId, COUNT(movieId) AS movie_count " +
                "    FROM stars_in_movies " +
                "    GROUP BY starId " +
                ") AS movie_counts ON s.id = movie_counts.starId " +
                "WHERE " + whereClause + " " +
                "GROUP BY " +
                "    m.id, m.title, m.year, m.director, r.rating, r.numVotes " +
                orderByClause + " " +
                "LIMIT " + pageSize + " OFFSET " + offset + ";";

    }
    private String parseSortRule(String sortRule) {
        if (sortRule == null || sortRule.isEmpty()) return ""; // Default sort if none provided
        String[] parts = sortRule.split("_");
        if (parts.length != 4) return ""; // Ensure sortRule is in the expected format
        String field1 = mapField(parts[0]);
        String direction1 = mapDirection(parts[1]);
        String field2 = mapField(parts[2]);
        String direction2 = mapDirection(parts[3]);

        return String.format("ORDER BY %s %s, %s %s", field1, direction1, field2, direction2);
    }

    private String mapField(String field) {
        switch (field) {
            case "title":
                return "m.title";
            case "rating":
                return "r.rating";
            default:
                throw new IllegalArgumentException("Invalid sorting field: " + field);
        }
    }

    private String mapDirection(String direction) {
        switch (direction) {
            case "asc":
                return "ASC";
            case "desc":
                return "DESC";
            default:
                throw new IllegalArgumentException("Invalid sorting direction: " + direction);
        }
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
