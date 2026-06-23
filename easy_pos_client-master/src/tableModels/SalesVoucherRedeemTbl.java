package tableModels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import serverDataModels.Voucher;

public class SalesVoucherRedeemTbl extends AbstractTableModel {

    private final DecimalFormat df = new DecimalFormat("#0.00");

    private static final String[] COLUMN_NAMES = {"Voucher Number", "Amount", "Action"};
    private final ArrayList<Voucher> voucherList;

    public SalesVoucherRedeemTbl() {
        this.voucherList = new ArrayList<>();
    }

    public void addVoucher(Voucher voucher) {
        voucherList.add(voucher);
        fireTableRowsInserted(voucherList.size() - 1, voucherList.size() - 1);
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < voucherList.size()) {
            voucherList.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public double getTotalVoucherAmount() {
        double total = 0;
        for (Voucher v : voucherList) {
            total += v.amount;
        }
        return total;
    }

    public boolean isDuplicateVoucher(int voucherID) {
        for (Voucher v : voucherList) {
            if (v.voucher_id == voucherID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getRowCount() {
        return voucherList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return voucherList.get(rowIndex).voucher_name;
            case 1:
                return df.format(voucherList.get(rowIndex).amount);
            case 2:
                return "Remove";
            default:
                return "Error";
        }
    }
}
