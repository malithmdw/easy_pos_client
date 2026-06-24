
package control;

import com.itextpdf.text.pdf.Barcode128;
import dataModels.Language;
import dataModels.ReceiptCommonData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturnVoucherPrint implements Printable {

    private final Font FONT_TIMES_B_12 = new Font("Times New Roman", Font.BOLD, 12);
    private final Font FONT_TIMES_B_10 = new Font("Times New Roman", Font.BOLD, 10);
    private final Font FONT_MONOS_P_9 = new Font("Monospaced", Font.PLAIN, 9);
    private final Font FONT_MONOS_B_9 = new Font("Monospaced", Font.BOLD, 9);

    private final DecimalFormat df = new DecimalFormat("#0.00");

    private final String voucherId;
    private final double voucherAmount;
    private final String voucherDateTime;
    private final ReceiptCommonData receiptCommonData;
    private final String cashierName;

    public SaleReturnVoucherPrint(String voucherId, double voucherAmount, String voucherDateTime,
            ReceiptCommonData receiptCommonData, String cashierName) {
        this.voucherId = voucherId;
        this.voucherAmount = voucherAmount;
        this.voucherDateTime = voucherDateTime;
        this.receiptCommonData = receiptCommonData;
        this.cashierName = cashierName;
    }

    private void printDashSeparator(Graphics2D g2, int x, int y) {
        String dashedLineBreak = "-----------------------------------------------------------\n";
        g2.setFont(FONT_MONOS_P_9);
        g2.drawString(dashedLineBreak, x, y);
    }

    @Override
    public int print(Graphics g, PageFormat Pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.black);

        Font FONT_SINHAL_P_9 = FONT_MONOS_P_9;
        Font FONT_SINHAL_B_10 = FONT_TIMES_B_10;
        Font FONT_SINHAL_B_12 = FONT_TIMES_B_12;

        try {
            FONT_SINHAL_P_9 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(9f);

            FONT_SINHAL_B_10 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(Font.BOLD, 10f);

            FONT_SINHAL_B_12 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(Font.BOLD, 12f);

        } catch (FontFormatException | IOException e) {
            System.err.println(e);
        }

        int x = 5;
        int y = 12;

        BufferedImage logo = null;
        try {
            if (ApplicationDataManager.getInstance().getReceiptLogo() != null) {
                logo = ImageIO.read(ApplicationDataManager.getInstance().getReceiptLogo());
            }
        } catch (IOException ex) {
            Logger.getLogger(SaleReturnVoucherPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (logo != null) {
            int printWidth = 200;
            int scaledHeight = (logo.getHeight() * printWidth) / logo.getWidth();
            g2.drawImage(logo, 0, 0, printWidth, scaledHeight, null);
            y = y + scaledHeight;
        }

        // Header
        String header = receiptCommonData.getBusinessName();
        String subHeader = receiptCommonData.getBusinessSubName();

        if (header != null && !header.isEmpty()) {
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage())) {
                g2.setFont(FONT_SINHAL_B_12);
            } else {
                g2.setFont(FONT_TIMES_B_12);
            }
            g2.drawString(header + "\n", x, y);
            y = y + 10;
        }
        if (subHeader != null && !subHeader.isEmpty()) {
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage())) {
                g2.setFont(FONT_SINHAL_B_10);
            } else {
                g2.setFont(FONT_TIMES_B_10);
            }
            g2.drawString(subHeader + "\n", x, y);
            y = y + 10;
        }

        y = y + 5;

        printDashSeparator(g2, x, y);
        y = y + 11;

        // Title
        g2.setFont(FONT_MONOS_B_9);
        g2.drawString("SALE RETURN VOUCHER", x, y);
        y = y + 13;

        g2.setFont(FONT_MONOS_P_9);
        g2.drawString("Date       : " + voucherDateTime, x, y);
        y = y + 11;

        g2.drawString("Voucher ID : " + voucherId, x, y);
        y = y + 11;

        g2.setFont(FONT_MONOS_B_9);
        g2.drawString("Amount     : " + util.GeneralUtil.getCurrencyString(voucherAmount), x, y);
        y = y + 11;

        if (cashierName != null && !cashierName.isEmpty()) {
            g2.setFont(FONT_MONOS_P_9);
            g2.drawString("Cashier    : " + cashierName, x, y);
            y = y + 11;
        }

        y = y + 5;
        printDashSeparator(g2, x, y);
        y = y + 11;

        // Barcode of the Voucher ID
        try {
            Barcode128 barcode128 = new Barcode128();
            barcode128.setCode(voucherId);
            barcode128.setBarHeight(40);
            Image barcodeImage = barcode128.createAwtImage(Color.BLACK, Color.WHITE);
            g2.drawImage(barcodeImage, x, y, 200, 50, null);
            y = y + 60;
        } catch (Exception ex) {
            Logger.getLogger(SaleReturnVoucherPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Footer
        String[] footer = new String[3];
        footer[0] = receiptCommonData.getFooterLine1();
        footer[1] = receiptCommonData.getFooterLine2();
        footer[2] = receiptCommonData.getServiceProviderLine();

        for (String footer1 : footer) {
            if (footer1 == null || footer1.isEmpty()) {
                continue;
            }
            g2.setFont(FONT_SINHAL_P_9);
            g2.drawString(footer1 + "\n", x, y);
            y = y + 10;
        }

        return PAGE_EXISTS;
    }
}
