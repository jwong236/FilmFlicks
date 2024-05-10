import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.sql.rowset.serial.SerialArray;
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

public class StarParser {

    List<StarXML> starList = new ArrayList<>();
    List<StarXML> inconsistentStarList = new ArrayList<>();
    HashMap<String, Integer> starMap = new HashMap<>();

    Document dom;

    public void runExample() {
        StarInsert starInsert = new StarInsert();
        starMap = starInsert.hashmapBuilder();


        // parse the xml file and get the dom object
        parseXmlFile();

        // get each employee element and create a Employee object
        parseDocument();

        // iterate through the list and print the data
        printData();
        inconStarReport();


    }


    private void parseXmlFile() {
        // get the factory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {

            // using factory get an instance of document builder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            dom = documentBuilder.parse("./backend/xml/actors63.xml");
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
        NodeList nodeList = documentElement.getElementsByTagName("actor");
        //String textVal = nodeList.item(0).getFirstChild().getNodeValue();

//        System.out.println("NODE LIST " + textVal);
        for (int i = 0; i < nodeList.getLength(); i++) {

            // get the employee element
            Element element = (Element) nodeList.item(i);



            // get the Employee object
            StarXML star = parseStar(element);


//            if (movie.getYear() != 0 && movie.getDirector() != null && movie.getTitle() != null && movie.getGenre() != null){
//                // add it to list
//                movieList.add(movie);
//            }else{
//                inconsistentMovieList.add(movie);
//            }


            if(star.getId() != null && star.getName() != null && star.getBirthYear() != 0){
                if (starMap.containsKey(star.getName())){
                    System.out.println("duplicate star: " + star.getName());
                    inconsistentStarList.add(star);

                }else{
                    starList.add(star);
                }
            }else{
                inconsistentStarList.add(star);
            }

        }
        StarInsert starInsert = new StarInsert();
        try{
            StarInsert.insertBatch(starList);
            System.out.println("inserting batch from dom parser");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private StarXML parseStar(Element element) {
        String name  = getTextValue(element, "stagename");

        //System.out.println("genres " + genres);
        //System.out.println("director " + director);
        int birthYear = getIntValue(element, "dob");

        // create a new Employee with the value read from the xml nodes
        return new StarXML(name, birthYear);
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

        System.out.println("Total parsed " + starList.size() + " stars");

        for (StarXML star : starList) {
            System.out.println("\t" + star.toString());
        }
    }

    private void inconStarReport() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("inconsistentStars.txt");
            writer.println("Total inconsistent " + inconsistentStarList.size() + " stars");

            for (StarXML star : inconsistentStarList) {
                writer.println("\t" + star.toString());
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