package uiUtil;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class TableHeaderLocalizer {

    public static void refresh(JTable table) {
        if (table == null) return;
        TableModel model = table.getModel();
        if (model == null) return;
        int modelCols = model.getColumnCount();
        int viewCols  = table.getColumnModel().getColumnCount();
        int cols = Math.min(modelCols, viewCols);
        for (int i = 0; i < cols; i++) {
            table.getColumnModel().getColumn(i).setHeaderValue(model.getColumnName(i));
        }
        if (table.getTableHeader() != null) {
            table.getTableHeader().repaint();
        }
    }
}
