package com.filmflicks.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    // Add stored endpoint and params to the session
    @PostMapping("/stored-request/add")
    public ResponseEntity<Map<String, String>> addStoredRequest(HttpSession session,
                                                                @RequestParam String endpoint,
                                                                @RequestParam Map<String, String> params) {
        if (endpoint == null || endpoint.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Endpoint parameter cannot be null or empty"));
        }
        if (params == null || params.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Params cannot be null or empty"));
        }

        // Ensure params do not contain "endpoint"
        params.remove("endpoint");

        // Store endpoint and params in the session
        session.setAttribute("storedEndpoint", endpoint);
        session.setAttribute("storedParams", params);

        return ResponseEntity.ok(Map.of("message", "Request stored successfully"));
    }

    // Retrieve the stored endpoint and params from the session
    @GetMapping("/stored-request/get")
    public ResponseEntity<Map<String, Object>> getStoredRequest(HttpSession session) {
        Object storedEndpoint = session.getAttribute("storedEndpoint");
        Object storedParams = session.getAttribute("storedParams");

        if (storedEndpoint != null && storedParams != null) {
            return ResponseEntity.ok(Map.of(
                    "endpoint", storedEndpoint,
                    "params", storedParams
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "No stored request found"));
    }

    // Retrieve all session contents
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getSessionContents(HttpSession session) {
        Map<String, Object> sessionContents = new HashMap<>();
        session.getAttributeNames().asIterator().forEachRemaining(attributeName -> {
            Object attributeValue = session.getAttribute(attributeName);
            sessionContents.put(attributeName, attributeValue);
        });
        return ResponseEntity.ok(sessionContents);
    }
}
