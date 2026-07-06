package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // Centralized database URL path
    private static final String URL = "jdbc:sqlite:maomao.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        initializeSchema(conn);
        return conn;
    }
    
    private static void initializeSchema(Connection conn) {
        String createCategoriesTable = """
            CREATE TABLE IF NOT EXISTS Categories (
                category_id INTEGER PRIMARY KEY AUTOINCREMENT,
                category_name VARCHAR NOT NULL,
                maintenance_fee FLOAT NOT NULL,
                category_discount FLOAT NOT NULL,
                late_penalty FLOAT NOT NULL,
                damage_penalty FLOAT NOT NULL
            );
            """;

        String createEquipmentsTable = """
            CREATE TABLE IF NOT EXISTS Equipments (
                equipment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                category INTEGER,
                daily_rental_rate FLOAT NOT NULL,
                status VARCHAR NOT NULL,    
                FOREIGN KEY (category) REFERENCES Categories(category_id)
            );
            """;

        String createRentalsTable = """
            CREATE TABLE IF NOT EXISTS Rentals (
                rental_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                equipment INTEGER NOT NULL,
                bookedDate DATE NOT NULL,
                dueDate DATE NOT NULL,
                duration INTEGER NOT NULL,
                returnStatus BOOLEAN,
                lateStatus BOOLEAN NOT NULL,    
                FOREIGN KEY (equipment) REFERENCES Equipments(equipment_id)
            );
            """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createCategoriesTable);
            stmt.execute(createEquipmentsTable);
            stmt.execute(createRentalsTable);

        } catch (SQLException e) {
            System.err.println("Failed to initialize database tables: " + e.getMessage());
        }
    }
}
