package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author MalithWanniarachchi
 */
public class PurchaseInvoice {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long rec_id;
    public int institute_id;
    public String system_invoice_no;
    public String invoice_no;
    public String date;
    public String supplier_code;
    public double total;
    public double discount;
    public double charges;
    public double net_total;
    public int set_alert;
    public double paid;
    public double balance;
    public String due_to_pay;
    public String alert_date;
    public String input_method;
    public int status;
    public String edit_by;
    public List<PurchaseItem> purchase_items;

    public PurchaseInvoice() {
        this.purchase_items = new ArrayList<>();
    }
}
