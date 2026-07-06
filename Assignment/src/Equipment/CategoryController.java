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
    
    public boolean addCategory(String name, float fee, float discount, float late_penalty, float dmg_penalty){
        
        int id = this.repository.create(name, fee, discount, late_penalty, dmg_penalty);
        
        if(id != -1){
            Category newCategory = new Category(id, name, fee, discount, late_penalty, dmg_penalty);
            categoryMap.put(id, newCategory);
        }
        
        
        return id != -1;
    }
    
    public boolean editCategory(int id, float fee, float discount, float latePenalty, float damagePenalty){
        
        boolean success = this.repository.update(id, fee, discount, latePenalty, damagePenalty);
        
        if(success){
            Category record = categoryMap.get(id);
            record.setFee(fee);
            record.setDiscount(discount);
            record.setLatePenalty(latePenalty);
            record.setDamagePenalty(damagePenalty);
        }
        
        return success;
    }
}
