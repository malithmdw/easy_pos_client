
package uiUtil;

import control.ApplicationDataManager;
import dataModels.Language;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import webService.ResponseCodes;

/**
 *
 * @author MalithWanniarachchi
 */
public class EasyPOSMessageDialog {
    public static void showErrorMessageDialog(Component c, ResponseCodes.ApiResponse apiResponse)
    {
        if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage().SINHALA)) {
            JLabel label = new JLabel(apiResponse.getMessageWithErrorCodeSinhala());
            try {

                Font customFont = Font.createFont(
                        Font.TRUETYPE_FONT,
                        ApplicationDataManager.getInstance().getSinhalaFontFile()
                ).deriveFont(12f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                label.setFont(customFont);
            } catch (IOException | FontFormatException ignored) {}

            JOptionPane.showMessageDialog(c, label, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(c, apiResponse.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
