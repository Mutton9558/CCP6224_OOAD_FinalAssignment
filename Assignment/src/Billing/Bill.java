//package Billing;
//import java.time.*;
//import Equipment.*;
//
//
//public class Bill {
//    private final int rental_id;
//    private final int user_id;
//    private final Bill bill;
//    private final Equipment equipment;
//    private LocalTime duration;
//    private Boolean lateStatus;
//    
//    public Bill(int id, int userID, Bill bill, Equipment equipment, LocalTime duration, Boolean lateStatus){
//        this.rental_id = id;
//        this.user_id = userID;
//        this.bill = bill;
//        this.equipment = equipment;
//        this.duration = duration;
//        this.lateStatus = lateStatus;
//    }
//    
//    public int getId(){
//        return this.rental_id;
//    }
//    
//    public float getBaseFee(){
//        return this.name;
//    }
//    
//    public Category getCategory(){
//        return this.category;
//    }
//    
//    public float getRate(){
//        return this.daily_rental_rate;
//    }
//    
//    public String getStatus(){
//        return this.status;
//    }
//    
//    public void setRate(float rate){
//        this.daily_rental_rate = rate;
//    }
//    
//    public void setStatus(String status){
//        this.status = status;
//    }
//}
