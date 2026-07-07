package Billing;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import Rental.Rental;
import Equipment.Category;

public class BillingController {
    private Map<Integer, Bill> billMap = new HashMap<>();
    private BillDB repository;
    public static BillingController instance;
    
    private BillingController(BillDB repository){
        this.repository = repository;
        this.billMap = repository.fetchAllBills();
    }
    
    public static BillingController getInstance(BillDB repository){
        if(instance == null){
            instance = new BillingController(repository);
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
        Category category = rental.getEquipment().getCategory();
        return base_fee * category.getDiscount();
    }
    
    public float applyPenalty(Rental rental){
//        flat category penalty when late, no per-day overdue count yet
        Category category = rental.getEquipment().getCategory();
        return rental.getLateStatus() ? category.getLatePenalty() : 0;
    }
    
    public boolean createRentalBill(Rental rental){
        float base_fee = calculateFee(rental);
        float discount_amount = applyDiscount(rental);
        float net_payable = base_fee - discount_amount;
        
        int id = repository.create(rental.getId(), base_fee, discount_amount, 0, net_payable, "Rental Fee", "Unpaid");
        if(id != -1){
            Bill newBill = new Bill(id, rental.getId(), base_fee, discount_amount, 0, net_payable, "Rental Fee", "Unpaid");
            billMap.put(id, newBill);
        }
        return id != -1;
    }
    
    public boolean createDamageBill(Rental rental){
        float penalty_amount = applyPenalty(rental);
        
        int id = repository.create(rental.getId(), 0, 0, penalty_amount, penalty_amount, "Damage Fee", "Unpaid");
        if(id != -1){
            Bill newBill = new Bill(id, rental.getId(), 0, 0, penalty_amount, penalty_amount, "Damage Fee", "Unpaid");
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