
package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author MalithWanniarachchi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleInvoice {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long rec_id;
    public int institute_id; 
    public String invoice_no; 
    public int invoice_type; 
    public String sale_time; 
    public String sale_by; 
    public long customer_id;
    public String cust_contact_no;
    public double gross_amount; 
    public double no_of_items; 
    public double total_discount; 
    public double net_total; 
    public double money_received; 
    public double card_received; 
    public double voucher_received; 
    public double total_received; 
    public double balance_amount; 
    public double total_cost; 
    public String settle_date_time; 
    public String settle_update_by;
    public String cash_ref;
    public String card_ref;
    public String voucher_ref;
        
    public List<SaleItem> sale_items;
}
