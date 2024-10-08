import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(filterName = "Cors", urlPatterns = "/*")
public class Cors implements Filter {
    private final ArrayList<String> allowedURIs = new ArrayList<>();

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        PrintWriter out = response.getWriter();

        String origin = ((HttpServletRequest) request).getHeader("Origin");
        String correctURL = null;
        ArrayList<String> origins =  new ArrayList<>(Arrays.asList(
                "http://localhost:8081",
                "http://fabflixs.com",
                "http://gcp.fabflixs.com",
                "http://3.101.155.88:8081",
                "http://13.52.183.212:8081"
        ));

        for(String o: origins){
            if (origin.equals(o)){
                System.out.println("The correct origin is : " + o);
                correctURL = o;
            }
        }
        httpResponse.setHeader("Access-Control-Allow-Origin", correctURL);
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        //System.out.println("CORS FILTER INITIATED ");
        ((HttpServletResponse) response).setStatus(200);
        chain.doFilter(httpRequest, httpResponse);

    }




}