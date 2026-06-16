package serverDataModels;

/**
 *
 * @author MalithWanniarachchi
 */
public class ItemStock {
    public long batch_id; 
    public long item_id; 
    public String purchase_date; 
    public double quantity_purchased; 
    public double quantity_available; 
    public double purchasing_price; 
    public double label_price; 
    public double discount; 
    public double selling_price; 
    public double wholesale_price; 
    public String expiry_date; 
    public int is_active; 
}
