package User;
import java.time.LocalDate;

public class Admin extends User{
    
    public Admin(int id, String name, String email, String password, String gender, LocalDate dob){
        super(id, name, email, password, gender, dob);
        setPermissions();
    }
    
    @Override 
    public String returnRole(){
       return "Admin";
    }
    
    @Override 
    public void setPermissions(){
        
        clearPermissionSet();
        addPermissions(
           Permission.VIEW_PROFILE,
           Permission.EDIT_PROFILE,
           Permission.REGISTER_ACCOUNT);   
    }
    
    @Override
    public void setDiscountRate(){
        setDiscount(0);
    }
}
