package com.filmflicks.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, CartItem> cartItems = new HashMap<>();

    // Constructors
    public ShoppingCart() {
    }

    // Add an item to the cart
    public void addItem(String title, double price, int quantity) {
        CartItem cartItem = cartItems.get(title);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItems.put(title, new CartItem(title, price, quantity));
        }
    }

    // Remove a quantity of an item from the cart by title
    public void removeItem(String title, int quantity) {
        CartItem cartItem = cartItems.get(title);
        if (cartItem != null) {
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity <= quantity) {
                cartItems.remove(title);
            } else {
                cartItem.setQuantity(currentQuantity - quantity);
            }
        }
    }

    // Get the total price of the shopping cart
    public double getTotalPrice() {
        return cartItems.values().stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    // Get all items in the cart
    public Map<String, CartItem> getCartItems() {
        return cartItems;
    }

    // Inner class to represent an item in the cart
    public static class CartItem implements Serializable {
        private static final long serialVersionUID = 1L;

        private String title;
        private double price;
        private int quantity;

        // Constructors
        public CartItem(String title, double price, int quantity) {
            this.title = title;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return price * quantity;
        }
    }
}
