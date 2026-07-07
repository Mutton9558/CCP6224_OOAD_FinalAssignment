package Equipment;

public class Category {
    private final int category_id;
    private final String category_name;
    private float maintenance_fee;
    private float late_penalty;
    private float damage_penalty;
    
    public Category(int id, String name, float fee, float late_penalty, float dmg_penalty){
        this.category_id = id;
        this.category_name = name;
        this.maintenance_fee = fee;
        this.late_penalty = late_penalty;
        this.damage_penalty = dmg_penalty;
    }
    
    public int getId(){
        return this.category_id;
    }
    
    public String getName(){
        return this.category_name;
    }
    
    public float getFee(){
        return this.maintenance_fee;
    }
    
    
    public float getLatePenalty(){
        return this.late_penalty;
    }
    
    public float getDamagePenalty(){
        return this.damage_penalty;
    }
    
    public void setFee(float fee){
        this.maintenance_fee = fee;
    }
    
    public void setLatePenalty(float penalty){
        this.late_penalty = penalty;
    }
    
    public void setDamagePenalty(float penalty){
        this.damage_penalty = penalty;
    }
}
