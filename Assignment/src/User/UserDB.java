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

    public UserDB(){
        
        String insertQuery = "INSERT OR IGNORE INTO Users (user_id, user_name, email, password, gender, date_of_birth, role) VALUES (?,?,?,?,?,?,?)";
        Object[][] testData = {
            {1, "Elsa", "elsa@mail.com", "test01", "Female", LocalDate.of(2003, 5, 12), "Student"},
            {2, "Shawn", "shawn@mail.com", "test02", "Male", LocalDate.of(2003, 5, 12), "EquipmentManager"},
            {3, "Jason", "jason@mail.com", "test03", "Male", LocalDate.of(2003, 5, 12), "Staff"},
            {4, "Zaki", "zaki@mail.com", "test04", "Male", LocalDate.of(2003, 5, 12), "Admin"},
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
                statement.setDate(6, java.sql.Date.valueOf((LocalDate)row[5]));
                statement.setString(7, (String) row[6]);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public int addUser(User user){
        
        String insertReq =  "INSERT OR IGNORE INTO Users ( user_name, email, password, gender, date_of_birth, role) VALUES (?,?,?,?,?,?)";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(insertReq, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getGender());
                statement.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
                statement.setString(6, user.returnRole());
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
    
    public boolean deleteUser(int user_id){
        
        String deleteQuery = "DELETE FROM Users WHERE user_id = ?";
        try(Connection conn = core.DatabaseManager.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(deleteQuery)){
                statement.setInt(1, user_id);
                int rowsDeleted = statement.executeUpdate();
                if(rowsDeleted>0){
                    return true;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //LIST OF ALL USERS
    public Map<Integer, User> fetchAllUsers(){
        Map<Integer, User> temp = new HashMap<>();
        String query = "SELECT * FROM Users";
        
        try(
           Connection conn = core.DatabaseManager.getConnection();
           PreparedStatement statement = conn.prepareStatement(query);
           ResultSet result = statement.executeQuery()){
            
            while(result.next()){
                User user = createUserByRole(result);
                if(user != null){
                   temp.put(user.getId(),user);
                }
            }
                               
            } catch(SQLException e){
                System.err.println("Error on Fetching users!"+ e.getMessage());
            }
            return temp;         

        }
    
    public boolean updateUser(int user_id, String newName, String newMail, String newPassword, String newGender, LocalDate newDob){
        String updateQuery = "UPDATE Users SET user_name = ?, email = ?, password = ?, gender = ?, date_of_birth = ? WHERE user_id = ?";
        try(
            Connection conn = core.DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(updateQuery))
            {
                statement.setString(1,newName);
                statement.setString(2, newMail);
                statement.setString(3, newPassword);
                statement.setString(4, newGender);
                statement.setDate(5, java.sql.Date.valueOf(newDob));
                statement.setInt(6, user_id);
                int rowsUpdated = statement.executeUpdate();
                if(rowsUpdated>0){
                    return true;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        return false;  
    }
    
    public User findUserById(int user_id){
        String findQuery = "SELECT * FROM Users WHERE user_id = ?";
        try(
            Connection conn = core.DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(findQuery)
            ){
            
            statement.setInt(1, user_id);
            ResultSet result = statement.executeQuery();
            User user;
            if(result.next()){
                user = createUserByRole(result);
                return user;
            }
            }catch (SQLException e){
            
               e.printStackTrace();
            }
            return null;
    }
        
    private User createUserByRole(ResultSet result) throws SQLException{
            
        int id = result.getInt("user_id");
        String name = result.getString("user_name");
        String email = result.getString("email");
        String password = result.getString("password");
        String gender = result.getString("gender");
        LocalDate dob = result.getDate("date_of_birth").toLocalDate();
        String role = result.getString("role");
        User user;
                   
        switch(role){
                       
            case "Student":
                user = new Student( id,  name,  email,  password,  gender,  dob);
                break;
            case "Staff":
                user = new Staff( id,  name,  email,  password,  gender,  dob);
                break;
            case "EquipmentManager":
                user = new EquipmentManager( id,  name,  email,  password,  gender,  dob);
                break;
            case "Admin":
                user = new Admin( id,  name,  email,  password,  gender,  dob);
                break;
            default :
                return null;
        }
        return user;
    }
}