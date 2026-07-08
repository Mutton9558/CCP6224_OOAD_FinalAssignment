package core;
import User.*;
import Equipment.*;
import Rental.*;
import Billing.*;
import ui.MainUI;
import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    
    public Main(){

        try(Connection conn = DatabaseManager.getConnection()){
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Database initialization failed" + e.getMessage());
            System.exit(1);
        }
        //initialize DB
        UserDB userRepo = new UserDB();
        CategoryDB categoryRepo = new CategoryDB();
        EquipmentDB equipmentRepo = new EquipmentDB();
        RentalDB rentalRepo = new RentalDB();
        BillDB billingRepo = new BillDB();
        
        UserController userService = UserController.getInstance(userRepo);
        CategoryController categoryService = CategoryController.getInstance(categoryRepo);
        EquipmentController equipmentService = EquipmentController.getInstance(equipmentRepo, categoryService);
        RentalController rentalController = RentalController.getInstance(rentalRepo, equipmentService);
        BillingController billingService = BillingController.getInstance(billingRepo);
        
        core.SystemServices services = new core.SystemServices(userService, categoryService, equipmentService, billingService);
        core.SystemFacade facade = new core.SystemFacade(services);
       //Create new MainUI
        MainUI mainScreen = new MainUI(facade);
    }
    
    public static void main(String[] args){
        //starts program
        SwingUtilities.invokeLater(Main::new);
    }
}