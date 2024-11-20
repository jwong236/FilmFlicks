/*
package com.filmflicks.security;

import com.filmflicks.models.Customer;
import com.filmflicks.repositories.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true) // Read-only as this only fetches data
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("[DEBUG] loadUserByUsername called with email: " + email);

        // Fetch the customer from the database
        try {
            System.out.println("[DEBUG] Attempting to fetch customer from the database for email: " + email);
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] User not found with email: " + email);
                        return new UsernameNotFoundException("User not found with email: " + email);
                    });
            System.out.println("[DEBUG] Customer fetched successfully: " + customer);

            // Build UserDetails
            System.out.println("[DEBUG] Building UserDetails for email: " + email);
            UserDetails userDetails = User.builder()
                    .username(customer.getEmail())
                    .password(customer.getPassword()) // Password must already be encrypted in the database
                    .roles("USER") // Assign roles as needed
                    .build();
            System.out.println("[DEBUG] UserDetails built successfully for email: " + email);

            return userDetails;
        } catch (Exception ex) {
            System.out.println("[ERROR] Exception occurred in loadUserByUsername: " + ex.getMessage());
            ex.printStackTrace(); // Print the stack trace to help debug the recursion
            throw ex;
        }
    }
}
*/
