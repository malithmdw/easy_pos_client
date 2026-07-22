package serverDataModels;

/**
 * Flat per-batch row returned by get-stock-needto-restock.php — unlike the
 * other item endpoints, batch fields are inlined here rather than nested
 * under a "stock" list, and total_available_qty/shortage_qty are
 * precomputed server-side per item (repeated across that item's batch rows).
 *
 * @author MalithWanniarachchi
 */
public class RestockNeededRow {
    public int item_id;
    public int institute_id;
    public String barcode;
    public String item_name;
    public String item_name_sin;
    public String item_name_tam;
    public double minimum_stock;
    public long batch_id;
    public String purchase_date;
    public double quantity_available;
    public double label_price;
    public double discount;
    public double selling_price;
    public double wholesale_price;
    public String expiry_date;
    public double total_available_qty;
    public double shortage_qty;

    // Not part of the API response - resolved locally by item_id since this
    // endpoint doesn't return category_id.
    public Category category;
}
