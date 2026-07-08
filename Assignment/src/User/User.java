package User;
import java.util.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public abstract class User{
    
    private int user_id;
    private String user_name;
    private String email;
    private String password;
    private String gender;
    private LocalDate date_of_birth;
    private double discount_rate;
    private Set<Permission> permissionSet = EnumSet.noneOf(Permission.class);

    public User(int id, String name, String email, String password, String gender, LocalDate dob){
        this.user_id = id;
        this.user_name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.date_of_birth = dob;
    }
    
    //abstract methods 
    public abstract String returnRole();
    public abstract void setPermissions();
    public abstract void setDiscountRate();
    
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
    
                
    public void setDiscount(double discount){
        this.discount_rate= discount;
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
    
        public double getDiscount(){
        return discount_rate;
    }
    
    // Permission functions 
    public void addPermissions(Permission... permissions){
        for(Permission permission: permissions){
            permissionSet.add(permission);
        }
    }
    
    public void removePermissions(Permission... permissions){
        for(Permission permission: permissions){
            permissionSet.remove(permission);
        }
    }
    
    public void clearPermissionSet(){
        permissionSet.clear();
    }
    
    public Set<Permission> getPermissions(){
        return EnumSet.copyOf(permissionSet);
    }
    
    //checks if they have the permissions
    public boolean hasPermission(Permission permission){
        return permissionSet.contains(permission);
    }
}
