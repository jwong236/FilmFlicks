package com.filmflicks.security;

import com.filmflicks.models.Employee;
import com.filmflicks.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Initialize the password encoder
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Find employee using email
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("Admin not found with email: " + email);
                    return new UsernameNotFoundException("Admin not found with email: " + email);
                });

        // 2. Check if the password is already encrypted
        if (!employee.getPassword().startsWith("$2a$")) {

            // 2a. If the password is not encrypted, encrypt it now
            String encryptedPassword = passwordEncoder.encode(employee.getPassword());

            // 2b. Set our employee object to the new encrypted password
            employee.setPassword(encryptedPassword);

            // 2c. Save the new encrypted password to the database
            employeeRepository.save(employee);
        }

        // 3. Return a UserDetails object containing the employee's email and password
        return User.builder()
                .username(employee.getEmail())
                .password(employee.getPassword())
                .roles("ADMIN")
                .build();
    }
}
