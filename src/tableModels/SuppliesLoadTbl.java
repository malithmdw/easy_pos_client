
package tableModels;

import dataModels.SupplyVariables;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SuppliesLoadTbl extends AbstractTableModel{
    DecimalFormat df=new DecimalFormat("#0.00");
    String dateFormatForUI(String date){
        String dt=""+date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4)+"";       
        return dt;
    }
    private static final String[] COLUMN_NAMES={"  ","Date","Supplier Code","Invoice No","Total","Discount","Net Total","Paid","Balance","Due to Pay(Date)","Alert date for Pay","Input method","Edit by"};
    private static ArrayList<SupplyVariables> list;
    
    public SuppliesLoadTbl(ArrayList<SupplyVariables> supplyV){
        list=supplyV;
    }
    @Override
    public int getRowCount() {
        return list.size();
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
                return dateFormatForUI(list.get(rowIndex).getDate());
            case 2:
                return list.get(rowIndex).getSupplier_code();
            case 3:
                return list.get(rowIndex).getInvoice_no();
            case 4:
                return df.format(list.get(rowIndex).getTotal());
            case 5:
                return df.format(list.get(rowIndex).getDiscount());
            case 6:
                return df.format(list.get(rowIndex).getNet_total());
            case 7:
                return df.format(list.get(rowIndex).getPaid());
            case 8:
                return df.format(list.get(rowIndex).getBalance());
            case 9:
                return list.get(rowIndex).getDue_to_pay();
            case 10:
                return list.get(rowIndex).getAlert_date();
            case 11:
                return list.get(rowIndex).getInputMethod();
            case 12:
                return list.get(rowIndex).getEditBy();
            default:
                return "Error";
            
                 
        }
    }
    
}