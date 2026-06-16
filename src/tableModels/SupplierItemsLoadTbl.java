package tableModels;

import dataModels.StockItemDataModel;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SupplierItemsLoadTbl extends AbstractTableModel{

    private static final String[] COLUMN_NAMES={"  ","Item Code","Item Name","Sub Name","Category"};
    private static ArrayList<StockItemDataModel> list;
    
    

    public SupplierItemsLoadTbl(ArrayList<StockItemDataModel> stList) {
        list=stList;
    }
    public int getRowCount() {
        return list.size();
    }
    public String getColumnName(int columnIndex){
        return COLUMN_NAMES[columnIndex];
    }
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rowIndex+1;
            case 1:
                return list.get(rowIndex).getItem_code();
            case 2:
                return list.get(rowIndex).getItem_name();
            case 3:
                return list.get(rowIndex).getSub_name();
            case 4:
                return list.get(rowIndex).getCategory();
            default:
                return "Error";
            
                 
        }
    }
    
}
