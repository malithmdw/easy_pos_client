package serverResponseDataModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Maps the EasyReload API response envelope: {"status":"SUCCESS"|"ERROR", "message":"...", "data":...}
 * The EasyPOS API uses response_code/response_msg; EasyReload uses status/message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentServerResponse {
    public String status;
    public String message;
    public JsonNode data;
}
