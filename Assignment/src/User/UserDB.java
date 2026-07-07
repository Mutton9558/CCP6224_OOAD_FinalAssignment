package User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

public class UserDB {
    public UserDB(){}

    public void seedTestUsers(){
        
        String insertQuery = "INSERT OR IGNORE INTO Users (user_id, user_name, email, password, gender, dob, discount, role) VALUES (?,?,?,?,?,?,?,?,?)";
        Object[][] testData = {
            {1, "Elsa", "elsa@mail.com", "test01", "Female", LocalDate.of(2003, 5, 12), 0.3, "Student"},
            {2, "Shawn", "shawn@mail.com", "test02", "Male", LocalDate.of(2003, 5, 12), 0.3, "EquipmentManager"},
            {3, "Jason", "jason@mail.com", "test03", "Male", LocalDate.of(2003, 5, 12), 0.3, "Staff"},
            {4, "Zaki", "zaki@mail.com", "test04", "Male", LocalDate.of(2003, 5, 12), 0.3, "Admin"},
        };
        
        try (Connection conn = 
            core.DatabaseManager.getConnection(); 
            PreparedStatement statement = conn.prepareStatement(insertQuery)){
            
            for (Object[] row : testData){
                statement.setInt(1, (Integer) row[0]);
                statement.setString(2, (String) row[1]);
                statement.setString(3, (String) row[2]);
                statement.setString(4, (String) row[3]);
                statement.setString(5, (String) row[4]);
                statement.setDouble(6, (Double) row[5]);
                statement.setDate(7, (java.sql.Date) row[6]);
                statement.setString(8, (String) row[7]);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public int addUser(User user){
        
        String insertReq =  "INSERT OR IGNORE INTO Users ( user_name, email, password, gender, dob, discount, role) VALUES (?,?,?,?,?,?,?)";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getGender());
                statement.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
                statement.setDouble(6, user.getDiscount());
                statement.setString(7, user.returnRole());
                statement.executeUpdate();
                try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        return(generatedKeys.getInt(1));
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    return -1;
    
    }
    
    public int deleteUser(int user_id){
        
        String deleteQuery = "DELETE FROM Users WHERE user_id = ?";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(deleteQuery)){
                statement.setInt(1, user_id);
                int rowsDeleted = statement.executeUpdate();
                if(rowsDeleted>0){
                    return 0;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    
    //LIST OF ALL USERS
    public Map<Integer, User> fetchAllUsers(Map<Integer, User> userMap){
        Map<Integer, User> temp = new HashMap<>();
        String query = "SELECT * FROM Users";
        
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(query);
                ResultSet result = statement.executeQuery()){
                while(result.next()){
                    int id = result.getInt("user_id");
                    String name = result.getString("user_name");
                    String email = result.getString("email");
                    String password = result.getString("password");
                    String gender = result.getString("gender");
                    LocalDate dob = result.getDate("date_of_birth");
//                    double discount = result.getDouble("discount");
                    String role = result.getString("role");
                    User user;
                   
                   switch(role){
                       
                       case "Student":
                           user = new Student(int id, String name, String email, String password, String gender, LocalDate dob, double discount);
                           break;
                       case "Staff":
                           user = new Staff(int id, String name, String email, String password, String gender, LocalDate dob, double discount);
                           break;
                       case "EquipmentManager":
                           user = new EquipmentManager(int id, String name, String email, String password, String gender, LocalDate dob, double discount);
                           break;
                       case "Admin":
                           user = new Admin(int id, String name, String email, String password, String gender, LocalDate dob, double discount);
                           break;
                       default :
                           continue;
                   }
                }
            } catch(SQLException e){
                System.err.println("Error on Fetching users!"+ e.getMessage());
            }
            return temp;         
        }
    }
    
    
    
    

        
        
        
    
    
    
    
    

    
    public void updateUser(User user){};
    public void findUser(int user_id){};
    public void getAllUsers(){};
}
