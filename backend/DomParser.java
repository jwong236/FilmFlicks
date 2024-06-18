import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.io.PrintWriter;

public class DomParser {
    //genres: dram -> drama, myst -> mystery, susp -> thriller, comd -> comedy, romt->romantic, musc->musical
    HashMap<String, String> genreMap = new HashMap<>();
    HashMap<String, String> movieMap = new HashMap<>();

    HashMap<String, Integer> genreDupeMap = new HashMap<>();


    List<MovieXML> movieList = new ArrayList<>();
    List<MovieXML> inconsistentMovieList = new ArrayList<>();

    HashMap<String, String> starMap = new HashMap<>();


    Document dom;

    public void runExample() {
        genreMap.put("Dram", "Drama");
        genreMap.put("Myst", "Mystery");
        genreMap.put("Susp", "Thriller");
        genreMap.put("Comd", "Comedy");
        genreMap.put("Romt", "Romantic");
        genreMap.put("Musc", "Musical");
        genreMap.put("West", "Western");
        genreMap.put("Porn", "Adult");
        genreMap.put("Horr", "Horror");
        genreMap.put("Advt", "Adventure");
        genreMap.put("S.F.", "Sci-Fi");
        genreMap.put("Docu", "Documentary");
        genreMap.put("BioP", "Biography");
        genreMap.put("Actn", "Action");
        genreMap.put("Fant", "Fantasy");

        MovieInsert movieInsert = new MovieInsert();
        movieMap = movieInsert.hashmapBuilder();
        genreDupeMap = movieInsert.genreMapBuilder();
        System.out.println(genreDupeMap);

        addNewGenres();


        // parse the xml file and get the dom object
        parseXmlFile();

        // get each employee element and create a Employee object
        parseDocument();

        // iterate through the list and print the data
        printData();
        inconMovieReport();

        StarParser starParser = new StarParser();
        starMap = starParser.runExample();


        CastParser castParser = new CastParser();
        castParser.runExample();

    }

    private void addNewGenres(){

        try{
            Connection conn = null;
            String jdbcURL = "jdbc:mysql://127.0.0.1:3307/moviedb";
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try {
                conn = DriverManager.getConnection(jdbcURL, "admin", "andy");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String[] genres = {"Black", "Television"};
            PreparedStatement preparedStatement = null;

            if (!genreDupeMap.containsKey("Black") && !genreDupeMap.containsKey("Television")){
                String genreQuery = "INSERT IGNORE INTO genres (name) VALUES (?)";
                preparedStatement = conn.prepareStatement(genreQuery);
                genreMap.put("Tv", "Television"); //new add to database
                genreMap.put("Noir", "Black"); //new add to database

                for (String g: genres){
                    preparedStatement.setString(1, g);
                    preparedStatement.executeUpdate();
                }
            }else{
                System.out.println("Black and Television already in genres table");
            }

            if(conn!=null) conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }




    }

    private void parseXmlFile() {
        // get the factory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {

            // using factory get an instance of document builder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            dom = documentBuilder.parse("./backend/xml/mains243.xml");
            System.out.println(dom);
            if (dom == null){
                System.out.println("ERROR: document is null");
            }
        } catch (ParserConfigurationException | SAXException | IOException error) {
            error.printStackTrace();
        }
    }

    public static String uniformCapitalization(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void parseDocument() {
        // get the document root Element
        Element documentElement = dom.getDocumentElement();

        // get a nodelist of employee Elements, parse each into Employee object
        NodeList nodeList = documentElement.getElementsByTagName("film");
        //String textVal = nodeList.item(0).getFirstChild().getNodeValue();

//        System.out.println("NODE LIST " + textVal);
        for (int i = 0; i < nodeList.getLength(); i++) {

            // get the employee element
            Element element = (Element) nodeList.item(i);



            // get the Employee object
            MovieXML movie = parseMovie(element);


            if (movie.getYear() != 0 && movie.getDirector() != null && movie.getTitle() != null && movie.getGenre() != null){
                if (movieMap.containsKey(movie.getTitle())){
                    //System.out.println("duplicate movie: " + movie.getTitle());
                    inconsistentMovieList.add(movie);
                }else{
                    // add it to list
                    movieList.add(movie);
                }

            }else{
                inconsistentMovieList.add(movie);
            }


        }
        MovieInsert movieInsert = new MovieInsert();
        try{
            movieInsert.insertBatch(movieList);
            System.out.println("inserting batch from dom parser");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * It takes an employee Element, reads the values in, creates
     * an Employee object for return
     */
    private MovieXML parseMovie(Element element) {

        // for each <employee> element get text or int values of
        // name ,id, age and name
        String title  = getTextValue(element, "t");
        String director  = getTextValue(element, "dirn");
        if (director != null && director.equals("dePalma")){
            director ="B.dePalma";
        }
        String genre = uniformCapitalization(getTextValue(element, "cat"));
        if (genreMap.containsKey(genre)){
            genre = genreMap.get(genre);
            //System.out.println("GENRE: " + genre);
        }else{
            genre = null;
        }
        //System.out.println("genres " + genres);
        //System.out.println("director " + director);
        int year = getIntValue(element, "year");

        // create a new Employee with the value read from the xml nodes
        return new MovieXML(title, year, director, genre);
    }

    /**
     * It takes an XML element and the tag name, look for the tag and get
     * the text content
     * i.e for <Employee><Name>John</Name></Employee> xml snippet if
     * the Element points to employee node and tagName is name it will return John
     */
    private String getTextValue(Element element, String tagName) {
        String textVal = null;
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            // here we expect only one <Name> would present in the <Employee>
            //System.out.println("tag name " + tagName);
            if (nodeList.item(0).getFirstChild() != null){
                String temp = nodeList.item(0).getFirstChild().getNodeValue();
                if(temp != null){
                    boolean slashChecker = temp.indexOf('\\') != -1;
                    boolean tildeChecker = temp.indexOf('~') != -1;
                    if (tildeChecker){
                        temp = temp.replace("~", "");
                    }
                    if (!slashChecker) {
                        textVal = temp;
                    }
                }
            }

        }
        return textVal;
    }


    private boolean yearValidator(String year){
        for (char c : year.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Calls getTextValue and returns a int value
     */
    private int getIntValue(Element ele, String tagName) {
        // in production application you would catch the exception

        String year = getTextValue(ele, tagName).trim();
        boolean validYear = yearValidator(year);
        //System.out.println(validYear);
        //System.out.println("integer has letters in it: " + year);

        if (validYear){
            //System.out.println("DOESNT HAVE LETTERS");
            return Integer.parseInt(year);
        }else{
            //System.out.println("integer has letters in it: " + year);

            return 0; //turns it into null
        }
    }

    /**
     * Iterate through the list and print the
     * content to console
     */
    private void printData() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("consistentMovies.txt");
            writer.println("Total consistent " + movieList.size() + " movies");

            for (MovieXML movie : movieList) {
                writer.println("\t" + movie.toString());
            }
        }catch(Exception e){
            // Ensure the writer is closed even if an exception occurs
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    private void inconMovieReport() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("inconsistentMovies.txt");
            writer.println("Total inconsistent " + inconsistentMovieList.size() + " movies");

            for (MovieXML movie : inconsistentMovieList) {
                writer.println("\t" + movie.toString());
            }
        }catch(Exception e){
            // Ensure the writer is closed even if an exception occurs
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    public static void main(String[] args) {
        // create an instance
        DomParser domParser= new DomParser();


        // call run example
        domParser.runExample();
    }

}