package tableModels;

import appDataModels.PurchaseInvoiceModel;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PendingInvoicesTbl extends AbstractTableModel{
    DecimalFormat df = new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"Date Time","Syatem Invoice ID","Invoice Number","Supplier","Total","Discount","Paid", "Balance", "Added By"};
    private static List<PurchaseInvoiceModel> list;
    
    public PendingInvoicesTbl(List<PurchaseInvoiceModel> stList) {
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
                return list.get(rowIndex).getDate();
            case 1:
                return list.get(rowIndex).getSystemInvoiceNumber();
            case 2:
                return list.get(rowIndex).getInvoiceNo();
            case 3:
                return list.get(rowIndex).getSupplierCode();
            case 4:
                return df.format(list.get(rowIndex).getTotal());
            case 5:
                return df.format(list.get(rowIndex).getDiscount());
            case 6:
                return df.format(list.get(rowIndex).getPaid());
            case 7:
                return df.format(list.get(rowIndex).getBalance());
            case 8:
                return list.get(rowIndex).getEditBy();
            default:
                return "Error";
            
                 
        }
    }
}
