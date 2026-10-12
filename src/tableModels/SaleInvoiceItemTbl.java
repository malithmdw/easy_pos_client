 package tableModels;

import control.ApplicationDataManager;
import dataModels.BillItemDataModel;
import static dataModels.Language.SINHALA;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class SaleInvoiceItemTbl extends AbstractTableModel{
    DecimalFormat df=new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"No","Code","Description","Price","Discount","Qty","Amount"};
    private static List<BillItemDataModel> billItemList;
    
    public SaleInvoiceItemTbl(List<BillItemDataModel> allItmst){
        billItemList=allItmst;
    }
    @Override
    public int getRowCount() {
        return billItemList.size();
    }
    @Override
    public String getColumnName(int columnIndex){
        return COLUMN_NAMES[columnIndex];
    }
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    private String getItemNameBySelectedAppLanguage(BillItemDataModel bidm){
        switch (ApplicationDataManager.getInstance().getApplicationLanguage()) {
            case ENGLISH:
                return bidm.getItemName();
            case SINHALA:
                return bidm.getItemNameSin();
            case TAMIL:
                return bidm.getItemNameTam();
            default:
                return bidm.getItemName();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rowIndex+1;
            case 1:
                return billItemList.get(rowIndex).getItemId();
            case 2:
                return getItemNameBySelectedAppLanguage(billItemList.get(rowIndex));
            case 3:
                return df.format(billItemList.get(rowIndex).getUnitPrice());
            case 4:
                return df.format(billItemList.get(rowIndex).getDiscountPerone());
            case 5:
                return df.format(billItemList.get(rowIndex).getQty());
            case 6:
                return df.format(billItemList.get(rowIndex).getNetAmount());
            default:
                return "Error";
            
                 
        }    
    }
}



    

    
    
    
   
    
