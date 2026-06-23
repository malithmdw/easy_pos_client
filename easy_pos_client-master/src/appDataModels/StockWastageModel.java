
package appDataModels;

import serverDataModels.StockWastage;


/**
 *
 * @author MalithWanniarachchi
 */
public class StockWastageModel {
    private long requestNo;
    private long itemId;
    private long stockId;
    private long supplierId;
    private double purchasingUnitPrice;
    private double sellingUnitPrice;
    private double qty;
    private String addedDate;
    private String addedBy;

    // ✅ DTO → Model
    public StockWastageModel(StockWastage dto) {
        this.requestNo = dto.request_no;
        this.itemId = dto.item_id;
        this.stockId = dto.stock_id;
        this.supplierId = dto.supplier_id;
        this.purchasingUnitPrice = dto.purchasing_unit_price;
        this.sellingUnitPrice = dto.selling_unit_price;
        this.qty = dto.qty;
        this.addedDate = dto.added_date;
        this.addedBy = dto.added_by;
    }

    // ✅ Model → DTO
    public StockWastage newStockWastageDTO() {
        StockWastage dto = new StockWastage();
        dto.request_no = this.getRequestNo();
        dto.item_id = this.getItemId();
        dto.stock_id = this.getStockId();
        dto.supplier_id = this.getSupplierId();
        dto.purchasing_unit_price = this.getPurchasingUnitPrice();
        dto.selling_unit_price = this.getSellingUnitPrice();
        dto.qty = this.getQty();
        dto.added_date = this.getAddedDate();
        dto.added_by = this.getAddedBy();
        return dto;
    }

    // ✅ Getters & Setters
    public long getRequestNo() { return requestNo; }
    public void setRequestNo(long requestNo) { this.requestNo = requestNo; }

    public long getItemId() { return itemId; }
    public void setItemId(long itemId) { this.itemId = itemId; }

    public long getStockId() { return stockId; }
    public void setStockId(long stockId) { this.stockId = stockId; }

    public long getSupplierId() { return supplierId; }
    public void setSupplierId(long supplierId) { this.supplierId = supplierId; }

    public double getPurchasingUnitPrice() { return purchasingUnitPrice; }
    public void setPurchasingUnitPrice(double purchasingUnitPrice) { this.purchasingUnitPrice = purchasingUnitPrice; }

    public double getSellingUnitPrice() { return sellingUnitPrice; }
    public void setSellingUnitPrice(double sellingUnitPrice) { this.sellingUnitPrice = sellingUnitPrice; }

    public double getQty() { return qty; }
    public void setQty(double qty) { this.qty = qty; }

    public String getAddedDate() { return addedDate; }
    public void setAddedDate(String addedDate) { this.addedDate = addedDate; }

    public String getAddedBy() { return addedBy; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }
}
