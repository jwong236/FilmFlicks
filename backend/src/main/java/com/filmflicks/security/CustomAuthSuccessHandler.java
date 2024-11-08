package com.filmflicks.security;

import com.filmflicks.models.Customer;
import com.filmflicks.models.ShoppingCart;
import com.filmflicks.repositories.CustomerRepository;  // Import CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomAuthSuccessHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Retrieve or create session
        HttpSession session = request.getSession();

        // Get authenticated user's email
        String email = authentication.getName();

        // Retrieve customer metadata by email
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        // Store required customer metadata in the session
        Map<String, Object> customerSessionData = new HashMap<>();
        customerSessionData.put("id", customer.getId());
        customerSessionData.put("firstName", customer.getFirstName());
        customerSessionData.put("lastName", customer.getLastName());
        customerSessionData.put("ccId", customer.getCcId());
        customerSessionData.put("address", customer.getAddress());
        customerSessionData.put("email", customer.getEmail());

        session.setAttribute("customer", customerSessionData);

        // Create empty shopping cart and set it in the session
        ShoppingCart shoppingCart = new ShoppingCart();
        session.setAttribute("shoppingCart", shoppingCart);

        // Set response type as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Prepare JSON response indicating success
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Login successful");
        jsonResponse.put("status", "success");

        // Write JSON response to the output
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }
}
