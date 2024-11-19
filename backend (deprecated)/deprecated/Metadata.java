/*
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/metadata")
public class Metadata extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource dataSource;

    @Override
    public void init() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'moviedb' ORDER BY TABLE_NAME, ORDINAL_POSITION");
             ResultSet resultSet = statement.executeQuery()) {

            List<TableMetadata> metadataList = new ArrayList<>();
            TableMetadata currentTable = null;
            String currentTableName = "";

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (!tableName.equals(currentTableName)) {
                    if (currentTable != null) {
                        metadataList.add(currentTable);
                    }
                    currentTableName = tableName;
                    currentTable = new TableMetadata(tableName);
                }
                currentTable.addColumn(resultSet.getString("COLUMN_NAME"), resultSet.getString("DATA_TYPE"));
            }
            if (currentTable != null) {
                metadataList.add(currentTable);
            }

            PrintWriter out = response.getWriter();
            out.println(objectMapper.writeValueAsString(metadataList));
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.println("{\"error\": \"Error retrieving database metadata\", \"message\": \"" + e.getMessage() + "\"}");
            out.flush();
        }
    }

    private static class TableMetadata {
        private String tableName;
        private List<ColumnMetadata> columns;

        public TableMetadata(String tableName) {
            this.tableName = tableName;
            this.columns = new ArrayList<>();
        }

        public void addColumn(String columnName, String dataType) {
            this.columns.add(new ColumnMetadata(columnName, dataType));
        }

        public String getTableName() {
            return tableName;
        }

        public List<ColumnMetadata> getColumns() {
            return columns;
        }
    }

    private static class ColumnMetadata {
        private String columnName;
        private String dataType;

        public ColumnMetadata(String columnName, String dataType) {
            this.columnName = columnName;
            this.dataType = dataType;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getDataType() {
            return dataType;
        }
    }
}
*/
