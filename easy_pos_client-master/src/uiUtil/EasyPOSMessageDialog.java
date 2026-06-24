
package uiUtil;

import control.ApplicationDataManager;
import dataModels.Language;
import easyPOS.localization.ApplicationMessages;
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

    public static void showErrorMessageDialog(Component c, ResponseCodes.ApiResponse apiResponse) {
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
        } else {
            JOptionPane.showMessageDialog(c, apiResponse.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showApiResponseDialog(Component c, ResponseCodes.ApiResponse apiResponse) {
        Language lang = ApplicationDataManager.getInstance().getApplicationLanguage();
        if (lang == Language.SINHALA) {
            JLabel label = new JLabel(apiResponse.getMessageWithErrorCodeSinhala());
            applyFont(label);
            if (apiResponse.isSuccess()) {
                JOptionPane.showMessageDialog(c, label, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(c, label, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (apiResponse.isSuccess()) {
                JOptionPane.showMessageDialog(c, apiResponse.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(c, apiResponse.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void showUnexpectedError(Component c, String exMessage) {
        JOptionPane.showMessageDialog(c, "Unexpected error: " + exMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showLocalizedError(Component c, String messageKey) {
        showLocalizedDialog(c, messageKey, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showLocalizedWarning(Component c, String messageKey) {
        showLocalizedDialog(c, messageKey, "Validation", JOptionPane.WARNING_MESSAGE);
    }

    public static void showLocalizedInfo(Component c, String messageKey) {
        showLocalizedDialog(c, messageKey, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showLocalizedFormattedError(Component c, String messageKey, Object... args) {
        showRawDialog(c, ApplicationMessages.getFormattedMessage(messageKey, args), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showLocalizedFormattedInfo(Component c, String messageKey, Object... args) {
        showRawDialog(c, ApplicationMessages.getFormattedMessage(messageKey, args), "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showRawError(Component c, String message) {
        JOptionPane.showMessageDialog(c, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showRawInfo(Component c, String message) {
        JOptionPane.showMessageDialog(c, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showLocalizedDialog(Component c, String messageKey, String title, int messageType) {
        String msg = ApplicationMessages.getMessage(messageKey);
        Language lang = ApplicationDataManager.getInstance().getApplicationLanguage();
        if (lang == Language.SINHALA) {
            JLabel label = new JLabel(msg);
            applyFont(label);
            JOptionPane.showMessageDialog(c, label, title, messageType);
        } else {
            JOptionPane.showMessageDialog(c, msg, title, messageType);
        }
    }

    private static void showRawDialog(Component c, String msg, String title, int messageType) {
        Language lang = ApplicationDataManager.getInstance().getApplicationLanguage();
        if (lang == Language.SINHALA) {
            JLabel label = new JLabel(msg);
            applyFont(label);
            JOptionPane.showMessageDialog(c, label, title, messageType);
        } else {
            JOptionPane.showMessageDialog(c, msg, title, messageType);
        }
    }

    private static void applyFont(JLabel label) {
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(12f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
            label.setFont(f);
        } catch (IOException | FontFormatException ignored) {}
    }
}
