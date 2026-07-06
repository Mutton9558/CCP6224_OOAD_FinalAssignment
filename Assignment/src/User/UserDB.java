package User;
import java.util.ArrayList;
import java.util.List;

//implemented singleton design pattern
public class UserDB {
    
    private static UserDB userDB = new UserDB();
    private UserDB(){};
    
    public static UserDB getInstance(){
        return userDB;
    }
    
    public void addUser(User user){};
    public void loadUsers(User user){};
    public void saveUser(User user){};
    public void deleteUser(User user){};
    public void updateUser(User user){};
    public void findUser(User user){};
    public void getAllUsers(User user){};

}
