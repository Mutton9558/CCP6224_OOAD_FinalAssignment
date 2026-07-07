package User;
import java.util.*;
import java.awt.*;
import java.time.LocalDate;
//implemented singleton design pattern

public class UserController {
    
    private User currentUser;
    
    //static bcs want it to be shared memory
    private static UserController userController = new UserController();
    
    //private constructor to not make it possible to be instantiated
    private UserController(){}
    
    public static UserController getInstance(){
        return userController;
    }
    
    //register
    public void registerUser(){}
        
    //login
    public void login(int id, String password){}

    //logout 
    public void logout(){
        currentUser = null;
    }
    
    //UPDATING USER DETAILS BY HAVING SETTERS THROUGH USER CONTROLLER
    public boolean updateUserProfile(User user, String newName, String newEmail, String newPassword, String newGender, LocalDate newDob ){
        
//        if(!isValidName(newName)){
//            return false;
//        }
        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setGender(newGender);
        user.setGender(newGender);
        user.setDateOfBirth(newDob);
        return true;
       }
    
    }
