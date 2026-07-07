
package webService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import appDataModels.APIHeaderData;
import control.ApplicationDataManager;
import control.EasyPosLogger;
import control.RuntimeDataManager;
import java.util.ArrayList;
import serverDataModels.Category;
import serverDataModels.Customer;
import serverDataModels.CustomerLedger;
import serverDataModels.DiscountRule;
import serverDataModels.Institute;
import serverDataModels.Item;
import serverDataModels.MeasureUnit;
import serverDataModels.PurchaseInvoice;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleReturn;
import serverDataModels.StockWastage;
import serverDataModels.Supplier;
import serverDataModels.Terminal;
import serverDataModels.Voucher;
import serverResponseDataModel.CommonResponse;
import serverResponseDataModel.InsertRecordResponse;
import serverResponseDataModel.LoginResponse;

/**
 *
 * @author MalithWanniarachchi
 */
public class ServerAPIConnection {

    private static ServerAPIConnection INSTANCE;
    private static APIHeaderData aPIHeaderData;

    private ServerAPIConnection() {}

    public static ServerAPIConnection getInstance(APIHeaderData aPIHeaderData) {
        ServerAPIConnection.aPIHeaderData = aPIHeaderData;
        if (INSTANCE == null) {
            INSTANCE = new ServerAPIConnection();
        }
        return INSTANCE;
    }

    enum API_NAME
    {
        LOGIN("login.php"),
        SALE("add-sale-invoice.php"),
        SALE_RETURN("add-sale-return.php"),
        GET_SALE_INVOICE("get-sale-invoice-data.php"),
        GET_CUSTOMERS("get-customers.php"),
        GET_CUSTOMER("get-customer.php"),
        GET_VOUCHER("get-voucher-details.php"),
        CUSTOMER_TRANSACT("add-customer-transact.php"),
        GET_DISCOUNT_RULES("get-discount-rules.php"),
        GET_CUSTOMER_TRANSACTION_HISTORY("get-customer-transaction-history.php"),
        ADD_CUSTOMER("add-customer.php"),
        GET_ALL_ITEM_STOCK("get-item-stock.php"),
        GET_ALL_SUPPLIERS("get-suppliers.php"),
        ADD_ITEM("add-item.php"),
        EDIT_ITEM("update-item.php"),
        DELETE_ITEM("delete-item.php"),
        GET_ITEM("get-item.php"),
        ADD_STOCK_WASTAGE("add-stock-wastage.php"),
        BATCH_PRICE_UPDATE("batch-price-update.php"),
        GET_INSTITUTE("get-institute-data.php"),
        GET_CATEGORIES("get-category-data.php"),
        GET_TERMINAL("get-terminal-data.php"),
        ADD_PURCHASING_INVOICE("add-purchasing-invoice.php"),
        PROCESS_PURCHASING_INVOICE("process-perchasing-invoice.php"),
        GET_MEASURE_UNITS_DATA("get-measure-units.php"),
        GET_PENDING_PURCHASING_INVOICES("get-pending-purchasing-invoices.php"),
        UPDATE_BATCH_SELLING_PRICES("update-batch-selling-price.php"),
        REDEEM_VOUCHER("redeem-voucher.php");

        private final String name;

        API_NAME(String name) {
            this.name = name;
        }

        public String getAPIName() {
            return name;
        }
    }

