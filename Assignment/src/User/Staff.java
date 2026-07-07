package User;
import java.time.LocalDate;

public class Staff extends User{
    
    public Staff(int id, String name, String email, String password, String gender, LocalDate dob, double discount){
        super(id, name, email, password, gender, dob, discount);
        setPermissions();
    }
    
    @Override 
    public String returnRole(){
       return "Staff";
    }
    
    @Override 
    public void setPermissions(){
        
        clearPermissionSet();
        addPermissions(
           Permission.VIEW_PROFILE,
           Permission.MAKE_PAYMENT,
           Permission.VIEW_FINANCE_HISTORY,
           Permission.RENT,
           Permission.RETURN,
           Permission.VIEW_RENTAL);   
    }

}
