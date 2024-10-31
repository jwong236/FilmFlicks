package com.filmflicks.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String adminLoginPage() {
        return "Login endpoint has been reached";
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome to the admin dashboard!";
    }
}
