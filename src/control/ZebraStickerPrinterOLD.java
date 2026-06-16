
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
public class ZebraStickerPrinterOLD {

    // =========================================================
    // PRINTER CONFIGURATION
    // =========================================================

    private static final String PRINTER_NAME =
            "ZDesigner ZD888-203dpi ZPL";

    // 110mm paper width
    private static final int PAPER_WIDTH = 880;

    // 34mm label width
    private static final int LABEL_WIDTH = 272;

    // 25mm label height
    private static final int LABEL_HEIGHT = 200;

    // 2mm gap
    private static final int GAP = 16;

    // Column positions
    private static final int COL1_X = 0;
    private static final int COL2_X = LABEL_WIDTH + GAP;
    private static final int COL3_X =
            (LABEL_WIDTH * 2) + (GAP * 2);

    // =========================================================
    // PUBLIC PRINT METHOD
    // =========================================================

    public void print(
            List<BarcodeLableItemDataModel> labelItemList,
            String businessName
    ) {

        if (labelItemList == null
                    || labelItemList.isEmpty()) {

                System.out.println("No labels to print");
                return;
            }
        
        // Arrange as multiple lables
        ArrayList<BarcodeLableItemDataModel> lablesToPrint = new ArrayList<>();
        
        for (BarcodeLableItemDataModel barcodeLableItemDataModel : labelItemList) {
            for (int i = 0; i < barcodeLableItemDataModel.getStickerCount(); i++) {
                lablesToPrint.add(barcodeLableItemDataModel);
            }
        }
        
        try {

            String zpl =
                    buildBulk3ColumnLabels(
                            lablesToPrint,
                            businessName
                    );

            printToZebra(PRINTER_NAME, zpl);

            System.out.println("Printed Successfully");

        } catch (Exception e) {
            EasyPosLogger.getInstance().error("LABEL PRINTING ERROR", e);
        }
    }

    // =========================================================
    // BUILD BULK LABEL ZPL
    // =========================================================

    private String buildBulk3ColumnLabels(
            List<BarcodeLableItemDataModel> list,
            String businessName
    ) {

        StringBuilder zpl = new StringBuilder();

        zpl.append("^XA\n");

        // =====================================
        // PRINTER SETTINGS
        // =====================================

        zpl.append("^PW").append(PAPER_WIDTH).append("\n");

        // temporary height
        zpl.append("^LL200\n");

        zpl.append("^MNW\n");
        zpl.append("^MMT\n");
        zpl.append("^LT0\n");

        zpl.append("^PR4\n");
        zpl.append("^MD15\n");

        // =====================================
        // GENERATE LABELS
        // =====================================

        for (int i = 0; i < list.size(); i++) {

            BarcodeLableItemDataModel item =
                    list.get(i);

            int column = i % 3;
            int row = i / 3;

            int startX;

            switch (column) {
                case 0:
                    startX = COL1_X;
                    break;
                case 1:
                    startX = COL2_X;
                    break;
                default:
                    startX = COL3_X;
                    break;
            }

            int startY = row * LABEL_HEIGHT;

            appendSingleLabel(
                    zpl,
                    startX,
                    startY,
                    item.getItemName(),
                    "Rs. " + formatPrice(item.getPrice()),
                    item.getBarcode(),
                    businessName
            );
        }

        // =====================================
        // DYNAMIC FINAL HEIGHT
        // =====================================

        int totalRows =
                (int) Math.ceil(list.size() / 3.0);

        int finalHeight =
                totalRows * LABEL_HEIGHT;

        int index = zpl.indexOf("^LL200");

        zpl.replace(
                index,
                index + 6,
                "^LL" + finalHeight
        );

        zpl.append("^XZ");

        return zpl.toString();
    }

    // =========================================================
    // APPEND SINGLE LABEL
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

        // =====================================================
        // LABEL CENTER
        // =====================================================

        int centerX = startX + (LABEL_WIDTH / 2);

        // =====================================================
        // TOP MARGIN
        // =====================================================

        int topOffset = 16;

        // =====================================================
        // ITEM NAME
        // =====================================================

        String itemText = limit(itemName, 20);

        int itemNameX =
                centerX - ((itemText.length() * 8) / 2);

        zpl.append("^FO")
                .append(itemNameX)
                .append(",")
                .append(startY + topOffset)
                .append("^A0N,18,18")
                .append("^FD")
                .append(itemText)
                .append("^FS\n");

        // =====================================================
        // PRICE
        // =====================================================

        int priceX =
                centerX - ((price.length() * 13) / 2);

        zpl.append("^FO")
                .append(priceX)
                .append(",")
                .append(startY + topOffset + 22)
                .append("^A0N,30,30")
                .append("^FD")
                .append(price)
                .append("^FS\n");

        // =====================================================
        // BARCODE
        // =====================================================
        
        // fixed visual center for 34mm label
        int barcodeX = startX + 30;

        zpl.append("^FO")
                .append(barcodeX)
                .append(",")
                .append(startY + topOffset + 56)

                // module width, wide ratio, height
                .append("^BY2,2,58")

                // height = 52
                .append("^BCN,52,N,N,N")

                .append("^FD")
                .append(barcode)
                .append("^FS\n");

        // =====================================================
        // BARCODE TEXT
        // =====================================================

        int barcodeTextX =
                centerX - ((barcode.length() * 7) / 2);

        zpl.append("^FO")
                .append(barcodeTextX)
                .append(",")
                .append(startY + topOffset + 114)
                .append("^A0N,15,15")
                .append("^FD")
                .append(barcode)
                .append("^FS\n");

        // =====================================================
        // BUSINESS NAME
        // =====================================================

        String businessText =
                limit(businessName, 22);

        int businessX =
                centerX - ((businessText.length() * 6) / 2);

        zpl.append("^FO")
                .append(businessX)
                .append(",")
                .append(startY + topOffset + 130)
                .append("^A0N,13,13")
                .append("^FD")
                .append(businessText)
                .append("^FS\n");
    }

    // =========================================================
    // PRINT TO ZEBRA
    // =========================================================

    private void printToZebra(
            String printerName,
            String zpl
    ) throws Exception {

        PrintService[] services =
                PrintServiceLookup.lookupPrintServices(
                        null,
                        null
                );

        PrintService printer = null;

        for (PrintService service : services) {

            if (service.getName()
                    .equalsIgnoreCase(printerName)) {

                printer = service;
                break;
            }
        }

        if (printer == null) {

            throw new Exception(
                    "Printer not found : "
                            + printerName
            );
        }

        DocPrintJob job =
                printer.createPrintJob();

        byte[] bytes =
                zpl.getBytes(StandardCharsets.UTF_8);

        Doc doc = new SimpleDoc(
                new ByteArrayInputStream(bytes),
                DocFlavor.INPUT_STREAM.AUTOSENSE,
                null
        );

        job.print(doc, null);
    }

    // =========================================================
    // LIMIT TEXT
    // =========================================================

    private String limit(
            String text,
            int max
    ) {

        if (text == null) {
            return "";
        }

        if (text.length() <= max) {
            return text;
        }

        return text.substring(0, max);
    }

    // =========================================================
    // FORMAT PRICE
    // =========================================================

    private String formatPrice(double value) {

        return String.format("%.2f", value);
    }
}