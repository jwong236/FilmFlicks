package com.filmflicks.controllers;

import com.filmflicks.DTOs.ApiResponse;
import com.filmflicks.DTOs.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            System.out.println("Login endpoint reached with email: " + loginRequest.getEmail());

            // Attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // Set authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("user", authentication.getName());

            // Construct a success response
            ApiResponse response = new ApiResponse("success", "Login successful", null);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // Construct an error response for login failure
            ApiResponse response = new ApiResponse("error", "Invalid email or password", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
