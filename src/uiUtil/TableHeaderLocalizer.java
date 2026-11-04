package uiUtil;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.awt.Font;

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
            dataModels.Language lang = control.ApplicationDataManager.getInstance().getApplicationLanguage();
            if (lang == dataModels.Language.SINHALA) {
                try {
                    Font sinhalaFont = Font.createFont(Font.TRUETYPE_FONT,
                            control.ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(12f);
                    table.getTableHeader().setFont(sinhalaFont);
                } catch (Exception e) {
                    // fall back to default header font
                }
            }
            table.getTableHeader().repaint();
        }
    }
}
