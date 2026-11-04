package webService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import control.ApplicationDataManager;
import control.EasyPosLogger;
import control.RuntimeDataManager;
import serverDataModels.BillPaymentCategoryModel;
import serverDataModels.BillPaymentProviderModel;
import serverResponseDataModel.BillPaymentHistoryResponse;
import serverResponseDataModel.BillPaymentLoginResponse;
import serverResponseDataModel.BillPaymentResult;
import serverResponseDataModel.CommonResponse;

/**
 * Singleton API client for the EasyReload bill-payment backend.
 *
 * Flow on startup:
 *   1. login()  — authenticates and stores the JWT in RuntimeData.billPaymentJwt
 *   2. getBillerCategory()  — fetches categories and caches them in RuntimeData
 *   3. getBillers()         — fetches providers  and caches them in RuntimeData
 *
 * Subsequent calls attach "Authorization: Bearer <jwt>". If the server responds with
 * an auth error (ResponseCode "04") the class silently re-logs-in once and retries.
 */
public class BillPaymentServerAPIConnection {

    private static BillPaymentServerAPIConnection INSTANCE;

    private BillPaymentServerAPIConnection() {}

    public static BillPaymentServerAPIConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BillPaymentServerAPIConnection();
        }
        return INSTANCE;
    }

    enum API_NAME {
        BILL_PAYMENT_LOGIN("login.php"),
        BILL_PAYMENT_GET_CATEGORY("get-category.php"),
        BILL_PAYMENT_GET_PROVIDERS("get-providers.php"),
        BILL_PAYMENT_HISTORY("payment-history.php"),
        BILL_PAYMENT_PAY("process-payment.php");

        private final String name;

        API_NAME(String name) { this.name = name; }

        public String getAPIName() { return name; }
    }

    // -------------------------------------------------------------------------
    // Header generation
    // -------------------------------------------------------------------------

    private Map<String, String> generateHeader() {
        Map<String, String> header = new HashMap<>();
        String jwt = RuntimeDataManager.getInstance().getRuntimeData().getBillPaymentJwt();
        if (jwt != null && !jwt.isEmpty()) {
            header.put("Authorization", "Bearer " + jwt);
        }
        return header;
    }

    // -------------------------------------------------------------------------
    // JWT retry helpers
    // -------------------------------------------------------------------------

    private boolean isJwtError(CommonResponse response) {
        return "04".equals(response.getAPIResponse().getResponseCode());
    }

    private boolean refreshToken() {
        RuntimeDataManager.getInstance().getRuntimeData().setBillPaymentJwt(null);
        String user = ApplicationDataManager.getInstance().getBillPaymentUser();
        String pwd  = ApplicationDataManager.getInstance().getBillPaymentPassword();
        if (user == null || user.isEmpty() || pwd == null || pwd.isEmpty()) {
            return false;
        }
        CommonResponse loginResp = login(user, pwd);
        return loginResp.getAPIResponse().isSuccess();
    }

    private CommonResponse callWithRetry(java.util.function.Supplier<CommonResponse> apiCall) {
        CommonResponse response = apiCall.get();
        if (isJwtError(response)) {
            CommonResponse original = response;
            if (refreshToken()) {
                response = apiCall.get();
                if (isJwtError(response)) {
                    return original;
                }
            } else {
                return original;
            }
        }
        return response;
    }

    // -------------------------------------------------------------------------
    // API methods
    // -------------------------------------------------------------------------

    /**
     * Authenticates with the EasyReload API using the bill-payment credentials
     * stored in ApplicationDataManager. Stores the returned JWT in RuntimeData.
     */
    public CommonResponse login(String userName, String password) {
        WebService webService = new WebService(true);
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("username", userName);   // EasyReload API field name is "username"
        bodyData.put("password", password);

        CommonResponse commonResponse = webService.sendBillPaymentPOSTRequest(
                API_NAME.BILL_PAYMENT_LOGIN.getAPIName(), new HashMap<>(), bodyData);

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                BillPaymentLoginResponse loginResponse = mapper.treeToValue(
                        (JsonNode) commonResponse.getData(), BillPaymentLoginResponse.class);
                commonResponse.setData(loginResponse);
                RuntimeDataManager.getInstance().getRuntimeData()
                        .setBillPaymentJwt(loginResponse.accessToken);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("BillPayment login parse error", ex);
            }
        }
        return commonResponse;
    }

    /**
     * Fetches bill-payment categories. On success, result is cached in
     * RuntimeData.billPaymentCategories for use by the UI without further API calls.
     */
    public CommonResponse getBillerCategory() {        
        WebService webService = new WebService(true);
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendBillPaymentGETRequest(API_NAME.BILL_PAYMENT_GET_CATEGORY.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<BillPaymentCategoryModel> categories = mapper.convertValue(
                        commonResponse.getData(),
                        new TypeReference<List<BillPaymentCategoryModel>>() {});
                commonResponse.setData(categories);
                RuntimeDataManager.getInstance().getRuntimeData().setBillPaymentCategories(categories);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("BillPayment get-category parse error", ex);
            }
        }
        return commonResponse;
    }

    /**
     * Fetches bill-payment providers (billers). On success, result is cached in
     * RuntimeData.billPaymentProviders for use by the UI without further API calls.
     */
    public CommonResponse getBillers() {        
        WebService webService = new WebService(true);
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendBillPaymentGETRequest(API_NAME.BILL_PAYMENT_GET_PROVIDERS.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<BillPaymentProviderModel> providers = mapper.convertValue(
                        commonResponse.getData(),
                        new TypeReference<List<BillPaymentProviderModel>>() {});
                commonResponse.setData(providers);
                RuntimeDataManager.getInstance().getRuntimeData().setBillPaymentProviders(providers);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("BillPayment get-providers parse error", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getHistory(String fromDate, String toDate) {
        WebService webService = new WebService(true);
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("fromDate", fromDate);
        bodyData.put("toDate", toDate);
        bodyData.put("page", 1);
        bodyData.put("pageSize", 50);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendBillPaymentPOSTRequest(API_NAME.BILL_PAYMENT_HISTORY.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                BillPaymentHistoryResponse historyResponse = mapper.treeToValue(
                        (JsonNode) commonResponse.getData(), BillPaymentHistoryResponse.class);
                commonResponse.setData(historyResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("BillPayment history parse error", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse payBill(String categoryId, String serviceProviderId,
                                   String accountNumber, double amount,
                                   String packageId, String clientRef) {
        WebService webService = new WebService(true);
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("clientRef", clientRef);
        bodyData.put("categoryId", categoryId);
        bodyData.put("serviceProviderId", serviceProviderId);
        bodyData.put("accountNumber", accountNumber);
        bodyData.put("amount", amount);
        if (packageId != null && !packageId.isEmpty()) {
            bodyData.put("packageId", packageId);
        }

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendBillPaymentPOSTRequest(API_NAME.BILL_PAYMENT_PAY.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                BillPaymentResult result = mapper.treeToValue(
                        (JsonNode) commonResponse.getData(), BillPaymentResult.class);
                commonResponse.setData(result);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("BillPayment process-payment parse error", ex);
            }
        }
        return commonResponse;
    }
}
