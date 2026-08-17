package easyPOS.customerdisplay;

import com.fazecast.jSerialComm.SerialPort;
import control.EasyPosLogger;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Driver for an 8-digit 7-segment LED customer pole display
 * (POSMAX "LED8" type: fixed Price / Total / Collect / Change labels on the glass).
 *
 * IMPORTANT: This hardware is NUMERIC-ONLY. It cannot render letters, and it does
 * NOT understand ESC/POS control bytes (ESC @, FF, ESC Q B, column padding).
 * Sending any of those produces garbled segments. So we send digits only,
 * terminated by CR (0x0D).
 *
 * @author malit
 */
public class CustomerPoleDisplay {

    // Max number of digit tubes on the unit (8 on this model). Values longer than
    // this are trimmed so we never overrun the field.
    private static final int MAX_DIGITS = 8;

    // Frame terminator most LED8 units expect. If your unit needs a different
    // terminator (or a leading field byte), change it here in ONE place.
    private static final byte[] TERMINATOR = { 0x0D }; // CR

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private CustomerPoleDisplay() {
        startWorker();
    }

    private static CustomerPoleDisplay INSTANCE;

    public static CustomerPoleDisplay getInstance(String port) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerPoleDisplay();
        }
        return INSTANCE;
    }

    private void startWorker() {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    queue.take().run(); // waits if empty
                } catch (InterruptedException e) {
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
                }
            }
        });

        worker.setName("CustomerPoleDisplay-Writer");
        worker.setDaemon(true);
        worker.start();
    }


    /**
     * line2 is accepted for backwards compatibility but IGNORED — an LED8 has a
     * single numeric field. Only line1's numeric content is displayed.
     */
    public void initializePortAndSendData(String port, String line) {
        // Serial I/O blocks, so it must never run on the caller's thread (typically the
        // Swing EDT) - queue it on the single writer thread instead.
        
        // TEMPEROARY DISABLED
//        queue.offer(new Runnable() {
//            @Override
//            public void run() {
//                writeToDisplay(port, line);
//            }
//        });
    }

    /**
     * Sends the startup / idle value ("0"). Call this once when the app opens,
     * after the display finishes its power-on self-test (the "8.8.8.8.8.8.8.8"
     * all-segments flash).
     */
    public void showStartupValue(String port) {
        initializePortAndSendData(port, "0");
    }

    private void writeToDisplay(String port, String value) {
        if (value == null || value.isEmpty()) {
            value = "0.00";
        }
        SerialPort comPort = SerialPort.getCommPort(port);

        // POSMAX terminal defaults: 9600 8N1
        comPort.setBaudRate(9600);
        comPort.setNumDataBits(8);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setParity(SerialPort.NO_PARITY);
        // Block until the bytes are actually handed to the device.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO,
                "Customer Pole Display Opening port: " + comPort.getSystemPortName());

        if (comPort.openPort()) {
            try (OutputStream out = comPort.getOutputStream()) {
                String digits = toNumeric(value);

                // Digits only, then CR. No ESC/POS init, no clear, no line-2 move.
                out.write(digits.getBytes(StandardCharsets.US_ASCII));
                out.write(TERMINATOR);
                out.flush();

                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO,
                        "Data sent successfully: [" + digits + "]");

            } catch (Exception e) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            } finally {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO,
                        "Customer Pole Display Closing port: " + comPort.getSystemPortName());
                comPort.closePort();
            }
        } else {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR,
                    "Failed to open the COM port: " + port);
        }
    }

    /**
     * Keeps only characters the tubes can actually show: digits, decimal point,
     * and a leading minus. Everything else (letters like "Total", spaces,
     * currency symbols, thousands separators) is stripped.
     *
     * The decimal point does not consume a digit tube on 7-segment displays, so
     * only the actual digits are counted against MAX_DIGITS.
     */
    private String toNumeric(String value) {
        if (value == null) {
            return "0";
        }

        boolean negative = value.trim().startsWith("-");

        // Strip everything except digits and the decimal point.
        String cleaned = value.replaceAll("[^0-9.]", "");

        // Collapse to a single decimal point (keep the first one).
        int firstDot = cleaned.indexOf('.');
        if (firstDot >= 0) {
            String intPart = cleaned.substring(0, firstDot).replace(".", "");
            String fracPart = cleaned.substring(firstDot + 1).replace(".", "");
            cleaned = intPart + "." + fracPart;
        }

        if (cleaned.isEmpty() || cleaned.equals(".")) {
            cleaned = "0";
        }

        // Trim to the number of physical digit tubes (count digits only).
        cleaned = trimToDigits(cleaned, MAX_DIGITS);

        return negative ? "-" + cleaned : cleaned;
    }

    private String trimToDigits(String s, int maxDigits) {
        StringBuilder sb = new StringBuilder();
        int digitCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '.') {
                sb.append(c); // decimal point doesn't use a tube
            } else {
                if (digitCount >= maxDigits) {
                    break;
                }
                sb.append(c);
                digitCount++;
            }
        }
        return sb.toString();
    }
}