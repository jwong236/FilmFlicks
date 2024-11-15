package com.filmflicks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filmflicks.models.CreditCard;
import com.filmflicks.repositories.CreditCardRepository;
import com.filmflicks.models.ShoppingCart;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private CreditCardRepository creditCardRepository;

    // Process payment
    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody CreditCard creditCard, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart == null || shoppingCart.getCartItems().isEmpty()) {
            response.put("error", "No items in shopping cart");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (creditCard == null) {
            response.put("error", "Invalid credit card information");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCard.getId());

        if (optionalCreditCard.isPresent()) {
            CreditCard existingCreditCard = optionalCreditCard.get();
            if (!existingCreditCard.getFirstName().equals(creditCard.getFirstName()) ||
                    !existingCreditCard.getLastName().equals(creditCard.getLastName()) ||
                    !existingCreditCard.getExpiration().equals(creditCard.getExpiration())) {
                response.put("error", "Invalid credit card information");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
            }

            // Process payment and clear the shopping cart
            shoppingCart.clear();
            session.setAttribute("shoppingCart", shoppingCart);
            response.put("message", "Payment processed successfully and shopping cart cleared");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid credit card information");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }
    }

    // Get shopping cart
    @GetMapping("/shopping-cart")
    public Map<String, Object> getShoppingCart(HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart != null) {
            Map<String, ShoppingCart.CartItem> cartItems = shoppingCart.getCartItems();
            response.put("cartItems", cartItems);
            response.put("totalPrice", shoppingCart.getTotalPrice());
        } else {
            response.put("message", "Session doesn't have anything in the shopping cart");
            response.put("cartItems", Map.of());
            response.put("totalPrice", 0.0);
        }

        return response;
    }

    // Add to shopping cart
    @PostMapping("/shopping-cart/add")
    public Map<String, Object> addToShoppingCart(@RequestParam String id, @RequestParam String title, @RequestParam double price, @RequestParam int quantity, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);
        }

        shoppingCart.addItem(id, title, price, quantity);
        response.put("success", true);
        response.put("message", "Item added to shopping cart successfully.");

        return response;
    }

    // Remove from shopping cart
    @DeleteMapping("/shopping-cart/remove")
    public Map<String, Object> removeFromShoppingCart(@RequestParam String id, @RequestParam int quantity, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        Map<String, Object> response = new HashMap<>();

        if (shoppingCart != null) {
            shoppingCart.removeItem(id, quantity);
            response.put("success", true);
            response.put("message", "Item removed from shopping cart successfully.");
        } else {
            response.put("success", false);
            response.put("message", "Shopping cart is empty.");
        }

        return response;
    }
}
