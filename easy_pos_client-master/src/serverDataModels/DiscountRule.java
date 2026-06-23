package serverDataModels;

/**
 *
 * @author MalithWanniarachchi
 */
public class DiscountRule {
    public int rule_id; 
    public int institute_id;
    public String name; 
    public double bill_above; 
    public int only_for_category; 
    public int only_for_mop; 
    public int only_for_loyalty_customers; 
    public double discount_percentage; 
    public double fix_discount; 
    public int status; 
}
