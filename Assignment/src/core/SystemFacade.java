package core;

import Equipment.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SystemFacade {
    private final SystemRepositories repositories;
    private final SystemServices services;
    
    public record EquipmentPanelContext(boolean canEdit, Map<Category, List<Equipment>> equipments) {}
    
    public SystemFacade(SystemRepositories repo, SystemServices services){
        this.repositories = repo;
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
}
