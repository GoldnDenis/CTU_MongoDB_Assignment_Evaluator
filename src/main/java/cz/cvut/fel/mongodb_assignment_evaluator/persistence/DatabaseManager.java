package cz.cvut.fel.mongodb_assignment_evaluator.persistence;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseManager {
    public Connection connect() throws SQLException {
        String url = "jdbc:h2:mem:testdb";
        String user = "test";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

//    public void connect() {
//        String url = "jdbc:h2:mem:testdb";
//        String user = "test";
//        String password = "";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             Statement stmt = conn.createStatement()) {
//            // Create a sample table
////            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(255))";
////            stmt.execute(createTableSQL);
//
//            // Insert sample data
////            String insertSQL = "INSERT INTO users (id, name) VALUES (1, 'Alice')";
////            stmt.executeUpdate(insertSQL);
//
//            // Retrieve data
////            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
////            while (rs.next()) {
////                System.out.println("User ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
////            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
