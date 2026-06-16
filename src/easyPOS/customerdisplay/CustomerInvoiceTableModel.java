package javaapplication1;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author MalithWanniarachchi
 */
public class CustomerInvoiceTableModel extends AbstractTableModel
{
    private final TableModel delegate;
    
    private final String[] columnNames = new String[]{"No.", "Price", "Discount", "Qty", "Amount"};
    
    public CustomerInvoiceTableModel(TableModel delegate)
    {
        this.delegate= delegate;
    }

    @Override
    public int getRowCount() { return this.delegate.getRowCount();}
    
    @Override
    public int getColumnCount() { return this.delegate.getColumnCount()-1;}
    
    @Override
    public Object getValueAt(int row, int col)
    {
        if(col==0) return ""+delegate.getValueAt(row,col)+delegate.getValueAt(row,col+1);
        return null;//delegate.getValueAt(col+1);
    }
}
