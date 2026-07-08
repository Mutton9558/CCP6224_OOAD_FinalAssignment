package ui;
import Equipment.EquipmentPanelUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import User.User;
import User.Permission;


public class DashboardPanels{
    
    User current_user;
    
    private ArrayList<DashboardPanel> panels = new ArrayList<>();
    
    public class EquipmentPanelUI implements DashboardPanel{
        
        private core.SystemFacade facade;
        private final EquipmentPanelUI panelUI;
        
        public EquipmentPanelUI(){ 
            this.panelUI = new EquipmentPanelUI(true, facade);
        }
        
        @Override
        public String getName(){
            return "Equipment Management";
        }
        
        @Override
        public boolean isVisible(){
            return current_user.hasPermission(Permission.VIEW_EQUIPMENT);
        }
        
        @Override
        public JPanel getPanel(){
            return panelUI;
        }
        
        
        
    }
    
    public DashboardPanels(){
        
        
    }
    
    
    
    
    
}