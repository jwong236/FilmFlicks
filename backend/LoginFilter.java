import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    private final ArrayList<String> allowedURIs = new ArrayList<>();

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        PrintWriter out = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        //System.out.println("LoginFilter: " + httpRequest.getRequestURI());

        // Check if this URL is allowed to access without logging in
        if (this.isUrlAllowedWithoutLogin(httpRequest.getRequestURI())) {
            // Keep default action: pass along the filter chain
            chain.doFilter(request, response);
            return;
        }

        out.flush();
        // Redirect to login page if the "user" attribute doesn't exist in session
        if (httpRequest.getSession().getId() == null) {
            //System.out.println("redirection to login ");
            //sends json to the jsx files so that when they receive the message as login then redirect
            out.print("{\"message\": \"login\"}");
        } else {
            System.out.println("navigate past the login");
            chain.doFilter(request, response);
            System.out.println(response);
            out.print("{\"message\": \"no_login\"}");
        }

        out.flush();
    }

    private boolean isUrlAllowedWithoutLogin(String requestURI) {
        /*
         Setup your own rules here to allow accessing some resources without logging in
         Always allow your own login related requests(html, js, servlet, etc..)
         You might also want to allow some CSS files, etc..
         */
        System.out.println("request uri " + requestURI);
        if ((requestURI.equals("/fabFlix/movielist")) || (requestURI.equals("/fabFlix/") ) ){
            return true;
        }
        return allowedURIs.stream().anyMatch(requestURI.toLowerCase()::endsWith);
    }


    //these should not need authentication each time these are requested indirectly
    //essential without auth, login should not need to login to login
    public void init(FilterConfig fConfig) {
        allowedURIs.add("login.jsx");
        allowedURIs.add("app.jsx");
        allowedURIs.add("main.jsx");
        allowedURIs.add("index.html");
        allowedURIs.add("index.js");
        allowedURIs.add("login");
        allowedURIs.add("/fabFlix/");
        //allowedURIs.add("movielist");


    }

}