package appDataModels;

import serverDataModels.ItemStock;


/**
 *
 * @author MalithWanniarachchi
 */
public class ItemStockModel {

    private long batchId;
    private long itemId;
    private String purchaseDate;
    private double quantityPurchased;
    private double quantityAvailable;
    private double purchasingPrice;
    private Double purchasingUnitPrice;
    private double labelPrice;
    private double discount;
    private double sellingPrice;
    private double wholeSalePrice;
    private String expiryDate;
    private boolean active;

    // Constructor: Map API DTO -> Application Model
    public ItemStockModel(ItemStock dto) {
        this.batchId = dto.batch_id;
        this.itemId = dto.item_id;
        this.purchaseDate = dto.purchase_date;
        this.quantityPurchased = dto.quantity_purchased;
        this.quantityAvailable = dto.quantity_available;
        this.purchasingPrice = dto.purchasing_price;
        this.purchasingUnitPrice = dto.purchasing_unit_price;
        this.labelPrice = dto.label_price;
        this.discount = dto.discount;
        this.sellingPrice = dto.selling_price;
        this.wholeSalePrice = dto.wholesale_price;
        this.expiryDate = dto.expiry_date;
        this.active = (dto.is_active == 1);
    }

    // Convert Application Model -> API DTO
    public ItemStock newItemStockDTO() {
        ItemStock dto = new ItemStock();
        dto.batch_id = this.getBatchId();
        dto.item_id = this.getItemId();
        dto.purchase_date = this.getPurchaseDate();
        dto.quantity_purchased = this.getQuantityPurchased();
        dto.quantity_available = this.getQuantityAvailable();
        dto.purchasing_price = this.getPurchasingPrice();
        dto.purchasing_unit_price = this.getPurchasingUnitPrice();
        dto.label_price = this.getLabelPrice();
        dto.discount = this.getDiscount();
        dto.selling_price = this.getSellingPrice();
        dto.wholesale_price = this.getWholeSalePrice();
        dto.expiry_date = this.getExpiryDate();
        dto.is_active = this.isActive() ? 1 : 0;
        return dto;
    }

    // Getters & Setters
    public long getBatchId() { return batchId; }
    public void setBatchId(long batchId) { this.batchId = batchId; }

    public long getItemId() { return itemId; }
    public void setItemId(long itemId) { this.itemId = itemId; }

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }

    public double getQuantityPurchased() { return quantityPurchased; }
    public void setQuantityPurchased(double quantityPurchased) { this.quantityPurchased = quantityPurchased; }

    public double getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(double quantityAvailable) { this.quantityAvailable = quantityAvailable; }

    public double getPurchasingPrice() { return purchasingPrice; }
    public void setPurchasingPrice(double purchasingPrice) { this.purchasingPrice = purchasingPrice; }

    public Double getPurchasingUnitPrice() { return purchasingUnitPrice; }
    public void setPurchasingUnitPrice(Double purchasingUnitPrice) { this.purchasingUnitPrice = purchasingUnitPrice; }

    public double getLabelPrice() { return labelPrice; }
    public void setLabelPrice(double labelPrice) { this.labelPrice = labelPrice; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    
    public double getWholeSalePrice() { return wholeSalePrice; }
    public void setWholeSalePrice(double wholeSalePrice) { this.wholeSalePrice = wholeSalePrice; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
