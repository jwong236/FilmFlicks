package com.filmflicks.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomeController {

    @GetMapping("/")
    @RequestMapping("/api")
    public String home() {
        return "Welcome, authenticated user!";
    }
}
