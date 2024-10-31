/*
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    private final ArrayList<String> allowedURIs = new ArrayList<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestMethod = httpRequest.getMethod();
        String requestURI = httpRequest.getRequestURI();

        System.out.println("Incoming request: " + requestMethod + " " + requestURI);

        if (requestMethod.equals("OPTIONS")) {
            System.out.println("Handling preflight request...");
            chain.doFilter(request, response); // Handle preflight request
            return;
        }

        if (isUrlAllowedWithoutLogin(requestURI)) {
            System.out.println("Allowing access without login: " + requestURI);
            chain.doFilter(request, response); // Allow access without logging in
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            System.out.println("Session exists for user: " + session.getAttribute("email"));
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response); // Allow access if session exists
        } else {
            System.out.println("Unauthorized access attempt: " + requestURI);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isUrlAllowedWithoutLogin(String requestURI) {
        return allowedURIs.stream().anyMatch(uri -> requestURI.toLowerCase().endsWith(uri.toLowerCase()));
    }

    public void init(FilterConfig fConfig) {
        // Define URLs that are allowed without logging in
        allowedURIs.add("app.jsx");
        allowedURIs.add("main.jsx");
        allowedURIs.add("/fabFlix/");
        allowedURIs.add("index.html");
        allowedURIs.add("index.js");
        allowedURIs.add("/login");
        allowedURIs.add("/subtract");
        allowedURIs.add("/employeeLogin");
        allowedURIs.add("/metadata");
        allowedURIs.add("/insert/star");
        allowedURIs.add("/insert/movie");
        allowedURIs.add("/fullTextSearch");

    }
}
*/
