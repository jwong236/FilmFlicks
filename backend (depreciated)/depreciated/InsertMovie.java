import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/insert/movie")
public class InsertMovie extends HttpServlet {
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
        Movie movie = objectMapper.readValue(request.getReader(), Movie.class);

        try (Connection connection = dataSource.getConnection();
             CallableStatement cstmt = connection.prepareCall("{CALL add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            cstmt.setString(1, movie.getTitle());
            cstmt.setInt(2, movie.getYear());
            cstmt.setString(3, movie.getDirector());
            cstmt.setString(4, movie.getStarName());
            if (movie.getStarBirthYear() > 0) {
                cstmt.setInt(5, movie.getStarBirthYear());
            } else {
                cstmt.setNull(5, java.sql.Types.INTEGER);
            }
            cstmt.setString(6, movie.getGenreName());
            cstmt.registerOutParameter(7, java.sql.Types.CHAR, 9); // newMovieId
            cstmt.registerOutParameter(8, java.sql.Types.CHAR, 9); // newStarId
            cstmt.registerOutParameter(9, java.sql.Types.INTEGER); // newGenreId
            cstmt.registerOutParameter(10, java.sql.Types.VARCHAR); // outputMessage

            cstmt.execute();

            String newMovieId = cstmt.getString(7);
            String newStarId = cstmt.getString(8);
            int newGenreId = cstmt.getInt(9);
            String outputMessage = cstmt.getString(10);

            out.println("{\"status\": \"success\", \"newMovieId\": \"" + newMovieId +
                    "\", \"newStarId\": \"" + newStarId +
                    "\", \"newGenreId\": " + newGenreId +
                    ", \"message\": \"" + outputMessage + "\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    static class Movie {
        private String title;
        private int year;
        private String director;
        private String starName;
        private int starBirthYear;
        private String genreName;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
        public String getDirector() { return director; }
        public void setDirector(String director) { this.director = director; }
        public String getStarName() { return starName; }
        public void setStarName(String starName) { this.starName = starName; }
        public int getStarBirthYear() { return starBirthYear; }
        public void setStarBirthYear(int starBirthYear) { this.starBirthYear = starBirthYear; }
        public String getGenreName() { return genreName; }
        public void setGenreName(String genreName) { this.genreName = genreName; }
    }
}