    private Map<String, String> generateHeader() {
        Map<String, String> header = new HashMap<>();

        header.put("institute_id", aPIHeaderData.getInstituteId());
        header.put("terminal_id", aPIHeaderData.getTerminalId());

        String authToken = RuntimeDataManager.getInstance().getRuntimeData().getAuthToken();
        if (authToken != null && !authToken.isEmpty()) {
            header.put("Authorization", "Bearer " + authToken);
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
        appDataModels.UserAccountModel loggedInUser = ApplicationDataManager.getInstance().getLoggedInUser();
        if (loggedInUser == null) {
            return false;
        }
        CommonResponse loginResp = login(loggedInUser.getUserName(), loggedInUser.getPassword());
        return loginResp.getAPIResponse().isSuccess();
    }

    /**
     * Executes the supplied API call. If the server returns a JWT error (04),
     * attempts one silent re-login and retries. On any failure the original
     * JWT-error response is returned so callers see a consistent error.
     */
    private CommonResponse callWithRetry(java.util.function.Supplier<CommonResponse> apiCall) {
        CommonResponse response = apiCall.get();
        if (isJwtError(response)) {
            CommonResponse originalResponse = response;
            if (refreshToken()) {
                response = apiCall.get();
                if (isJwtError(response)) {
                    return originalResponse;
                }
            } else {
                return originalResponse;
            }
        }
        return response;
    }

    // -------------------------------------------------------------------------
    // API methods
    // -------------------------------------------------------------------------

    public CommonResponse getAllItemStock() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_ALL_ITEM_STOCK.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<Item> items = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Item>>() {});
                commonResponse.setData(items);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getAllSuppliers() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_ALL_SUPPLIERS.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<Supplier> suppliers = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Supplier>>() {});
                commonResponse.setData(suppliers);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getTerminal() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_TERMINAL.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Terminal terminal = mapper.treeToValue((JsonNode) commonResponse.getData(), Terminal.class);
                commonResponse.setData(terminal);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getCustomers() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_CUSTOMERS.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<Customer> customers = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Customer>>() {});
                commonResponse.setData(customers);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getPendingPurchasingInvoices() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_PENDING_PURCHASING_INVOICES.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<PurchaseInvoice> purchaseInvoices = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<PurchaseInvoice>>() {});
                commonResponse.setData(purchaseInvoices);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getCustomer(String contactNumber) {
        WebService webService = new WebService();
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("contact_number", contactNumber);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.GET_CUSTOMER.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Customer customer = mapper.treeToValue((JsonNode) commonResponse.getData(), Customer.class);
                commonResponse.setData(customer);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getVoucher(String voucherId) {
        WebService webService = new WebService();
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("voucher_id", voucherId);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.GET_VOUCHER.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Voucher voucher = mapper.treeToValue((JsonNode) commonResponse.getData(), Voucher.class);
                commonResponse.setData(voucher);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getInstitute() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_INSTITUTE.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Institute institute = mapper.treeToValue((JsonNode) commonResponse.getData(), Institute.class);
                commonResponse.setData(institute);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getMeasureUnitsData() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_MEASURE_UNITS_DATA.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ArrayList<MeasureUnit> measureUnit = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<MeasureUnit>>() {});
                commonResponse.setData(measureUnit);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getCategories() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_CATEGORIES.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<Category> categories = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Category>>() {});
                commonResponse.setData(categories);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getDiscountRules() {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendGETRequest(API_NAME.GET_DISCOUNT_RULES.getAPIName(), generateHeader()));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<DiscountRule> discountRules = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<DiscountRule>>() {});
                commonResponse.setData(discountRules);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse login(String userName, String password) {
        WebService webService = new WebService();
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("user_name", userName);
        bodyData.put("password", password);

        CommonResponse commonResponse = webService.sendPOSTRequest(API_NAME.LOGIN.getAPIName(), generateHeader(), bodyData);

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                LoginResponse loginResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), LoginResponse.class);
                commonResponse.setData(loginResponse);
                RuntimeDataManager.getInstance().getRuntimeData().setAuthToken(loginResponse.auth_token);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse sale(SaleInvoice saleInvoice) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.SALE.getAPIName(), generateHeader(), saleInvoice));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse saleReturn(SaleReturn saleReturn) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.SALE_RETURN.getAPIName(), generateHeader(), saleReturn));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Voucher voucherForReturn = mapper.treeToValue((JsonNode) commonResponse.getData(), Voucher.class);
                commonResponse.setData(voucherForReturn);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getSaleInvoiceData(String invoiceNo) {
        WebService webService = new WebService();
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("invoice_no", invoiceNo);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.GET_SALE_INVOICE.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                SaleInvoice saleInvoice = mapper.treeToValue((JsonNode) commonResponse.getData(), SaleInvoice.class);
                commonResponse.setData(saleInvoice);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse addCustomerTransaction(String customerContactno, CustomerLedger customerLedger) {
        WebService webService = new WebService();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> bodyData = oMapper.convertValue(customerLedger, Map.class);
        bodyData.put("contact_number", customerContactno);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.CUSTOMER_TRANSACT.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getCustomerTransactionHistory(String customerContact, String dateFrom, String dateTo) {
        WebService webService = new WebService();
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("contact_number", customerContact);
        bodyData.put("date_from", dateFrom);
        bodyData.put("date_to", dateTo);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.GET_CUSTOMER_TRANSACTION_HISTORY.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<CustomerLedger> customerLedgers = mapper.convertValue(commonResponse.getData(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<CustomerLedger>>() {});
                commonResponse.setData(customerLedgers);
            } catch (IllegalArgumentException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse addCustomer(Customer customer) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.ADD_CUSTOMER.getAPIName(), generateHeader(), customer));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse editCustomer(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CommonResponse addItem(Item item) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.ADD_ITEM.getAPIName(), generateHeader(), item));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse editItem(Item item) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.EDIT_ITEM.getAPIName(), generateHeader(), item));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse deleteItem(long itemId) {
        WebService webService = new WebService();
        Map<String, Long> bodyData = new HashMap<>();
        bodyData.put("item_id", itemId);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.DELETE_ITEM.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse getItem(String barcode) {
        WebService webService = new WebService();
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("barcode", barcode);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.GET_ITEM.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Item item = mapper.treeToValue((JsonNode) commonResponse.getData(), Item.class);
                commonResponse.setData(item);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse addStockWastage(StockWastage stockWastage) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.ADD_STOCK_WASTAGE.getAPIName(), generateHeader(), stockWastage));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse batchPriceUpdate(long batchId,
            double newLabelPrice, double newDiscount,
            double newSellingPrice, double newWholeSalePrice) {
        WebService webService = new WebService();
        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("batch_id", batchId);
        bodyData.put("label_price", newLabelPrice);
        bodyData.put("discount", newDiscount);
        bodyData.put("selling_price", newSellingPrice);
        bodyData.put("wholesale_price", newWholeSalePrice);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.UPDATE_BATCH_SELLING_PRICES.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse addUpdatePurchasingInvoice(PurchaseInvoice purchaseInvoice) {
        WebService webService = new WebService();
        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.ADD_PURCHASING_INVOICE.getAPIName(), generateHeader(), purchaseInvoice));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }

    public CommonResponse redeemVoucher(String voucherId, String completedBy) {
        WebService webService = new WebService();
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("voucher_id", voucherId);
        bodyData.put("completed_by", completedBy);

        return callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.REDEEM_VOUCHER.getAPIName(), generateHeader(), bodyData));
    }

    public CommonResponse processCompletePurchasingInvoice(String systemInvoiceNumber) {
        WebService webService = new WebService();
        Map<String, String> bodyData = new HashMap<>();
        bodyData.put("system_invoice_no", systemInvoiceNumber);

        CommonResponse commonResponse = callWithRetry(() ->
                webService.sendPOSTRequest(API_NAME.PROCESS_PURCHASING_INVOICE.getAPIName(), generateHeader(), bodyData));

        if (commonResponse.getAPIResponse().isSuccess()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InsertRecordResponse insertRecordResponse = mapper.treeToValue((JsonNode) commonResponse.getData(), InsertRecordResponse.class);
                commonResponse.setData(insertRecordResponse);
            } catch (JsonProcessingException ex) {
                commonResponse.setAPIResponse(ResponseCodes.get("95"));
                EasyPosLogger.getInstance().error("", ex);
            }
        }
        return commonResponse;
    }
}
