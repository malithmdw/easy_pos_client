
package util;

/**
 *
 * @author malit
 */
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {

    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("https://clients3.google.com/generate_204");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");

            return conn.getResponseCode() == 204;

        } catch (Exception e) {
            return false;
        }
        // below code does not work for dialog wifi
//        try (Socket socket = new Socket()) {
//
//            // Google DNS (very reliable)
//            socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
//
//            return true;
//
//        } catch (IOException ex) {
//            return false;
//        }
    }
}
