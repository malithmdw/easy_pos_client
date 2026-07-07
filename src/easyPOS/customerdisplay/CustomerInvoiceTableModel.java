package easyPOS.customerdisplay;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Wraps SaleInvoiceItemTbl and exposes only the columns relevant to the
 * customer-facing display: #, Item, Price, Qty, Amount.
 *
 * Source column mapping (SaleInvoiceItemTbl):
 *   0=No  1=Code  2=Description  3=Price  4=Discount  5=Qty  6=Amount
 */
public class CustomerInvoiceTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"#", "Item", "Price", "Qty", "Amount"};
    private static final int[]    SOURCE_COLS   = { 0,    2,      3,      5,     6      };

    private TableModel delegate;

    public CustomerInvoiceTableModel(TableModel delegate) {
        this.delegate = delegate;
    }

    public void setDelegate(TableModel delegate) {
        this.delegate = delegate;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return delegate.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return delegate.getValueAt(row, SOURCE_COLS[col]);
    }
}
