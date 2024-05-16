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
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/fullTextSearch")
public class FullTextSearch extends HttpServlet {
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
        String userPage = request.getParameter("page");
        String userPageSize = request.getParameter("pageSize");
        String sortRule = request.getParameter("sortRule");

        System.out.println("SEARCH");
        System.out.println(" title: " + title + " userPage :" + userPage + " userPageSize: " + userPageSize + " sort rule: " + sortRule);

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
            List<Movie> movies = searchMovies(connection, title, page, pageSize, sortRule);

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

    private List<Movie> searchMovies(Connection connection, String title, int page, int pageSize, String sortRule) throws SQLException {
        String newTitle = "";
        if (title != null && !title.isEmpty()) {
            String[] titleTokens = title.split(" ");
            for (int i = 0; i < titleTokens.length - 1; i++) {
                titleTokens[i] = "+" + titleTokens[i] + "* ";
                newTitle += titleTokens[i];
            }
            newTitle += "+" + titleTokens[titleTokens.length - 1] + "*";
        }

        String sortClause = buildSortClause(sortRule);
        int offset = (page - 1) * pageSize;

        String query = "SELECT " +
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
                "WHERE MATCH (title) AGAINST (? in BOOLEAN MODE) " +
                "GROUP BY " +
                "    m.id, m.title, m.year, m.director, r.rating, r.numVotes " +
                sortClause +
                " LIMIT ? OFFSET ?;";

        System.out.println("query: " + query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, offset);

            String preparedQuery = preparedStatement.toString();
            System.out.println("Prepared query: " + preparedQuery);

            return getMoviesFromResultSet(preparedStatement.executeQuery());
        }
    }

    private String buildSortClause(String sortRule) {
        if (sortRule == null || sortRule.isEmpty()) return "";
        String[] parts = sortRule.split("_");
        if (parts.length != 4) return "";

        String field1 = mapField(parts[0]);
        String direction1 = mapDirection(parts[1]);
        String field2 = mapField(parts[2]);
        String direction2 = mapDirection(parts[3]);

        return "ORDER BY " + field1 + " " + direction1 + ", " + field2 + " " + direction2;
    }

    private String mapField(String field) {
        switch (field) {
            case "title":
                return "m.title";
            case "rating":
                return "r.rating";
            case "year":
                return "m.year";
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

    private List<Movie> getMoviesFromResultSet(ResultSet resultSet) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            Movie movie = new Movie();
            movie.setTitle(resultSet.getString("title"));
            movie.setYear(resultSet.getInt("year"));
            movie.setDirector(resultSet.getString("director"));
            String genresString = resultSet.getString("Genres");
            if (genresString != null) {
                movie.setGenres(Arrays.asList(genresString.split(", ")));
            }
            String starsString = resultSet.getString("Stars");
            if (starsString != null) {
                movie.setStars(Arrays.asList(starsString.split(", ")));
            }
            movie.setRating(resultSet.getDouble("rating"));
            movie.setNumVotes(resultSet.getInt("numvotes"));
            movies.add(movie);
        }
        return movies;
    }
}
