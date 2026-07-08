package User;
import java.util.*;
import java.awt.*;
import java.time.LocalDate;
//implemented singleton design pattern

public class UserController {
    
    private Map<Integer, User> userMap = new HashMap<>();
    private UserDB repository; 
    private User currentUser;
    //static bcs want it to be shared memory
    private static UserController instance;
    
    //private constructor to not make it possible to be instantiated
    private UserController(UserDB repository){
        this.repository = repository; 
        this.userMap = repository.fetchAllUsers();
    }
    
    //for main
    public static UserController getInstance(UserDB repository){
        if(instance == null){
            return instance = new UserController(repository);    
        }
        return instance;
    }
 
//    public static UserController getInstance(){
//        if(instance != null){
//            throw new IllegalStateException("Must initialize the user controller first!");
//        }
//        return instance;
//    }
    
    public Map<Integer, User> fetchMap(){
        return this.userMap;
    }
    
    //gets the current user 
    public User getCurrentUser(){
        return currentUser;
    }
      
    public User getCurUser(){
        return currentUser;
    }
    
    //register
    public void registerUser(String name, String email, String password, String gender,String role, LocalDate dob){
        User newUser;
        if(!isValid(name, email, password, gender, dob)){
            throw new IllegalArgumentException("Invalid Registration details");
        }
        switch(role){
                       
            case "Student":
                newUser = new Student(name,  email,  password,  gender,  dob);
                break;
            case "Staff":
                newUser = new Staff(name,  email,  password,  gender,  dob);
                break;
            case "EquipmentManager":
                newUser = new EquipmentManager(name,  email,  password,  gender,  dob);
                break;
            case "Admin":
                newUser = new Admin(name,  email,  password,  gender,  dob);
                break;
            default :
                throw new IllegalArgumentException("Unknown role: " + role);
        }
        
        int id = repository.addUser(newUser);
        newUser.setId(id);
        userMap.put(id, newUser);
    }
    
    //delete user 
    public boolean deleteUser(int user_id){
        return repository.deleteUser(user_id);
  }
        
    //login
    public User loginUser(int id, String password){
        
        User loginAttemptUser = userMap.get(id);

        if(loginAttemptUser == null){
            return null;
        }
        
        if(!loginAttemptUser.getPassword().equals(password)){
            return null;
        } 
        
        this.currentUser = loginAttemptUser;
        return loginAttemptUser;
        }
    

    //logout 
    public void logoutUser(){
        currentUser = null;
    }
    
    public boolean updateUserProfile(User user, String newName, String newEmail, String newPassword, String newGender, LocalDate newDob ){
        
        if(!isValid(newName,newEmail, newPassword, newGender, newDob)){
            return false;
        }
        boolean successResult = repository.updateUser(user.getId(), newName,newEmail,newPassword,newGender, newDob);
        if(successResult){
           user.setName(newName);
           user.setEmail(newEmail);
           user.setPassword(newPassword);
           user.setGender(newGender);
           user.setDateOfBirth(newDob);
        }
        return successResult;
       }
    
     public boolean isValid(String newName, String newEmail, String newPassword, String newGender, LocalDate newDob){
        
         if(newName == null || newName.length()>50){
             return false;
         }
         if(newEmail == null || !newEmail.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")){
             return false;
         }
         if(newPassword == null || newPassword.length()<8){
            return false;
         }
         if (newGender == null || !(newGender.equals("Male") || newGender.equals("Female"))) {
            return false;
         } 
         if(newDob == null || newDob.isAfter(LocalDate.now())|| newDob.isBefore(LocalDate.now().minusYears(120))){
             return false;
         }
         return true;
     }
     
     
          
    }


