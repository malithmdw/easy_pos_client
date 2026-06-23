
package appDataModels;

import serverDataModels.PurchaseItem;


/**
 *
 * @author MalithWanniarachchi
 */
public class PurchaseItemModel {

    private long recId;
    private long invoiceRecordId;
    private String insertDateTime;
    private long itemId;
    private long batchId;
    private double purchaseUnitPrice;
    private double labelPrice;
    private double discount;
    private double sellingUnitPrice;
    private double wholesalePrice;
    private double qty;
    private String batchNum;
    private String expDate;
    private String addedBy;
    private String addedDate;
    private String updatedBy;
    private String updatedDate;
    
    public PurchaseItemModel(){}
    
    public PurchaseItemModel(PurchaseItemModel other) {
        if (other != null) {
            this.recId = other.recId;
            this.invoiceRecordId = other.invoiceRecordId;
            this.insertDateTime = other.insertDateTime;
            this.itemId = other.itemId;
            this.batchId = other.batchId;
            this.purchaseUnitPrice = other.purchaseUnitPrice;
            this.labelPrice = other.labelPrice;
            this.discount = other.discount;
            this.sellingUnitPrice = other.sellingUnitPrice;
            this.wholesalePrice = other.wholesalePrice;
            this.qty = other.qty;
            this.batchNum = other.batchNum;
            this.expDate = other.expDate;
            this.addedBy = other.addedBy;
            this.addedDate = other.addedDate;
            this.updatedBy = other.updatedBy;
            this.updatedDate = other.updatedDate;
        }
    }
    
    // ✅ DTO → Model
    public PurchaseItemModel(PurchaseItem dto) {
        this.recId = dto.rec_id;
        this.invoiceRecordId = dto.invoice_record_id;
        this.insertDateTime = dto.insert_date_time;
        this.itemId = dto.item_id;
        this.batchId = dto.batch_id;
        this.purchaseUnitPrice = dto.purchase_unit_price;
        this.labelPrice = dto.label_price;
        this.discount = dto.discount;
        this.sellingUnitPrice = dto.selling_unit_price;
        this.wholesalePrice = dto.wholesale_price;
        this.qty = dto.qty;
        this.batchNum = dto.batch_num;
        this.expDate = dto.exp_date;
        this.addedBy = dto.added_by;
        this.addedDate = dto.added_date;
        this.updatedBy = dto.updated_by;
        this.updatedDate = dto.updated_date;
    }

    // ✅ Model → DTO
    public PurchaseItem newPurchaseItemDTO() {
        PurchaseItem dto = new PurchaseItem();
        dto.rec_id = this.getRecId();
        dto.invoice_record_id = this.getInvoiceRecordId();
        dto.insert_date_time = this.getInsertDateTime();
        dto.item_id = this.getItemId();
        dto.batch_id = this.getBatchId();
        dto.purchase_unit_price = this.getPurchaseUnitPrice();
        dto.label_price = this.getLabelPrice();
        dto.discount = this.getDiscount();
        dto.selling_unit_price = this.getSellingUnitPrice();
        dto.wholesale_price = this.getWholesalePrice();
        dto.qty = this.getQty();
        dto.batch_num = this.getBatchNum();
        dto.exp_date = this.getExpDate();
        dto.added_by = this.getAddedBy();
        dto.added_date = this.getAddedDate();
        dto.updated_by = this.getUpdatedBy();
        dto.updated_date = this.getUpdatedDate();
        return dto;
    }

    // ✅ Getters & Setters
    public long getRecId() { return recId; }
    public void setRecId(long recId) { this.recId = recId; }

    public long getInvoiceRecordId() { return invoiceRecordId; }
    public void setInvoiceRecordId(long invoiceRecordId) { this.invoiceRecordId = invoiceRecordId; }

    public String getInsertDateTime() { return insertDateTime; }
    public void setInsertDateTime(String insertDateTime) { this.insertDateTime = insertDateTime; }

    public long getItemId() { return itemId; }
    public void setItemId(long itemId) { this.itemId = itemId; }

    public double getPurchaseUnitPrice() { return purchaseUnitPrice; }
    public void setPurchaseUnitPrice(double purchaseUnitPrice) { this.purchaseUnitPrice = purchaseUnitPrice; }

    public double getSellingUnitPrice() { return sellingUnitPrice; }
    public void setSellingUnitPrice(double sellingUnitPrice) { this.sellingUnitPrice = sellingUnitPrice; }

    public double getQty() { return qty; }
    public void setQty(double qty) { this.qty = qty; }

    public String getBatchNum() { return batchNum; }
    public void setBatchNum(String batchNum) { this.batchNum = batchNum; }

    public String getExpDate() { return expDate; }
    public void setExpDate(String expDate) { this.expDate = expDate; }

    public String getAddedBy() { return addedBy; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }

    public String getAddedDate() { return addedDate; }
    public void setAddedDate(String addedDate) { this.addedDate = addedDate; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }
    
    public long getBatchId() { return batchId; } 
    public void setBatchId(long batch_id) { this.batchId = batch_id; } 
    
    public double getLabelPrice() { return labelPrice; } 
    public void setLabelPrice(double label_price) { this.labelPrice = label_price; } 
    
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getWholesalePrice() { return wholesalePrice; }
    public void setWholesalePrice(double wholesale_price) { this.wholesalePrice = wholesale_price; }
}
