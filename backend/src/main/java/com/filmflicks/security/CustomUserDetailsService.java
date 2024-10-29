package com.filmflicks.security;

import com.filmflicks.models.Customer;
import com.filmflicks.repositories.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Find customer using email
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User not found with email: " + email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });


        // 2. Check if the password is already encrypted
        if (!customer.getPassword().startsWith("$2a$")) {

            // 2a. If the password is not encrypted, encrypt it now
            String encryptedPassword = passwordEncoder.encode(customer.getPassword());

            // 2b. Set our customer object to the new encrypted password
            customer.setPassword(encryptedPassword);

            // 2c. Save the new encrypted password to the database
            customerRepository.save(customer);
        }

        // 3. Return a UserDetails object containing the customer's email and password
        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles("USER")
                .build();
    }
}
