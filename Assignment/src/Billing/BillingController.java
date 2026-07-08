package Billing;

import Equipment.Category;
import Rental.Rental;
import User.UserController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingController {
    private Map<Integer, Bill> billMap = new HashMap<>();
    private final BillDB repository;
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
//        flat category penalty when late, not a percentage
        Category category = rental.getEquipment().getCategory();
        return rental.getLateStatus() ? category.getLatePenalty() : 0;
    }
    
    public boolean createRentalBill(Rental rental){
        float base_fee = calculateFee(rental);
        float discount_amount = applyDiscount(rental);
        float penalty_amount = applyPenalty(rental);
        float net_payable = base_fee - discount_amount + penalty_amount;
        
//      find the existing bill for this rental (e.g. the optimistic bill made at rent time)
        for (Map.Entry<Integer, Bill> entry : this.billMap.entrySet()) {
            int id = entry.getKey();
            Bill bill = entry.getValue();

            if (bill.getRentalId() == rental.getId()) {
                if ("Unpaid".equals(bill.getStatus())) {
                    boolean success = repository.update(id, penalty_amount, net_payable);
                    if (success) {
                        bill.setPenaltyAmount(penalty_amount);
                        bill.setNetPayable(net_payable);
                    }
                    return success;
                } else {
                    int newId = repository.create(rental.getId(), base_fee, discount_amount, penalty_amount, net_payable, "Rental Fee", "Unpaid");
                    if(newId != -1){
                        Bill newBill = new Bill(newId, rental.getId(), base_fee, discount_amount, penalty_amount, net_payable, "Rental Fee", "Unpaid");
                        billMap.put(newId, newBill);
                    }
                    return newId != -1;
                }
            }
        }
        
        int id = repository.create(rental.getId(), base_fee, discount_amount, penalty_amount, net_payable, "Rental Fee", "Unpaid");
        if(id != -1){
            Bill newBill = new Bill(id, rental.getId(), base_fee, discount_amount, penalty_amount, net_payable, "Rental Fee", "Unpaid");
            billMap.put(id, newBill);
        }
        return id != -1;
    }
    
    public boolean createDamageBill(Rental rental){
        float damage_penalty = rental.getEquipment().getCategory().getDamagePenalty();
        
        int id = repository.create(rental.getId(), 0, 0, damage_penalty, damage_penalty, "Damage Fee", "Unpaid");
        if(id != -1){
            Bill newBill = new Bill(id, rental.getId(), 0, 0, damage_penalty, damage_penalty, "Damage Fee", "Unpaid");
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