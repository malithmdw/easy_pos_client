
package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author MalithWanniarachchi
 */
public class PurchaseItem {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long rec_id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long invoice_record_id;
    public String insert_date_time;
    public long item_id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long batch_id;
    public double purchase_unit_price;
    public double label_price;
    public double discount;
    public double selling_unit_price;
    public double wholesale_price;
    public double qty;
    public String batch_num;
    public String exp_date;
    public String added_by;
    public String added_date;
    public String updated_by;
    public String updated_date;
}
