package com.filmflicks.security;

import com.filmflicks.models.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Retrieve or create session
        HttpSession session = request.getSession();

        // Get authenticated user’s email
        String email = authentication.getName();

        // Set session attribute
        session.setAttribute("userEmail", email);

        // Create empty shopping cart
        ShoppingCart shoppingCart = new ShoppingCart();

        // Set as session attribute
        session.setAttribute("shoppingCart", shoppingCart);

        // Redirect to homepage or any specific page
        response.sendRedirect("/");
    }
}
