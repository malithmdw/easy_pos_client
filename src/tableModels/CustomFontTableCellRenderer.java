
package tableModels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 *
 * @author MalithWanniarachchi
 */
public class CustomFontTableCellRenderer extends DefaultTableCellRenderer {
    private final Font customFont;

    public CustomFontTableCellRenderer(Font font) {
        this.customFont = font;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Let the super class handle background, foreground, selections, and text setup
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Explicitly override the font for this rendering pass
        setFont(customFont);
        
        return this;
    }
}
