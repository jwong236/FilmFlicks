import java.util.UUID;

public class MovieXML {

    private final String title;
    private final int year;

    private final String director;

    private final String genre;

    private String id;

    public MovieXML(String title, int year, String director, String genre) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.genre = genre;

        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();;

    }

    public String getTitle(){
        return this.title;
    }

    public int getYear(){
        return this.year;
    }

    public String getDirector(){
        return this.director;
    }

    public String getGenre(){
        return this.genre;
    }

    public String getId(){
        return this.id;
    }



    public String toString() {

        return "Title:" + getTitle() + ", " +
                "ID:" + getId() + ", " +
                "Year:" + String.valueOf(getYear()) + ", " +
                "Director:" + getDirector() + ", " +
                "Genre:" + getGenre();
    }
}