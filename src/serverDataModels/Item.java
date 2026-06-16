package serverDataModels;

import java.util.List;

/**
 *
 * @author MalithWanniarachchi
 */
public class Item {
    public int item_id; 
    public int institute_id; 
    public String barcode; 
    public String item_name; 
    public String item_name_sin; 
    public String item_name_tam; 
    public int category_id; 
    public int measure_unit_id; 
    public String image_name; 
    public double minimum_stock; 
    public String added_by; 
    public String added_date_time; 
    public String modified_by; 
    public String modified_date_time; 
    public int is_active;
    
    public Category category;
    public MeasureUnit measureUnit;
    public List<ItemStock> stock;
}
