package Rental;

import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.util.List;
    import java.util.ArrayList;
    import java.util.Map;

import Equipment.Equipment;

import java.util.HashMap;

public class RentalDB {
    
        public RentalDB(){
            String insertQuery = "INSERT INTO Rental (user_id, equipment, duration, lateStatus) VALUES (?, ?, ?, ?)";
    
            Object[][] testData = {
                {1, 1, 2, true},
                {2, 2, 8, false},
                {3, 3, 4, true}
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
        
        public Map<Integer, Rental> fetchAllRentals(Map<Integer, Equipment> equipmentMap){
            Map<Integer, Rental> temp = new HashMap<>();
            
            String query = "SELECT * FROM Rental";
            
            try (Connection conn = core.DatabaseManager.getConnection()){
                try(PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet res2 = statement.executeQuery()) {
                    while(res2.next()){
                        int id = res2.getInt("rental_id");
                        int user_id = res2.getInt("user_id");
                        int equipment = res2.getInt("equipment");
                        int duration = res2.getInt("duration");
                        boolean lateStatus = res2.getBoolean("lateStatus");
                        
                        Rental newRental = new Rental(id, user_id, equipmentMap.get(equipment), duration, lateStatus);
                        temp.put(id, newRental);
                    }
                }
                
            } catch (SQLException e) {
                System.err.println("Error on Fetching Rentals: " + e.getMessage());
            }
            
            return temp;
        }
        
        public int create(int user_id, Equipment equipment, int duration, boolean status){
            String insertReq = "INSERT INTO Rental VALUES (?, ?, ?, ?)";
            try(Connection conn = core.DatabaseManager.getConnection()){
                try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                    statement.setInt(1, user_id);
                    statement.setInt(2, equipment.getId());
                    statement.setInt(3, duration);
                    statement.setBoolean(4, status);
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
        
        public boolean update(int id, int new_duration, boolean new_status){
            String updateQuery = "UPDATE Rental SET duration = ?, lateStatus = ? WHERE equipment_id = ?";
    
            try(Connection conn = core.DatabaseManager.getConnection()){
                try(PreparedStatement statement = conn.prepareStatement(updateQuery)){
                    statement.setInt(1, new_duration);
                    statement.setBoolean(2, new_status);
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
            String deleteQuery = "DELETE FROM Rental WHERE id = ?";
            
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

