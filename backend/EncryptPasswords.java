import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class EncryptPasswords {

    public static void main(String[] args) {
        String loginUser = "admin";
        String loginPasswd = "andy";
        String loginUrl = "jdbc:mysql://localhost:3307/moviedb";

        try (Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd)) {
            // Alter both tables to accommodate encrypted passwords
            alterTable(connection, "customers", "password", 128);
            alterTable(connection, "employees", "password", 128);

            // Encrypt customer passwords
            encryptPasswords(connection, "customers", "id");

            // Encrypt employee passwords
            encryptPasswords(connection, "employees", "email");

            System.out.println("Password encryption for all tables completed successfully.");
        } catch (Exception e) {
            System.out.println("Database operation failed.");
            e.printStackTrace();
        }
    }

    private static void alterTable(Connection connection, String tableName, String columnName, int length) throws Exception {
        String sql = "ALTER TABLE " + tableName + " MODIFY COLUMN " + columnName + " VARCHAR(" + length + ")";
        try (PreparedStatement alterStmt = connection.prepareStatement(sql)) {
            int alterResult = alterStmt.executeUpdate();
            System.out.println("Altering " + tableName + " table schema completed, " + alterResult + " rows affected");
        }
    }

    private static void encryptPasswords(Connection connection, String tableName, String keyField) throws Exception {
        String selectSQL = "SELECT " + keyField + ", password FROM " + tableName;
        String updateSQL = "UPDATE " + tableName + " SET password = ? WHERE " + keyField + " = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL)) {
            ResultSet rs = selectStmt.executeQuery();
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

            while (rs.next()) {
                String key = rs.getString(keyField);
                String plainPassword = rs.getString("password");
                String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

                try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                    updateStmt.setString(1, encryptedPassword);
                    updateStmt.setString(2, key);
                    int updateResult = updateStmt.executeUpdate();
                    System.out.println("Updated password for " + tableName + " with " + keyField + " " + key + ": " + updateResult + " row(s) affected.");
                }
            }
            rs.close();
        }
    }
}
