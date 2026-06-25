
package control;

import appDataModels.BarcodeLableItemDataModel;
import javax.print.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
public class ZebraStickerPrinter {

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
    // PUBLIC PRINT METHOD
    // =========================================================

    public void print(
            List<BarcodeLableItemDataModel> labelItemList,
            String businessName
    ) {

        if (labelItemList == null || labelItemList.isEmpty()) {
            System.out.println("No labels to print");
            return;
        }

        ArrayList<BarcodeLableItemDataModel> expandedList = new ArrayList<>();

        for (BarcodeLableItemDataModel item : labelItemList) {
            for (int i = 0; i < item.getStickerCount(); i++) {
                expandedList.add(item);
            }
        }

        try {

            // ============================================
            // SPLIT INTO ROW JOBS (3 LABELS PER JOB)
            // ============================================
            for (int i = 0; i < expandedList.size(); i += 3) {

                List<BarcodeLableItemDataModel> chunk =
                        expandedList.subList(
                                i,
                                Math.min(i + 3, expandedList.size())
                        );

                String zpl = buildRowJob(chunk, businessName);

                printToZebra(ApplicationDataManager.getInstance().getLabelPrinterName(), zpl);
            }

            System.out.println("Printed Successfully");

        } catch (Exception e) {
            EasyPosLogger.getInstance().error("LABEL PRINTING ERROR", e);
        }
    }

    // =========================================================
    // BUILD SINGLE ROW JOB (3 LABELS MAX)
    // =========================================================

    private String buildRowJob(
            List<BarcodeLableItemDataModel> row,
            String businessName
    ) {

        StringBuilder zpl = new StringBuilder();

        zpl.append("^XA\n");
        zpl.append("^PW").append(PAPER_WIDTH).append("\n");
        zpl.append("^LL").append(LABEL_HEIGHT).append("\n");

        zpl.append("^MNY\n");
        zpl.append("^MMT\n");
        zpl.append("^PR4\n");
        zpl.append("^MD15\n");

        for (int j = 0; j < row.size(); j++) {

            BarcodeLableItemDataModel item = row.get(j);

            int startX = (j == 0) ? COL1_X :
                         (j == 1) ? COL2_X : COL3_X;

            appendSingleLabel(
                    zpl,
                    startX,
                    0,
                    item.getItemName(),
                    "Rs. " + formatPrice(item.getPrice()),
                    item.getBarcode(),
                    businessName
            );
        }

        zpl.append("^XZ");

        return zpl.toString();
    }

    // =========================================================
    // SINGLE LABEL DRAWING
    // =========================================================

    private void appendSingleLabel(
            StringBuilder zpl,
            int startX,
            int startY,
            String itemName,
            String price,
            String barcode,
            String businessName
    ) {

        int centerX = startX + (LABEL_WIDTH / 2);
        int topOffset = 16;

        // ITEM NAME
        String itemText = limit(itemName, 20);
        int itemNameX = centerX - (itemText.length() * 6);

        zpl.append("^FO").append(itemNameX).append(",")
                .append(startY + topOffset)
                .append("^A0N,18,18^FD")
                .append(itemText)
                .append("^FS\n");

        // PRICE
        int priceX = centerX - (price.length() * 7);

        zpl.append("^FO").append(priceX).append(",")
                .append(startY + topOffset + 22)
                .append("^A0N,30,30^FD")
                .append(price)
                .append("^FS\n");

        // BARCODE (fixed left padding inside label)
        int barcodeX = startX + 10;

        zpl.append("^FO")
                .append(barcodeX).append(",")
                .append(startY + topOffset + 56)
                .append("^BY2,2,58")
                .append("^BCN,52,N,N,N")
                .append("^FD")
                .append(barcode)
                .append("^FS\n");

        // BARCODE TEXT
        int barcodeTextX = centerX - (barcode.length() * 4);

        zpl.append("^FO").append(barcodeTextX).append(",")
                .append(startY + topOffset + 114)
                .append("^A0N,15,15^FD")
                .append(barcode)
                .append("^FS\n");

        // BUSINESS NAME
        String businessText = limit(businessName, 22);
        int businessX = centerX - (businessText.length() * 4);

        zpl.append("^FO").append(businessX).append(",")
                .append(startY + topOffset + 130)
                .append("^A0N,13,13^FD")
                .append(businessText)
                .append("^FS\n");
    }

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

    private String formatPrice(double value) {
        return String.format("%.2f", value);
    }
}