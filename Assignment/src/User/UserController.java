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
    
    public static UserController getInstance(UserDB repository){
        return instance = new UserController(repository);
    }
    
    public Map<Integer, User> fetchMap(){
        return this.userMap;
    }
      
    //register
    public void registerUser(UserDB repository, String name, String email, String password, String gender, LocalDate dob){
        User newStudent = new Student(name, email, password, gender, dob);
        int id = repository.addUser(newStudent);
        newStudent.setId(id);
        userMap.put(id, newStudent);
    }
        
    //login
    public void login(int id, String password){
        if(userMap.containsKey(id)){
            User loginAttemptUser = userMap.get(id);
            if(loginAttemptUser.getPassword().equals(password)){
                System.out.println("Successful Login!");
                this.currentUser = loginAttemptUser;
            }
            else{
                System.out.println("No such user exists!");
            }
        }
    }

    //logout 
    public void logout(){
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


