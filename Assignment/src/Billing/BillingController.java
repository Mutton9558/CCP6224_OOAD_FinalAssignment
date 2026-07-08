package Billing;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import Rental.Rental;
import Equipment.Category;
import User.UserController;

public class BillingController {
    private Map<Integer, Bill> billMap = new HashMap<>();
    private BillDB repository;
    public static BillingController instance;
    private UserController userController;
    
    private BillingController(BillDB repository, UserController userController){
        this.repository = repository;
        this.billMap = repository.fetchAllBills();
        this.userController = userController;
    }
    
    public static BillingController getInstance(BillDB repository, UserController userController){
        if(instance == null){
            instance = new BillingController(repository, userController);
        }
        
        return instance;
    }
    
    public Map<Integer, Bill> fetchMap(){
        return this.billMap;
    }
    
    public float calculateFee(Rental rental){
        return rental.getEquipment().getRate() * rental.getDuration();
    }
    
    public float applyDiscount(Rental rental){
        float base_fee = calculateFee(rental);
        return base_fee * this.userController.getCurUser().getDiscount();
    }
    
    public float applyPenalty(Rental rental){
//        flat category penalty when late, no per-day overdue count yet
        Category category = rental.getEquipment().getCategory();
        return rental.getLateStatus() ? category.getLatePenalty() : 0;
    }
    
    public boolean createRentalBill(Rental rental){
        float base_fee = calculateFee(rental);
        float discount_amount = applyDiscount(rental);
        float penalty = applyPenalty(rental);
        float net_payable = base_fee - discount_amount;
        float penaltyAmount = net_payable * penalty;
//      find the billing for the current rental
        for (Map.Entry<Integer, Bill> entry : this.billMap.entrySet()) {
            int id = entry.getKey();
            Bill bill = entry.getValue();

            // Find the billing for the current rental
            if (bill.getId() == rental.getId()) {
                if ("Unpaid".equals(bill.getStatus())) {
                    boolean success = repository.update(id, penaltyAmount, net_payable + penaltyAmount);
                    if (success) {
                        bill.setNetPayable(net_payable);
                        bill.setPenaltyAmount(penaltyAmount);
                    }
                    return success; // Stops function and returns status
                } else {
                    Bill newBill = new Bill(id, rental.getId(), 0, 0, penaltyAmount, net_payable + penaltyAmount, "Late Return Fee", "Unpaid");
                    billMap.put(id, newBill);
                    return true; // Stops function immediately
                }
            }
        }
        
        int id = repository.create(rental.getId(), base_fee, discount_amount, penaltyAmount, net_payable + penaltyAmount, "Rental Fee", "Unpaid");
        if(id != -1){
            Bill newBill = new Bill(id, rental.getId(), base_fee, discount_amount, 0, net_payable, "Rental Fee", "Unpaid");
            billMap.put(id, newBill);
        }
        return id != -1;
    }
    
    public boolean createDamageBill(Rental rental){
        float penalty_amount = rental.getEquipment().getCategory().getDamagePenalty();
        float totalPayable = (float) this.billMap.values().stream()
        .filter(bill -> bill.getRentalId() == rental.getId())
        .mapToDouble(bill -> bill.getBaseFee() + bill.getPenalty() - bill.getDiscount())
        .sum();
        
        float netPenalty = totalPayable * penalty_amount;
        
        int id = repository.create(rental.getId(), 0, 0, netPenalty, totalPayable + netPenalty, "Damage Fee", "Unpaid");
        if(id != -1){
            Bill newBill = new Bill(id, rental.getId(), 0, 0, netPenalty, totalPayable + netPenalty, "Damage Fee", "Unpaid");
            billMap.put(id, newBill);
        }
        return id != -1;
    }
    
    public boolean payBill(int id){
        boolean success = repository.updateStatus(id, "Paid");
        if(success){
            Bill record = billMap.get(id);
            record.setStatus("Paid");
        }
        return success;
    }
    
    public List<Bill> getUnpaidBills(){
        List<Bill> temp = new ArrayList<>();
        billMap.forEach((id, val) -> {
            if(val.getStatus().equals("Unpaid")){
                temp.add(val);
            }
        });
        return temp;
    }
    
    public List<Bill> getAllBills(){
        List<Bill> temp = new ArrayList<>();
        billMap.forEach((id, val) -> {
            temp.add(val);
        });
        return temp;
    }
    
    public Bill getBillById(int id){
        return billMap.get(id);
    }
}