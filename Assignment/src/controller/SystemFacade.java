package controller;
import User.UserController;
//import Bill.BillController;
import Equipment.EquipmentController;
import Rental.RentalController;

//does the common user user cases by managing and controlling all the controllers 
// HOW IT WORKS: 
// UI ONLY CALLS THE FACADE, NEVER USING CONTROLLERS DIRECTLY !!!
public class SystemFacade {
    
    private UserController userController;
//    private BillController billController;
    private EquipmentController equipmentController;
    private RentalController rentalController;
    
    public void SystemFacade(){
        
    }
    
    //GENERAL USER USE CASES
    public void getCurrentUserProfile(){
    
    }
    
    public void searchEquipment(){
        
    }
    
    public void rentEquipment(){
        
    }
    
    public void payBill(){
        
    }
    
    public void getFinanceHistory(){
        
    }
    
    //GENERAL EQUIPMENT MANAGER USE CASES
    public void getCategories(){
        
    }
    
    public void getRentals(){
        
    }
    
    public void registerUser(){
        
    }
    
    public void createUserType(){
        
        
    } 

}
