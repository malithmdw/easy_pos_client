package tableModels;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverDataModels.Item;


public class LiveStockItemTbl extends AbstractTableModel{
    DecimalFormat df = new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={
        "#", "Item ID", "Barcode", "Item Name", 
        "Item Name-SINHALA", "Item Name-TAMIL", "Category", "Measure Unit"};
    private static List<Item> list;
    
    public LiveStockItemTbl(List<Item> stList){
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
                return list.get(rowIndex).item_id;
            case 2:
                return list.get(rowIndex).barcode;
            case 3:
                return list.get(rowIndex).item_name;
            case 4:
                return list.get(rowIndex).item_name_sin;
            case 5:
                return list.get(rowIndex).item_name_tam;
            case 6:
                return list.get(rowIndex).category.cat_name;
            case 7:
                return (list.get(rowIndex).measureUnit != null) ? list.get(rowIndex).measureUnit.unit_name_eng : "ERROR";
            default:
                return "Error";
            
                 
        }
    }
    
}
