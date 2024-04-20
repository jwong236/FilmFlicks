import java.util.Random;

public class MovieSession {
    private final String movieTitle;
    private int quantity;
    private final double price;

    private double price() {
        Random rand = new Random();
        // Generating a random double between 5 and 20
        double price = 5 + (20 - 5) * rand.nextDouble();
        // Rounding the price to two decimal places
        return Math.round(price * 100.0) / 100.0;
    }

    public MovieSession(String movieTitle, int quantity) {

        this.movieTitle = movieTitle;
        this.quantity = quantity;
        this.price = price();
    }

    public void increase(){
        this.quantity += 1;
    }
    public void decrease(){
        this.quantity -= 1;
    }

    public double getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }


}
