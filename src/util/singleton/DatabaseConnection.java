package util.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton Pattern Implementation
public class DatabaseConnection {
private static DatabaseConnection instance;
private Connection connection;
private String url = "jdbc:mysql://localhost:3306/ecommerce";
private String username = "root";
private String password = "Rithvik@2003";

private DatabaseConnection() {
try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    this.connection = DriverManager.getConnection(url, username, password);
    System.out.println("Database connection established");
} catch (ClassNotFoundException | SQLException e) {
    System.out.println("Database Connection Creation Failed : " + e.getMessage());
}
}

public static synchronized DatabaseConnection getInstance() {
if (instance == null) {
    instance = new DatabaseConnection();
} else {
    try {
        if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
    } catch (SQLException e) {
        instance = new DatabaseConnection();
    }
}
return instance;
}

public Connection getConnection() {
return connection;
}
}