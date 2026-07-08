package Billing;

public class Bill {
    private final int bill_id;
    private final int rental_id;
    private final float base_fee;
    private final float discount_amount;
    private float penalty_amount;
    private float net_payable;
    private final String bill_type;
    private String bill_status;
    
    public Bill(int id, int rental_id, float base_fee, float discount_amount, float penalty_amount, float net_payable, String bill_type, String bill_status){
        this.bill_id = id;
        this.rental_id = rental_id;
        this.base_fee = base_fee;
        this.discount_amount = discount_amount;
        this.penalty_amount = penalty_amount;
        this.net_payable = net_payable;
        this.bill_type = bill_type;
        this.bill_status = bill_status;
    }
    
    public int getId(){
        return this.bill_id;
    }
    
    public int getRentalId(){
        return this.rental_id;
    }
    
    public float getBaseFee(){
        return this.base_fee;
    }
    
    public float getDiscount(){
        return this.discount_amount;
    }
    
    public float getPenalty(){
        return this.penalty_amount;
    }
    
    public float getNetPayable(){
        return this.net_payable;
    }
    
    public String getBillType(){
        return this.bill_type;
    }
    
    public String getStatus(){
        return this.bill_status;
    }
    
    public void setPenaltyAmount(float amt){
        this.net_payable = amt;
    }
    
    public void setNetPayable(float amt){
        this.penalty_amount = amt;
    }
    
    public void setStatus(String status){
        this.bill_status = status;
    }
}
