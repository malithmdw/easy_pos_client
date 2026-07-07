package webService;

import appDataModels.APIHeaderData;
import appDataModels.UserAccountModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import serverDataModels.Customer;
import serverDataModels.CustomerLedger;
import serverDataModels.Item;
import serverDataModels.ItemStock;
import serverDataModels.PurchaseInvoice;
import serverDataModels.PurchaseItem;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverDataModels.SaleReturn;
import serverDataModels.SaleReturnItem;
import serverDataModels.StockWastage;
import serverDataModels.UserAccount;
import serverDataModels.Voucher;
import serverResponseDataModel.CommonResponse;
import serverResponseDataModel.InsertRecordResponse;
import serverResponseDataModel.LoginResponse;

import static org.junit.Assert.*;

/**
 * Integration test suite for ServerAPIConnection.
 *
 * Configure the constants below to match your test server environment before running.
 * Run with:  ant test -Dtest.class=webService.ServerAPIConnectionTest
 *
 * Test ordering is enforced by T01..T31 name prefixes so that IDs from create
 * operations are available to subsequent read/update/delete tests.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerAPIConnectionTest {

    // -------------------------------------------------------------------------
    // Configure these constants for your test environment
    // -------------------------------------------------------------------------
    private static final String TEST_INSTITUTE_ID = "0001";
    private static final String TEST_TERMINAL_ID  = "000001";
    private static final String TEST_USERNAME      = "admin";
    private static final String TEST_PASSWORD      = "123";

    /** Category ID that already exists on the server (used when adding a test item). */
    private static final int EXISTING_CATEGORY_ID    = 1;
    /** Measure unit ID that already exists on the server. */
    private static final int EXISTING_MEASURE_UNIT_ID = 1;
    /** Supplier code that already exists on the server (used for purchase invoice). */
    private static final String EXISTING_SUPPLIER_CODE = "S001";

    // -------------------------------------------------------------------------
    // Shared state captured by earlier tests for use in later tests
    // -------------------------------------------------------------------------
    private static ServerAPIConnection api;

    private static long   createdItemId;
    private static String testBarcode;
    private static long   stockBatchId;
    private static long   stockItemId;
    private static double stockSellingPrice;

    private static String testCustomerContact;
    private static int    testCustomerId;

    private static String saleInvoiceNo;
    private static String saleReturnVoucherId;

    private static String purchasingSystemInvoiceNo;

    // -------------------------------------------------------------------------
    // Date/time helpers
    // -------------------------------------------------------------------------
    private static String today() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private static String nowDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    // -------------------------------------------------------------------------
    // Suite-level setup and teardown
    // -------------------------------------------------------------------------

    @BeforeClass
    public static void setUpClass() {
        APIHeaderData headerData = new APIHeaderData();
        headerData.setInstituteId(TEST_INSTITUTE_ID);
        headerData.setTerminalId(TEST_TERMINAL_ID);

        api = ServerAPIConnection.getInstance(headerData);

        // Populate ApplicationDataManager so the JWT auto-refresh path can re-login
        UserAccount dto = new UserAccount();
        dto.user_name = TEST_USERNAME;
        dto.password  = TEST_PASSWORD;
        ApplicationDataManager.getInstance().setLoggedInUser(new UserAccountModel(dto));
    }

    @AfterClass
    public static void tearDownClass() {
        // Nothing to clean up at the suite level — individual tests handle cleanup.
    }

    // =========================================================================
    // Phase 1 – Authentication
    // =========================================================================

    @Test
    public void T01_loginWithWrongPassword_shouldFail() {
        CommonResponse response = api.login(TEST_USERNAME, "wrong_password_xyz");
        assertFalse("Login with wrong password must not succeed",
                response.getAPIResponse().isSuccess());
    }

    @Test
    public void T02_loginWithCorrectCredentials_shouldSucceed() {
        CommonResponse response = api.login(TEST_USERNAME, TEST_PASSWORD);
        assertTrue("Login must succeed with correct credentials",
                response.getAPIResponse().isSuccess());

        LoginResponse loginResponse = (LoginResponse) response.getData();
        assertNotNull("LoginResponse must not be null", loginResponse);
        assertNotNull("JWT auth_token must not be null", loginResponse.auth_token);
        assertFalse("JWT auth_token must not be empty", loginResponse.auth_token.isEmpty());

        // Confirm the token is stored in RuntimeDataManager
        String storedToken = RuntimeDataManager.getInstance().getRuntimeData().getAuthToken();
        assertEquals("Stored auth token must match the one returned by login",
                loginResponse.auth_token, storedToken);
    }

    // =========================================================================
    // Phase 2 – Reference data reads
    // =========================================================================

    @Test
    public void T03_getInstitute_shouldReturnData() {
        CommonResponse response = api.getInstitute();
        assertTrue("getInstitute must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
        assertNotNull("Institute data must not be null", response.getData());
    }

    @Test
    public void T04_getTerminal_shouldReturnData() {
        CommonResponse response = api.getTerminal();
        assertTrue("getTerminal must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
        assertNotNull("Terminal data must not be null", response.getData());
    }

    @Test
    public void T05_getMeasureUnitsData_shouldReturnNonEmptyList() {
        CommonResponse response = api.getMeasureUnitsData();
        assertTrue("getMeasureUnitsData must succeed",
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<?> units = (List<?>) response.getData();
        assertNotNull("Measure units list must not be null", units);
        assertFalse("Measure units list must not be empty", units.isEmpty());
    }

    @Test
    public void T06_getCategories_shouldReturnNonEmptyList() {
        CommonResponse response = api.getCategories();
        assertTrue("getCategories must succeed",
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<?> categories = (List<?>) response.getData();
        assertNotNull("Categories list must not be null", categories);
        assertFalse("Categories list must not be empty", categories.isEmpty());
    }

    @Test
    public void T07_getDiscountRules_shouldSucceed() {
        CommonResponse response = api.getDiscountRules();
        assertTrue("getDiscountRules must succeed",
                response.getAPIResponse().isSuccess());
        // The list may be empty if no rules are configured — just verify no error
        assertNotNull("Discount rules data must not be null", response.getData());
    }

    @Test
    public void T08_getAllSuppliers_shouldReturnNonEmptyList() {
        CommonResponse response = api.getAllSuppliers();
        assertTrue("getAllSuppliers must succeed",
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<?> suppliers = (List<?>) response.getData();
        assertNotNull("Suppliers list must not be null", suppliers);
    }

    @Test
    public void T09_getCustomers_shouldReturnList() {
        CommonResponse response = api.getCustomers();
        assertTrue("getCustomers must succeed",
                response.getAPIResponse().isSuccess());
        assertNotNull("Customers list must not be null", response.getData());
    }

    // =========================================================================
    // Phase 3 – Item CRUD
    // =========================================================================

    @Test
    public void T10_addItem_shouldReturnNewItemId() {
        testBarcode = "TBAR" + System.currentTimeMillis();

        Item item = new Item();
        item.barcode          = testBarcode;
        item.item_name        = "Test Item " + System.currentTimeMillis();
        item.item_name_sin    = "Test Item Sin";
        item.item_name_tam    = "Test Item Tam";
        item.category_id      = EXISTING_CATEGORY_ID;
        item.measure_unit_id  = EXISTING_MEASURE_UNIT_ID;
        item.image_name       = "";
        item.minimum_stock    = 1.0;
        item.added_by         = TEST_USERNAME;
        item.added_date_time  = nowDateTime();
        item.is_active        = 1;

        CommonResponse response = api.addItem(item);
        assertTrue("addItem must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        InsertRecordResponse insertResponse = (InsertRecordResponse) response.getData();
        assertNotNull("InsertRecordResponse must not be null", insertResponse);
        assertTrue("New item_id must be > 0", insertResponse.record_id > 0);

        createdItemId = insertResponse.record_id;
    }

    @Test
    public void T11_getItemByBarcode_shouldMatchCreatedItem() {
        assertNotNull("testBarcode must be set by T10", testBarcode);

        CommonResponse response = api.getItem(testBarcode);
        assertTrue("getItem must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        Item item = (Item) response.getData();
        assertNotNull("Item data must not be null", item);
        assertEquals("Barcode must match", testBarcode, item.barcode);
    }

    @Test
    public void T12_editItem_shouldSucceed() {
        assertTrue("createdItemId must be set by T10", createdItemId > 0);

        Item item = new Item();
        item.item_id         = (int) createdItemId;
        item.barcode         = testBarcode;
        item.item_name       = "Test Item EDITED " + System.currentTimeMillis();
        item.item_name_sin   = "Edited Sin";
        item.item_name_tam   = "Edited Tam";
        item.category_id     = EXISTING_CATEGORY_ID;
        item.measure_unit_id = EXISTING_MEASURE_UNIT_ID;
        item.image_name      = "";
        item.minimum_stock   = 2.0;
        item.modified_by     = TEST_USERNAME;
        item.is_active       = 1;

        CommonResponse response = api.editItem(item);
        assertTrue("editItem must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    // =========================================================================
    // Phase 4 – Stock operations
    // =========================================================================

    @Test
    public void T13_getAllItemStock_shouldReturnNonEmptyList() {
        CommonResponse response = api.getAllItemStock();
        assertTrue("getAllItemStock must succeed",
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<Item> items = (List<Item>) response.getData();
        assertNotNull("Item stock list must not be null", items);
        assertFalse("Item stock list must not be empty — server must have at least one item with stock",
                items.isEmpty());

        // Capture first available stock batch for price update and wastage tests
        outer:
        for (Item item : items) {
            if (item.stock != null && !item.stock.isEmpty()) {
                ItemStock batch = item.stock.get(0);
                stockBatchId      = batch.batch_id;
                stockItemId       = (long) item.item_id;
                stockSellingPrice = batch.selling_price;
                break outer;
            }
        }
        assertTrue("A batch_id must have been captured from existing stock", stockBatchId > 0);
    }

    @Test
    public void T14_batchPriceUpdate_shouldSucceed() {
        assertTrue("stockBatchId must be set by T13", stockBatchId > 0);

        CommonResponse response = api.batchPriceUpdate(
                stockBatchId,
                stockSellingPrice + 5.0,  // new label price
                0.0,                       // discount
                stockSellingPrice,         // keep selling price the same
                stockSellingPrice * 0.9    // wholesale = 90% of selling
        );
        assertTrue("batchPriceUpdate must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    @Test
    public void T15_addStockWastage_shouldSucceed() {
        assertTrue("stockBatchId must be set by T13", stockBatchId > 0);

        StockWastage wastage = new StockWastage();
        wastage.item_id             = stockItemId;
        wastage.stock_id            = stockBatchId;
        wastage.supplier_id         = 1;   // use any valid supplier_id
        wastage.purchasing_unit_price = 50.0;
        wastage.selling_unit_price  = stockSellingPrice;
        wastage.qty                 = 1.0;
        wastage.added_date          = today();
        wastage.added_by            = TEST_USERNAME;

        CommonResponse response = api.addStockWastage(wastage);
        assertTrue("addStockWastage must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    // =========================================================================
    // Phase 5 – Customer CRUD
    // =========================================================================

    @Test
    public void T16_addCustomer_shouldReturnNewCustomerId() {
        testCustomerContact = "07" + (System.currentTimeMillis() % 100000000L);
        // Ensure it fits a Sri Lankan mobile number length (10 digits)
        if (testCustomerContact.length() > 10) {
            testCustomerContact = testCustomerContact.substring(0, 10);
        }

        Customer customer = new Customer();
        customer.customer_name  = "Test Customer " + System.currentTimeMillis();
        customer.contact_number = testCustomerContact;
        customer.email          = "test" + System.currentTimeMillis() + "@example.com";
        customer.account_balance = 0.0;
        customer.is_active      = 1;
        customer.added_by       = TEST_USERNAME;
        customer.added_time     = nowDateTime();

        CommonResponse response = api.addCustomer(customer);
        assertTrue("addCustomer must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        InsertRecordResponse insertResponse = (InsertRecordResponse) response.getData();
        assertNotNull("InsertRecordResponse must not be null", insertResponse);
        assertTrue("New customer_id must be > 0", insertResponse.record_id > 0);
        testCustomerId = (int) insertResponse.record_id;
    }

    @Test
    public void T17_getCustomerByContact_shouldMatchCreatedCustomer() {
        assertNotNull("testCustomerContact must be set by T16", testCustomerContact);

        CommonResponse response = api.getCustomer(testCustomerContact);
        assertTrue("getCustomer must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        Customer customer = (Customer) response.getData();
        assertNotNull("Customer data must not be null", customer);
        assertEquals("Contact number must match", testCustomerContact, customer.contact_number);
        testCustomerId = customer.customer_id;
    }

    @Test
    public void T18_getCustomers_shouldContainCreatedCustomer() {
        assertNotNull("testCustomerContact must be set by T16", testCustomerContact);

        CommonResponse response = api.getCustomers();
        assertTrue("getCustomers must succeed",
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<Customer> customers = (List<Customer>) response.getData();
        assertNotNull("Customer list must not be null", customers);

        boolean found = false;
        for (Customer c : customers) {
            if (testCustomerContact.equals(c.contact_number)) {
                found = true;
                break;
            }
        }
        assertTrue("Created customer must appear in getCustomers response", found);
    }

    @Test
    public void T19_addCustomerTransaction_shouldSucceed() {
        assertNotNull("testCustomerContact must be set by T16", testCustomerContact);

        CustomerLedger ledger = new CustomerLedger();
        ledger.description        = "Test transaction";
        ledger.ledger_type        = CustomerLedger.LEDGER_TYPE.DEBIT;
        ledger.transaction_amount = 100.0;
        ledger.transaction_date   = today();
        ledger.accept_by          = TEST_USERNAME;

        CommonResponse response = api.addCustomerTransaction(testCustomerContact, ledger);
        assertTrue("addCustomerTransaction must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    @Test
    public void T20_getCustomerTransactionHistory_shouldContainTestTransaction() {
        assertNotNull("testCustomerContact must be set by T16", testCustomerContact);

        CommonResponse response = api.getCustomerTransactionHistory(
                testCustomerContact, today(), today());
        assertTrue("getCustomerTransactionHistory must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<CustomerLedger> history = (List<CustomerLedger>) response.getData();
        assertNotNull("Transaction history must not be null", history);
        assertFalse("Transaction history must not be empty after T19", history.isEmpty());
    }

    // =========================================================================
    // Phase 6 – Sale flow
    // =========================================================================

    @Test
    public void T21_sale_shouldSucceedAndReturnInvoiceRecord() {
        assertTrue("stockBatchId must be set by T13 — need existing stock for sale",
                stockBatchId > 0);

        saleInvoiceNo = TEST_INSTITUTE_ID + TEST_TERMINAL_ID + today().replace("-", "")
                + String.format("%05d", (System.currentTimeMillis() % 100000L));

        SaleItem saleItem = new SaleItem();
        saleItem.batch_id      = (int) stockBatchId;
        saleItem.item_id       = (int) stockItemId;
        saleItem.qty           = 1.0;
        saleItem.selling_price = stockSellingPrice;
        saleItem.discount      = 0.0;
        saleItem.billing_price = stockSellingPrice;
        saleItem.net_amount    = stockSellingPrice;
        saleItem.cost_for_unit = 0.0;
        saleItem.total_cost    = 0.0;
        saleItem.status        = 1;

        List<SaleItem> items = new ArrayList<>();
        items.add(saleItem);

        SaleInvoice invoice = new SaleInvoice();
        invoice.invoice_no      = saleInvoiceNo;
        invoice.invoice_type    = 1;
        invoice.sale_time       = nowDateTime();
        invoice.sale_by         = TEST_USERNAME;
        invoice.customer_id     = testCustomerId;
        invoice.cust_contact_no = testCustomerContact;
        invoice.gross_amount    = stockSellingPrice;
        invoice.no_of_items     = 1;
        invoice.total_discount  = 0.0;
        invoice.net_total       = stockSellingPrice;
        invoice.money_received  = stockSellingPrice;
        invoice.card_received   = 0.0;
        invoice.voucher_received = 0.0;
        invoice.total_received  = stockSellingPrice;
        invoice.balance_amount  = 0.0;
        invoice.total_cost      = 0.0;
        invoice.sale_items      = items;

        CommonResponse response = api.sale(invoice);
        assertTrue("sale must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        InsertRecordResponse insertResponse = (InsertRecordResponse) response.getData();
        assertNotNull("Sale InsertRecordResponse must not be null", insertResponse);
        assertTrue("Sale record_id must be > 0", insertResponse.record_id > 0);
    }

    @Test
    public void T22_getSaleInvoiceData_shouldMatchSubmittedInvoice() {
        assertNotNull("saleInvoiceNo must be set by T21", saleInvoiceNo);

        CommonResponse response = api.getSaleInvoiceData(saleInvoiceNo);
        assertTrue("getSaleInvoiceData must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        SaleInvoice invoice = (SaleInvoice) response.getData();
        assertNotNull("SaleInvoice must not be null", invoice);
        assertEquals("Invoice number must match", saleInvoiceNo, invoice.invoice_no);
        assertEquals("Sale total must match", stockSellingPrice, invoice.net_total, 0.01);
    }

    @Test
    public void T23_saleReturn_shouldSucceedAndReturnVoucher() {
        assertNotNull("saleInvoiceNo must be set by T21", saleInvoiceNo);
        assertTrue("stockItemId must be set by T13", stockItemId > 0);

        SaleReturnItem returnItem = new SaleReturnItem();
        returnItem.item_id       = stockItemId;
        returnItem.qty           = 1.0;
        returnItem.selling_price = stockSellingPrice;
        returnItem.cost          = 0.0;

        List<SaleReturnItem> returnItems = new ArrayList<>();
        returnItems.add(returnItem);

        SaleReturn saleReturn = new SaleReturn();
        saleReturn.sale_invoice_no   = saleInvoiceNo;
        saleReturn.customer_id       = testCustomerId;
        saleReturn.total_amount      = stockSellingPrice;
        saleReturn.request_date_time = nowDateTime();
        saleReturn.request_by        = TEST_USERNAME;
        saleReturn.return_items      = returnItems;

        CommonResponse response = api.saleReturn(saleReturn);
        assertTrue("saleReturn must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        Voucher voucher = (Voucher) response.getData();
        assertNotNull("Voucher from saleReturn must not be null", voucher);
        assertTrue("Voucher ID must be > 0", voucher.voucher_id > 0);

        saleReturnVoucherId = String.valueOf(voucher.voucher_id);
    }

    @Test
    public void T24_getVoucher_shouldReturnSaleReturnVoucher() {
        assertNotNull("saleReturnVoucherId must be set by T23", saleReturnVoucherId);

        CommonResponse response = api.getVoucher(saleReturnVoucherId);
        assertTrue("getVoucher must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        Voucher voucher = (Voucher) response.getData();
        assertNotNull("Voucher data must not be null", voucher);
        assertEquals("Voucher ID must match", Integer.parseInt(saleReturnVoucherId), voucher.voucher_id);
    }

    @Test
    public void T25_redeemVoucher_shouldSucceed() {
        assertNotNull("saleReturnVoucherId must be set by T23", saleReturnVoucherId);

        CommonResponse response = api.redeemVoucher(saleReturnVoucherId, TEST_USERNAME);
        assertTrue("redeemVoucher must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    // =========================================================================
    // Phase 7 – Purchasing flow
    // =========================================================================

    @Test
    public void T26_addUpdatePurchasingInvoice_shouldSucceed() {
        assertTrue("createdItemId must be set by T10", createdItemId > 0);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.item_id             = createdItemId;
        purchaseItem.purchase_unit_price  = 80.0;
        purchaseItem.label_price          = 120.0;
        purchaseItem.discount             = 0.0;
        purchaseItem.selling_unit_price   = 100.0;
        purchaseItem.wholesale_price      = 90.0;
        purchaseItem.qty                  = 10.0;
        purchaseItem.batch_num            = "BATCH001";
        purchaseItem.exp_date             = "2027-12-31";
        purchaseItem.added_by             = TEST_USERNAME;
        purchaseItem.added_date           = today();

        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(purchaseItem);

        PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
        purchaseInvoice.invoice_no    = "PINV" + System.currentTimeMillis();
        purchaseInvoice.date          = today();
        purchaseInvoice.supplier_code = EXISTING_SUPPLIER_CODE;
        purchaseInvoice.total         = 800.0;
        purchaseInvoice.discount      = 0.0;
        purchaseInvoice.net_total     = 800.0;
        purchaseInvoice.paid          = 800.0;
        purchaseInvoice.balance       = 0.0;
        purchaseInvoice.input_method  = "MANUAL";
        purchaseInvoice.status        = 0; // pending
        purchaseInvoice.edit_by       = TEST_USERNAME;
        purchaseInvoice.purchase_items = purchaseItems;

        CommonResponse response = api.addUpdatePurchasingInvoice(purchaseInvoice);
        assertTrue("addUpdatePurchasingInvoice must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        InsertRecordResponse insertResponse = (InsertRecordResponse) response.getData();
        assertNotNull("InsertRecordResponse must not be null", insertResponse);
        assertTrue("Purchasing invoice record_id must be > 0", insertResponse.record_id > 0);

        // The system_invoice_no is needed for T27 and T28. Try to retrieve it from pending invoices.
        // We store the raw rec_id and look up the system_invoice_no in T27.
        purchasingSystemInvoiceNo = null; // will be populated in T27
    }

    @Test
    public void T27_getPendingPurchasingInvoices_shouldContainCreatedInvoice() {
        CommonResponse response = api.getPendingPurchasingInvoices();
        assertTrue("getPendingPurchasingInvoices must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        @SuppressWarnings("unchecked")
        List<serverDataModels.PurchaseInvoice> invoices =
                (List<serverDataModels.PurchaseInvoice>) response.getData();
        assertNotNull("Pending invoice list must not be null", invoices);
        assertFalse("Pending invoice list must not be empty after T26", invoices.isEmpty());

        // Capture the system_invoice_no of the most recent pending invoice (our test invoice)
        serverDataModels.PurchaseInvoice latest = invoices.get(invoices.size() - 1);
        purchasingSystemInvoiceNo = latest.system_invoice_no;
        assertNotNull("system_invoice_no must not be null", purchasingSystemInvoiceNo);
    }

    @Test
    public void T28_processCompletePurchasingInvoice_shouldSucceed() {
        assertNotNull("purchasingSystemInvoiceNo must be set by T27", purchasingSystemInvoiceNo);

        CommonResponse response = api.processCompletePurchasingInvoice(purchasingSystemInvoiceNo);
        assertTrue("processCompletePurchasingInvoice must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    // =========================================================================
    // Phase 8 – Cleanup
    // =========================================================================

    @Test
    public void T29_deleteItem_shouldSucceed() {
        assertTrue("createdItemId must be set by T10", createdItemId > 0);

        CommonResponse response = api.deleteItem(createdItemId);
        assertTrue("deleteItem must succeed: " + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());
    }

    // =========================================================================
    // Phase 9 – Error and edge cases
    // =========================================================================

    @Test(expected = UnsupportedOperationException.class)
    public void T30_editCustomer_shouldThrowUnsupportedOperationException() {
        api.editCustomer(new Customer());
    }

    /**
     * JWT auto-refresh test:
     * Overwrite the stored auth token with a bogus value so the next API call
     * triggers a code-04 JWT error. The callWithRetry() logic must silently
     * re-login and retry, so the API call should still succeed.
     */
    @Test
    public void T31_jwtAutoRefresh_shouldRetryAndSucceedAfterTokenExpiry() {
        // Sabotage the stored token
        RuntimeDataManager.getInstance().getRuntimeData().setAuthToken("INVALID.TOKEN.FORTEST");

        // Any authenticated API call should trigger the retry path
        CommonResponse response = api.getInstitute();
        assertTrue("API call must succeed after JWT auto-refresh: "
                + response.getAPIResponse().getResponseCode(),
                response.getAPIResponse().isSuccess());

        // Confirm a new valid token was stored after the refresh
        String refreshedToken = RuntimeDataManager.getInstance().getRuntimeData().getAuthToken();
        assertNotNull("Refreshed token must not be null", refreshedToken);
        assertFalse("Refreshed token must not be the bogus value",
                "INVALID.TOKEN.FORTEST".equals(refreshedToken));
    }
}
