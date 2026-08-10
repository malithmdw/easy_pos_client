package control;

import appDataModels.InstituteModel;
import com.itextpdf.text.pdf.Barcode128;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import serverDataModels.OnlineOrder;

/**
 * Zebra ZD888TA shipping label printer — 4 inch x 6 inch labels at 203 DPI.
 *
 * Label layout:
 *   [ LOGO ]
 *   Business Name (centered)
 *   ──────────────────────
 *   SHIP TO:
 *     <customer_name>
 *     <address_line1 / 2 / 3>
 *     <district_name>, <province_name>
 *     <contact_number> / <contact_number_2>
 *   ──────────────────────
 *   RETURN ADDRESS - WAREHOUSE:
 *     <institute print business name / sub name>
 *     <institute print business address lines>
 *     <institute print business contact>
 *   ──────────────────────
 *   PACKAGE DETAILS:
 *     Order No / Weight / Dimension / Payment method
 *   ──────────────────────
 *   NOTE:
 *     <note>
 *   [ BARCODE IMAGE ]
 *
 * Institute address data is checked for Sinhala Unicode characters; ZPL's
 * built-in fonts cannot render Sinhala glyphs, so any such field is rasterized
 * with the app's configured Sinhala font and embedded as a ZPL graphic (^GFA)
 * instead of a native ZPL text field.
 */
public class ZebraShippingLabelPrinter {

    // 4 inch x 6 inch at 203 DPI
    private static final int LABEL_WIDTH  = 812;   // 4 * 203
    private static final int LABEL_HEIGHT = 1218;  // 6 * 203

    private static final int MARGIN_X = 20;

