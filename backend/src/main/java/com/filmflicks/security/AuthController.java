package com.filmflicks.security;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    public String login() {
        return "Login endpoint has been reached";
    }
}
