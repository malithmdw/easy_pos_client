
package appDataModels;

import serverDataModels.SaleItem;


/**
 *
 * @author MalithWanniarachchi
 */
public class SaleItemModel {

    private long recId;
    private long invoiceRecordId;
    private int itemId;
    private int batchId;
    private double qty;
    private double sellingPrice;
    private double discount;
    private double billingPrice;
    private double netAmount;
    private double costForUnit;
    private double totalCost;
    private int status;

    // ✅ DTO → Model
    public SaleItemModel(SaleItem dto) {
        this.recId = dto.rec_id;
        this.invoiceRecordId = dto.invoice_record_id;
        this.itemId = dto.item_id;
        this.batchId = dto.batch_id;
        this.qty = dto.qty;
        this.sellingPrice = dto.selling_price;
        this.discount = dto.discount;
        this.billingPrice = dto.billing_price;
        this.netAmount = dto.net_amount;
        this.costForUnit = dto.cost_for_unit;
        this.totalCost = dto.total_cost;
        this.status = dto.status;
    }

    // ✅ Model → DTO
    public SaleItem newSaleItemDTO() {
        SaleItem dto = new SaleItem();
        dto.rec_id = this.getRecId();
        dto.invoice_record_id = this.getInvoiceRecordId();
        dto.item_id = this.getItemId();
        dto.batch_id = this.getBatchId();
        dto.qty = this.getQty();
        dto.selling_price = this.getSellingPrice();
        dto.discount = this.getDiscount();
        dto.billing_price = this.getBillingPrice();
        dto.net_amount = this.getNetAmount();
        dto.cost_for_unit = this.getCostForUnit();
        dto.total_cost = this.getTotalCost();
        dto.status = this.getStatus();
        return dto;
    }

    // ✅ Getters & Setters
    public long getRecId() { return recId; }
    public void setRecId(long recId) { this.recId = recId; }

    public long getInvoiceRecordId() { return invoiceRecordId; }
    public void setInvoiceRecordId(long invoiceRecordId) { this.invoiceRecordId = invoiceRecordId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public double getQty() { return qty; }
    public void setQty(double qty) { this.qty = qty; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getBillingPrice() { return billingPrice; }
    public void setBillingPrice(double billingPrice) { this.billingPrice = billingPrice; }

    public double getNetAmount() { return netAmount; }
    public void setNetAmount(double netAmount) { this.netAmount = netAmount; }

    public double getCostForUnit() { return costForUnit; }
    public void setCostForUnit(double costForUnit) { this.costForUnit = costForUnit; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public int getBatchId() { return batchId; }
    public void setBatchId(int batchId) { this.batchId = batchId; }
}
