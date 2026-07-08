package User;
import java.time.LocalDate;

public class Student extends User{
    
    //no id constructor
    public Student(String name, String email, String password, String gender, LocalDate dob){
        super(0, name, email, password, gender, dob);
        setPermissions();
    }
    
    public Student(int id, String name, String email, String password, String gender, LocalDate dob){
        super(id, name, email, password, gender, dob);
        setPermissions();
    }
    
    @Override 
    public String returnRole(){
       return "Student";
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
        
    @Override
    public void setDiscountRate(){
        setDiscount(0.25);
    }
}
