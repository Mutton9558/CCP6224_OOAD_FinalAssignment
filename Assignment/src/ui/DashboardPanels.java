package ui;
import Equipment.EquipmentPanelUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import User.User;
import User.Permission;


public class DashboardPanels{
    
    private final User current_user;
    private final core.SystemFacade facade;
    
    private ArrayList<DashboardPanel> panels = new ArrayList<>();
    
    public class EquipmentPanel implements DashboardPanel{
        
        private final Equipment.EquipmentPanelUI panelUI;
        
        public EquipmentPanel(core.SystemFacade facade){ 
            this.panelUI = new Equipment.EquipmentPanelUI(true, facade);
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
    
    public DashboardPanels(core.SystemFacade facade, User current_user){
        
        this.facade = facade;
        this.current_user = current_user;
        this.panels(new EquipmentPanel(facade));
        
    }
    
    public ArrayList<DashboardPanel> returnDashboardPanels(){
        return this.panels;
    }   
}