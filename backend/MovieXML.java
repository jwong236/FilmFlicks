import java.util.UUID;

public class MovieXML {

    private final String title;
    private final int year;

    private final String director;

    private final String genres;

    private String id;

    public MovieXML(String title, int year, String director, String genres) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.genres = genres;

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

    public String getGenres(){
        return this.genres;
    }

    public String getId(){
        return this.id;
    }



    public String toString() {

        return "Title:" + getTitle() + ", " +
                "ID:" + getId() + ", " +
                "Year:" + String.valueOf(getYear()) + ", " +
                "Director:" + getDirector() + ", " +
                "Genre:" + getGenres();
    }
}