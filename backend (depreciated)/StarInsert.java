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

public class StarInsert {

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


    public static void insertBatch (List<StarXML> starList) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        Connection conn = null;

        try{
            conn = getConnection();

        }catch(Exception e){
            e.printStackTrace();
        }


//        try {
//            conn = DriverManager.getConnection(jdbcURL, "admin", "andy");
//            System.out.println("INTO THE BATCH INSERT");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }




        PreparedStatement psInsertStar=null;
        PreparedStatement psInsertGenre_Movies=null;

        String starSQL=null;

        int[] iNoRows=null;


        starSQL="insert ignore into stars (id, name, birthYear) values(?,?, ?)";

        try {
            conn.setAutoCommit(false);

            psInsertStar=conn.prepareStatement(starSQL);

            for(StarXML star: starList)
            {
                psInsertStar.setString(1, star.getId());
                psInsertStar.setString(2, star.getName());
                psInsertStar.setInt(3, star.getBirthYear());


                psInsertStar.addBatch();


            }

            iNoRows=psInsertStar.executeBatch();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(psInsertStar!=null) psInsertStar.close();

            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> hashmapBuilder(){
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


