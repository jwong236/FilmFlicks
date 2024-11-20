package com.filmflicks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomerUserDetailsService customerUserDetailsService;
    private final AdminUserDetailsService adminUserDetailsService;

    public SecurityConfig(CustomerUserDetailsService customerUserDetailsService, AdminUserDetailsService adminUserDetailsService) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.adminUserDetailsService = adminUserDetailsService;
        System.out.println("SecurityConfig initialized with CustomerUserDetailsService and AdminUserDetailsService.");
    }

    // Admin Security Filter Chain
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configuring Admin Security Filter Chain...");
        http.securityMatcher("/admin/**") // Matches only /admin/** paths
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/login").permitAll() // Allow access to admin login
                        .anyRequest().hasRole("ADMIN") // Restrict all other admin paths to ADMIN role
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation().newSession()
                )
                .authenticationProvider(adminAuthenticationProvider()) // Use admin authentication provider
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );
        return http.build();
    }

    // User Security Filter Chain
    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configuring User Security Filter Chain...");
        http.securityMatcher(request -> !request.getRequestURI().startsWith("/admin")) // Exclude /admin/** paths
                .cors(cors -> {
                    System.out.println("Applying CORS configuration for User Security Chain...");
                    cors.configurationSource(corsConfigurationSource());
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    System.out.println("Configuring authorization rules for User Security Chain...");
                    authorize
                            .requestMatchers("/login", "/error", "/css/**", "/js/**", "/test-backend", "/test-database").permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    System.out.println("Configuring session management for User Security Chain...");
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                            .sessionFixation().newSession();
                })
                .authenticationProvider(userAuthenticationProvider()) // Use user authentication provider
                .exceptionHandling(ex -> {
                    System.out.println("Configuring exception handling for User Security Chain...");
                    ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                });
        return http.build();
    }


    // CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        System.out.println("Configuring CORS...");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://filmflicks-frontend"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // User Authentication Provider
    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        System.out.println("Initializing User Authentication Provider...");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Admin Authentication Provider
    @Bean
    public AuthenticationProvider adminAuthenticationProvider() {
        System.out.println("Initializing Admin Authentication Provider...");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("Initializing Password Encoder...");
        return new BCryptPasswordEncoder();
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        System.out.println("Initializing Authentication Manager...");
        return authenticationConfiguration.getAuthenticationManager();
    }
}
