package Rental;
import java.time.*;
import Equipment.*;


public class Bill {
    private final int rental_id;
    private final int user_id;
    private final Bill bill;
    private final Equipment equipment;
    private LocalTime duration;
    private Boolean lateStatus;
    
    public Bill(int id, int userID, Bill bill, Equipment equipment, LocalTime duration, Boolean lateStatus){
        this.rental_id = id;
        this.user_id = userID;
        this.bill = Bill;
        this.equipment = equipment;
        this.daily_rental_rate = rate;
    }
    
    public int getId(){
        return this.equipment_id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Category getCategory(){
        return this.category;
    }
    
    public float getRate(){
        return this.daily_rental_rate;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setRate(float rate){
        this.daily_rental_rate = rate;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
}
