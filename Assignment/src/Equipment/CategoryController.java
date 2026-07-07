package Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryController {
    private Map<Integer, Category> categoryMap = new HashMap<>();
    private static CategoryController instance;
    private CategoryDB repository;
    
    private CategoryController(CategoryDB repository){
        this.repository = repository;
        this.categoryMap = repository.fetchAllCategories();
    }
    
    public static CategoryController getInstance(CategoryDB repository){
        if(instance == null){
            instance = new CategoryController(repository);
        }
        
        return instance;
    }
    
    
    public Map<Integer, Category> fetchMap(){
        return this.categoryMap;
    }
    
    public List<Category> getCategoriesList(){
        List<Category> temp = new ArrayList<>();
        this.categoryMap.forEach((id, category) -> {
            temp.add(category);
        });
        return temp;
    }
    
    public boolean addCategory(String name, float fee, float late_penalty, float dmg_penalty){
        
        int id = this.repository.create(name, fee, late_penalty, dmg_penalty);
        
        if(id != -1){
            Category newCategory = new Category(id, name, fee, late_penalty, dmg_penalty);
            categoryMap.put(id, newCategory);
        }
        
        
        return id != -1;
    }
    
    public boolean editCategory(int id, float fee, float latePenalty, float damagePenalty){
        
        boolean success = this.repository.update(id, fee, latePenalty, damagePenalty);
        
        if(success){
            Category record = categoryMap.get(id);
            record.setFee(fee);
            record.setLatePenalty(latePenalty);
            record.setDamagePenalty(damagePenalty);
        }
        
        return success;
    }
    
    public boolean deleteCategory(int id){
        boolean success = repository.delete(id);
        if(success){
            this.categoryMap.remove(id);
        } 
        return success;
    }  
    
    public Category getCategoryByName(String name) {
        return this.categoryMap.values().stream()
            .filter(category -> category.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
}
