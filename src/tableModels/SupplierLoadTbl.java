package tableModels;

import dataModels.SupplierDataModel;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class SupplierLoadTbl extends AbstractTableModel{
    
    private static final String[] COLUMN_NAMES={"  ","Supplier Code","Supplier Name","Supplier Address","Company","Phone No","NIC NO","Account No","Bank","Phone No"};
    private static ArrayList<SupplierDataModel> supplierlist;
    
    public SupplierLoadTbl(ArrayList<SupplierDataModel> supprList){
        supplierlist=supprList;
    }
    @Override
    public int getRowCount() {
        return supplierlist.size();
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
                return supplierlist.get(rowIndex).getSupplierCode();
            case 2:
                return supplierlist.get(rowIndex).getSupplierName();
            case 3:
                return supplierlist.get(rowIndex).getSupplierAddress();
            case 4:
                return supplierlist.get(rowIndex).getCompany();
            case 5:
                return supplierlist.get(rowIndex).getPhoneNo1();
            case 6:
                return supplierlist.get(rowIndex).getNIC();
            case 7:
                return supplierlist.get(rowIndex).getAccNo();
            case 8:
                return supplierlist.get(rowIndex).getBank();
            case 9:
                return supplierlist.get(rowIndex).getPhoneNo();
            default:
                return "Error";
            
                 
        }    
    }
    
    
    
   
    
}
