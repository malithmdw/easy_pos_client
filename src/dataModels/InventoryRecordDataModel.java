
package dataModels;

/**
 *
 * @author malit
 */
public class InventoryRecordDataModel {
    
    private int recordId;
    private String insertDateTime;
    private int itemcode;
    private int invoiceId;
    private double purchaseUnitPrice;
    private double sellingUnitPrice;
    private double qty;
    private String batchNum;
    private String expDate;
    private double remainingStock;
    private String addedBy;
    private String addedDate;
    private String updatedBy;
    private String updatedDate;
    
    /**
     * @return the recordId
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the insertDateTime
     */
    public String getInsertDateTime() {
        return insertDateTime;
    }

    /**
     * @param insertDateTime the insertDateTime to set
     */
    public void setInsertDateTime(String insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    /**
     * @return the itemcode
     */
    public int getItemcode() {
        return itemcode;
    }

    /**
     * @param itemcode the itemcode to set
     */
    public void setItemcode(int itemcode) {
        this.itemcode = itemcode;
    }

    /**
     * @return the invoiceId
     */
    public int getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the purchaseUnitPrice
     */
    public double getPurchaseUnitPrice() {
        return purchaseUnitPrice;
    }

    /**
     * @param purchaseUnitPrice the purchaseUnitPrice to set
     */
    public void setPurchaseUnitPrice(double purchaseUnitPrice) {
        this.purchaseUnitPrice = purchaseUnitPrice;
    }

    /**
     * @return the sellingUnitPrice
     */
    public double getSellingUnitPrice() {
        return sellingUnitPrice;
    }

    /**
     * @param sellingUnitPrice the sellingUnitPrice to set
     */
    public void setSellingUnitPrice(double sellingUnitPrice) {
        this.sellingUnitPrice = sellingUnitPrice;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the batchNum
     */
    public String getBatchNum() {
        return batchNum;
    }

    /**
     * @param batchNum the batchNum to set
     */
    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    /**
     * @return the expDate
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * @param expDate the expDate to set
     */
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    /**
     * @return the remainingStock
     */
    public double getRemainingStock() {
        return remainingStock;
    }

    /**
     * @param remainingStock the remainingStock to set
     */
    public void setRemainingStock(double remainingStock) {
        this.remainingStock = remainingStock;
    }

    /**
     * @return the addedBy
     */
    public String getAddedBy() {
        return addedBy;
    }

    /**
     * @param addedBy the addedBy to set
     */
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     * @return the addedDate
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     * @param addedDate the addedDate to set
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
