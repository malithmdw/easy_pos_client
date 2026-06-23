package tableModels;

import appDataModels.BarcodeLableItemDataModel;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class BarcodeLabletemTbl extends AbstractTableModel{
    DecimalFormat df = new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={
        "#", "Item ID", "Barcode", "Item Name", 
        "Item Name-SINHALA", "Item Name-TAMIL", "Price", "Sticker Count"};
    private static List<BarcodeLableItemDataModel> list;
    
    public BarcodeLabletemTbl(List<BarcodeLableItemDataModel> stList){
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
                return list.get(rowIndex).getItemId();
            case 2:
                return list.get(rowIndex).getBarcode();
            case 3:
                return list.get(rowIndex).getItemName();
            case 4:
                return list.get(rowIndex).getItemNameSin();
            case 5:
                return list.get(rowIndex).getItemNameTam();
            case 6:
                return util.GeneralUtil.getCurrencyString(list.get(rowIndex).getPrice());
            case 7:
                return list.get(rowIndex).getStickerCount();
            default:
                return "Error";
            
                 
        }
    }
    
}
