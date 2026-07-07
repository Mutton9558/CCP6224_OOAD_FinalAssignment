package Equipment;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EquipmentController {
    private Map<Integer, Equipment> equipmentMap = new HashMap<>();
    private Map<Integer, Category> categoryMap;
    private EquipmentDB repository;
    public static EquipmentController instance;
    
    private EquipmentController(EquipmentDB repository, CategoryController categoryInstance){
        this.categoryMap = categoryInstance.fetchMap();
        this.repository = repository;
        this.equipmentMap = repository.fetchAllEquipments(categoryMap);
    }
    
    public static EquipmentController getInstance(EquipmentDB repository, CategoryController categoryInstance){
        if(instance == null){
            instance = new EquipmentController(repository, categoryInstance);
        }
        
        return instance;
    }

    public Map<Integer, Equipment> fetchMap(){
        return this.equipmentMap;
    }
    
//    public List<Equipment> getAllEquipments(){
//        List<Equipment> temp = new ArrayList<>();
//        equipmentMap.forEach((id, val) -> {
//            temp.add(val);
//        });
//        return temp;
//    }
    
    public List<Equipment> getEquipmentsByCategory(int category_id){
        List<Equipment> temp = new ArrayList<>();
        equipmentMap.forEach((id, val) -> {
            if(val.getCategory().getId() == category_id){
                temp.add(val);
            }
        });
        return temp;
    }
    
    public boolean addEquipment(String name, Category category, float rate, String status){
        int id = repository.create(name, category, rate, status);
        if(id != -1){
            Equipment newEquipment = new Equipment(id, name, category, rate, status);
            equipmentMap.put(id, newEquipment);
        }
        return id != -1;
    }
    
    public boolean editEquipment(int id, float new_rate, String new_status){
        boolean success = repository.update(id, new_rate, new_status);
        if(success){
            Equipment record = equipmentMap.get(id);
            record.setRate(new_rate);
            record.setStatus(new_status);
        }
        
        return success;  
    }
    
    public boolean deleteEquipment(int id){
        boolean success = repository.delete(id);
        if(success){
            this.equipmentMap.remove(id);
        } 
        return success;
    }
    
    public List<Equipment> getRentedEquipments(){
        List<Equipment> rentedEquipments = new ArrayList<>();
        this.equipmentMap.forEach((id, equipment) -> {
            if(equipment.getStatus().equals("Rented Out")){
                rentedEquipments.add(equipment);
            }
        });
        return rentedEquipments;
    }
    
    public List<Equipment> getPendingEquipments(){
        List<Equipment> pendingEquipments = new ArrayList<>();
        this.equipmentMap.forEach((id, equipment) -> {
            if(equipment.getStatus().equals("Pending Return Confirmation")){
                pendingEquipments.add(equipment);
            }
        });
        
        return pendingEquipments;
    }
}