    public void print(List<OnlineOrder> orders, InstituteModel institute, String barcodeData, String weight, String dimension, String note) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        String printerName = ApplicationDataManager.getInstance().getLabelPrinterName();
        for (OnlineOrder order : orders) {
            try {
                String zpl = buildLabel(order, institute, barcodeData, weight, dimension, note);
                printToZebra(printerName, zpl);
            } catch (Exception e) {
                EasyPosLogger.getInstance().error("SHIPPING LABEL PRINT ERROR", e);
            }
        }
    }

    private String buildLabel(OnlineOrder order, InstituteModel institute, String barcodeData, String weight, String dimension, String note) {
        StringBuilder zpl = new StringBuilder();

        zpl.append("^XA\n");
        zpl.append("^PW").append(LABEL_WIDTH).append("\n");
        zpl.append("^LL").append(LABEL_HEIGHT).append("\n");
        zpl.append("^MNY\n");
        zpl.append("^MMT\n");
        zpl.append("^PR4\n");
        zpl.append("^MD15\n");

        int x = MARGIN_X;
        int contentWidth = LABEL_WIDTH - MARGIN_X * 2;
        int y = 20;

        // ── LOGO + BUSINESS NAME (centered) ──
        y = appendLogoAndBusinessName(zpl, institute, y);

        y += 10;
        zpl.append(divider(x, y));
        y += 16;

        // ── SHIP TO ──
        zpl.append(field(x, y, 30, 30, "SHIP TO:"));
        y += 38;

        zpl.append(field(x + 10, y, 28, 28, safe(order.customer_name)));
        y += 34;

        y = appendIfNotEmpty(zpl, x + 10, y, 24, order.address_line1);
        y = appendIfNotEmpty(zpl, x + 10, y, 24, order.address_line2);
        y = appendIfNotEmpty(zpl, x + 10, y, 24, order.address_line3);

        String region = buildRegion(order.district_name, order.province_name);
        y = appendIfNotEmpty(zpl, x + 10, y, 24, region);

        zpl.append(field(x + 10, y, 24, 24, safe(order.contact_number)));
        y += 30;

        if (notEmpty(order.contact_number_2)) {
            zpl.append(field(x + 10, y, 24, 24, safe(order.contact_number_2)));
            y += 30;
        }

        y += 6;
        zpl.append(divider(x, y));
        y += 16;

        // ── RETURN ADDRESS - WAREHOUSE ──
        zpl.append(field(x, y, 24, 24, "RETURN ADDRESS - WAREHOUSE:"));
        y += 30;

        String returnName = notEmpty(institute.getPrintBusinessName())
                ? institute.getPrintBusinessName()
                : institute.getBusinessName();
        y = appendInstituteLine(zpl, x + 10, y, 22, returnName);

        if (notEmpty(institute.getPrintBusinessSubName())) {
            y = appendInstituteLine(zpl, x + 10, y, 22, institute.getPrintBusinessSubName());
        }

        y = appendInstituteLine(zpl, x + 10, y, 22, institute.getPrintBusinessAddressLine1());
        y = appendInstituteLine(zpl, x + 10, y, 22, institute.getPrintBusinessAddressLine2());
        y = appendInstituteLine(zpl, x + 10, y, 22, institute.getPrintBusinessAddressLine3());

        if (notEmpty(institute.getPrintBusinessContact())) {
            y = appendInstituteLine(zpl, x + 10, y, 22, institute.getPrintBusinessContact());
        }

        y += 6;
        zpl.append(divider(x, y));
        y += 16;

        // ── PACKAGE DETAILS ──
        zpl.append(field(x, y, 24, 24, "PACKAGE DETAILS:"));
        y += 30;

        zpl.append(field(x + 10, y, 22, 22, "Order No: " + safe(order.order_no)));
        y += 28;

        if (notEmpty(weight)) {
            zpl.append(field(x + 10, y, 22, 22, "Weight: " + safe(weight)));
            y += 28;
        }

        if (notEmpty(dimension)) {
            zpl.append(field(x + 10, y, 22, 22, "Dimension: " + safe(dimension)));
            y += 28;
        }

        String paymentLabel = "CODS".equalsIgnoreCase(order.mop) ? "Payment: COD - Cash On Delivery" : "Payment: Paid";
        zpl.append(field(x + 10, y, 22, 22, paymentLabel));
        y += 28;

        // ── NOTE ──
        if (notEmpty(note)) {
            y += 6;
            zpl.append(divider(x, y));
            y += 16;

            zpl.append(field(x, y, 22, 22, "NOTE:"));
            y += 28;

            for (String line : wrapText(safe(note), 45)) {
                zpl.append(field(x + 10, y, 20, 20, line));
                y += 26;
            }
        }

        // ── BARCODE ──
        if (notEmpty(barcodeData)) {
            y += 10;
            appendBarcode(zpl, barcodeData, x, y, contentWidth);
        }

        zpl.append("^XZ");
        return zpl.toString();
    }

    // ── LOGO / BUSINESS NAME ──

    private int appendLogoAndBusinessName(StringBuilder zpl, InstituteModel institute, int y) {
        File logoFile = ApplicationDataManager.getInstance().getReceiptLogo();
        if (logoFile != null && logoFile.exists()) {
            try {
                BufferedImage logo = ImageIO.read(logoFile);
                if (logo != null) {
                    BufferedImage scaled = scaleToFit(logo, LABEL_WIDTH - MARGIN_X * 4, 130);
                    int logoX = (LABEL_WIDTH - scaled.getWidth()) / 2;
                    y = appendImage(zpl, logoX, y, scaled);
                    y += 8;
                }
            } catch (Exception e) {
                EasyPosLogger.getInstance().error("SHIPPING LABEL LOGO LOAD ERROR", e);
            }
        }

        String businessName = notEmpty(institute.getPrintBusinessName())
                ? institute.getPrintBusinessName()
                : institute.getBusinessName();
        if (notEmpty(businessName)) {
            y = appendCenteredInstituteText(zpl, businessName, y, 28);
        }

        return y;
    }

    private int appendCenteredInstituteText(StringBuilder zpl, String text, int y, int fontSize) {
        String safeText = safe(text);
        if (safeText.isEmpty()) {
            return y;
        }
        if (containsSinhala(safeText)) {
            BufferedImage img = textToImage(safeText, sinhalaFont(fontSize));
            BufferedImage scaled = scaleToFit(img, LABEL_WIDTH - MARGIN_X * 2, fontSize + 10);
            int centerX = (LABEL_WIDTH - scaled.getWidth()) / 2;
            return appendImage(zpl, centerX, y, scaled);
        } else {
            int approxCharWidth = (int) (fontSize * 0.55);
            int textWidth = safeText.length() * approxCharWidth;
            int centerX = Math.max(MARGIN_X, (LABEL_WIDTH - textWidth) / 2);
            zpl.append(field(centerX, y, fontSize, fontSize, safeText));
            return y + fontSize + 10;
        }
    }

    // ── INSTITUTE ADDRESS LINES (Sinhala-aware) ──

    private int appendInstituteLine(StringBuilder zpl, int x, int y, int fontSize, String text) {
        if (!notEmpty(text)) {
            return y;
        }
        String safeText = safe(text);
        if (containsSinhala(safeText)) {
            BufferedImage img = textToImage(safeText, sinhalaFont(fontSize));
            BufferedImage scaled = scaleToFit(img, LABEL_WIDTH - x - MARGIN_X, fontSize + 10);
            return appendImage(zpl, x, y, scaled);
        } else {
            zpl.append(field(x, y, fontSize, fontSize, safeText));
            return y + fontSize + 6;
        }
    }

    private boolean containsSinhala(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '\u0D80' && c <= '\u0DFF') {
                return true;
            }
        }
        return false;
    }

    private Font sinhalaFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile())
                    .deriveFont(size);
        } catch (Exception e) {
            EasyPosLogger.getInstance().error("SHIPPING LABEL SINHALA FONT LOAD ERROR", e);
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    // ── BARCODE ──

    private void appendBarcode(StringBuilder zpl, String barcodeData, int x, int y, int contentWidth) {
        try {
            Barcode128 barcode128 = new Barcode128();
            barcode128.setCode(barcodeData);
            barcode128.setBarHeight(50);
            java.awt.Image awtImage = barcode128.createAwtImage(Color.BLACK, Color.WHITE);

            BufferedImage buffered = new BufferedImage(
                    awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffered.createGraphics();
            g2.drawImage(awtImage, 0, 0, null);
            g2.dispose();

            BufferedImage scaled = scaleToFit(buffered, contentWidth, 160);
            int barcodeX = x + (contentWidth - scaled.getWidth()) / 2;
            appendImage(zpl, barcodeX, y, scaled);
        } catch (Exception e) {
            EasyPosLogger.getInstance().error("SHIPPING LABEL BARCODE ERROR", e);
        }
    }

    // ── IMAGE / ZPL GRAPHIC HELPERS ──

    private BufferedImage textToImage(String text, Font font) {
        BufferedImage measureImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D measureG = measureImg.createGraphics();
        measureG.setFont(font);
        FontMetrics fm = measureG.getFontMetrics();
        int width = Math.max(1, fm.stringWidth(text));
        int height = fm.getHeight();
        measureG.dispose();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.BLACK);
        g2.setFont(font);
        g2.drawString(text, 0, fm.getAscent());
        g2.dispose();
        return img;
    }

    private BufferedImage scaleToFit(BufferedImage src, int maxWidth, int maxHeight) {
        if (src.getWidth() <= maxWidth && src.getHeight() <= maxHeight) {
            return src;
        }
        double widthRatio = (double) maxWidth / src.getWidth();
        double heightRatio = (double) maxHeight / src.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        int newW = Math.max(1, (int) Math.round(src.getWidth() * ratio));
        int newH = Math.max(1, (int) Math.round(src.getHeight() * ratio));

        BufferedImage scaled = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, newW, newH);
        g2.drawImage(src, 0, 0, newW, newH, null);
        g2.dispose();
        return scaled;
    }

    private int appendImage(StringBuilder zpl, int x, int y, BufferedImage img) {
        zpl.append("^FO").append(x).append(",").append(y).append("\n");
        zpl.append(toZplGraphic(img)).append("^FS\n");
        return y + img.getHeight();
    }

    private String toZplGraphic(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int bytesPerRow = (width + 7) / 8;
        int totalBytes = bytesPerRow * height;
        StringBuilder hex = new StringBuilder(totalBytes * 2);

        for (int row = 0; row < height; row++) {
            int bitBuffer = 0;
            int bitCount = 0;
            for (int col = 0; col < width; col++) {
                int rgb = img.getRGB(col, row);
                int alpha = (rgb >>> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int gray = (r + g + b) / 3;
                boolean black = alpha > 128 && gray < 128;

                bitBuffer = (bitBuffer << 1) | (black ? 1 : 0);
                bitCount++;
                if (bitCount == 8) {
                    hex.append(String.format("%02X", bitBuffer));
                    bitBuffer = 0;
                    bitCount = 0;
                }
            }
            if (bitCount > 0) {
                bitBuffer <<= (8 - bitCount);
                hex.append(String.format("%02X", bitBuffer));
            }
        }

        return "^GFA," + totalBytes + "," + totalBytes + "," + bytesPerRow + "," + hex;
    }

    // ── TEXT UTIL ──

    private List<String> wrapText(String text, int maxCharsPerLine) {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return lines;
        }
        String[] words = text.split("\\s+");
        StringBuilder current = new StringBuilder();
        for (String word : words) {
            if (current.length() == 0) {
                current.append(word);
            } else if (current.length() + 1 + word.length() <= maxCharsPerLine) {
                current.append(" ").append(word);
            } else {
                lines.add(current.toString());
                current = new StringBuilder(word);
            }
        }
        if (current.length() > 0) {
            lines.add(current.toString());
        }
        return lines;
    }

    private String buildRegion(String district, String province) {
        if (notEmpty(district) && notEmpty(province)) {
            return safe(district) + ", " + safe(province);
        } else if (notEmpty(district)) {
            return safe(district);
        } else if (notEmpty(province)) {
            return safe(province);
        }
        return "";
    }

    private String field(int x, int y, int fontW, int fontH, String text) {
        return "^FO" + x + "," + y + "^A0N," + fontH + "," + fontW + "^FD" + text + "^FS\n";
    }

    private String divider(int x, int y) {
        return "^FO" + x + "," + y + "^GB" + (LABEL_WIDTH - MARGIN_X * 2) + ",2,2^FS\n";
    }

    private int appendIfNotEmpty(StringBuilder zpl, int x, int y, int fontSize, String text) {
        if (notEmpty(text)) {
            zpl.append(field(x, y, fontSize, fontSize, safe(text)));
            y += fontSize + 6;
        }
        return y;
    }

    private boolean notEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private String safe(String s) {
        if (s == null) return "";
        // ZPL uses ^ and ~ as control characters — strip them from data fields
        return s.replaceAll("[\\^~]", "").trim();
    }

    private void printToZebra(String printerName, String zpl) throws Exception {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printer = null;
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                printer = service;
                break;
            }
        }
        if (printer == null) {
            throw new Exception("Printer not found: " + printerName);
        }
        DocPrintJob job = printer.createPrintJob();
        byte[] bytes = zpl.getBytes(StandardCharsets.UTF_8);
        Doc doc = new SimpleDoc(new ByteArrayInputStream(bytes), DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        job.print(doc, null);
    }
}
