package tableModels;

import dataModels.StockItemDataModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class ListOfExp extends AbstractTableModel{
    DecimalFormat df=new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"  ","Item Code","Item Name","Sub Name","Category","Unit Price","Discount","Available Stoke","purchase price","Exp Date","supplier 1","supplier 2","coment"};
    private static ArrayList<StockItemDataModel> list;

    String dateFormatForUI(String date){
        String dt=""+date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4)+"";       
        return dt;
    }
    
    ListOfExp(ArrayList<StockItemDataModel> stList3) {
        list=stList3;
    }
    public int getRowCount() {
        return list.size();
    }
    public String getColumnName(int columnIndex){
        return COLUMN_NAMES[columnIndex];
    }
    public int getColumnCount(){
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
            case 5:
                return df.format(list.get(rowIndex).getSelling_price());
            case 6:
                return df.format(list.get(rowIndex).getDiscount());
            case 7:
                return list.get(rowIndex).getQty();
            case 8:
                return "";//df.format(list.get(rowIndex).getPurchase_price());
            case 9:
                return dateFormatForUI(list.get(rowIndex).getExp_date());
            case 10:
                return list.get(rowIndex).getSupp_code1();
            case 11:
                return list.get(rowIndex).getSupp_code2();
            case 12:
                return list.get(rowIndex).getComment();
            default:
                return "Error";
            
                 
        }
    }
}