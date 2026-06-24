package tableModels;

import dataModels.SaleReturnInvoiceItemDataModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class SaleReturnInvoiceItemsTableModel extends AbstractTableModel {

    private final DecimalFormat df = new DecimalFormat("#0.00");

    private static final String[] COLUMN_NAMES = {"Item Code", "Item Name", "Qty Sold", "Unit Price", "Discount", "Line Total"};
    private final List<SaleReturnInvoiceItemDataModel> invoiceItems;

    public SaleReturnInvoiceItemsTableModel(List<SaleReturnInvoiceItemDataModel> invoiceItems) {
        this.invoiceItems = (invoiceItems != null) ? invoiceItems : new ArrayList<>();
    }

    public SaleReturnInvoiceItemDataModel getRow(int rowIndex) {
        return invoiceItems.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return invoiceItems.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SaleReturnInvoiceItemDataModel item = invoiceItems.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getItemCode();
            case 1:
                return item.getItemName();
            case 2:
                return df.format(item.getQtySold());
            case 3:
                return df.format(item.getUnitPrice());
            case 4:
                return df.format(item.getDiscount());
            case 5:
                return df.format(item.getLineTotal());
            default:
                return "Error";
        }
    }
}
