package com.filmflicks.security;

import com.filmflicks.models.Employee;
import com.filmflicks.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public AdminUserDetailsService(EmployeeRepository employeeRepository) {
        System.out.println("AdminUserDetailsService initialized.");
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Loading admin by email: " + email);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + email));

        return User.builder()
                .username(employee.getEmail())
                .password(employee.getPassword())
                .roles("ADMIN")
                .build();
    }
}
