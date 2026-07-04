package User;
import java.util.EnumSet;
import java.util.Set;

public class UserType {
    
    private String user_type_name;
    private double discount_rate;
    private Set<Permission> permissionSet = EnumSet.noneOf(Permission.class);
    
    public UserType(){
    }
    
    public UserType(String typename, double discount){
        
        this.user_type_name = typename;
        this.discount_rate = discount;
    }
    
    //getters
    public String getName(){
        return user_type_name;
    }
    public double getDiscount(){
        return discount_rate;
    }
    
    //setters
    public void setName(String typename){
        this.user_type_name = typename;
    }
    public void setDiscount(double discount){
        this.discount_rate= discount;
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
