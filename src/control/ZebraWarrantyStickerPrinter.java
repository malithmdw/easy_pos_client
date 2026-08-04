
package control;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
/**
 *
 * @author malit
 */

/**
 * Zebra ZD888
 * 110mm paper
 * 34mm x 25mm labels
 * 3 columns
 *
 * 203 DPI
 */
public class ZebraWarrantyStickerPrinter {

    // =========================================================
    // PRINTER CONFIGURATION
    // =========================================================

    private static final int PAPER_WIDTH = 880;
    private static final int LABEL_WIDTH = 272;
    private static final int LABEL_HEIGHT = 200;
    private static final int GAP = 16;

    private static final int COL1_X = 0;
    private static final int COL2_X = LABEL_WIDTH + GAP;
    private static final int COL3_X = (LABEL_WIDTH * 2) + (GAP * 2);

    // =========================================================
    // PRINT TO ZEBRA
    // =========================================================

    private void printToZebra(String printerName, String zpl) throws Exception {

        PrintService[] services =
                PrintServiceLookup.lookupPrintServices(null, null);

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

        Doc doc = new SimpleDoc(
                new ByteArrayInputStream(bytes),
                DocFlavor.INPUT_STREAM.AUTOSENSE,
                null
        );

        job.print(doc, null);
    }

    // =========================================================
    // UTIL METHODS
    // =========================================================

    private String limit(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max);
    }

    // =========================================================
    // WARRANTY LABEL – PUBLIC ENTRY POINT
    // =========================================================

    /**
     * Prints a single warranty sticker label on the Zebra printer.
     *
     * @param businessName the business name to show at the bottom of the label
     */
    public void printWarrantyLabel(String businessName) {
        try {
            String zpl = buildWarrantyLabelJob(businessName);
            printToZebra(ApplicationDataManager.getInstance().getLabelPrinterName(), zpl);
            System.out.println("Warranty label printed successfully");
        } catch (Exception e) {
            EasyPosLogger.getInstance().error("WARRANTY LABEL PRINTING ERROR", e);
        }
    }

    // =========================================================
    // WARRANTY LABEL – BUILD ZPL JOB
    // =========================================================

    private String buildWarrantyLabelJob(String businessName) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String warrantyYears = getWarrantyYears(currentYear);

        StringBuilder zpl = new StringBuilder();

        // Use identical paper/label/printer settings as the barcode label job
        zpl.append("^XA\n");
        zpl.append("^PW").append(PAPER_WIDTH).append("\n");
        zpl.append("^LL").append(LABEL_HEIGHT).append("\n");
        zpl.append("^MNY\n");
        zpl.append("^MMT\n");
        zpl.append("^PR4\n");
        zpl.append("^MD15\n");

        appendWarrantyContent(zpl, COL1_X, 0, warrantyYears, businessName);
        appendWarrantyContent(zpl, COL2_X, 0, warrantyYears, businessName);
        appendWarrantyContent(zpl, COL3_X, 0, warrantyYears, businessName);

        zpl.append("^XZ");
        return zpl.toString();
    }

    // =========================================================
    // WARRANTY LABEL – YEAR STRING
    // =========================================================

    /**
     * Returns the current year and next four years as last-two-digit
     * slash-separated string, e.g. 2026 → "26/27/28/29/30".
     */
    private String getWarrantyYears(int year) {
        return String.format("%02d/%02d/%02d/%02d/%02d",
                year % 100,
                (year + 1) % 100,
                (year + 2) % 100,
                (year + 3) % 100,
                (year + 4) % 100);
    }

    // =========================================================
    // WARRANTY LABEL – DRAW ALL CONTENT
    // =========================================================

    /**
     * Appends ZPL field commands for the full warranty label content:
     * header, warranty years, 6×2 month grid, and business name.
     *
     * Layout within a 272×200 dot label cell (203 DPI):
     *   y=  8  "WARRANTY PROTECTION"  font 17
     *   y= 28  warranty years         font 18
     *   y= 52  month grid             6 cols × 2 rows, 42×33 dots per cell
     *   y=126  business name          font 14
     */
    private void appendWarrantyContent(
            StringBuilder zpl,
            int startX,
            int startY,
            String warrantyYears,
            String businessName
    ) {
        // Horizontal centre of this label column
        int centerX = startX + (LABEL_WIDTH / 2);

        // ---- HEADER "WARRANTY PROTECTION" ----
        // font 17,17 → half-char width ≈ 5 dots
        String header = "WARRANTY PROTECTION";
        int headerY = startY + 8;
        int headerX = centerX - (header.length() * 5);
        zpl.append("^FO").append(headerX).append(",").append(headerY)
           .append("^A0N,17,17^FD").append(header).append("^FS\n");

        // ---- WARRANTY YEARS e.g. "26/27/28/29/30" ----
        // font 18,18 → half-char width ≈ 6 dots (same as existing item-name font)
        int yearsY = startY + 28;
        int yearsX = centerX - (warrantyYears.length() * 6);
        zpl.append("^FO").append(yearsX).append(",").append(yearsY)
           .append("^A0N,18,18^FD").append(warrantyYears).append("^FS\n");

        // ---- MONTH GRID (months 1–12 in a 6-column × 2-row grid) ----
        int gridStartX = startX + 10;   // 10-dot left margin inside label
        int gridY      = startY + 52;
        int cellW      = 42;             // 6 cols × 42 = 252 dots
        int cellH      = 33;             // 2 rows × 33 = 66 dots
        int numCols    = 6;
        int numRows    = 2;
        int gridW      = cellW * numCols; // 252
        int gridH      = cellH * numRows; // 66

        // Outer border box (2-dot thick)
        zpl.append("^FO").append(gridStartX).append(",").append(gridY)
           .append("^GB").append(gridW).append(",").append(gridH).append(",2^FS\n");

        // Horizontal separator between the two rows
        zpl.append("^FO").append(gridStartX).append(",").append(gridY + cellH)
           .append("^GB").append(gridW).append(",1,1^FS\n");

        // Vertical separators between the six columns (5 internal lines)
        for (int col = 1; col < numCols; col++) {
            int lineX = gridStartX + col * cellW;
            zpl.append("^FO").append(lineX).append(",").append(gridY)
               .append("^GB1,").append(gridH).append(",1^FS\n");
        }

        // Month numbers inside each cell
        // font 18,18 → half-char width ≈ 6 dots
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int month     = row * numCols + col + 1;
                String mStr   = String.valueOf(month);
                int cellCX    = gridStartX + col * cellW + cellW / 2;
                int numX      = cellCX - (mStr.length() * 6);
                int numY      = gridY + row * cellH + (cellH - 18) / 2;
                zpl.append("^FO").append(numX).append(",").append(numY)
                   .append("^A0N,18,18^FD").append(mStr).append("^FS\n");
            }
        }

        // ---- BUSINESS NAME ----
        // font 14,14 → half-char width ≈ 4 dots (same scale as existing business name)
        String bizText = limit(businessName, 22);
        int bizY = gridY + gridH + 8;
        int bizX = centerX - (bizText.length() * 4);
        zpl.append("^FO").append(bizX).append(",").append(bizY)
           .append("^A0N,14,14^FD").append(bizText).append("^FS\n");
    }
}