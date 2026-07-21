package control;

import appDataModels.InstituteModel;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import serverDataModels.OnlineOrder;

/**
 * Zebra shipping label printer — 4 inch × 5 inch labels at 203 DPI.
 *
 * Label layout:
 *   SHIP TO:
 *     <customer_name>
 *     <address_line1>
 *     <address_line2>
 *     <address_line3>
 *     <district_name>, <province_name>
 *     <contact_number>
 *   ──────────────────────
 *   ORDER NUMBER: <order_no>
 *   ──────────────────────
 *   RETURN ADDRESS:
 *     <institute name>
 *     <institute address>
 */
public class ZebraShippingLabelPrinter {

    // 4 inch × 5 inch at 203 DPI
    private static final int LABEL_WIDTH  = 812;   // 4 * 203
    private static final int LABEL_HEIGHT = 1015;  // 5 * 203

    private static final int MARGIN_X = 20;

    public void print(List<OnlineOrder> orders, InstituteModel institute) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        String printerName = ApplicationDataManager.getInstance().getLabelPrinterName();
        for (OnlineOrder order : orders) {
            try {
                String zpl = buildLabel(order, institute);
                printToZebra(printerName, zpl);
            } catch (Exception e) {
                EasyPosLogger.getInstance().error("SHIPPING LABEL PRINT ERROR", e);
            }
        }
    }

    private String buildLabel(OnlineOrder order, InstituteModel institute) {
        StringBuilder zpl = new StringBuilder();

        zpl.append("^XA\n");
        zpl.append("^PW").append(LABEL_WIDTH).append("\n");
        zpl.append("^LL").append(LABEL_HEIGHT).append("\n");
        zpl.append("^MNY\n");
        zpl.append("^MMT\n");
        zpl.append("^PR4\n");
        zpl.append("^MD15\n");

        int x = MARGIN_X;
        int y = 20;

        // ── SHIP TO header ──
        zpl.append(field(x, y, 30, 30, "SHIP TO:"));
        y += 38;

        // Customer name
        zpl.append(field(x + 10, y, 28, 28, safe(order.customer_name)));
        y += 34;

        // Address lines
        y = appendIfNotEmpty(zpl, x + 10, y, 24, order.address_line1);
        y = appendIfNotEmpty(zpl, x + 10, y, 24, order.address_line2);
        y = appendIfNotEmpty(zpl, x + 10, y, 24, order.address_line3);

        // District, Province
        String region = buildRegion(order.district_name, order.province_name);
        y = appendIfNotEmpty(zpl, x + 10, y, 24, region);

        // Primary contact number
        zpl.append(field(x + 10, y, 24, 24, safe(order.contact_number)));
        y += 30;

        // Secondary contact number (if different)
        if (notEmpty(order.contact_number_2) && !order.contact_number_2.equals(order.contact_number)) {
            zpl.append(field(x + 10, y, 24, 24, safe(order.contact_number_2)));
            y += 30;
        }

        // ── Divider ──
        y += 6;
        zpl.append(divider(x, y));
        y += 14;

        // ── ORDER NUMBER ──
        zpl.append(field(x, y, 26, 26, "ORDER NUMBER: " + safe(order.order_no)));
        y += 36;

        // ── PAYMENT METHOD ──
        String paymentLabel = "CODS".equalsIgnoreCase(order.mop) ? "COD - Cash On Delivery" : "Paid";
        zpl.append(field(x, y, 26, 26, paymentLabel));
        y += 36;

        // ── Divider ──
        y += 4;
        zpl.append(divider(x, y));
        y += 14;

        // ── RETURN ADDRESS header ──
        zpl.append(field(x, y, 24, 24, "RETURN ADDRESS:"));
        y += 30;

        // Institute name
        String returnName = notEmpty(institute.getPrintBusinessName())
                ? institute.getPrintBusinessName()
                : institute.getBusinessName();
        zpl.append(field(x + 10, y, 22, 22, safe(returnName)));
        y += 28;

        // Institute sub name (if present)
        if (notEmpty(institute.getPrintBusinessSubName())) {
            zpl.append(field(x + 10, y, 22, 22, safe(institute.getPrintBusinessSubName())));
            y += 28;
        }

        // Institute address lines
        y = appendIfNotEmpty(zpl, x + 10, y, 22, institute.getPrintBusinessAddressLine1());
        y = appendIfNotEmpty(zpl, x + 10, y, 22, institute.getPrintBusinessAddressLine2());
        y = appendIfNotEmpty(zpl, x + 10, y, 22, institute.getPrintBusinessAddressLine3());

        // Institute contact
        if (notEmpty(institute.getPrintBusinessContact())) {
            zpl.append(field(x + 10, y, 22, 22, safe(institute.getPrintBusinessContact())));
        }

        zpl.append("^XZ");
        return zpl.toString();
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
