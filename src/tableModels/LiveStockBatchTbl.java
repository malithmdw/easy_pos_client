package tableModels;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverDataModels.ItemStock;


public class LiveStockBatchTbl extends AbstractTableModel{
    DecimalFormat df = new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={
        "#", "Batch ID", "QTY Available", "Label Price", 
        "Discount per 1", "Retail Price", "Whole Sale Price", "Purchasing Unit Price", "Expiry Date"};
    
    private static List<ItemStock> list;
    
    public LiveStockBatchTbl(List<ItemStock> stList){
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
            case 0:
                return rowIndex + 1;
            case 1:
                return list.get(rowIndex).batch_id;
            case 2:
                return list.get(rowIndex).quantity_available;
            case 3:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).label_price);
            case 4:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).discount);
            case 5:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).selling_price);
            case 6:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).wholesale_price);
            case 7:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).purchasing_price);
            case 8:
                return list.get(rowIndex).expiry_date;
            default:
                return "Error";
            
                 
        }
    }
    
}
