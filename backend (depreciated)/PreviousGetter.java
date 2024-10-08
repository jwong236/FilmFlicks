import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/previousGetter")
public class PreviousGetter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"message\":\"Unauthorized access\"}");
            return;
        }

        HashMap<String, HashMap<String, String>> prev = (HashMap<String, HashMap<String, String>>) session.getAttribute("prev");
        String endpoint = request.getParameter("endpoint");
        boolean isEmptyEndpoint = endpoint == null || endpoint.isEmpty();

        if (isEmptyEndpoint && prev != null) {
            endpoint = determinePreviousEndpoint(prev);
        }

        HashMap<String, HashMap<String, String>> searchParams = buildSearchParams(request, prev, endpoint, isEmptyEndpoint);

        session.setAttribute("prev", searchParams);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String jsonResponse = mapper.writeValueAsString(searchParams);
        out.print(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        out.flush();
    }

    private String determinePreviousEndpoint(HashMap<String, HashMap<String, String>> prev) {
        String key = prev.keySet().iterator().next();
        switch (key) {
            case "browsegenre":
                return "browse/genre";
            case "browsecharacter":
                return "browse/character";
            case "fullTextSearch":
                return "fullTextSearch";
            default:
                return "search";
        }
    }

    private HashMap<String, HashMap<String, String>> buildSearchParams(HttpServletRequest request, HashMap<String, HashMap<String, String>> prev, String endpoint, boolean isEmptyEndpoint) {
        HashMap<String, HashMap<String, String>> searchParams = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();

        switch (endpoint) {
            case "search":
                populateSearchParams(request, prev, params, isEmptyEndpoint, "search");
                break;
            case "browse/genre":
                populateBrowseParams(request, prev, params, isEmptyEndpoint, "browsegenre", "genre");
                break;
            case "browse/character":
                populateBrowseParams(request, prev, params, isEmptyEndpoint, "browsecharacter", "character");
                break;
            case "fullTextSearch":
                populateFullTextSearchParams(request, prev, params, isEmptyEndpoint, "fullTextSearch");
                break;
        }

        if (!isEmptyEndpoint) {
            params.put("page", request.getParameter("page"));
            params.put("pageSize", request.getParameter("pageSize"));
            params.put("sortRule", request.getParameter("sortRule"));
        } else if (prev != null) {
            String prevKey = endpoint.replace("/", "");
            params.put("page", prev.get(prevKey).get("page"));
            params.put("pageSize", prev.get(prevKey).get("pageSize"));
            params.put("sortRule", prev.get(prevKey).get("sortRule"));
        }

        searchParams.put(endpoint.replace("/", ""), params);
        return searchParams;
    }

    private void populateSearchParams(HttpServletRequest request, HashMap<String, HashMap<String, String>> prev, HashMap<String, String> params, boolean isEmptyEndpoint, String prevKey) {
        String[] searchFields = {"title", "director", "star", "year"};
        for (String field : searchFields) {
            String value = request.getParameter(field);
            if (value != null && !value.isEmpty()) {
                params.put(field, value);
            } else if (isEmptyEndpoint && prev != null && prev.containsKey(prevKey)) {
                params.put(field, prev.get(prevKey).get(field));
            }
        }
    }

    private void populateBrowseParams(HttpServletRequest request, HashMap<String, HashMap<String, String>> prev, HashMap<String, String> params, boolean isEmptyEndpoint, String prevKey, String paramKey) {
        String value = request.getParameter(paramKey);
        if (value != null && !value.isEmpty()) {
            params.put(paramKey, value);
        } else if (isEmptyEndpoint && prev != null && prev.containsKey(prevKey)) {
            params.put(paramKey, prev.get(prevKey).get(paramKey));
        }
    }

    private void populateFullTextSearchParams(HttpServletRequest request, HashMap<String, HashMap<String, String>> prev, HashMap<String, String> params, boolean isEmptyEndpoint, String prevKey) {
        String title = request.getParameter("title");
        if (title != null && !title.isEmpty()) {
            params.put("title", title);
        } else if (isEmptyEndpoint && prev != null && prev.containsKey(prevKey)) {
            params.put("title", prev.get(prevKey).get("title"));
        }
    }
}
