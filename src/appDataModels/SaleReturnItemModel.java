
package appDataModels;

import serverDataModels.SaleReturnItem;


/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturnItemModel {
    private long returnItemId;
    private long saleReturnRecordId;
    private long itemId;
    private double qty;
    private double sellingPrice;
    private double cost;

    // ✅ DTO → Model
    public SaleReturnItemModel(SaleReturnItem dto) {
        this.returnItemId = dto.return_item_id;
        this.saleReturnRecordId = dto.sale_return_record_id;
        this.itemId = dto.item_id;
        this.qty = dto.qty;
        this.sellingPrice = dto.selling_price;
        this.cost = dto.cost;
    }

    // ✅ Model → DTO
    public SaleReturnItem newSaleReturnItemDTO() {
        SaleReturnItem dto = new SaleReturnItem();
        dto.return_item_id = this.getReturnItemId();
        dto.sale_return_record_id = this.getSaleReturnRecordId();
        dto.item_id = this.getItemId();
        dto.qty = this.getQty();
        dto.selling_price = this.getSellingPrice();
        dto.cost = this.getCost();
        return dto;
    }

    // ✅ Getters & Setters
    public long getReturnItemId() { return returnItemId; }
    public void setReturnItemId(long returnItemId) { this.returnItemId = returnItemId; }

    public long getSaleReturnRecordId() { return saleReturnRecordId; }
    public void setSaleReturnRecordId(long saleReturnRecordId) { this.saleReturnRecordId = saleReturnRecordId; }

    public long getItemId() { return itemId; }
    public void setItemId(long itemId) { this.itemId = itemId; }

    public double getQty() { return qty; }
    public void setQty(double qty) { this.qty = qty; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}
