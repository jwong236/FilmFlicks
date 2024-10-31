package com.filmflicks.security;

import com.filmflicks.repositories.CustomerRepository;
import com.filmflicks.repositories.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AdminUserDetailsService adminUserDetailsService;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public SecurityConfig(CustomUserDetailsService userDetailsService, AdminUserDetailsService adminUserDetailsService, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.userDetailsService = userDetailsService;
        this.adminUserDetailsService = adminUserDetailsService;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // 0. Only apply to requests with /admin
                .securityMatcher("/admin/**")

                // 1. CORS Configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Set CORS configuration source

                // 2. Session Management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation()
                        .newSession()
                )
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Authentication Configuration
                .authenticationProvider(adminAuthenticationProvider())
                .formLogin(form -> form
                        .loginProcessingUrl("/admin/login")
                        .successHandler(new AdminAuthSuccessHandler(employeeRepository))
                )

                // 4. Authorization Rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )

                // 5. Logout Handling
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS Configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Set CORS configuration source

                // 2. Session Management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation()
                        .newSession()
                )

                // 3. CSRF Protection (Disable for non-browser clients or APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // 4. Authentication Configuration
                .authenticationProvider(userAuthenticationProvider())
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler(new CustomAuthSuccessHandler(customerRepository))
                )

                // 5. Authorization Rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/error", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 6. Logout Handling
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Allow only frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allowed headers
        configuration.setAllowCredentials(true); // Allow credentials like cookies or HTTP sessions

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Use UserDetailsService for users
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService); // Use UserDetailsService for admins
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
