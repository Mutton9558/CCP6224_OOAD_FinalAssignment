package Rental;

import Billing.Bill;
import Equipment.Equipment;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;


public class RentalController {
    private Map<Integer, Rental> rentalMap = new HashMap<>();
    private Map<Integer, Bill> billMap = new HashMap<>();
    public static RentalController instance;

    private RentalController(){}

    public static RentalController getInstance(){
        if(instance == null){
            instance = new RentalController();
        }
        return instance;
    }

    //    temp for now until we connect the db
    public List<Rental> getAllEquipments(){
        List<Rental> temp = new ArrayList<>();
        return temp;
    }
    
    public List<Bill> getAllCategories(){
        List<Bill> temp = new ArrayList<>();
        return temp;
    }

    public boolean checkDiscountLegibility(int rental_id){
        Rental rental = rentalMap.get(rental_id);
        User user = rental.getUser

        else

        return validDiscount;
    }

    public boolean addRental(int id, int userID, Equipment equipment, LocalTime duration, Boolean lateStatus){
        
//        db stuff
        
        Rental newRental = new Rental(id, userID, equipment, duration, lateStatus);
        rentalMap.put(id, newRental);
        return true;
    }
    
    public boolean editRental(int id, LocalTime time, Boolean lateStatus){
//        db stuff

        Rental record = rentalMap.get(id);
        record.setDuration(time);
        record.setLateStatus(lateStatus);
        return true;
    }
    

    //Will edit once Bill is created
    public boolean addBill(int id, String name, float fee, float discount, float late_penalty, float dmg_penalty){
        
        Bill newBill = new Bill(id, name, fee, discount, late_penalty, dmg_penalty);
        BillMap.put(id, newBill);
        return true;
    }
    
    public boolean editBill(int id, float fee, float discount, float latePenalty, float damagePenalty){
        Bill record = billMap.get(id);
        record.setFee(fee);
        record.setDiscount(discount);
        record.setLatePenalty(latePenalty);
        record.setDamagePenalty(damagePenalty);
        return true;
    }

}
