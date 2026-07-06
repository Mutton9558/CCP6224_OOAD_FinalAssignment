package Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class EquipmentDB {
    public EquipmentDB(){
        String insertQuery = "INSERT INTO Equipments (name, category, daily_rental_rate, status) VALUES (?, ?, ?, ?)";

        Object[][] testData = {
            {"Camera Sony A7IV", 1, 250.00f, "Available"},
            {"Lens 24-70mm f2.8", 1, 80.00f, "Rented Out"},
            {"Tripod Manfrotto", 2, 45.00f, "Pending Return Confirmation"}
        };

        try (Connection conn = core.DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(insertQuery)) {

            for (Object[] row : testData) {
                statement.setString(1, (String) row[0]);
                statement.setString(2, (String) row[1]);
                statement.setFloat(3, (Float) row[2]);
                statement.setString(4, (String) row[3]);
                statement.addBatch();
            }

            statement.executeBatch();
            System.out.println("Test data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Map<Integer, Equipment> fetchAllEquipments(Map<Integer, Category> categoryMap){
        Map<Integer, Equipment> temp = new HashMap<>();
        
        String query = "SELECT * FROM Equipments";
        
        try (Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(query);
                ResultSet res2 = statement.executeQuery()) {
                while(res2.next()){
                    int id = res2.getInt("equipment_id");
                    String name = res2.getString("name");
                    int category = res2.getInt("category");
                    float dailyRentalRate = res2.getFloat("daily_rental_rate");
                    String status = res2.getString("status");
                    
                    Equipment newEquipment = new Equipment(id, name, categoryMap.get(category), dailyRentalRate, status);
                    temp.put(id, newEquipment);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error on Fetching Equipments: " + e.getMessage());
        }
        
        return temp;
    }
    
    public int create(String name, Category category, float rate, String status){
        String insertReq = "INSERT INTO Equipments VALUES (?, ?, ?, ?)";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, name);
                statement.setInt(2, category.getId());
                statement.setFloat(3, rate);
                statement.setString(4, status);
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean update(int id, float new_rate, String new_status){
        String updateQuery = "UPDATE Equipments SET daily_rental_rate = ?, status = ? WHERE equipment_id = ?";

        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(updateQuery)){
                statement.setFloat(1, new_rate);
                statement.setString(2, new_status);
                statement.setInt(3, id);
                int rowsUpdated = statement.executeUpdate();
                if(rowsUpdated > 0){
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean delete(int id){
        String deleteQuery = "DELETE FROM Equipments WHERE id = ?";
        
        try(Connection conn = core.DatabaseManager.getConnection()){
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                int rowsDeleted = pstmt.executeUpdate(); 
                if(rowsDeleted > 0){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
