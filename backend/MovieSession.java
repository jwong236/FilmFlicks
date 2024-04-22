import java.util.Random;
import java.time.LocalDate;


public class MovieSession {
    private final String id;

    private final String movieTitle;
    private int quantity;
    private final double price;
    private final LocalDate saleDate;



    private double price() {
        Random rand = new Random();
        // Generating a random double between 5 and 20
        double price = 5 + (20 - 5) * rand.nextDouble();
        // Rounding the price to two decimal places
        return Math.round(price * 100.0) / 100.0;
    }

    public MovieSession(String id, String movieTitle, int quantity, LocalDate saleDate) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.quantity = quantity;
        this.price = price();
        this.saleDate = saleDate;

    }

    public void increase(){
        this.quantity += 1;
    }
    public void decrease(){
        this.quantity -= 1;
    }


    public String getId(){
        return this.id;
    }
    public double getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getTotal(){
        return (this.quantity * this.price);
    }
    public String getTitle(){
        return this.movieTitle;
    }
    public LocalDate getSaleDate(){
        return this.saleDate;
    }

}
