package core;

import Equipment.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SystemFacade {
    private final SystemServices services;
    
    public record EquipmentPanelContext(boolean canEdit, Map<Category, List<Equipment>> equipments) {}
    public record AddEquipmentContext(List<String> categoryNames){}
    public record RentedEquipmentContext(List<Equipment> cur, List<Equipment> prev){}
    public record ReturnConfirmationContext(List<Equipment> cur){}
    
    public SystemFacade(SystemServices services){
        this.services = services;
    }
    
    public EquipmentPanelContext getEquipmentPanelData(){
        Map<Category, List<Equipment>> equipmentsByCategoryMap = new HashMap<>();
        services.categoryService().fetchMap().forEach((id, category) -> {
            List<Equipment> tempList = services.equipmentService().getEquipmentsByCategory(id);
            equipmentsByCategoryMap.put(category, tempList);
        });
//        will change the true to getting user perm
        return new EquipmentPanelContext(false, equipmentsByCategoryMap);
    }
    
    public AddEquipmentContext getAddEquipmentContext(){
        List<Category> tempList = services.categoryService().getCategoriesList();
        List<String> tempList2 = new ArrayList<>();
        for(Category c: tempList) {
            tempList2.add(c.getName());
        }
        
        return new AddEquipmentContext(tempList2);
    }
    
    public RentedEquipmentContext getRentedEquipmentContext(){
        
        Map<Integer, Equipment> equipmentMap = services.equipmentService().fetchMap();
        
        List<Equipment> cur = new ArrayList<>();
        equipmentMap.forEach((id, equipment) -> {
            if(equipment.getStatus().equals("Rented Out")){
                cur.add(equipment);
            }
        });
        
        List<Equipment> prev = new ArrayList<>();
        equipmentMap.forEach((id, equipment) -> {
            if(equipment.getStatus().equals("Pending Return Confirmation")){
                prev.add(equipment);
            }
        });
        
        return (new RentedEquipmentContext(cur, prev));
    }
    
//    public ReturnConfirmationContext getReturnConfirmationContext(){
//        Map<Integer, Equipment> equipmentMap = services.equipmentService().fetchMap();
//        List<Equipment> cur = new ArrayList<>();
//        equipmentMap.forEach((id, equipment) -> {
//            if(equipment.getStatus().equals("Rented Out")){
//                cur.add(equipment);
//            }
//        });
//        return (new ReturnConfirmationContext(cur));
//    }
    
    public void addNewEquipment(String name, String categoryName, String rentalRate, String status) {
        // validate empty fields
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment name cannot be empty.");
        }
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Please select a valid category.");
        }

        // validate and convert numeric data types safely
        float parsedRate;
        try {
            parsedRate = Float.parseFloat(rentalRate);
            if (parsedRate < 0) {
                throw new IllegalArgumentException("Rental rate cannot be a negative value.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rental rate must be a valid numeric number (e.g., 10.50).");
        }

        Category category = services.categoryService().getCategoryByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Selected category does not exist in the system.");
        }

        boolean success = services.equipmentService().addEquipment(name, category, parsedRate, status);

        if (!success) {
            throw new RuntimeException("Database insertion operation failed.");
        }
    }
    
    public void editEquipment(int id, String rentalRate, String status){
        float parsedRate;
        try {
            parsedRate = Float.parseFloat(rentalRate);
            if (parsedRate < 0) {
                throw new IllegalArgumentException("Rental rate cannot be a negative value.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rental rate must be a valid numeric number (e.g., 10.50).");
        }
        
        boolean success = services.equipmentService().editEquipment(id, parsedRate, status);
        
        if (!success) {
            throw new RuntimeException("Database insertion operation failed.");
        }
    }
    
    public void addNewCategory(String name, String fee, String late_penalty, String dmg_penalty){
        float parsedFee, parsedLatePenalty, parsedDmgPenalty;
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        
        try {
            parsedFee = Float.parseFloat(fee);
            parsedLatePenalty = Float.parseFloat(late_penalty);
            parsedDmgPenalty = Float.parseFloat(dmg_penalty);
            if (parsedFee < 0 || parsedLatePenalty < 0 || parsedDmgPenalty < 0) {
                throw new IllegalArgumentException("Ensure all values are above 0!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-name sections must be a valid numeric number (e.g., 10.50).");
        }
        boolean success = services.categoryService().addCategory(name, parsedFee, parsedLatePenalty, parsedDmgPenalty);
        
        if (!success) {
            throw new RuntimeException("Database insertion operation failed.");
        }
    }
    
    public void editCategory(int id, String fee, String late_penalty, String dmg_penalty){
        float parsedFee, parsedLatePenalty, parsedDmgPenalty;
        
        try {
            parsedFee = Float.parseFloat(fee);
            parsedLatePenalty = Float.parseFloat(late_penalty);
            parsedDmgPenalty = Float.parseFloat(dmg_penalty);
            if (parsedFee < 0 || parsedLatePenalty < 0 || parsedDmgPenalty < 0) {
                throw new IllegalArgumentException("Ensure all values are above 0!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-name sections must be a valid numeric number (e.g., 10.50).");
        }
        boolean success = services.categoryService().editCategory(id, parsedFee, parsedLatePenalty, parsedLatePenalty);
        
        if (!success) {
            throw new RuntimeException("Database update operation failed.");
        }
    }
    
    public boolean deleteEquipment(int id){
//        temp will change later to also remove all rental and billings related
        boolean success = services.equipmentService().deleteEquipment(id);
        return success;
    }
    
    public boolean deleteCategory(int category_id){
        List<Equipment> temp;
        temp = services.equipmentService().getEquipmentsByCategory(category_id);
        if(temp != null){
            for(Equipment e: temp){
                boolean success = deleteEquipment(e.getId());

                if(success == false){
                    return false;
                }
            }
        }

        boolean success2 = services.categoryService().deleteCategory(category_id);
        return success2;
    }
    
    public boolean returnEquipment(int equipment_id){
        Equipment item = services.equipmentService().getEquipmentById(equipment_id);
        return services.equipmentService().editEquipment(equipment_id, item.getRate(), "Pending Return Confirmation");
    }
    
    public Map<Category, List<Equipment>> searchEquipment(String equipment_id){
        
        Map<Category, List<Equipment>> temp = new HashMap<>();
        if(equipment_id == null || equipment_id.trim().isEmpty()){
            return temp;
        }
        
        try{
            int id = Integer.parseInt(equipment_id);
            Equipment item = services.equipmentService().getEquipmentById(id);
            if(item == null){
                return temp;
            }
            
            List<Equipment> temp2 = new ArrayList<>();
            temp2.add(item);
            temp.put(item.getCategory(), temp2);
            return temp;
        } catch (NumberFormatException e){
            return temp;
        }
    }
}
