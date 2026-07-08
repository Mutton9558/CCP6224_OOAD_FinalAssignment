package User;
import java.time.LocalDate;

public class EquipmentManager extends User{
    
    public EquipmentManager(int id, String name, String email, String password, String gender, LocalDate dob){
        super(id, name, email, password, gender, dob);
        setPermissions();
    }
    
    @Override 
    public String returnRole(){
       return "EquipmentManager";
    }
    
    @Override 
    public void setPermissions(){
        
        clearPermissionSet();
        addPermissions(
           Permission.VIEW_PROFILE,
           Permission.VIEW_EQUIPMENT,
           Permission.ADD_EQUIPMENT,
           Permission.EDIT_EQUIPMENT,
           Permission.ADD_CATEGORY,
           Permission.EDIT_CATEGORY,
           Permission.VIEW_RENTAL);   
    }
    
    @Override
    public void setDiscountRate(){
        setDiscount(0);
    }
}
