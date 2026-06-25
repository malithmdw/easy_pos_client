package tableModels;

import dataModels.SaleReturnLineDataModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SaleReturnItemsTableModel extends AbstractTableModel {

    private final DecimalFormat df = new DecimalFormat("#0.00");

    private static final String[] COLUMN_NAMES = {"Item Code", "Item Name", "Unit Price", "Return Qty", "Return Amount", "Action"};
    private final ArrayList<SaleReturnLineDataModel> returnLines;

    public SaleReturnItemsTableModel() {
        this.returnLines = new ArrayList<>();
    }

    public void addReturnLine(SaleReturnLineDataModel line) {
        returnLines.add(line);
        fireTableRowsInserted(returnLines.size() - 1, returnLines.size() - 1);
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < returnLines.size()) {
            returnLines.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void clearAll() {
        returnLines.clear();
        fireTableDataChanged();
    }

    public SaleReturnLineDataModel getRow(int rowIndex) {
        return returnLines.get(rowIndex);
    }

    public ArrayList<SaleReturnLineDataModel> getReturnLines() {
        return returnLines;
    }

    public double getReturnedQtyForItem(int itemId) {
        double qty = 0;
        for (SaleReturnLineDataModel line : returnLines) {
            if (line.getItemId() == itemId) {
                qty += line.getReturnQty();
            }
        }
        return qty;
    }

    public double getTotalReturnAmount() {
        double total = 0;
        for (SaleReturnLineDataModel line : returnLines) {
            total += line.getReturnAmount();
        }
        return total;
    }

    @Override
    public int getRowCount() {
        return returnLines.size();
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
        SaleReturnLineDataModel line = returnLines.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return line.getItemCode();
            case 1:
                return line.getItemName();
            case 2:
                return df.format(line.getUnitPrice());
            case 3:
                return df.format(line.getReturnQty());
            case 4:
                return df.format(line.getReturnAmount());
            case 5:
                return "Remove";
            default:
                return "Error";
        }
    }
}
