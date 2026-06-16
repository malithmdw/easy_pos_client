
package appDataModels;

/**
 *
 * @author MalithWanniarachchi
 */
public class PurchasingInvoiceItemUIDataModel {

    private PurchaseItemModel purchaseItemModel;
    private ItemModel itemModel;

    public PurchasingInvoiceItemUIDataModel(ItemModel itemModel, PurchaseItemModel purchaseItemModel) {
        this.purchaseItemModel = purchaseItemModel;
        this.itemModel = itemModel;
    }
    
    /**
     * @return the purchaseItemModel
     */
    public PurchaseItemModel getPurchaseItemModel() {
        return purchaseItemModel;
    }

    /**
     * @param purchaseItemModel the purchaseItemModel to set
     */
    public void setPurchaseItemModel(PurchaseItemModel purchaseItemModel) {
        this.purchaseItemModel = purchaseItemModel;
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
}
