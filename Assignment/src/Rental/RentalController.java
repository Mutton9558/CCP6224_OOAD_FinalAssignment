package Rental;

import Equipment.EquipmentController;
import Equipment.Equipment;
import java.util.Map;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;


public class RentalController {
    private Map<Integer, Rental> rentalMap = new HashMap<>();
    private Map<Integer, Equipment> equipmentMap;
    private RentalDB repository;
    public static RentalController instance;

    private RentalController(RentalDB repository, EquipmentController equipmentInstance){
        this.equipmentMap = equipmentInstance.fetchMap();
        this.repository = repository;
        this.rentalMap = repository.fetchAllRentals(equipmentMap);
    }

    public static RentalController getInstance(RentalDB repository, EquipmentController equipmentInstance){
        if(instance == null){
            instance = new RentalController(repository, equipmentInstance);
        }
        return instance;
    }

    public Map<Integer, Rental> fetchMap(){
        return this.rentalMap;
    }

    public List<Rental> getRentalsByUserID(int userID){
        List<Rental> temp = new ArrayList<>();
        rentalMap.forEach((id, val) -> {
            if(val.getId() == id && val.getUserId() == userID){
                temp.add(val);
            }
        });
        return temp;
    }

    public List<Rental> getAllRentals(){
       List<Rental> temp = new ArrayList<>();
       rentalMap.forEach((id, val) -> {
           temp.add(val);
       });
       return temp;
   }


    public boolean addRental(int userID, Equipment equipment, int duration){
        int id = repository.create(userID, equipment, duration);
        if(id != -1){
            Rental newRental = new Rental(id, userID, equipment, duration);
            rentalMap.put(id, newRental);
        }
        return id != -1;
    }
    
    public boolean editRental(int id, Boolean return_status, boolean late_status){
        boolean success = repository.update(id, return_status, late_status);
        if(success){
            Rental record = rentalMap.get(id);
            record.setReturnStatus(return_status);
            record.setLateStatus(late_status);
        }
        
        return success; 
    }
}
