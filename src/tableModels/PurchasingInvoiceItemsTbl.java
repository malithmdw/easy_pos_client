package tableModels;

import appDataModels.PurchasingInvoiceItemUIDataModel;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PurchasingInvoiceItemsTbl extends AbstractTableModel{
        
    private static final String[] COLUMN_NAMES={"Item Id","Item Name","Purchasing Price","Selling Price","Qty","Batch Num","Expire Date", "Added Date", "Added By"};
    private static List<PurchasingInvoiceItemUIDataModel> list;
    
    public PurchasingInvoiceItemsTbl(List<PurchasingInvoiceItemUIDataModel> stList) {
        list=stList;
    }
    @Override
    public int getRowCount() {
        return list.size();
    }
    @Override
    public String getColumnName(int columnIndex){
        return COLUMN_NAMES[columnIndex];
    }
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            //"Batch Num","Expire Date", "Added Date", "Added By"};
            case 0:
                return list.get(rowIndex).getPurchaseItemModel().getItemId();
            case 1:
                return (list.get(rowIndex).getItemModel() == null)?"NOT FOUND" : (list.get(rowIndex).getItemModel().getItemName());
            case 2:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).getPurchaseItemModel().getPurchaseUnitPrice());
            case 3:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).getPurchaseItemModel().getSellingUnitPrice());
            case 4:
                return list.get(rowIndex).getPurchaseItemModel().getQty();
            case 5:
                return list.get(rowIndex).getPurchaseItemModel().getBatchNum();
            case 6:
                return list.get(rowIndex).getPurchaseItemModel().getExpDate();
            case 7:
                return list.get(rowIndex).getPurchaseItemModel().getAddedDate();
            case 8:
                return list.get(rowIndex).getPurchaseItemModel().getAddedBy();
            default:
                return "Error";
            
                 
        }
    }
}
