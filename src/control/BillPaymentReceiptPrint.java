package control;

import dataModels.Language;
import dataModels.ReceiptCommonData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import serverDataModels.BillPaymentProviderModel;
import serverResponseDataModel.BillPaymentResult;

/**
 * Prints a bill-payment receipt on the thermal printer.
 *
 * Receipt layout:
 *   [Logo]
 *   Business Name / Sub-Name
 *   Address lines / Contact
 *   ---- BILL PAYMENT ----
 *   Provider   : ...
 *   Account    : ...
 *   Amount     : Rs. X,XXX.XX
 *   Ref        : ...
 *   Date/Time  : ...
 *   Status     : SUCCESS / PENDING
 *   ---------------------------
 *   Footer lines
 */
public class BillPaymentReceiptPrint implements Printable {

    private static final Font FONT_TIMES_B_12 = new Font("Times New Roman", Font.BOLD, 12);
    private static final Font FONT_TIMES_B_10 = new Font("Times New Roman", Font.BOLD, 10);
    private static final Font FONT_TIMES_P_9  = new Font("Times New Roman", Font.PLAIN, 9);
    private static final Font FONT_ARIAL_P_9  = new Font("Arial", Font.PLAIN, 9);
    private static final Font FONT_MONOS_P_9  = new Font("Monospaced", Font.PLAIN, 9);
    private static final Font FONT_MONOS_B_9  = new Font("Monospaced", Font.BOLD, 9);
    private static final Font FONT_MONOS_B_10 = new Font("Monospaced", Font.BOLD, 10);

    private static final String DASH_LINE =
            "-----------------------------------------------------------";

    private final BillPaymentResult result;
    private final BillPaymentProviderModel provider;
    private final ReceiptCommonData receiptCommonData;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public BillPaymentReceiptPrint(BillPaymentResult result,
                                   BillPaymentProviderModel provider,
                                   ReceiptCommonData receiptCommonData) {
        this.result            = result;
        this.provider          = provider;
        this.receiptCommonData = receiptCommonData;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);

        // Load Sinhala font variants (fall back to system fonts on failure)
        Font fontSinB12 = FONT_TIMES_B_12;
        Font fontSinB10 = FONT_TIMES_B_10;
        Font fontSinP9  = FONT_ARIAL_P_9;
        Font fontSinB9  = FONT_MONOS_B_9;
        try {
            fontSinB12 = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(Font.BOLD, 12f);
            fontSinB10 = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(Font.BOLD, 10f);
            fontSinP9  = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(9f);
            fontSinB9  = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(Font.BOLD, 9f);
        } catch (FontFormatException | IOException ignored) {}

        boolean sinBusiness = Language.SINHALA.equals(
                ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage());
        boolean sinContent = Language.SINHALA.equals(
                ApplicationDataManager.getInstance().getReceiptContentLanguage());

        int x = 5;
        int y = 12;

        // ---- Logo ----
        try {
            if (ApplicationDataManager.getInstance().getReceiptLogo() != null) {
                BufferedImage logo = ImageIO.read(ApplicationDataManager.getInstance().getReceiptLogo());
                if (logo != null) {
                    int pw = 200;
                    int ph = (logo.getHeight() * pw) / logo.getWidth();
                    g2.drawImage(logo, 0, 0, pw, ph, null);
                    y += ph;
                }
            }
        } catch (IOException ex) {
            EasyPosLogger.getInstance().error("", ex);
        }

        // ---- Business header ----
        String bizName    = receiptCommonData.getBusinessName();
        String bizSub     = receiptCommonData.getBusinessSubName();
        String[] address  = {
            receiptCommonData.getAddressLine1(),
            receiptCommonData.getAddressLine2(),
            receiptCommonData.getAddressLine3()
        };
        String contact    = receiptCommonData.getContactDataLine();

