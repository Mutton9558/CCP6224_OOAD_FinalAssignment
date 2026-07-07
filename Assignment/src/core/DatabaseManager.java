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
        
        String createBillsTable = """
            CREATE TABLE IF NOT EXISTS Bills (
                bill_id INTEGER PRIMARY KEY AUTOINCREMENT,
                rental_id INTEGER,
                base_fee FLOAT NOT NULL,
                discount_amount FLOAT NOT NULL,
                penalty_amount FLOAT NOT NULL,
                net_payable FLOAT NOT NULL,
                bill_type VARCHAR NOT NULL,
                bill_status VARCHAR NOT NULL
            );
            """;    

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createCategoriesTable);
            stmt.execute(createEquipmentsTable);
            stmt.execute(createBillsTable);
        } catch (SQLException e) {
            System.err.println("Failed to initialize database tables: " + e.getMessage());
        }
    }
}
