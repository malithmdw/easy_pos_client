
package easyPOS.customerdisplay;

import com.fazecast.jSerialComm.SerialPort;
import control.EasyPosLogger;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author malit
 */
public class CustomerPoleDisplay {

    private static final int LINE_WIDTH = 20;

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private CustomerPoleDisplay(){
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

    public void initializePortAndSendData(String port, String line1) {
        initializePortAndSendData(port, line1, null);
    }

    public void initializePortAndSendData(String port, String line1, String line2) {
        // Serial I/O blocks, so it must never run on the caller's thread (typically the
        // Swing EDT) - queue it on the single writer thread instead.
        queue.offer(() -> writeToDisplay(port, line1, line2));
    }

    private void writeToDisplay(String port, String line1, String line2) {
        // 1. Replace with the COM port you found in Device Manager
        SerialPort comPort = SerialPort.getCommPort(port);

        // 2. Set typical POSMAX terminal defaults (9600 8N1)
        comPort.setBaudRate(9600);
        comPort.setNumDataBits(8);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setParity(SerialPort.NO_PARITY);
        // Block until the bytes are actually handed to the device, instead of
        // guessing with a fixed sleep after the write.
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "Customer Pole Display Opening port: " + comPort.getSystemPortName());

        if (comPort.openPort()) {
            try (OutputStream out = comPort.getOutputStream()) {
                // Common ESC/POS Command Codes (Hexadecimal)
                byte[] INITIALIZE = { 0x1B, 0x40 };       // ESC @ (Initializes display)
                byte[] CLEAR_SCREEN = { 0x0C };           // FF (Clears screen & homes cursor)
                byte[] MOVE_LINE_2 = { 0x1B, 0x51, 0x42 }; // ESC Q B (Moves to line 2 on many displays)

                // Send Initialization & Clear
                out.write(INITIALIZE);
                out.write(CLEAR_SCREEN);
                out.flush();

                // Write Line 1 text
                out.write(formatLine(line1).getBytes(StandardCharsets.US_ASCII));
                out.flush();

                if (line2 != null) {
                    // Move to Line 2 and write
                    out.write(MOVE_LINE_2);
                    out.write(formatLine(line2).getBytes(StandardCharsets.US_ASCII));
                    out.flush();
                }

                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "Data sent successfully!");

            } catch (Exception e) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            } finally {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "Customer Pole Display Closing port: " + comPort.getSystemPortName());
                comPort.closePort();
            }
        } else {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, "Failed to open the COM port: " + port);
        }
    }

    private String formatLine(String value) {
        String text = value == null ? "" : value;
        if (text.length() >= LINE_WIDTH) {
            return text.substring(0, LINE_WIDTH);
        }
        StringBuilder padded = new StringBuilder(text);
        while (padded.length() < LINE_WIDTH) {
            padded.append(' ');
        }
        return padded.toString();
    }
}
