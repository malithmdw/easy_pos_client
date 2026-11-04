package uiUtil;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class TableHeaderLocalizer {

    // Wraps whatever renderer JTattoo (or the LAF) installs and overrides the font.
    private static class SinhalaHeaderRenderer implements TableCellRenderer {
        private final TableCellRenderer delegate;
        private final Font font;

        SinhalaHeaderRenderer(TableCellRenderer delegate, Font font) {
            this.delegate = delegate;
            this.font = font;
        }

        TableCellRenderer getDelegate() { return delegate; }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setFont(font);
            return c;
        }
    }

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
        if (table.getTableHeader() == null) return;

        dataModels.Language lang = control.ApplicationDataManager.getInstance().getApplicationLanguage();
        if (lang == dataModels.Language.SINHALA) {
            try {
                Font sinhalaFont = Font.createFont(Font.TRUETYPE_FONT,
                        control.ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(12f);

                // Unwrap to get the original LAF renderer, then re-wrap cleanly.
                TableCellRenderer current = table.getTableHeader().getDefaultRenderer();
                TableCellRenderer original = (current instanceof SinhalaHeaderRenderer)
                        ? ((SinhalaHeaderRenderer) current).getDelegate()
                        : current;

                table.getTableHeader().setDefaultRenderer(new SinhalaHeaderRenderer(original, sinhalaFont));
            } catch (Exception e) {
                // fall back — header remains unstyled but functional
            }
        } else {
            // Switching away from Sinhala: restore the original LAF renderer.
            TableCellRenderer current = table.getTableHeader().getDefaultRenderer();
            if (current instanceof SinhalaHeaderRenderer) {
                table.getTableHeader().setDefaultRenderer(((SinhalaHeaderRenderer) current).getDelegate());
            }
        }
        table.getTableHeader().repaint();
    }
}
