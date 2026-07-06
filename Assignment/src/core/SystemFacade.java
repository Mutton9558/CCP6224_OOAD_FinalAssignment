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
        return new EquipmentPanelContext(true, equipmentsByCategoryMap);
    }
    
    public AddEquipmentContext getAddEquipmentContext(){
        List<Category> tempList = services.categoryService().getCategoriesList();
        List<String> tempList2 = new ArrayList<>();
        for(Category c: tempList) {
            tempList2.add(c.getName());
        }
        
        return new AddEquipmentContext(tempList2);
    }
    
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
    
    public boolean addNewCategory(String name, float fee, float discount, float late_penalty, float dmg_penalty){
        boolean success = services.categoryService().addCategory(name, fee, discount, late_penalty, dmg_penalty);
        return success;
    }
    
    public boolean deleteEquipment(int id){
//        temp will change later to also remove all rental and billings related
        boolean success = services.equipmentService().deleteEquipment(id);
        return success;
    }
    
    public boolean deleteCategory(int category_id){
        List<Equipment> temp;
        temp = services.equipmentService().getEquipmentsByCategory(category_id);
        for(Equipment e: temp){
            boolean success = deleteEquipment(e.getId());
            
            if(success == false){
                return false;
            }
        }
        
        boolean success2 = services.categoryService().deleteCategory(category_id);
        return success2;
    }
}
