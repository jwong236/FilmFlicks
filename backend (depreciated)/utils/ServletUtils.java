package utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for servlets
 */
public class ServletUtils {

    /**
     * Retrieves a database connection using JNDI lookup to access the configured DataSource.
     *
     * @return A new database Connection object from the pooled DataSource.
     * @throws NamingException If a naming exception is encountered during JNDI lookup.
     * @throws SQLException If a database access error occurs or the datasource is not found.
     */
    public static Connection getConnection() throws NamingException, SQLException {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/moviedb");
        return ds.getConnection();
    }

    /**
     * Parses a sort rule string into an SQL ORDER BY clause.
     * The sort rule is expected to be in the format "field1_direction1_field2_direction2".
     *
     * @param sortRule The sorting rule string, which dictates the ordering of query results.
     * @return An SQL ORDER BY clause constructed based on the provided sort rule.
     */
    public static String parseSortRule(String sortRule) {
        if (sortRule == null || sortRule.trim().isEmpty()) {
            return "ORDER BY m.title ASC";
        }

        String[] parts = sortRule.split("_");
        if (parts.length != 4) {
            return "ORDER BY m.title ASC";
        }

        String field1 = mapField(parts[0]);
        String direction1 = mapDirection(parts[1]);
        String field2 = mapField(parts[2]);
        String direction2 = mapDirection(parts[3]);

        return String.format("ORDER BY %s %s, %s %s", field1, direction1, field2, direction2);
    }

    /**
     * Maps a field name from a request parameter to an SQL field name.
     *
     * @param field The field name as provided in the sort rule.
     * @return The corresponding SQL field name.
     * @throws IllegalArgumentException If the input field name is not recognized.
     */
    private static String mapField(String field) {
        switch (field) {
            case "title":
                return "m.title";
            case "rating":
                return "r.rating";
            default:
                throw new IllegalArgumentException("Invalid sorting field: " + field);
        }
    }

    /**
     * Maps a direction identifier from a request parameter to an SQL order direction.
     *
     * @param direction The direction identifier (e.g., 'asc' or 'desc').
     * @return The corresponding SQL keyword for ordering.
     * @throws IllegalArgumentException If the input direction is not recognized.
     */
    private static String mapDirection(String direction) {
        switch (direction) {
            case "asc":
                return "ASC";
            case "desc":
                return "DESC";
            default:
                throw new IllegalArgumentException("Invalid sorting direction: " + direction);
        }
    }
}
