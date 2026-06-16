package serverResponseDataModel;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author MalithWanniarachchi
 */
public class ServerResponse {
    public String response_code;
    public String response_msg;
    public JsonNode data;
}
