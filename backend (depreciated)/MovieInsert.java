import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
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

    private static Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Replace with your connection details
            String jdbcURL = "jdbc:mysql://127.0.0.1:3307/moviedb";
            return DriverManager.getConnection(jdbcURL, "admin", "andy");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

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

        try{
            conn = getConnection();

        }catch(Exception e){
            e.printStackTrace();
        }





        PreparedStatement psInsertMovie=null;
        PreparedStatement psInsertGenre_Movies=null;

        String movieSQL=null;
        String genre_movieSQL = null;

        int[] iNoRows=null;


        movieSQL="insert ignore into movies (id, title, year, director) values(?,?, ?, ?)";
        genre_movieSQL = "insert ignore into genres_in_movies (genreId, movieId) values(?,?)";
        try {
            conn.setAutoCommit(false);

            psInsertMovie=conn.prepareStatement(movieSQL);
            psInsertGenre_Movies = conn.prepareStatement(genre_movieSQL);

            for(MovieXML movie: movieList)
            {
                psInsertMovie.setString(1, movie.getId());
                psInsertMovie.setString(2, movie.getTitle());
                psInsertMovie.setInt(3, movie.getYear());
                psInsertMovie.setString(4, movie.getDirector());

                String genre = movie.getGenre();

                //retrieve the right id based on genre
                if (genre != null && genreIdMap.containsKey(genre)){
                    psInsertGenre_Movies.setInt(1, genreIdMap.get(movie.getGenre()));
                }
                psInsertGenre_Movies.setString(2, movie.getId());


                psInsertMovie.addBatch();
                psInsertGenre_Movies.addBatch();
            }

            iNoRows=psInsertMovie.executeBatch();
            iNoRows=psInsertGenre_Movies.executeBatch();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(psInsertMovie!=null) psInsertMovie.close();
            if(psInsertGenre_Movies!=null) psInsertGenre_Movies.close();

            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static HashMap<String, String> hashmapBuilder(){
        //returns a hashmap that has the stars linked with the birthYear
        HashMap<String, String> movieMap = new HashMap<>();
        Statement statement = null;
        Connection conn = null;

        try{
            conn = getConnection();

        }catch(Exception e){
            e.printStackTrace();
        }


        try{
            String query = "Select title,id from movies";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String name = resultSet.getString("title");
                String id = resultSet.getString("id");

                movieMap.put(name, id);
            }

            if (!movieMap.isEmpty()){
                return movieMap;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        try {
            if(statement!=null) statement.close();

            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }


        return null;

    }

    public static HashMap<String, Integer> genreMapBuilder(){
        //returns a hashmap that has the stars linked with the birthYear
        HashMap<String, Integer> genreMap = new HashMap<>();
        Statement statement = null;
        Connection conn = null;

        try{
            conn = getConnection();

        }catch(Exception e){
            e.printStackTrace();
        }


        try{
            String query = "Select name, id from genres";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String genre = resultSet.getString("name");
                int id = resultSet.getInt("id");

                genreMap.put(genre, id);
            }

            if (!genreMap.isEmpty()){
                return genreMap;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        try {
            if(statement!=null) statement.close();

            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }


        return null;

    }






}


