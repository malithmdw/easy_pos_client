
package tableModels;

import dataModels.SaleDataModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class DailySalesLoadTbl extends AbstractTableModel{
    DecimalFormat df=new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"  ","Date","Income","Cost","Profit"};
    private static ArrayList<SaleDataModel> dsList;
    
    public DailySalesLoadTbl(ArrayList<SaleDataModel> dailySaleLst){
        dsList=dailySaleLst;
    }
    @Override
    public int getRowCount() {
        return dsList.size();
    }
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
                return rowIndex+1;
            case 1:
                return dateFormatForUI(dsList.get(rowIndex).getDate());
            case 2:
                return df.format(dsList.get(rowIndex).getIncome());
            case 3:
                return df.format(dsList.get(rowIndex).getCost());
            case 4:
                return df.format(dsList.get(rowIndex).getProfit());
            default:
                return "Error";
            
                 
        }    
    }
    
    String dateFormatForUI(String date){
        String dt=""+date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4)+"";       
        return dt;
    }
}
