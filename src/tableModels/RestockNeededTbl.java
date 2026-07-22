package tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverDataModels.RestockNeededRow;

public class RestockNeededTbl extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "#", "Barcode", "Item Name", "Category",
        "Min Required", "Available", "Shortage"
    };

    private final List<RestockNeededRow> items;

    public RestockNeededTbl(List<RestockNeededRow> items) {
        this.items = items;
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
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RestockNeededRow item = items.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return item.barcode;
            case 2: return item.item_name;
            case 3: return (item.category != null) ? item.category.cat_name : "";
            case 4: return item.minimum_stock;
            case 5: return item.total_available_qty;
            case 6: return item.shortage_qty > 0 ? item.shortage_qty : 0;
            default: return "";
        }
    }
}
