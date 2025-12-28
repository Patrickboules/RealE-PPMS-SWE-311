package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Singleton class for managing SQLite database connections.
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:ppms.db";
    private static DatabaseConnection instance;
    private Connection connection;

    /**
     * Private constructor for singleton pattern.
     */
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            initializeSchema();
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    /**
     * Get the singleton instance.
     * @return DatabaseConnection instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get the database connection.
     * @return Connection object
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Initialize the database schema from schema.sql file.
     */
    private void initializeSchema() {
        try {
            connection.setAutoCommit(true); // Ensure auto-commit is on
            String schema = Files.readString(Paths.get("DataAccess/schema.sql"));
            Statement stmt = connection.createStatement();
            
            // Split and execute each statement
            String[] statements = schema.split(";");
            for (String sql : statements) {
                sql = sql.trim();
                if (!sql.isEmpty() && !sql.startsWith("--")) {
                    stmt.executeUpdate(sql);
                }
            }
            stmt.close();
            System.out.println("Database schema initialized successfully.");
        } catch (Exception e) {
            System.err.println("Failed to initialize schema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Close the database connection.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Main method for testing database initialization.
     */
    public static void main(String[] args) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        System.out.println("Database test completed.");
        db.close();
    }
}
