package Rental;
import java.time.*;
import Equipment.*;
import Billing.*;


public class Rental {
    private final int rental_id;
    private final int user_id;
    private Bill bill;
    private final Equipment equipment;
    private LocalDate bookedDate;
    private LocalDate dueDate;
    private int duration;
    private Boolean returnStatus;
    private Boolean lateStatus;
    
    public Rental(int id, int userID, Equipment equipment, int duration, Boolean lateStatus){
        this.rental_id = id;
        this.user_id = userID;
        this.equipment = equipment;
        this.bookedDate = LocalDate.now();
        this.dueDate = bookedDate.plusDays(duration);
        this.duration = duration;
        this.returnStatus = false;
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
    
    public int getDuration(){
        return this.duration;
    }
    
    public Boolean getReturnStatus(){
        return this.returnStatus;
    }

    public Boolean getLateStatus(){
        return this.lateStatus;
    }

    public LocalDate getBookedDate(){
        return this.bookedDate;
    }

    public LocalDate getDueDate(){
        return this.dueDate;
    }
    
    public void setDuration(int time){
        this.duration = time;
    }
    
    public void setLateStatus(Boolean status){
        this.lateStatus = status;
    }

    public void setBill(Bill cipher){
        this.bill = cipher;
    }
}
