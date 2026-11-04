package tableModels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverResponseDataModel.BillPaymentHistoryItem;

public class BillPaymentHistoryTableModel extends AbstractTableModel {

    public static final int COL_ROW_NUM   = 0;
    public static final int COL_DATETIME  = 1;
    public static final int COL_CATEGORY  = 2;
    public static final int COL_PROVIDER  = 3;
    public static final int COL_ACCOUNT   = 4;
    public static final int COL_AMOUNT    = 5;
    public static final int COL_STATUS    = 6;
    public static final int COL_PRINT     = 7;

    private static final String[] COLUMN_NAMES = {
        "#", "Date / Time", "Category", "Provider", "Account / Mobile", "Amount", "Status", "Print"
    };

    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private List<BillPaymentHistoryItem> items;

    public BillPaymentHistoryTableModel(List<BillPaymentHistoryItem> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public void setItems(List<BillPaymentHistoryItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        fireTableDataChanged();
    }

    public BillPaymentHistoryItem getItem(int row) {
        return items.get(row);
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int col) {
        try {
            dataModels.Language lang = control.ApplicationDataManager.getInstance().getApplicationLanguage();
            java.util.Locale locale = (lang == dataModels.Language.SINHALA)
                    ? new java.util.Locale("si", "LK") : java.util.Locale.ENGLISH;
            java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("tableModels/Bundle", locale);
            return bundle.getString("BillPaymentHistoryTableModel.col." + col);
        } catch (Exception e) {
            return COLUMN_NAMES[col];
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == COL_PRINT;
    }

    @Override
    public Object getValueAt(int row, int col) {
        BillPaymentHistoryItem item = items.get(row);
        switch (col) {
            case COL_ROW_NUM:  return row + 1;
            case COL_DATETIME: return item.dateTime          != null ? item.dateTime          : "";
            case COL_CATEGORY: return item.categoryId        != null ? item.categoryId        : "";
            case COL_PROVIDER: return item.serviceProviderId != null ? item.serviceProviderId : "";
            case COL_ACCOUNT:  return item.accountNumber     != null ? item.accountNumber     : "";
            case COL_AMOUNT:   return df.format(item.amount);
            case COL_STATUS:   return item.status            != null ? item.status            : "";
            case COL_PRINT:    return "Print";
            default:           return "";
        }
    }
}
