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
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

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
                .securityMatcher("/admin/**")
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation()
                        .newSession()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(adminAuthenticationProvider())
                .formLogin(form -> form
                        .loginProcessingUrl("/admin/login")
                        .successHandler(new AdminAuthSuccessHandler(employeeRepository))
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
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
                // 1. Session Management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation()
                        .newSession()
                )

                // 2. CSRF Protection (Disable for non-browser clients or APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Authentication Configuration
                .authenticationProvider(userAuthenticationProvider())
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler(new CustomAuthSuccessHandler(customerRepository))
                )

                // 4. Authorization Rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/error", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 5. Logout Handling
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
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
