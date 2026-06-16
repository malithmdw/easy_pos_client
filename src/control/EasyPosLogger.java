
package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author malit
 */
public class EasyPosLogger {

    public enum LogLevel{ INFO, DEBUG, ERROR;}
    
    private static EasyPosLogger instance;

    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private BufferedWriter writer;
    private String currentDate;

    private static final String LOG_DIR = "C:/EasyPOS/Logs/";
    private static final int MAX_LOG_DAYS = 30;

    private EasyPosLogger() {

        File folder = new File(LOG_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        rotateLogFile();
        startWorkerThread();
        cleanupOldLogs();
    }

    public static synchronized EasyPosLogger getInstance() {

        if (instance == null) {
            instance = new EasyPosLogger();
        }

        return instance;
    }

    private void startWorkerThread() {

        Thread worker = new Thread(() -> {

            while (true) {

                try {

                    String log = queue.take();

                    rotateLogFile();

                    synchronized (this) {

                        writer.write(log);
                        writer.newLine();
                        writer.flush();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        worker.setDaemon(true);
        worker.start();
    }

    private void rotateLogFile() {

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (!today.equals(currentDate)) {

            try {

                if (writer != null) {
                    writer.close();
                }

                currentDate = today;

                String fileName = LOG_DIR + "application-" + today + ".log";

                writer = new BufferedWriter(new FileWriter(fileName, true));

            } catch (IOException e) {
            }

        }

    }

    private void cleanupOldLogs() {

        File dir = new File(LOG_DIR);

        File[] files = dir.listFiles();

        if (files == null) return;

        long now = System.currentTimeMillis();

        for (File file : files) {

            long diff = now - file.lastModified();

            long days = diff / (1000 * 60 * 60 * 24);

            if (days > MAX_LOG_DAYS) {
                file.delete();
            }

        }

    }

    private String format(String level, String message) {

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date());

        return time + " [" + level + "] " + message;
    }

    private void log(String level, String message) {

        queue.offer(format(level, message));

    }
    
    public void log(LogLevel logLevel, String message){
        String level;
        
        switch (logLevel) {
            case INFO:
                level = "INFO";
                break;
            case DEBUG:
                level = "DEBUG";
                break;
            case ERROR:
                level = "ERROR";
                break;
            default:
                level = "UNCLASIFIED";
        }
        
        log(level, message);
    }

    public void error(String message, Exception ex) {

        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));

        log("ERROR", message + "\n" + sw.toString());

    }
}
