package com.filmflicks.security;

import com.filmflicks.models.Employee;
import com.filmflicks.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminAuthController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;

    public AdminAuthController(@Qualifier("adminAuthenticationManager") AuthenticationManager authenticationManager, EmployeeRepository employeeRepository) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "Email and password are required",
                        "status", "error"
                ));
            }

            // Invalidate the existing session if there is one
            HttpSession existingSession = request.getSession(false);
            if (existingSession != null) {
                existingSession.invalidate();
            }

            // Authenticate admin user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Set authentication in SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // Create a new session
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            // Retrieve admin metadata
            Employee employee = employeeRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Admin not found with email: " + email));

            // Set admin metadata in session
            Map<String, Object> employeeSessionData = new HashMap<>();
            employeeSessionData.put("email", employee.getEmail());
            employeeSessionData.put("fullName", employee.getFullName());
            newSession.setAttribute("employee", employeeSessionData);

            // Prepare JSON response
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Login successful");
            jsonResponse.put("status", "success");
            jsonResponse.put("admin", employeeSessionData);

            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid credentials",
                    "status", "error"
            ));
        }
    }
}
