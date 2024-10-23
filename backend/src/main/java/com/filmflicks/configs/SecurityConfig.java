package com.filmflicks.configs;

import com.filmflicks.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing; enable in production
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error", "/css/**", "/js/**").permitAll() // Allow access to login and error pages, plus static resources
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                        .permitAll() // Use default login page
                )
                .logout(logout -> logout
                        .permitAll() // Allow logout without authentication
                )
                .userDetailsService(userDetailsService); // Use the custom user details service

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
