import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/*

The SQL command to create the table ft.

DROP TABLE IF EXISTS ft;
CREATE TABLE ft (
    entryID INT AUTO_INCREMENT,
    entry text,
    PRIMARY KEY (entryID),
    FULLTEXT (entry)) ENGINE=MyISAM;

*/

/*

Note: Please change the username, password and the name of the datbase.

*/

public class MovieInsert {

    public static void insertBatch (List<MovieXML> movieList) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        HashMap<String, Integer> genreIdMap = new HashMap<>();
        String[] genres = {
                "Action", "Adult", "Adventure", "Animation", "Biography",
                "Comedy", "Crime", "Documentary", "Drama", "Family",
                "Fantasy", "History", "Horror", "Music", "Musical",
                "Mystery", "Reality-TV", "Romance", "Sci-Fi", "Sport",
                "Thriller", "War", "Western", "Black", "Television"
        };
        int index = 1;
        for (String genre : genres) {
            genreIdMap.put(genre, index);
            index++;
        }



        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String jdbcURL = "jdbc:mysql://127.0.0.1:3307/moviedb";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(jdbcURL, "admin", "andy");
        } catch (SQLException e) {
            e.printStackTrace();
        }




        PreparedStatement psInsertRecord=null;
        String sqlInsertRecord=null;

        int[] iNoRows=null;


        sqlInsertRecord="insert into movies (id, title, year, director) values(?,?, ?, ?)";
        try {
            conn.setAutoCommit(false);

            psInsertRecord=conn.prepareStatement(sqlInsertRecord);


            for(MovieXML movie: movieList)
            {
                psInsertRecord.setString(1, movie.getId());
                psInsertRecord.setString(2, movie.getTitle());
                psInsertRecord.setInt(3, movie.getYear());
                psInsertRecord.setString(4, movie.getDirector());
                psInsertRecord.addBatch();
            }

            iNoRows=psInsertRecord.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(psInsertRecord!=null) psInsertRecord.close();
            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}


