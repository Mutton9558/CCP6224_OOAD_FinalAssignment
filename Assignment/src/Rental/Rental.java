package Rental;
import java.time.*;
import Equipment.*;
import Billing.*;


public class Rental {
    private final int rental_id;
    private final int user_id;
    private Bill bill;
    private final Equipment equipment;
    private LocalTime duration;
    private Boolean lateStatus;
    
    public Rental(int id, int userID, Equipment equipment, LocalTime duration, Boolean lateStatus){
        this.rental_id = id;
        this.user_id = userID;
        this.equipment = equipment;
        this.duration = duration;
        this.lateStatus = lateStatus;
    }
    
    public int getId(){
        return this.rental_id;
    }

    public int getUserId(){
        return this.user_id;
    }

    public Bill getBill(){
        return this.bill;
    }

    public Equipment getEquipment(){
        return this.equipment;
    }
    
    public LocalTime getDuration(){
        return this.duration;
    }
    
    public Boolean getStatus(){
        return this.lateStatus;
    }
    
    public void setDuration(LocalTime time){
        this.duration = time;
    }
    
    public void setLateStatus(Boolean status){
        this.lateStatus = status;
    }

    public void setBill(Bill cipher){
        this.bill = cipher;
    }
}
