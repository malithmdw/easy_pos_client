package tableModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverDataModels.Item;
import serverDataModels.ItemStock;

public class ExpiredStockTbl extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "#", "Barcode", "Item Name", "Category", "Batch ID",
        "Qty Available", "Selling Price", "Expiry Date"
    };

    private final List<Row> rows = new ArrayList<>();

    private static class Row {
        final Item item;
        final ItemStock batch;

        Row(Item item, ItemStock batch) {
            this.item = item;
            this.batch = batch;
        }
    }

    public ExpiredStockTbl(List<Item> items) {
        for (Item item : items) {
            if (item.stock != null) {
                for (ItemStock batch : item.stock) {
                    rows.add(new Row(item, batch));
                }
            }
        }
    }

    @Override
    public int getRowCount() {
        return rows.size();
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
        Row row = rows.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return row.item.barcode;
            case 2: return row.item.item_name;
            case 3: return (row.item.category != null) ? row.item.category.cat_name : "";
            case 4: return row.batch.batch_id;
            case 5: return row.batch.quantity_available;
            case 6: return util.GeneralUtil.getCurrencyString(row.batch.selling_price);
            case 7: return row.batch.expiry_date;
            default: return "";
        }
    }
}
