package com.filmflicks.controllers;

import com.filmflicks.models.ShoppingCart;
import com.filmflicks.models.ShoppingCart.CartItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @GetMapping("/shopping-cart")
    public Map<String, Object> getShoppingCart(HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart != null) {
            Map<String, CartItem> cartItems = shoppingCart.getCartItems();
            response.put("cartItems", cartItems);
            response.put("totalPrice", shoppingCart.getTotalPrice());
        } else {
            response.put("message", "Session doesn't have anything in the shopping cart");
            response.put("cartItems", Map.of());
            response.put("totalPrice", 0.0);
        }

        return response;
    }

    @PostMapping("/shopping-cart/add")
    public Map<String, Object> addToShoppingCart(@RequestParam String title, @RequestParam double price, @RequestParam int quantity, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);
        }

        shoppingCart.addItem(title, price, quantity);
        response.put("success", true);
        response.put("message", "Item added to shopping cart successfully.");

        return response;
    }

    @DeleteMapping("/shopping-cart/remove")
    public Map<String, Object> removeFromShoppingCart(@RequestParam String title, @RequestParam int quantity, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart != null) {
            shoppingCart.removeItem(title, quantity);
            response.put("success", true);
            response.put("message", "Item removed from shopping cart successfully.");
        } else {
            response.put("success", false);
            response.put("message", "Shopping cart is empty.");
        }

        return response;
    }

}
