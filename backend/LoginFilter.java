import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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



        // Check if this URL is allowed to access without logging in
        if (this.isUrlAllowedWithoutLogin(httpRequest.getRequestURI())) {
            // Keep default action: pass along the filter chain
            chain.doFilter(request, response);
            return;
        }


//        System.out.println("cookie value " + jsessionId.getEmail());
        HttpSession session = httpRequest.getSession(false);

        //System.out.println("CHECK TO SEE IF EMAIL EXISTS " + httpRequest.getSession().getAttribute("email"));
        // Redirect to login page if the "user" attribute doesn't exist in session
        if (session != null) {
            //System.out.println("redirection to login ");
            Email email = (Email) session.getAttribute("email");

            if (email != null){
                //System.out.println("FILTER = email object: " + email);
            }else{
                //System.out.println("FILTER = Session exists but email is not set");
            }
            //sends json to the jsx files so that when they receive the message as login then redirect
//            System.out.println("email:" + httpRequest.getSession().getAttribute("email"));
//            System.out.println("navigate past the login");
//            chain.doFilter(request, response);
//            System.out.println(response);
            httpResponse.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(request,response);
        } else {
            //System.out.println("YOU MUST LOGIN");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        out.flush();
    }

    private boolean isUrlAllowedWithoutLogin(String requestURI) {
        /*
         Setup your own rules here to allow accessing some resources without logging in
         Always allow your own login related requests(html, js, servlet, etc..)
         You might also want to allow some CSS files, etc..
         */
//        System.out.println("request uri " + requestURI);
//        System.out.println("does it contain the same :" + allowedURIs.stream().anyMatch(requestURI.toLowerCase()::endsWith));
        return allowedURIs.stream().anyMatch(requestURI.toLowerCase()::endsWith);
    }


    //these should not need authentication each time these are requested indirectly
    //essential without auth, login should not need to login to login
    public void init(FilterConfig fConfig) {
        allowedURIs.add("app.jsx");
        allowedURIs.add("main.jsx");

        allowedURIs.add("/fabFlix/");
        allowedURIs.add("index.html");
        allowedURIs.add("index.js");

        allowedURIs.add("/login");
        allowedURIs.add("/fabFlix/");
        allowedURIs.add("/add");
        allowedURIs.add("/subtract");
        allowedURIs.add("/totalPrice");
        allowedURIs.add("/delete");
        allowedURIs.add("/payment");
        allowedURIs.add("/shoppingcart");
        //allowedURIs.add("movielist");

        allowedURIs.add("/");
        allowedURIs.add("login.jsx");
        allowedURIs.add("/topmovies.jsx");
        allowedURIs.add("/singlemovie.jsx");
        allowedURIs.add("/singlestar.jsx");
        allowedURIs.add("/homepage.jsx");

        allowedURIs.add("/login");
        allowedURIs.add("/topmovies");
        allowedURIs.add("/singlemovie");
        allowedURIs.add("/singlestar");


        allowedURIs.add("/search");
        allowedURIs.add("/browse/character");
        allowedURIs.add("/browse/genre");
        allowedURIs.add("/previousGetter");
        allowedURIs.add("/homepageGenres");

    }

}