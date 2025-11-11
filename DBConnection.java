package connectsphere;

import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:connectsphere.db";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                initializeDatabase();
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS friends (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "city TEXT NOT NULL" +
                    ")";
            stmt.execute(createTableSQL);
            System.out.println("✅ Database initialized successfully");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database: " + e.getMessage());
        }
    }
}
