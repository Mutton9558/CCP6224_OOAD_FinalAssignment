package User;
import java.util.*;
import java.awt.*;
import java.time.LocalDate;

public class User{
    
    private int user_id;
    private String user_name;
    private String email;
    private String password;
    private String gender;
    private LocalDate date_of_birth;
    private UserType userType;
    
    public User(){
    }
    
    //setters
    
 //DB HANDLES THE USER ID ASSIGNING 
    public void setId(int user_id){
        this.user_id = user_id;
    }
    
    public void setName(String user_name){
        this.user_name = user_name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
    
    public void setDateOfBirth(LocalDate dob){
        this.date_of_birth = dob;
    }
    
    public void setUserType(UserType userType){
        this.userType = userType;
    }
    
    //getters
    public int getId(){
        return user_id;
    }
    
    public String getName(){
        return user_name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getGender(){
        return gender;
    }
    
    public LocalDate getDateOfBirth(){
        return date_of_birth;
    }
    
    public UserType getUserType(){
        return userType;
    }
    
}
