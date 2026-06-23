
package tableModels;

import dataModels.SaleDataModel;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class AnnualSalesLoadTbl extends AbstractTableModel{
 
    private static final String[] COLUMN_NAMES={"  ","Year","Income","Cost","Profit","Calculated Date"};
    private static ArrayList<SaleDataModel> annualSalesList;
    
    public AnnualSalesLoadTbl(ArrayList<SaleDataModel> annualList){
        annualSalesList=annualList;
    }
    @Override
    public int getRowCount() {
        return annualSalesList.size();
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
                return annualSalesList.get(rowIndex).getYearName();
            case 2:
                return annualSalesList.get(rowIndex).getIncome();
            case 3:
                return annualSalesList.get(rowIndex).getCost();
            case 4:
                return annualSalesList.get(rowIndex).getProfit();
            case 5:
                return dateFormatForUI(annualSalesList.get(rowIndex).getDate());
            default:
                return "Error";  
        }    
    }
    
    String dateFormatForUI(String date){
        String dt=""+date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4)+"";       
        return dt;
    }
}
