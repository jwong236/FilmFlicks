import java.util.List;

public class Movie {
    private String title;
    private int year;
    private String director;
    private List<String> genres;
    private List<String> stars;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getStars() {
        return stars;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }
}
