package tableModels;

import dataModels.StockItemDataModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class StockLoadTbl extends AbstractTableModel{
    DecimalFormat df = new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"Item Code", "Barcode","Item Name","Sub Name", "Item Name-SIN", "Item Name-TAM",
        "Category", "Measure Unit", "Unit Price", "Discount", "Wholesale Price", "Wholesale Min Qty",
        "Available Stock"};
    private static List<StockItemDataModel> list;
    
    public StockLoadTbl(List<StockItemDataModel> stList){
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
                return list.get(rowIndex).getItem_code();
            case 1:
                return list.get(rowIndex).getBarcode();
            case 2:
                return list.get(rowIndex).getItem_name();
            case 3:
                return list.get(rowIndex).getSub_name();
            case 4:
                return list.get(rowIndex).getItem_name_sinhala();
            case 5:
                return list.get(rowIndex).getItem_name_tamil();
            case 6:
                return list.get(rowIndex).getCategory().getCategoryName();
            case 7:
                return list.get(rowIndex).getMeasureUnit().getUnitNameEng();
            case 8:
                return df.format(list.get(rowIndex).getSelling_price());
            case 9:
                return df.format(list.get(rowIndex).getDiscount());
            case 10:
                return df.format(list.get(rowIndex).getWholeSalePrice());
            case 11:
                return list.get(rowIndex).getWholeSaleMinQty();
            case 12:
                return list.get(rowIndex).getQty();
            default:
                return "Error";
            
                 
        }
    }
    
}
