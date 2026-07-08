package ui;
import Equipment.EquipmentPanelUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import User.User;
import User.Permission;
import core.SystemFacade;


public class DashboardPanels{
    
    private final User current_user;
    private final SystemFacade facade;
    private ArrayList<DashboardPanel> panels = new ArrayList<>();
    
    //EQUIP for equipment managers
    public class EquipmentPanel implements DashboardPanel{
        
        private final Equipment.EquipmentPanelUI panelUI;
        public EquipmentPanel(SystemFacade facade){ 
            this.panelUI = new Equipment.EquipmentPanelUI(facade);
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
    
    public class RentedEquipmentPanel implements DashboardPanel{
        
        private final Rental.RentedEquipmentUI panelUI;
        public RentedEquipmentPanel(SystemFacade facade){
            this.panelUI = new Rental.RentedEquipmentUI(facade);
        }
        
        @Override
        public String getName(){
            return "Rented Equipment";
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
    
    // FOR GENERAL USERS
    //My Rentals CANNOT PASS CONTROLLER, PLS PASS FACADE!!!!!!!!!!!!
//    public class RentalPanel implements DashboardPanel{
//        
//        private final Rental.RentalRecordsUI panelUI;
//        public RentalPanel(SystemFacade facade){
//            this.panelUI = new Rental.RentalRecordsUI(facade);
//        }
//        
//        @Override
//        public String getName(){
//            return "My Rentals";
//        }
//        
//        @Override
//        public boolean isVisible(){
//            return current_user.hasPermission(Permission.RENT);
//        }
//        
//        @Override
//        public JPanel getPanel(){
//            return panelUI;
//        }
//    }
    
    public class BillingPanel implements DashboardPanel{
        
        private final Billing.BillPaymentUI panelUI;
        public BillingPanel(SystemFacade facade){
            this.panelUI = new Billing.BillPaymentUI(facade);
        }
        
        @Override
        public String getName(){
            return "Make Payment";
        }
        
        @Override
        public boolean isVisible(){
            return current_user.hasPermission(Permission.MAKE_PAYMENT);
        }
        
        @Override
        public JPanel getPanel(){
            return panelUI;
        }
    }
    
    
    
    public DashboardPanels(core.SystemFacade facade, User current_user){
        
        this.facade = facade;
        this.current_user = current_user;
        this.panels.add(new EquipmentPanel(facade));
        this.panels.add(new RentedEquipmentPanel(facade));

    }
    
    public ArrayList<DashboardPanel> returnDashboardPanels(){
        return this.panels;
    }   
}