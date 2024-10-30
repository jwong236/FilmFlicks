package com.filmflicks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/shopping-cart")
public class PurchaseController {

    private final ObjectMapper objectMapper;

    public PurchaseController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public HashMap<String, Object> getShoppingCart(HttpSession session) throws IOException {

        // Check if session exists and retrieve movieMap
        HashMap<String, MovieSession> movieMap = (HashMap<String, MovieSession>) session.getAttribute("movieMap");
        HashMap<String, Object> movieMapJson = new HashMap<>();

        if (movieMap != null) {
            System.out.println("Session and map exist");

            // Process each movie entry in movieMap
            for (String title : movieMap.keySet()) {
                MovieSession movieObj = movieMap.get(title);
                int quantity = movieObj.getQuantity();
                String movieId = movieObj.getId();
                double price = movieObj.getPrice();
                double totalPrice = price * quantity;

                // Create a new JSON-like structure for the movie session
                HashMap<String, Object> movieSessionJson = new HashMap<>();
                movieSessionJson.put("id", movieId);
                movieSessionJson.put("quantity", quantity);
                movieSessionJson.put("price", price);
                movieSessionJson.put("totalPrice", totalPrice);

                movieMapJson.put(title, movieSessionJson);
            }
            System.out.println("New movie map JSON: " + movieMapJson);
            return movieMapJson;
        } else {
            System.out.println("Session doesn't have anything in the shopping cart");
            return new HashMap<>();  // Return an empty JSON object if the cart is empty
        }
    }
}
