package tableModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import serverDataModels.OnlineOrder;

public class OnlineOrdersTbl extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Select", "#", "Order No", "Customer Name", "Address", "Contact", "Status", "Date", "Amount"
    };

    private final List<OnlineOrder> orders;
    private final boolean[] selected;

    public OnlineOrdersTbl(List<OnlineOrder> orders) {
        this.orders = orders;
        this.selected = new boolean[orders.size()];
    }

    @Override
    public int getRowCount() {
        return orders.size();
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
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            selected[rowIndex] = (Boolean) value;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OnlineOrder order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0: return selected[rowIndex];
            case 1: return rowIndex + 1;
            case 2: return order.order_number;
            case 3: return order.customer_name;
            case 4: return order.address_line_1;
            case 5: return order.contact_number;
            case 6: return order.order_status;
            case 7: return order.order_date;
            case 8: return util.GeneralUtil.getCurrencyString(order.total_amount);
            default: return "";
        }
    }

    public List<OnlineOrder> getSelectedOrders() {
        List<OnlineOrder> result = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (selected[i]) {
                result.add(orders.get(i));
            }
        }
        return result;
    }
}
