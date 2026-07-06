package Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.sql.Statement;

public class CategoryDB {
    public CategoryDB(){
        String insertQuery = "INSERT OR IGNORE INTO Categories (category_id, category_name, maintenance_fee, category_discount, late_penalty, damage_penalty) VALUES (?, ?, ?, ?, ?, ?)";

        Object[][] testData = {
            {1, "Camera and Lenses", 100.00f, 0.1f, 0.1f, 0.5f},
            {2, "Lighting Equipment", 45.00f, 0.05f, 0.1f, 0.5f}
        };

        try (Connection conn = core.DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(insertQuery)) {

            for (Object[] row : testData) {
                statement.setInt(1, (Integer) row[0]);
                statement.setString(2, (String) row[1]);
                statement.setFloat(3, (Float) row[2]);
                statement.setFloat(4, (Float) row[3]);
                statement.setFloat(5, (Float) row[4]);
                statement.setFloat(6, (Float) row[5]);
                statement.addBatch();
            }

            statement.executeBatch();
            System.out.println("Test data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Map<Integer, Category> fetchAllCategories(){
        Map<Integer, Category> temp = new HashMap<>();
        String query = "SELECT * FROM Categories";
        
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
                while(res.next()){
                    int category_id = res.getInt("category_id");
                    String category_name = res.getString("category_name");
                    float maintenanceFee = res.getFloat("maintenance_fee");
                    float categoryDiscount = res.getFloat("category_discount");
                    float latePenalty = res.getFloat("late_penalty");
                    float dmgPenalty = res.getFloat("damage_penalty");
                    
                    Category newCategory = new Category(category_id, category_name, maintenanceFee, categoryDiscount, latePenalty, dmgPenalty);
                    temp.put(category_id, newCategory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
    
    public int create(String name, float fee, float discount, float late_penalty, float dmg_penalty){
        String insertReq = "INSERT INTO Categories (category_name, maintenance_fee, category_discount, late_penalty, damage_penalty) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, name);
                statement.setFloat(2, fee);
                statement.setFloat(3, discount);
                statement.setFloat(4, late_penalty);
                statement.setFloat(5, dmg_penalty);
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
    
    public boolean update(int id, float fee, float discount, float latePenalty, float damagePenalty){
        String updateQuery = "UPDATE Categories SET maintenance_fee = ?, category_discount = ?, late_penalty = ?, damage_penalty = ? WHERE category_id = ?";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(updateQuery)){
                statement.setFloat(1, fee);
                statement.setFloat(2, discount);
                statement.setFloat(3, latePenalty);
                statement.setFloat(4, damagePenalty);
                statement.setInt(5, id);
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
        String deleteQuery = "DELETE FROM Categories WHERE id = ?";
        
        try(Connection conn = core.DatabaseManager.getConnection()){
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, id);
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
