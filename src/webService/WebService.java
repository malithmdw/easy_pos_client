package webService;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import serverResponseDataModel.CommonResponse;
import serverResponseDataModel.ServerResponse;

/**
 *
 * @author MalithWanniarachchi
 */
public class WebService {
    
    private static final String POST_URL = "https://moosero.lk/EASYPOS-API-PHP-master/api";
    public static final String IMAGE_FOLDER_PATH = "https://moosero.lk/EASYPOS-images";
    private static final String POST_URL2 = "https://slbglk.net/SLBG-API-PHP-master/access_api/getAllData/getBankList.php";
    private static final String USER_AGENT = "Mozilla/5.0";
    
    private static final String SERVER_DB_USR = "admin";
    private static final String SERVER_DB_PWD = "2u#*BmNnMR";
    private static final String SERVER_DB_NAME = "u601745278_easy_pos";
    
    public CommonResponse sendPOSTRequest(String apiPath, Map<String, String> header, Object bodyData) {
        CommonResponse commonResponse = new CommonResponse();
        
        try {
            URL obj = new URL(POST_URL + "/" + apiPath);
            ObjectMapper mapper = new ObjectMapper();
            
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/json"); // important
            
            for (Map.Entry<String, String> entry : header.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());                
            }
            
            // Set timeout (30s connect + 30s read)
            con.setConnectTimeout(30_000); // max 30s to connect
            con.setReadTimeout(30_000);    // max 30s waiting for response
            
            //String requestBody = toJson(bodyData);
            String requestBody = mapper.writeValueAsString(bodyData);
            
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300)
                    ? con.getInputStream() : con.getErrorStream();

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            String result = response.toString();
            
            ServerResponse serverResponse = mapper.readValue(result, ServerResponse.class);
//            System.out.println(serverResponse.response_code);
//            System.out.println(serverResponse.response_msg);
            
            commonResponse.setData(serverResponse.data);
            commonResponse.setAPIResponse(ResponseCodes.get(serverResponse.response_code));
        } catch (SocketTimeoutException timeoutEx) {
            commonResponse.setAPIResponse(ResponseCodes.get("97"));//Request Timeout
        } catch (UnknownHostException | ConnectException networkEx) {
            commonResponse.setAPIResponse(ResponseCodes.get("96")); // No Network / Internet
        } catch (IOException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("98"));//IO Error
        }
        
        return commonResponse;
    }
    
    public CommonResponse sendGETRequest(String apiPath, Map<String, String> header) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            URL obj = new URL(POST_URL + "/" + apiPath);
            ObjectMapper mapper = new ObjectMapper();

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/json");

            for (Map.Entry<String, String> entry : header.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
            
            // Set timeout (30s connect + 30s read)
            con.setConnectTimeout(30_000); // max 30s to connect
            con.setReadTimeout(30_000);    // max 30s waiting for response

            int responseCode = con.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300)
                    ? con.getInputStream() : con.getErrorStream();

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            String result = response.toString();

            ServerResponse serverResponse = mapper.readValue(result, ServerResponse.class);

            commonResponse.setData(serverResponse.data);
            commonResponse.setAPIResponse(ResponseCodes.get(serverResponse.response_code));

        } catch (SocketTimeoutException timeoutEx) {
            commonResponse.setAPIResponse(ResponseCodes.get("97"));//Request Timeout
        } catch (UnknownHostException | ConnectException networkEx) {
            commonResponse.setAPIResponse(ResponseCodes.get("96")); // No Network / Internet
        } catch (IOException ex) {
            commonResponse.setAPIResponse(ResponseCodes.get("98"));//IO Error
        }

        return commonResponse;
    }

    
    private static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return "\"" + escape((String) obj) + "\"";
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            Map<?, ?> map = (Map<?, ?>) obj;
            Iterator<?> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
                sb.append("\"").append(escape(entry.getKey().toString())).append("\":");
                sb.append(toJson(entry.getValue()));
                if (it.hasNext()) sb.append(",");
            }
            sb.append("}");
            return sb.toString();
        }
        if (obj instanceof Collection) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Iterator<?> it = ((Collection<?>) obj).iterator();
            while (it.hasNext()) {
                sb.append(toJson(it.next()));
                if (it.hasNext()) sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        }
        // fallback for unknown objects
        return "\"" + escape(obj.toString()) + "\"";
    }
    
    private static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
    
    public static Map<String, Object> parseJson(String json) {
        Map<String, Object> map = new HashMap<>();

        // remove outer { }
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        // split top-level by commas not inside braces
        int braceLevel = 0;
        StringBuilder token = new StringBuilder();
        List<String> parts = new ArrayList<>();

        for (char c : json.toCharArray()) {
            if (c == '{') braceLevel++;
            if (c == '}') braceLevel--;
            if (c == ',' && braceLevel == 0) {
                parts.add(token.toString());
                token.setLength(0);
            } else {
                token.append(c);
            }
        }
        if (token.length() > 0) parts.add(token.toString());

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            String key = kv[0].trim().replace("\"", "");
            String value = kv[1].trim();

            if (value.startsWith("{")) {
                map.put(key, parseJson(value)); // recursion for nested object
            } else {
                value = value.replace("\"", "");
                map.put(key, value);
            }
        }

        return map;
    }
    
    public static void printMap(Map<?, ?> map, String indent) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                System.out.println(indent + entry.getKey() + " : ");
                printMap((Map<?, ?>) entry.getValue(), indent + "  ");
            } else {
                System.out.println(indent + entry.getKey() + " : " + entry.getValue());
            }
        }
    }
}


