
package webService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseCodes {

    public static class ApiResponse {
        private final int status;
        private final String responseCode;
        private final String message;
        private final String messageSinhala;

        public ApiResponse(int status, String responseCode, String message, String messageSinhala) {
            this.status = status;
            this.message = message;
            this.messageSinhala = messageSinhala;
            this.responseCode = responseCode;
        }
        
        public boolean isSuccess() {
            return (("00").equals(responseCode));
        }

        public int getStatus() {
            return status;
        }
        
        public String getResponseCode() {
            return responseCode;
        }

        public String getMessage() {
            return message;
        }

        public String getMessageSinhala() {
            return messageSinhala;
        }
        
        public String getMessageWithErrorCode() {
            if (("00").equals(responseCode)) {
                return message;
            }
            return "ERROR " + responseCode + " - " + message;
        }

        public String getMessageWithErrorCodeSinhala() {
            if (("00").equals(responseCode)) {
                return messageSinhala;
            }
            return "ERROR " + responseCode + " - " + messageSinhala;
        }
        
        @Override
        public String toString() {
            return "ApiResponse{status=" + status + ", message='" + message + "'}";
        }
    }

    private static final Map<String, ApiResponse> RESPONSE_MAP;

    static {
        Map<String, ApiResponse> map = new HashMap<>();
        map.put("00", new ApiResponse(200, "00", "Success", "සාර්ථකයි"));
        map.put("01", new ApiResponse(409, "01", "Duplicate entry", "වාර්තාව දැනටමත් පවතී"));
        map.put("02", new ApiResponse(409, "02", "Duplicate entry or Database key violation", "අනුපිටපත් ඇතුළත් කිරීම හෝ දත්ත සමුදා යතුර උල්ලංඝනය කිරීම"));
        map.put("03", new ApiResponse(409, "03", "Database Error", "දත්ත සමුදා දෝෂය"));
        map.put("04", new ApiResponse(401, "04", "Authentication failure", "සත්‍යාපනය අසාර්ථක වීම"));
        map.put("05", new ApiResponse(500, "05", "System Error", "පද්ධති දෝෂය"));
        map.put("06", new ApiResponse(403, "06", "Permission denied", "අවසරය ප්රතික්ෂේප කෙරිණි"));
        map.put("07", new ApiResponse(404, "07", "Resource not found", "සම්පත් හමු නොවීය"));
        map.put("08", new ApiResponse(400, "08", "Missing or invalid fields", "යවන ලද දත්ත අවලංගු හෝ අසම්පූර්ණයි."));
        map.put("09", new ApiResponse(422, "09", "Business rule violation", "ව්යාපාර රීති උල්ලංඝනය කිරීම"));
        map.put("10", new ApiResponse(429, "10", "Rate limit exceeded", "අනුපාත සීමාව ඉක්මවා ඇත"));
        map.put("11", new ApiResponse(503, "11", "Temporary server overload", "තාවකාලික අන්තර්ජාල සේවාදායක අධි බර"));
        map.put("12", new ApiResponse(502, "12", "Upstream error", "Upstream දෝෂයකි"));
        map.put("13", new ApiResponse(500, "13", "Server internal error", "සේවාදායකයේ අභ්‍යන්තර දෝෂයකි"));
        map.put("14", new ApiResponse(409, "14", "Data conflict", "දත්ත නොගැලපේ"));
        map.put("15", new ApiResponse(400, "15", "Invalid data format", "වලංගු නොවන දත්ත ආකෘතිය"));
        map.put("16", new ApiResponse(500, "16", "Transaction rolled back", "ගනුදෙනුව ආපසු හරවා යවා ඇත"));
        map.put("17", new ApiResponse(503, "17", "Sync queue full", "සමමුහුර්ත පෝලිම පිරී ඇත"));
        map.put("18", new ApiResponse(400, "18", "Unsupported API version", "සහාය නොදක්වන API අනුවාදය"));
        map.put("19", new ApiResponse(409, "19", "Nothing to update", "යාවත්කාලීන කිරීමට කිසිවක් නැත"));
        map.put("20", new ApiResponse(409, "20", "Data not found", "දත්ත හමු නොවීය"));
        
        
        map.put("51", new ApiResponse(409, "51", "Invalid Login Credentials", "වලංගු නොවන පිවිසුම් උත්සාහයක්"));
        
        map.put("95", new ApiResponse(408, "95", "Error while converting server data to local data model", "අන්තර්ජාල දත්ත, පද්ධති දත්ත ආකෘතියට පරිවර්තනය කිරීමේදී දෝෂයකි"));
        map.put("96", new ApiResponse(408, "96", "Network unavailable", "අන්තර්ජාලයට සම්බන්ධ වීමට නොහැකි විය"));
        map.put("97", new ApiResponse(408, "97", "Request Timeout", "ඉල්ලීම් කල් ඉකුත් වී ඇත"));
        map.put("98", new ApiResponse(500, "98", "IO Error", "ආදාන / ප්‍රතිදාන දෝෂයකි"));
        map.put("99", new ApiResponse(500, "99", "Undefined Response", "නිර්වචනය නොකළ ප්‍රතිචාරයක්"));
        
        RESPONSE_MAP = Collections.unmodifiableMap(map);
    }

    public static ApiResponse get(String code) {
        ApiResponse res = RESPONSE_MAP.get(code);
        if (res == null) {
            return RESPONSE_MAP.get("99");
        }
        return res;
    }
}