
package serverResponseDataModel;

import webService.ResponseCodes;

/**
 *
 * @author MalithWanniarachchi
 */
public class CommonResponse {

    private ResponseCodes.ApiResponse aPIResponse;
    private Object data;
    
    /**
     * @return the aPIResponse
     */
    public ResponseCodes.ApiResponse getAPIResponse() {
        return aPIResponse;
    }

    /**
     * @param aPIResponse the aPIResponse to set
     */
    public void setAPIResponse(ResponseCodes.ApiResponse aPIResponse) {
        this.aPIResponse = aPIResponse;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }
}
