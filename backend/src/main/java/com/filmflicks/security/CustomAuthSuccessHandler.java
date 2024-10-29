package com.filmflicks.security;

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

        System.out.println("Authentication success handler triggered.");

        // Retrieve or create session
        HttpSession session = request.getSession();
        System.out.println("Session ID: " + session.getId());

        // Get authenticated user’s email
        String email = authentication.getName();
        System.out.println("Authenticated user's email: " + email);

        // Set session attribute
        session.setAttribute("userEmail", email);
        System.out.println("User email set in session attribute.");

        // Redirect to homepage or any specific page
        response.sendRedirect("/");
        System.out.println("Redirecting to homepage.");
    }
}
