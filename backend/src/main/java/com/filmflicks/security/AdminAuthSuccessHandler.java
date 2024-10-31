package com.filmflicks.security;

import com.filmflicks.models.Employee;
import com.filmflicks.repositories.EmployeeRepository;  // Import EmployeeRepository
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public AdminAuthSuccessHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Retrieve or create session
        HttpSession session = request.getSession();

        // Get authenticated user’s email
        String email = authentication.getName();

        // Retrieve admin metadata by email
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found with email: " + email));

        // Set full employee metadata as session attribute
        Map<String, Object> employeeSessionData = new HashMap<>();
        employeeSessionData.put("email", employee.getEmail());
        employeeSessionData.put("fullName", employee.getFullName());
        session.setAttribute("employee", employeeSessionData);

        // Set response type as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Prepare JSON response indicating success
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Login successful");
        jsonResponse.put("status", "success");

        // Write JSON response to the output
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }
}
