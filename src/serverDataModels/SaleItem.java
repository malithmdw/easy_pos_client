
package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleItem {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long rec_id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long invoice_record_id;
    public int batch_id;
    public int item_id;
    public double qty;
    public double selling_price;
    public double discount;
    public double billing_price;
    public double net_amount;
    public double cost_for_unit;
    public double total_cost;
    public int status;
}
