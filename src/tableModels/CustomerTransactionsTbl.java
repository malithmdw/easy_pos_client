package tableModels;

import appDataModels.CustomerLedgerModel;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CustomerTransactionsTbl extends AbstractTableModel{
    DecimalFormat df = new DecimalFormat("#0.00");
    
    private static final String[] COLUMN_NAMES={"Date Time","Transaction ID","Invoice Number","Description","Ledger","Amount","Cashier"};
    private static List<CustomerLedgerModel> list;
    
    public CustomerTransactionsTbl(List<CustomerLedgerModel> stList) {
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
                return list.get(rowIndex).getTransactionDate();
            case 1:
                return list.get(rowIndex).getTranId();
            case 2:
                return list.get(rowIndex).getInvoiceNumber();
            case 3:
                return list.get(rowIndex).getDescription();
            case 4:
                return list.get(rowIndex).getLedgerType();
            case 5:
                return df.format(list.get(rowIndex).getTransactionAmount());
            case 6:
                return list.get(rowIndex).getAcceptBy();
            default:
                return "Error";
            
                 
        }
    }
}
