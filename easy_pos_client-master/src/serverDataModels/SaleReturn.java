
package serverDataModels;

import java.util.List;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturn {
    public long rec_id;
    public String request_no; 
    public long sale_invoice_no; 
    public long customer_id;
    public double total_amount;
    public String request_date_time; 
    public String request_by; 
    
    public List<SaleReturnItem> sale_return_items;
}