        if (bizName != null && !bizName.isEmpty()) {
            g2.setFont(sinBusiness ? fontSinB12 : FONT_TIMES_B_12);
            g2.drawString(bizName, x, y); y += 13;
        }
        if (bizSub != null && !bizSub.isEmpty()) {
            g2.setFont(sinBusiness ? fontSinB10 : FONT_TIMES_B_10);
            g2.drawString(bizSub, x, y); y += 13;
        }
        y += 3;
        for (String line : address) {
            if (line == null || line.isEmpty()) continue;
            g2.setFont(sinBusiness ? fontSinP9 : FONT_ARIAL_P_9);
            g2.drawString(line, x, y); y += 11;
        }
        if (contact != null && !contact.isEmpty()) {
            g2.setFont(sinBusiness ? fontSinP9 : FONT_ARIAL_P_9);
            g2.drawString(contact, x, y); y += 11;
        }

        y += 4;
        g2.setFont(FONT_MONOS_P_9);
        g2.drawString(DASH_LINE, x, y); y += 11;

        // ---- Title ----
        String title = sinContent ? "බිල්පත් ගෙවීම" : "BILL PAYMENT";
        g2.setFont(sinContent ? fontSinB10 : FONT_MONOS_B_10);
        g2.drawString(title, x, y); y += 13;

        g2.setFont(FONT_MONOS_P_9);
        g2.drawString(DASH_LINE, x, y); y += 13;

        // ---- Payment details ----
        Font labelFont  = sinContent ? fontSinP9 : FONT_MONOS_P_9;
        Font valueFont  = FONT_MONOS_P_9;

        String providerName = (provider != null) ? provider.getLocalizedName() : (result.serviceProviderId != null ? result.serviceProviderId : "");
        String[][] rows;
        if (sinContent) {
            rows = new String[][] {
                {"සේවා සපයන්නා   ", providerName},
                {"ගිණුම් / අංකය  ", result.accountNumber != null ? result.accountNumber : ""},
                {"මුදල           ", "Rs. " + df.format(result.amount)},
                {"යොමු           ", result.clientRef    != null ? result.clientRef    : ""},
                {"දිනය/වේලාව     ", result.dateTime     != null ? result.dateTime     : ""},
                {"තත්ත්වය        ", localizeStatus(result.status, sinContent)},
            };
        } else {
            rows = new String[][] {
                {"Provider   ", providerName},
                {"Account    ", result.accountNumber != null ? result.accountNumber : ""},
                {"Amount     ", "Rs. " + df.format(result.amount)},
                {"Ref        ", result.clientRef    != null ? result.clientRef    : ""},
                {"Date/Time  ", result.dateTime     != null ? result.dateTime     : ""},
                {"Status     ", localizeStatus(result.status, sinContent)},
            };
        }

        for (String[] row : rows) {
            g2.setFont(sinContent && !isAscii(row[0]) ? labelFont : FONT_MONOS_P_9);
            g2.drawString(row[0] + ": " + row[1], x, y);
            y += 12;
        }

        y += 4;
        g2.setFont(FONT_MONOS_P_9);
        g2.drawString(DASH_LINE, x, y); y += 11;

        // ---- Footer ----
        String[] footerLines = {
            receiptCommonData.getFooterLine1(),
            receiptCommonData.getFooterLine2(),
            receiptCommonData.getServiceProviderLine()
        };
        boolean sinFooter = Language.SINHALA.equals(
                ApplicationDataManager.getInstance().getReceiptFooterLanguage());
        for (String line : footerLines) {
            if (line == null || line.isEmpty()) continue;
            g2.setFont(sinFooter ? fontSinP9 : FONT_TIMES_P_9);
            g2.drawString(line, x, y); y += 11;
        }

        return PAGE_EXISTS;
    }

    private String localizeStatus(String status, boolean sinhala) {
        if (status == null) return "";
        if (sinhala) {
            return "SUCCESS".equalsIgnoreCase(status) ? "සාර්ථකයි" : "අපේක්ෂා කෙරේ";
        }
        return status;
    }

    private boolean isAscii(String s) {
        for (char c : s.toCharArray()) {
            if (c > 127) return false;
        }
        return true;
    }
}
