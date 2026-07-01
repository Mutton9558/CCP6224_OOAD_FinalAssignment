package Equipment;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class EquipmentController {
    private Map<Integer, Equipment> equipmentMap = new HashMap<>();
    private Map<Integer, Category> categoryMap = new HashMap<>();
    public static EquipmentController instance;
    
    private EquipmentController(){}
    
    public static EquipmentController getInstance(){
        if(instance == null){
            instance = new EquipmentController();
        }
        
        return instance;
    }
    
//    temp for now until we connect the db
    public List<Equipment> getAllEquipments(){
        List<Equipment> temp = new ArrayList<>();
        return temp;
    }
    
    public List<Category> getAllCategories(){
        List<Category> temp = new ArrayList<>();
        return temp;
    }
    
    public boolean addEquipment(int id, String name, Category category, float rate, String status){
        
//        db stuff
        
        Equipment newEquipment = new Equipment(id, name, category, rate, status);
        equipmentMap.put(id, newEquipment);
        return true;
    }
    
    public boolean editEquipment(int id, float new_rate, String new_status){
//        db stuff

        Equipment record = equipmentMap.get(id);
        record.setRate(new_rate);
        record.setStatus(new_status);
        return true;
    }
    
    public boolean addCategory(int id, String name, float fee, float discount, float late_penalty, float dmg_penalty){
        
        Category newCategory = new Category(id, name, fee, discount, late_penalty, dmg_penalty);
        categoryMap.put(id, newCategory);
        return true;
    }
    
    public boolean editCategory(int id, float fee, float discount, float latePenalty, float damagePenalty){
        Category record = categoryMap.get(id);
        record.setFee(fee);
        record.setDiscount(discount);
        record.setLatePenalty(latePenalty);
        record.setDamagePenalty(damagePenalty);
        return true;
    }
}
