package Rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import Equipment.Equipment;

import java.util.HashMap;

public class RentalDB {
    
        public RentalDB(){
            String insertQuery = "INSERT INTO Rentals (user_id, equipment, bookedDate, duration, returnStatus, lateStatus) VALUES (?, ?, ?, ?, ?, ?)";
    
            Object[][] testData = {
                {1, 1, LocalDate.now(), 2, false, false},
                {1, 2, LocalDate.now(), 8, false, false},
                {3, 3, LocalDate.now(), 4, false, false}
            };
    
            try (Connection conn = core.DatabaseManager.getConnection();
                 PreparedStatement statement = conn.prepareStatement(insertQuery)) {
    
                for (Object[] row : testData) {
                    statement.setInt(1, (Integer) row[0]);
                    statement.setInt(2, (Integer) row[1]);
                    statement.setObject(3, row[2]);
                    statement.setInt(3, (Integer) row[3]);
                    statement.setBoolean(4, (Boolean) row[4]);
                    statement.setBoolean(5, (Boolean) row[5]);
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
            
            String query = "SELECT * FROM Rentals";
            
            try (Connection conn = core.DatabaseManager.getConnection()){
                try(PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet res2 = statement.executeQuery()) {
                    while(res2.next()){
                        int id = res2.getInt("rental_id");
                        int user_id = res2.getInt("user_id");
                        int equipment = res2.getInt("equipment");
                        LocalDate bookedDate = res2.getDate("bookedDate").toLocalDate();
                        int duration = res2.getInt("duration");
                        boolean returnStatus = res2.getBoolean("returnStatus");
                        boolean lateStatus = res2.getBoolean("lateStatus");
                        
                        Rental newRental = new Rental(id, user_id, equipmentMap.get(equipment), bookedDate, duration, returnStatus, lateStatus);
                        temp.put(id, newRental);
                    }
                }
                
            } catch (SQLException e) {
                System.err.println("Error on Fetching Rentals: " + e.getMessage());
            }
            
            return temp;
        }
        
        public int create(int user_id, Equipment equipment, int duration){
            String insertReq = "INSERT INTO Rentals VALUES (?, ?, ?, ?, ?, ?)";
            LocalDate bookedDate = LocalDate.now();
            LocalDate dueDate = bookedDate.plusDays(duration);
            boolean returnStatus = false;
            boolean lateStatus = false;
            try(Connection conn = core.DatabaseManager.getConnection()){
                try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                    statement.setInt(1, user_id);
                    statement.setInt(2, equipment.getId());
                    statement.setDate(3, Date.valueOf(bookedDate));
                    statement.setInt(4, duration);
                    statement.setBoolean(5, returnStatus);
                    statement.setBoolean(6, lateStatus);
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
        
        public boolean update(int id, boolean returnStatus, boolean lateStatus){
            String updateQuery = "UPDATE Rentals SET returnStatus = ?, lateStatus = ? WHERE equipment_id = ?";
    
            try(Connection conn = core.DatabaseManager.getConnection()){
                try(PreparedStatement statement = conn.prepareStatement(updateQuery)){
                    statement.setBoolean(2, returnStatus);
                    statement.setBoolean(3, lateStatus);
                    statement.setInt(4, id);
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
            String deleteQuery = "DELETE FROM Rentals WHERE id = ?";
            
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

