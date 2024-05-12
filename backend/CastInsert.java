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

public class CastInsert {

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


    public static void insertBatch (List<CastXML> castList) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        Connection conn = null;

        try{
            conn = getConnection();
            System.out.println("INSERTING INTO CAST SQL");
        }catch(Exception e){
            e.printStackTrace();
        }





        PreparedStatement psInsertCast=null;

        String castSQL=null;

        int[] iNoRows=null;


        castSQL="insert ignore into stars_in_movies (starId, movieId) values(?,?)";

        try {
            conn.setAutoCommit(false);

            psInsertCast=conn.prepareStatement(castSQL);

            for(CastXML cast: castList)
            {
                //System.out.println("sql cast: " + cast.getActorId() + " movieid: " + cast.getMovieId());
                psInsertCast.setString(1, cast.getActorId());
                psInsertCast.setString(2, cast.getMovieId());


                psInsertCast.addBatch();


            }

            iNoRows=psInsertCast.executeBatch();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(psInsertCast!=null) psInsertCast.close();

            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> hashmapBuilder(){
        //returns a hashmap that has the stars linked with the birthYear
        HashMap<String, String> castMap = new HashMap<>();
        Statement statement = null;
        Connection conn = null;

        try{
            conn = getConnection();
            System.out.println("INSERTING INTO CAST");

        }catch(Exception e){
            e.printStackTrace();
        }



        try{
            String query = "Select starId, movieId from stars_in_movies";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String starId = resultSet.getString("starId");
                String movieId = resultSet.getString("movieId");

                castMap.put(starId, movieId);
            }

            if (!castMap.isEmpty()){
                return castMap;
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

    public  HashMap<String, String> movieMapBuilder(){
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

    public  HashMap<String, String> starMapBuilder(){
        //returns a hashmap that has the stars linked with the birthYear
        HashMap<String, String> starMap = new HashMap<>();
        Statement statement = null;
        Connection conn = null;

        try{
            conn = getConnection();

        }catch(Exception e){
            e.printStackTrace();
        }



        try{
            String query = "Select name, id from stars";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String name = resultSet.getString("name");
                String id = resultSet.getString("id");

                starMap.put(name, id);
            }

            if (!starMap.isEmpty()){
                return starMap;
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


