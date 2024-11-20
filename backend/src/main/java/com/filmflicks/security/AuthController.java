package com.filmflicks.security;

import com.filmflicks.models.Customer;
import com.filmflicks.models.ShoppingCart;
import com.filmflicks.repositories.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;

    public AuthController(AuthenticationManager authenticationManager, CustomerRepository customerRepository) {
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "Email and password are required",
                        "status", "error"
                ));
            }

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Set the authentication in the SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // Store SecurityContext in the session
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            // Retrieve customer data
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

            // Add customer metadata to the session
            Map<String, Object> customerSessionData = new HashMap<>();
            customerSessionData.put("id", customer.getId());
            customerSessionData.put("firstName", customer.getFirstName());
            customerSessionData.put("lastName", customer.getLastName());
            customerSessionData.put("ccId", customer.getCcId());
            customerSessionData.put("address", customer.getAddress());
            customerSessionData.put("email", customer.getEmail());
            session.setAttribute("customer", customerSessionData);

            // Initialize and store a shopping cart in the session
            ShoppingCart shoppingCart = new ShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);

            // Prepare JSON response
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Login successful");
            jsonResponse.put("status", "success");
            jsonResponse.put("customer", customerSessionData);

            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid credentials",
                    "status", "error"
            ));
        }
    }
}
