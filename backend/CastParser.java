import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.io.PrintWriter;

public class CastParser {

    List<CastXML> castList = new ArrayList<>();
    List<CastXML> inconsistentCastList = new ArrayList<>();
    HashMap<String, String> castMap = new HashMap<>(); //key is actor : value is movie since there can be multiple actors for a movie
    HashMap<String, String> starsMap = new HashMap<>(); //key is actor : value is movie since there can be multiple actors for a movie
    HashMap<String, String> moviesMap = new HashMap<>(); //key is actor : value is movie since there can be multiple actors for a movie

    Document dom;

    public void runExample() {
        CastInsert castInsert = new CastInsert();
        castMap = castInsert.hashmapBuilder();

        //grabs translations from name to id in hashmaps
        starsMap = castInsert.starMapBuilder();
        moviesMap = castInsert.movieMapBuilder();



        // parse the xml file and get the dom object
        parseXmlFile();

        // get each employee element and create a Employee object
        parseDocument();

        // iterate through the list and print the data
        printData();
        inconCastReport();



    }


    private void parseXmlFile() {
        // get the factory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {

            // using factory get an instance of document builder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            dom = documentBuilder.parse("./backend/xml/casts124.xml");
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
        NodeList nodeList = documentElement.getElementsByTagName("m");
        //String textVal = nodeList.item(0).getFirstChild().getNodeValue();

//        System.out.println("NODE LIST " + textVal);
        for (int i = 0; i < nodeList.getLength(); i++) {

            // get the employee element
            Element element = (Element) nodeList.item(i);



            // get the Employee object
            CastXML cast = parseCast(element);




            if (cast.getMovieId() != null && cast.getActorId() != null) {
                //makes sure the stars and movie already exist from loading them from actors.xml and movies.xml
                String actorId = cast.getActorId();
                String movieId = cast.getMovieId();
                //checks if the actor alr exists in stars
                //checks if movie alr exists in movies
                //checks if the actorId isn't a duplicate in the casts table, but has to check if the actor id is a key first in the casts


                if (castMap.containsKey(actorId)) {
                    String currMovieId = castMap.get(actorId);
                    if (!currMovieId.equals(movieId)) {
                        castList.add(cast);
                    } else {
                        System.out.println("duplicate cast entry: " + cast.getActorId());
                        inconsistentCastList.add(cast);
                    }
                }else{
                    castList.add(cast);
                }
            }else{
                inconsistentCastList.add(cast);
            }

        }
        CastInsert castInsert = new CastInsert();
        try{
            castInsert.insertBatch(castList);
            //System.out.println("inserting batch from dom parser");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private CastXML parseCast(Element element) {
        String star  = getTextValue(element, "a");

        String movie = getTextValue(element, "t");
        String starId = null;
        String movieId = null;
        //create a new cast object based on their id,
        //so once you get the name of actor and movie find their id's from the hashmap
        if (starsMap != null  && moviesMap != null && starsMap.containsKey(star) && moviesMap.containsKey(movie)){
            movieId = moviesMap.get(movie);
            starId =  starsMap.get(star);
        }

        //System.out.println("movie: " + movie + " star " + star);


        return new CastXML(starId, movieId);

    }


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

    private int getIntValue(Element ele, String tagName) {
        // in production application you would catch the exception
        String temp = getTextValue(ele, tagName);
        //System.out.println("temp: " + temp);
        String year = null;
        if(temp != null){
            year = temp.trim();
        }else{
            return 0;
        }

        if (year.contains("+")){
            year = year.substring(0, year.length()-2).toUpperCase();

        }
        boolean validYear = yearValidator(year);
        //System.out.println(validYear);
        //System.out.println("integer has letters in it: " + year);

        if (validYear && year.length() == 4){
            //System.out.println("DOESNT HAVE LETTERS");
            return Integer.parseInt(year);
        }else{
            //System.out.println("integer has letters in it: " + year);

            return 0; //turns it into null
        }
    }
    private boolean yearValidator(String year){
        for (char c : year.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private void printData() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("consistentCast.txt");
            writer.println("Total consistent " + castList.size() + " cast");

            for (CastXML cast : castList) {
                writer.println("\t" + cast.toString());
            }
        }catch(Exception e){
            // Ensure the writer is closed even if an exception occurs
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    private void inconCastReport() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("inconsistentCast.txt");
            writer.println("Total inconsistent " + inconsistentCastList.size() + " cast");

            for (CastXML cast : inconsistentCastList) {
                writer.println("\t" + cast.toString());
            }
        }catch(Exception e){
            // Ensure the writer is closed even if an exception occurs
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

//    public static void main(String[] args) {
//        // create an instance
//        DomParser domParser= new DomParser();
//
//
//        // call run example
//        domParser.runExample();
//    }

}