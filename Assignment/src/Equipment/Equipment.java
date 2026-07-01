package Equipment;

public class Equipment {
    private final int equipment_id;
    private final String name;
    private final Category category;
    private float daily_rental_rate;
    private String status;
    
    public Equipment(int id, String name, Category category, float rate, String status){
        this.equipment_id = id;
        this.name = name;
        this.status = status;
        this.category = category;
        this.daily_rental_rate = rate;
    }
    
    public int getId(){
        return this.equipment_id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Category getCategory(){
        return this.category;
    }
    
    public float getRate(){
        return this.daily_rental_rate;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setRate(float rate){
        this.daily_rental_rate = rate;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
}
