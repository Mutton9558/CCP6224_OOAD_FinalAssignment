package Billing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;

public class BillDB {
    public BillDB(){
        String insertQuery = "INSERT OR IGNORE INTO Bills (bill_id, rental_id, base_fee, discount_amount, penalty_amount, net_payable, bill_type, bill_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Object[][] testData = {
            {1, 1, 500.00f, 50.00f, 0.00f, 450.00f, "Rental Fee", "Paid"},
            {2, 2, 640.00f, 0.00f, 0.00f, 640.00f, "Rental Fee", "Unpaid"}
        };

        try (Connection conn = core.DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(insertQuery)) {

            for (Object[] row : testData) {
                statement.setInt(1, (Integer) row[0]);
                statement.setInt(2, (Integer) row[1]);
                statement.setFloat(3, (Float) row[2]);
                statement.setFloat(4, (Float) row[3]);
                statement.setFloat(5, (Float) row[4]);
                statement.setFloat(6, (Float) row[5]);
                statement.setString(7, (String) row[6]);
                statement.setString(8, (String) row[7]);
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Map<Integer, Bill> fetchAllBills(){
        Map<Integer, Bill> temp = new HashMap<>();
        
        String query = "SELECT * FROM Bills";
        
        try (Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(query);
                ResultSet res2 = statement.executeQuery()) {
                while(res2.next()){
                    int id = res2.getInt("bill_id");
                    int rental_id = res2.getInt("rental_id");
                    float base_fee = res2.getFloat("base_fee");
                    float discount_amount = res2.getFloat("discount_amount");
                    float penalty_amount = res2.getFloat("penalty_amount");
                    float net_payable = res2.getFloat("net_payable");
                    String bill_type = res2.getString("bill_type");
                    String bill_status = res2.getString("bill_status");
                    
                    Bill newBill = new Bill(id, rental_id, base_fee, discount_amount, penalty_amount, net_payable, bill_type, bill_status);
                    temp.put(id, newBill);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error on Fetching Bills: " + e.getMessage());
        }
        
        return temp;
    }
    
    public int create(int rental_id, float base_fee, float discount_amount, float penalty_amount, float net_payable, String bill_type, String bill_status){
        String insertReq = "INSERT INTO Bills(rental_id, base_fee, discount_amount, penalty_amount, net_payable, bill_type, bill_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                statement.setInt(1, rental_id);
                statement.setFloat(2, base_fee);
                statement.setFloat(3, discount_amount);
                statement.setFloat(4, penalty_amount);
                statement.setFloat(5, net_payable);
                statement.setString(6, bill_type);
                statement.setString(7, bill_status);
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
    
    public boolean update(int id, float penalty_amount, float net_payable){
        String updateQuery = "UPDATE Bills SET penalty_amount = ?, net_payable = ? WHERE bill_id = ?";

        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(updateQuery)){
                statement.setFloat(1, penalty_amount);
                 statement.setFloat(2, net_payable);
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
    
    public boolean updateStatus(int id, String new_status){
        String updateQuery = "UPDATE Bills SET bill_status = ? WHERE bill_id = ?";

        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(updateQuery)){
                statement.setString(1, new_status);
                statement.setInt(2, id);
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
}
