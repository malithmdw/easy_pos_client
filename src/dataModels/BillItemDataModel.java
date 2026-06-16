
package dataModels;

/**
 *
 * @author malit
 */
public class BillItemDataModel {

    private int itemId;
    private int batchId;
    private String itemName;
    private String itemNameSin;
    private String itemNameTam;
    private double qty;
    private double unitPrice;
    private double discountPerone;
    private double amount;
    private double costOfUnit;
    private double netAmount;

    public BillItemDataModel(BillItemDataModel billItem) {
        itemId = billItem.itemId;
        batchId = billItem.batchId;
        itemName = billItem.itemName;
        qty = billItem.qty;
        unitPrice = billItem.unitPrice;
        discountPerone = billItem.discountPerone;
        amount = billItem.amount;
        netAmount = billItem.netAmount;
    }

    public BillItemDataModel() {
    }
    
    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the batchId
     */
    public int getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }
    
    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the discountPerone
     */
    public double getDiscountPerone() {
        return discountPerone;
    }

    /**
     * @param discountPerone the discountPerone to set
     */
    public void setDiscountPerone(double discountPerone) {
        this.discountPerone = discountPerone;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the netAmount
     */
    public double getNetAmount() {
        return netAmount;
    }

    /**
     * @param netAmount the netAmount to set
     */
    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }
    
    /**
     * @return the itemNameSin
     */
    public String getItemNameSin() {
        return itemNameSin;
    }

    /**
     * @param itemNameSin the itemNameSin to set
     */
    public void setItemNameSin(String itemNameSin) {
        this.itemNameSin = itemNameSin;
    }

    /**
     * @return the itemNameTam
     */
    public String getItemNameTam() {
        return itemNameTam;
    }

    /**
     * @param itemNameTam the itemNameTam to set
     */
    public void setItemNameTam(String itemNameTam) {
        this.itemNameTam = itemNameTam;
    }
    
    /**
     * @return the costOfUnit
     */
    public double getCostOfUnit() {
        return costOfUnit;
    }

    /**
     * @param costOfUnit the costOfUnit to set
     */
    public void setCostOfUnit(double costOfUnit) {
        this.costOfUnit = costOfUnit;
    }

}
