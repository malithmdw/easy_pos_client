
package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturn {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long rec_id;
    public String request_no;
    public String sale_invoice_no;
    public long customer_id;
    public double total_amount;
    public String request_date_time;
    public String request_by;
    public String customer_name;
    public String customer_number;
    public String expire_date;

    public List<SaleReturnItem> return_items;
}
