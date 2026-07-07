
package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturnItem {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long return_item_id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long sale_return_record_id;
    public long item_id;
    public double qty;
    public double selling_price;
    public double cost;
}
