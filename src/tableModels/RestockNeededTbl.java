package tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverDataModels.Item;
import serverDataModels.ItemStock;

public class RestockNeededTbl extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "#", "Barcode", "Item Name", "Category",
        "Min Required", "Available", "Shortage"
    };

    private final List<Item> items;

    public RestockNeededTbl(List<Item> items) {
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
        Item item = items.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return item.barcode;
            case 2: return item.item_name;
            case 3: return (item.category != null) ? item.category.cat_name : "";
            case 4: return item.minimum_stock;
            case 5: {
                double total = 0;
                if (item.stock != null) {
                    for (ItemStock batch : item.stock) {
                        total += batch.quantity_available;
                    }
                }
                return total;
            }
            case 6: {
                double total = 0;
                if (item.stock != null) {
                    for (ItemStock batch : item.stock) {
                        total += batch.quantity_available;
                    }
                }
                double shortage = item.minimum_stock - total;
                return shortage > 0 ? shortage : 0;
            }
            default: return "";
        }
    }
}
