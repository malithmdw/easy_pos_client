package appDataModels;

/**
 *
 * @author MalithWanniarachchi
 */
public class ItemCardDataModel {
    
    public enum CardType
    {
        MAIN_ITEM,
        STOCK_ITEM
    }
    
    private CardType cardType;
    private ItemModel itemModel;
    private ItemStockModel itemStockModel;
    private String imageName;
    private String itemNameText;
    private String itemPriceText;

    /**
     * @return the cardType
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
    
    /**
     * @return the itemModel
     */
    public ItemModel getItemModel() {
        return itemModel;
    }

    /**
     * @param itemModel the itemModel to set
     */
    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

    /**
     * @return the itemStockModel
     */
    public ItemStockModel getItemStockModel() {
        return itemStockModel;
    }

    /**
     * @param itemStockModel the itemStockModel to set
     */
    public void setItemStockModel(ItemStockModel itemStockModel) {
        this.itemStockModel = itemStockModel;
    }

    /**
     * @return the image
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param image the image to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the itemNameText
     */
    public String getItemNameText() {
        return itemNameText;
    }

    /**
     * @param itemNameText the itemNameText to set
     */
    public void setItemNameText(String itemNameText) {
        this.itemNameText = itemNameText;
    }

    /**
     * @return the itemPriceText
     */
    public String getItemPriceText() {
        return itemPriceText;
    }

    /**
     * @param itemPriceText the itemPriceText to set
     */
    public void setItemPriceText(String itemPriceText) {
        this.itemPriceText = itemPriceText;
    }
    
}
