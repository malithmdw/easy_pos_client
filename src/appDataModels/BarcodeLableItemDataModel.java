
package appDataModels;

/**
 *
 * @author malit
 */
public class BarcodeLableItemDataModel {

    private String barcode;
    private int itemId;
    private String itemName;
    private String itemNameSin;
    private String itemNameTam;
    private double price;
    private int stickerCount;
    
    /**
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stickerCount
     */
    public int getStickerCount() {
        return stickerCount;
    }

    /**
     * @param stickerCount the stickerCount to set
     */
    public void setStickerCount(int stickerCount) {
        this.stickerCount = stickerCount;
    }
}
