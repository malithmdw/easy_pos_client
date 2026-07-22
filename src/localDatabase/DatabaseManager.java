
package localDatabase;

/**
 *
 * @author MalithWanniarachchi
 */
import appDataModels.CategoryModel;
import appDataModels.ItemModel;
import appDataModels.LoginResponseModel;
import appDataModels.Permission;
import control.EasyPosLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import serverDataModels.Category;
import serverDataModels.ChangeLog;
import serverDataModels.DiscountRule;
import serverDataModels.Institute;
import serverDataModels.Item;
import serverDataModels.ItemStock;
import serverDataModels.MeasureUnit;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverDataModels.UserAccount;
import serverResponseDataModel.LoginResponse;

public class DatabaseManager {
    
    private static DatabaseManager INSTANCE;
    private static final String DB_URL = "jdbc:sqlite:myapp.db"; // Database file
    private static Connection connection;
        
    public enum TableName {
        USER_TABLE("user_account"),
        USER_PERMISSION_TABLE("user_permission"),
        CATEGORY_TABLE("category"),
        CHANGE_LOG_TABLE("change_log"),
        MEASURE_UNIT_TABLE("measure_unit"),
        INSTITUTE_TABLE("institute"),
        APP_DATA_TABLE("app_data"),
        DISCOUNT_RULES_TABLE("discount_rule"),
        ITEM_TABLE("item"),
        ITEM_STOCK_TABLE("item_stock"),;

        // Field (instance variable)
        private final String tableName;

        // Constructor (must be private or package-private)
        TableName(String tableName) {
            this.tableName = tableName;
        }

        // Method
        public String getTableName() {
            return tableName;
        }
    }
    
    private DatabaseManager(){}

    private static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    private static void closeConnection() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException e) {EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());}
        }
    }
    
    public static DatabaseManager getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager();
        }
        return INSTANCE;
    }
    
    public static void createTablesIfNotExist() {
        
        String[] scripts = {
            "CREATE TABLE IF NOT EXISTS user_account (" +
            "user_code INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "user_name TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "phone TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "password_hint TEXT NOT NULL," +
            "hint_type INTEGER NOT NULL," +
            "note TEXT NOT NULL," +
            "status INTEGER NOT NULL," +
            "force_password_change INTEGER NOT NULL DEFAULT 0" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS user_permission (" +
            "record_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "user_name TEXT NOT NULL," +
            "permission TEXT NOT NULL" +
            ");",
                        
            "CREATE TABLE IF NOT EXISTS category (" +
            "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "cat_name TEXT NOT NULL," +
            "cat_name_sin TEXT NOT NULL," +
            "cat_name_tam TEXT NOT NULL," +
            "image TEXT," +
            "description TEXT NOT NULL," +
            "trspp REAL," +
            "twspp REAL," +
            "enable INTEGER NOT NULL" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS discount_rule (" +
            "rule_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "name TEXT," +
            "bill_above REAL," +
            "only_for_category INTEGER," +
            "only_for_mop INTEGER," +
            "only_for_loyalty_customers INTEGER," +
            "discount_percentage REAL," +
            "fix_discount REAL," +
            "status INTEGER" +
            ");",

            "CREATE TABLE IF NOT EXISTS change_log (" +
            "log_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "terminal_id INTEGER NOT NULL," +
            "user TEXT NOT NULL," +
            "timestamp TEXT DEFAULT CURRENT_TIMESTAMP," +
            "action TEXT NOT NULL," +
            "primary_db_table TEXT NOT NULL," +
            "pk_column_name INTEGER NOT NULL," +
            "pk_value TEXT NOT NULL," +
            "description TEXT NOT NULL," +
            "is_synched INTEGER NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS measure_unit (" +
            "measure_unit_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "unit_name_eng TEXT NOT NULL," +
            "unit_name_sin TEXT NOT NULL," +
            "unit_name_tam TEXT NOT NULL," +
            "description TEXT NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS institute (" +
            "institute_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "business_name TEXT NOT NULL," +
            "description TEXT," +
            "print_business_name TEXT," +
            "print_business_sub_name TEXT," +
            "print_business_address_line_1 TEXT," +
            "print_business_address_line_2 TEXT," +
            "print_business_address_line_3 TEXT," +
            "print_business_contact TEXT," +
            "print_footer_1 TEXT," +
            "print_footer_2 TEXT," +
            "service_provider_print_line TEXT," +
            "status INTEGER NOT NULL" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS app_data (" +
            "data_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "key TEXT NOT NULL," +
            "value TEXT NOT NULL," +
            "description TEXT NOT NULL" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS item (" +
            "item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "barcode TEXT NOT NULL," +
            "item_name TEXT NOT NULL," +
            "item_name_sin TEXT NOT NULL," +
            "item_name_tam TEXT NOT NULL," +
            "category_id INTEGER NOT NULL," +
            "measure_unit_id INTEGER NOT NULL," +
            "image_name TEXT NOT NULL," +
            "minimum_stock REAL NOT NULL," +
            "added_by TEXT NOT NULL," +
            "added_date_time TEXT NOT NULL," +
            "modified_by TEXT NOT NULL," +
            "modified_date_time TEXT NOT NULL," +
            "is_active INTEGER NOT NULL" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS item_stock (" +
            "batch_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "item_id INTEGER NOT NULL," +
            "purchase_date TEXT NOT NULL," +
            "quantity_purchased REAL NOT NULL," +
            "quantity_available REAL NOT NULL," +
            "purchasing_price REAL NOT NULL," +
            "label_price REAL NOT NULL," +
            "discount REAL NOT NULL," +
            "selling_price REAL NOT NULL," +
            "wholesale_price REAL NOT NULL," +
            "expiry_date TEXT NOT NULL," +
            "is_active INTEGER NOT NULL" + 
            ");",

            "CREATE TABLE IF NOT EXISTS sale_invoice (" +
            "rec_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "institute_id INTEGER NOT NULL," +
            "invoice_no TEXT NOT NULL," +
            "invoice_type INTEGER NOT NULL," +
            "sale_time TEXT NOT NULL," +
            "sale_by TEXT NOT NULL," +
            "customer_id INTEGER," +
            "cust_contact_no TEXT," +
            "gross_amount REAL NOT NULL," +
            "no_of_items REAL NOT NULL," +
            "total_discount REAL NOT NULL," +
            "net_total REAL NOT NULL," +
            "money_received REAL," +
            "card_received REAL," +
            "voucher_received REAL," +
            "total_received REAL NOT NULL," +
            "balance_amount REAL NOT NULL," +
            "total_cost REAL NOT NULL," +
            "settle_date_time TEXT NOT NULL," +
            "settle_update_by TEXT NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS sale_item (" +
            "rec_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "invoice_record_id INTEGER NOT NULL," +
            "item_id INTEGER NOT NULL," +
            "batch_id INTEGER NOT NULL," +
            "qty REAL NOT NULL," +
            "selling_price REAL NOT NULL," +
            "discount REAL NOT NULL," +
            "billing_price REAL NOT NULL," +
            "net_amount REAL NOT NULL," +
            "cost_for_unit REAL NOT NULL," +
            "total_cost REAL NOT NULL," +
            "status TEXT NOT NULL" +
            ");"
    };

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            for (String sql : scripts) {
                stmt.execute(sql);
            }

            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "All tables are initialized successfully!");

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }

        migrateCategoryTable();
        migrateUserAccountTable();
    }

    /**
     * Adds trspp/twspp columns to pre-existing category tables that were
     * created before these columns existed. SQLite has no
     * "ADD COLUMN IF NOT EXISTS", so failures (column already exists) are
     * expected and silently ignored.
     */
    private static void migrateCategoryTable() {
        String[] alterStatements = {
            "ALTER TABLE category ADD COLUMN trspp REAL;",
            "ALTER TABLE category ADD COLUMN twspp REAL;"
        };

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            for (String sql : alterStatements) {
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    // Column already exists - ignore.
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }

    /**
     * Adds the force_password_change column to pre-existing user_account
     * tables that were created before this column existed. SQLite has no
     * "ADD COLUMN IF NOT EXISTS", so failure (column already exists) is
     * expected and silently ignored.
     */
    private static void migrateUserAccountTable() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            try {
                stmt.execute("ALTER TABLE user_account ADD COLUMN force_password_change INTEGER NOT NULL DEFAULT 0;");
            } catch (SQLException e) {
                // Column already exists - ignore.
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    
    public static void versionUpdateDBChanges(){}
    
    
    // Get data 
    
    public LoginResponseModel login(String instituteId, String userName, String password) {
        LoginResponseModel loginResponseModel = null;
        UserAccount userAccount;
        
        String query = "SELECT * FROM user_account WHERE user_name = '" 
                + userName + "' AND password = '" + password + "' AND institute_id = '" + instituteId + "' LIMIT 1;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {                
                userAccount = new UserAccount();
                userAccount.user_name =  rs.getString("user_name");
                userAccount.institute_id =  rs.getInt("institute_id");
                userAccount.user_code = rs.getString("user_code");
                userAccount.email = rs.getString("email");
                userAccount.phone = rs.getString("phone");
                userAccount.password = rs.getString("password");
                //userAccount.account_type = rs.getString("account_type");
                userAccount.password_hint = rs.getString("password_hint");
                userAccount.hint_type = rs.getInt("hint_type");
                userAccount.note = rs.getString("note");
                userAccount.status = rs.getInt("status");
                userAccount.force_password_change = rs.getInt("force_password_change");

                LoginResponse loginResponse = new LoginResponse();
                loginResponse.auth_token = null;
                loginResponse.user = userAccount;
                loginResponse.permissions = new ArrayList<>();
                
                List<Permission> permissions = getPermissionsOfUser(instituteId, userName);
                
                loginResponseModel = new LoginResponseModel(loginResponse);
                loginResponseModel.setPermissions(permissions);
                
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }        
        
        return loginResponseModel;
    }
    private List<Permission> getPermissionsOfUser(String instituteId, String userName) {
        
        List<Permission> permissions = new ArrayList<>();
        
        String query = "SELECT * FROM user_permission WHERE user_name = '" 
                + userName + "' AND institute_id = '" + instituteId + "';";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Permission permission = Permission.valueOf(rs.getString("permission"));
                
                if (permission != null) {
                    permissions.add(permission);
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
               
        return permissions;
    }
    public HashMap<String, String> getAppData() {
        HashMap<String, String> resMap = new HashMap<>();

        String query = "SELECT * FROM app_data";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resMap.put(rs.getString("key"), rs.getString("value"));
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resMap;
    }
    public List<CategoryModel> getCategories() {
        List<CategoryModel> resultList = new ArrayList<>();

        String query = "SELECT * FROM category";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.category_id =  rs.getInt("category_id");
                category.institute_id =  rs.getInt("institute_id");
                category.cat_name = rs.getString("cat_name");
                category.cat_name_sin = rs.getString("cat_name_sin");
                category.cat_name_tam = rs.getString("cat_name_tam");
                category.image = rs.getString("image");
                category.description = rs.getString("description");
                category.trspp = rs.getDouble("trspp");
                category.twspp = rs.getDouble("twspp");
                category.enable = rs.getInt("enable");

                resultList.add(new CategoryModel(category));
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resultList;
    }
    public List<DiscountRule> getDiscountRules() {
        List<DiscountRule> resultList = new ArrayList<>();

        String query = "SELECT * FROM discount_rule";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DiscountRule discountRule = new DiscountRule();
                
                discountRule.rule_id =  rs.getInt("rule_id");
                discountRule.institute_id =  rs.getInt("institute_id");
                discountRule.name = rs.getString("name");
                discountRule.bill_above = rs.getDouble("bill_above");
                discountRule.only_for_category = rs.getInt("only_for_category");
                discountRule.only_for_mop = rs.getInt("only_for_mop");
                discountRule.only_for_loyalty_customers = rs.getInt("only_for_loyalty_customers");
                discountRule.discount_percentage = rs.getDouble("discount_percentage");
                discountRule.fix_discount = rs.getDouble("fix_discount");
                discountRule.status = rs.getInt("status");

                resultList.add(discountRule);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resultList;
    }
    public List<MeasureUnit> getMeasureUnits() {
        List<MeasureUnit> resultList = new ArrayList<>();

        String query = "SELECT * FROM measure_unit";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MeasureUnit measureUnit = new MeasureUnit();
                measureUnit.measure_unit_id =  rs.getInt("measure_unit_id");
                measureUnit.unit_name_eng =  rs.getString("unit_name_eng");
                measureUnit.unit_name_sin = rs.getString("unit_name_sin");
                measureUnit.unit_name_tam = rs.getString("unit_name_tam");
                measureUnit.description = rs.getString("description");

                resultList.add(measureUnit);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resultList;
    }
    public List<Map<String, Object>> getChangeLogs() {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String query = "SELECT * FROM change_log WHERE is_synched = 0";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("log_id", rs.getInt("category_id"));
                row.put("institute_id", rs.getInt("institute_id"));
                row.put("terminal_id", rs.getInt("terminal_id"));
                row.put("user", rs.getString("user"));
                row.put("timestamp", rs.getString("timestamp"));
                row.put("action", rs.getString("action"));
                row.put("primary_db_table", rs.getString("primary_db_table"));
                row.put("pk_column_name", rs.getString("pk_column_name"));
                row.put("pk_value", rs.getString("pk_value"));
                row.put("description", rs.getString("description"));
                row.put("is_synched", rs.getInt("is_synched"));

                resultList.add(row);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resultList;
    }
    public List<Institute> getInstitutes() {
        List<Institute> resultList = new ArrayList<>();

        String query = "SELECT * FROM institute";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Institute institute = new Institute();
                institute.institute_id = rs.getInt("institute_id");
                institute.business_name = rs.getString("business_name");
                institute.print_business_name = rs.getString("print_business_name");
                institute.print_business_sub_name = rs.getString("print_business_sub_name");
                institute.print_business_address_line_1 = rs.getString("print_business_address_line_1");
                institute.print_business_address_line_2 = rs.getString("print_business_address_line_2");
                institute.print_business_address_line_3 = rs.getString("print_business_address_line_3");
                institute.print_business_contact = rs.getString("print_business_contact");
                institute.print_footer_1 = rs.getString("print_footer_1");
                institute.print_footer_2 = rs.getString("print_footer_2");
                institute.service_provider_print_line = rs.getString("service_provider_print_line");
                institute.status = rs.getInt("status");
                
                resultList.add(institute);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resultList;
    }
    public List<Item> getItems() {
        List<Item> itemList = new ArrayList<>();

        String query = "SELECT * FROM item";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.item_id = rs.getInt("item_id");
                item.institute_id = rs.getInt("institute_id");
                item.barcode = rs.getString("barcode");
                item.item_name = rs.getString("item_name");
                item.item_name_sin = rs.getString("item_name_sin");
                item.item_name_tam = rs.getString("item_name_tam");
                item.category_id = rs.getInt("category_id");
                
                item.measure_unit_id = rs.getInt("measure_unit_id");
                item.image_name = rs.getString("image_name");
                item.minimum_stock = rs.getDouble("minimum_stock");
                item.added_by = rs.getString("added_by");
                item.added_date_time = rs.getString("added_date_time");
                item.modified_by = rs.getString("modified_by");
                item.modified_date_time = rs.getString("modified_date_time");
                item.is_active = rs.getInt("is_active");

                itemList.add(item);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return itemList;
    }
    public List<Map<String, Object>> getItemStock() {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String query = "SELECT * FROM item_stock";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("batch_id", rs.getInt("category_id"));
                row.put("item_id", rs.getInt("institute_id"));
                row.put("purchase_date", rs.getString("cat_name"));
                row.put("quantity_purchased", rs.getDouble("cat_name_sin"));
                row.put("quantity_available", rs.getDouble("cat_name_tam"));
                row.put("purchasing_price", rs.getDouble("description"));
                row.put("label_price", rs.getDouble("enable"));
                row.put("discount", rs.getDouble("cat_name_sin"));
                row.put("selling_price", rs.getDouble("selling_price"));
                row.put("wholesale_price", rs.getDouble("wholesale_price"));                
                row.put("expiry_date", rs.getString("expiry_date"));
                row.put("is_active", rs.getInt("is_active"));
                        
                resultList.add(row);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return resultList;
    }
    
    
    public ItemModel getItemByItemId(long itemId) {
        Item item = null;
        List<ItemStock> itemStocks = new ArrayList<>();

        String query = "SELECT " +
                "i.item_id, i.institute_id, i.barcode, " +
                "i.item_name, i.item_name_sin, i.item_name_tam, " +
                "i.category_id, c.cat_name, c.cat_name_sin, c.cat_name_tam, " +
                "c.description AS category_description, " +
                "i.measure_unit_id, mu.unit_name_eng, mu.unit_name_sin, mu.unit_name_tam, " +
                "mu.description AS measure_description, " +
                "i.image_name, i.minimum_stock, i.added_by, i.added_date_time, " +
                "i.modified_by, i.modified_date_time, i.is_active, " +
                "s.batch_id, s.purchase_date, s.quantity_purchased, " +
                "s.quantity_available, s.purchasing_price, s.label_price, " +
                "s.discount, s.selling_price, s.wholesale_price, s.expiry_date, " +
                "s.is_active AS stock_active " +
                "FROM item i " +
                "LEFT JOIN category c ON i.category_id = c.category_id " +
                "LEFT JOIN measure_unit mu ON i.measure_unit_id = mu.measure_unit_id " +
                "LEFT JOIN item_stock s ON i.item_id = s.item_id AND s.is_active = 1 " +
                "WHERE i.item_id = ? AND i.is_active = 1 " +
                "ORDER BY s.expiry_date ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, (int) itemId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    // Create item only once
                    if (item == null) {

                        item = new Item();

                        item.item_id = rs.getInt("item_id");
                        item.institute_id = rs.getInt("institute_id");
                        item.barcode = rs.getString("barcode");
                        item.item_name = rs.getString("item_name");
                        item.item_name_sin = rs.getString("item_name_sin");
                        item.item_name_tam = rs.getString("item_name_tam");

                        item.category_id = rs.getInt("category_id");

                        Category category = new Category();
                        category.category_id = item.category_id;
                        category.cat_name = rs.getString("cat_name");
                        category.cat_name_sin = rs.getString("cat_name_sin");
                        category.cat_name_tam = rs.getString("cat_name_tam");
                        category.description = rs.getString("category_description");

                        item.category = category;

                        item.measure_unit_id = rs.getInt("measure_unit_id");

                        MeasureUnit measureUnit = new MeasureUnit();
                        measureUnit.measure_unit_id = item.measure_unit_id;
                        measureUnit.unit_name_eng = rs.getString("unit_name_eng");
                        measureUnit.unit_name_sin = rs.getString("unit_name_sin");
                        measureUnit.unit_name_tam = rs.getString("unit_name_tam");
                        measureUnit.description = rs.getString("measure_description");

                        item.measureUnit = measureUnit;

                        item.image_name = rs.getString("image_name");
                        item.minimum_stock = rs.getDouble("minimum_stock");
                        item.added_by = rs.getString("added_by");
                        item.added_date_time = rs.getString("added_date_time");
                        item.modified_by = rs.getString("modified_by");
                        item.modified_date_time = rs.getString("modified_date_time");
                        item.is_active = rs.getInt("is_active");
                    }

                    // Add stock records
                    long batchId = rs.getLong("batch_id");

                    if (batchId != 0) {

                        ItemStock stock = new ItemStock();
                        stock.batch_id = batchId;
                        stock.item_id = item.item_id;
                        stock.purchase_date = rs.getString("purchase_date");
                        stock.quantity_purchased = rs.getDouble("quantity_purchased");
                        stock.quantity_available = rs.getDouble("quantity_available");
                        stock.purchasing_price = rs.getDouble("purchasing_price");
                        stock.label_price = rs.getDouble("label_price");
                        stock.discount = rs.getDouble("discount");
                        stock.selling_price = rs.getDouble("selling_price");
                        stock.wholesale_price = rs.getDouble("wholesale_price");
                        stock.expiry_date = rs.getString("expiry_date");
                        stock.is_active = rs.getInt("stock_active");

                        itemStocks.add(stock);
                    }
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }

        if (item != null) {
            item.stock = itemStocks;
            return new ItemModel(item);
        }

        return null;
    }
    
    public ItemModel getItemByBarcode(String barcode) {

        Item item = null;
        List<ItemStock> itemStocks = new ArrayList<>();

        String query = "SELECT " +
                "i.item_id, i.institute_id, i.barcode, " +
                "i.item_name, i.item_name_sin, i.item_name_tam, " +
                "i.category_id, c.cat_name, c.cat_name_sin, c.cat_name_tam, " +
                "c.description AS category_description, " +
                "i.measure_unit_id, mu.unit_name_eng, mu.unit_name_sin, mu.unit_name_tam, " +
                "mu.description AS measure_description, " +
                "i.image_name, i.minimum_stock, i.added_by, i.added_date_time, " +
                "i.modified_by, i.modified_date_time, i.is_active, " +
                "s.batch_id, s.purchase_date, s.quantity_purchased, " +
                "s.quantity_available, s.purchasing_price, s.label_price, " +
                "s.discount, s.selling_price, s.wholesale_price, s.expiry_date, " +
                "s.is_active AS stock_active " +
                "FROM item i " +
                "LEFT JOIN category c ON i.category_id = c.category_id " +
                "LEFT JOIN measure_unit mu ON i.measure_unit_id = mu.measure_unit_id " +
                "LEFT JOIN item_stock s ON i.item_id = s.item_id AND s.is_active = 1 " +
                "WHERE i.barcode = ? AND i.is_active = 1 " +
                "ORDER BY s.expiry_date ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, barcode);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    // Create item only once
                    if (item == null) {

                        item = new Item();

                        item.item_id = rs.getInt("item_id");
                        item.institute_id = rs.getInt("institute_id");
                        item.barcode = rs.getString("barcode");
                        item.item_name = rs.getString("item_name");
                        item.item_name_sin = rs.getString("item_name_sin");
                        item.item_name_tam = rs.getString("item_name_tam");

                        item.category_id = rs.getInt("category_id");

                        Category category = new Category();
                        category.category_id = item.category_id;
                        category.cat_name = rs.getString("cat_name");
                        category.cat_name_sin = rs.getString("cat_name_sin");
                        category.cat_name_tam = rs.getString("cat_name_tam");
                        category.description = rs.getString("category_description");

                        item.category = category;

                        item.measure_unit_id = rs.getInt("measure_unit_id");

                        MeasureUnit measureUnit = new MeasureUnit();
                        measureUnit.measure_unit_id = item.measure_unit_id;
                        measureUnit.unit_name_eng = rs.getString("unit_name_eng");
                        measureUnit.unit_name_sin = rs.getString("unit_name_sin");
                        measureUnit.unit_name_tam = rs.getString("unit_name_tam");
                        measureUnit.description = rs.getString("measure_description");

                        item.measureUnit = measureUnit;

                        item.image_name = rs.getString("image_name");
                        item.minimum_stock = rs.getDouble("minimum_stock");
                        item.added_by = rs.getString("added_by");
                        item.added_date_time = rs.getString("added_date_time");
                        item.modified_by = rs.getString("modified_by");
                        item.modified_date_time = rs.getString("modified_date_time");
                        item.is_active = rs.getInt("is_active");
                    }

                    // Add stock records
                    long batchId = rs.getLong("batch_id");

                    if (batchId != 0) {

                        ItemStock stock = new ItemStock();
                        stock.batch_id = batchId;
                        stock.item_id = item.item_id;
                        stock.purchase_date = rs.getString("purchase_date");
                        stock.quantity_purchased = rs.getDouble("quantity_purchased");
                        stock.quantity_available = rs.getDouble("quantity_available");
                        stock.purchasing_price = rs.getDouble("purchasing_price");
                        stock.label_price = rs.getDouble("label_price");
                        stock.discount = rs.getDouble("discount");
                        stock.selling_price = rs.getDouble("selling_price");
                        stock.wholesale_price = rs.getDouble("wholesale_price");
                        stock.expiry_date = rs.getString("expiry_date");
                        stock.is_active = rs.getInt("stock_active");

                        itemStocks.add(stock);
                    }
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }

        if (item != null) {
            item.stock = itemStocks;
            return new ItemModel(item);
        }

        return null;
    }
    public List<ItemModel> getItemsByCategoryId(int categoryId) {

        List<ItemModel> itemModels = new ArrayList<>();
        Map<Integer, Item> itemMap = new LinkedHashMap<>();

        String query = "SELECT " +
                "i.item_id, i.institute_id, i.barcode, " +
                "i.item_name, i.item_name_sin, i.item_name_tam, " +
                "i.category_id, c.cat_name, c.cat_name_sin, c.cat_name_tam, " +
                "c.description AS category_description, " +
                "i.measure_unit_id, mu.unit_name_eng, mu.unit_name_sin, mu.unit_name_tam, " +
                "mu.description AS measure_description, " +
                "i.image_name, i.minimum_stock, i.added_by, i.added_date_time, " +
                "i.modified_by, i.modified_date_time, i.is_active, " +
                "s.batch_id, s.purchase_date, s.quantity_purchased, " +
                "s.quantity_available, s.purchasing_price, s.label_price, " +
                "s.discount, s.selling_price, s.wholesale_price, s.expiry_date, " +
                "s.is_active AS stock_active " +
                "FROM item i " +
                "LEFT JOIN category c ON i.category_id = c.category_id " +
                "LEFT JOIN measure_unit mu ON i.measure_unit_id = mu.measure_unit_id " +
                "LEFT JOIN item_stock s ON i.item_id = s.item_id AND s.is_active = 1 " +
                "WHERE i.category_id = ? AND i.is_active = 1 " +
                "ORDER BY i.item_id, s.expiry_date ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, categoryId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    int itemId = rs.getInt("item_id");

                    // Create item only once
                    Item item = itemMap.get(itemId);

                    if (item == null) {

                        item = new Item();
                        item.stock = new ArrayList<>();

                        item.item_id = itemId;
                        item.institute_id = rs.getInt("institute_id");
                        item.barcode = rs.getString("barcode");
                        item.item_name = rs.getString("item_name");
                        item.item_name_sin = rs.getString("item_name_sin");
                        item.item_name_tam = rs.getString("item_name_tam");
                        item.category_id = rs.getInt("category_id");

                        Category category = new Category();
                        category.category_id = item.category_id;
                        category.cat_name = rs.getString("cat_name");
                        category.cat_name_sin = rs.getString("cat_name_sin");
                        category.cat_name_tam = rs.getString("cat_name_tam");
                        category.description = rs.getString("category_description");

                        item.category = category;

                        item.measure_unit_id = rs.getInt("measure_unit_id");

                        MeasureUnit measureUnit = new MeasureUnit();
                        measureUnit.measure_unit_id = item.measure_unit_id;
                        measureUnit.unit_name_eng = rs.getString("unit_name_eng");
                        measureUnit.unit_name_sin = rs.getString("unit_name_sin");
                        measureUnit.unit_name_tam = rs.getString("unit_name_tam");
                        measureUnit.description = rs.getString("measure_description");

                        item.measureUnit = measureUnit;

                        item.image_name = rs.getString("image_name");
                        item.minimum_stock = rs.getDouble("minimum_stock");
                        item.added_by = rs.getString("added_by");
                        item.added_date_time = rs.getString("added_date_time");
                        item.modified_by = rs.getString("modified_by");
                        item.modified_date_time = rs.getString("modified_date_time");
                        item.is_active = rs.getInt("is_active");

                        itemMap.put(itemId, item);
                    }

                    // Add stock if exists
                    long batchId = rs.getLong("batch_id");

                    if (batchId != 0) {

                        ItemStock stock = new ItemStock();
                        stock.batch_id = batchId;
                        stock.item_id = itemId;
                        stock.purchase_date = rs.getString("purchase_date");
                        stock.quantity_purchased = rs.getDouble("quantity_purchased");
                        stock.quantity_available = rs.getDouble("quantity_available");
                        stock.purchasing_price = rs.getDouble("purchasing_price");
                        stock.label_price = rs.getDouble("label_price");
                        stock.discount = rs.getDouble("discount");
                        stock.selling_price = rs.getDouble("selling_price");
                        stock.wholesale_price = rs.getDouble("wholesale_price");
                        stock.expiry_date = rs.getString("expiry_date");
                        stock.is_active = rs.getInt("stock_active");

                        item.stock.add(stock);
                    }
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }

        // Convert Items to ItemModel
        for (Item item : itemMap.values()) {
            itemModels.add(new ItemModel(item));
        }

        return itemModels;
    }
    
    public List<ChangeLog> getUnpostedChangeLogData() {
        List<ChangeLog> changeLogs = new ArrayList<>();

        String query = "SELECT * FROM change_log WHERE is_synched = 0 ORDER BY log_id;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ChangeLog changeLog = new ChangeLog();
                changeLog.log_id = rs.getInt("log_id");
                changeLog.institute_id = rs.getInt("institute_id");
                changeLog.terminal_id = rs.getInt("terminal_id");
                changeLog.user = rs.getString("user");
                changeLog.timestamp = rs.getString("timestamp");
                changeLog.action = rs.getString("action");
                changeLog.primary_db_table = rs.getString("primary_db_table");
                changeLog.pk_column_name = rs.getString("pk_column_name");
                changeLog.pk_value = rs.getString("pk_value");
                changeLog.description = rs.getString("description");
                changeLog.is_synched = rs.getInt("is_synched");
                        
                changeLogs.add(changeLog);
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return changeLogs;
    }
    
    public SaleInvoice getSaleInvoice(String invoiceNumber) {
        SaleInvoice saleInvoice = new SaleInvoice();

        String query = "SELECT * FROM sale_invoice WHERE invoice_no = ?;";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, invoiceNumber);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                
                    saleInvoice.rec_id = rs.getInt("rec_id");
                    saleInvoice.institute_id = rs.getInt("institute_id");
                    saleInvoice.invoice_no = rs.getString("invoice_no");
                    saleInvoice.invoice_type = rs.getInt("invoice_type");
                    saleInvoice.sale_time = rs.getString("sale_time");
                    saleInvoice.sale_by = rs.getString("sale_by");
                    saleInvoice.customer_id = rs.getInt("customer_id");
                    saleInvoice.cust_contact_no = rs.getString("cust_contact_no");
                    saleInvoice.gross_amount = rs.getDouble("gross_amount");
                    saleInvoice.no_of_items = rs.getDouble("no_of_items");
                    saleInvoice.total_discount = rs.getDouble("total_discount");
                    saleInvoice.net_total = rs.getDouble("net_total");
                    saleInvoice.money_received = rs.getDouble("money_received");
                    saleInvoice.card_received = rs.getDouble("card_received");
                    saleInvoice.voucher_received = rs.getDouble("voucher_received");
                    saleInvoice.total_received = rs.getDouble("total_received");
                    saleInvoice.balance_amount = rs.getDouble("balance_amount");
                    saleInvoice.total_cost = rs.getDouble("total_cost");
                    saleInvoice.settle_date_time = rs.getString("settle_date_time");
                    saleInvoice.settle_update_by = rs.getString("settle_update_by");

                    return saleInvoice;
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return null;
    }    
    public List<SaleItem> getSaleItems(int invRecId) {
        List<SaleItem> saleItems = new ArrayList<>();

        String query = "SELECT * FROM sale_item WHERE invoice_record_id = ?;";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invRecId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    SaleItem saleItem = new SaleItem();
                    saleItem.rec_id = rs.getInt("rec_id");
                    saleItem.invoice_record_id = rs.getInt("invoice_record_id");
                    saleItem.item_id = rs.getInt("item_id");
                    saleItem.batch_id = rs.getInt("batch_id");
                    saleItem.qty = rs.getDouble("qty");
                    saleItem.selling_price = rs.getDouble("selling_price");
                    saleItem.discount = rs.getDouble("discount");
                    saleItem.billing_price = rs.getDouble("billing_price");
                    saleItem.net_amount = rs.getDouble("net_amount");
                    saleItem.cost_for_unit = rs.getDouble("cost_for_unit");
                    saleItem.total_cost = rs.getDouble("total_cost");
                    saleItem.status = rs.getInt("status");

                    saleItems.add(saleItem);
                }
            }

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
        return saleItems;
    }
    
    public boolean processSale(SaleInvoice invoice, List<SaleItem> items, int terminalId) {
        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();

            // Start transaction
            conn.setAutoCommit(false);

            // Insert Sale Invoice
            String saleSql = "INSERT INTO sale_invoice "
                    + "(institute_id, invoice_no, invoice_type, sale_time, sale_by, "
                    + "customer_id, cust_contact_no, gross_amount, no_of_items, total_discount, "
                    + "net_total, money_received, card_received, voucher_received, total_received, "
                    + "balance_amount, total_cost, settle_date_time, settle_update_by) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?,"
                    + "?, ?, ?, ?, ?,"
                    + "?, ?, ?, ?, ?,"
                    + "?, ?, ?, ?)";
            PreparedStatement saleStmt = conn.prepareStatement(saleSql);

            saleStmt.setInt(1, invoice.institute_id);
            saleStmt.setString(2, invoice.invoice_no);
            saleStmt.setInt(3, invoice.invoice_type);
            saleStmt.setString(4, invoice.sale_time);
            saleStmt.setString(5, invoice.sale_by);
            saleStmt.setInt(6, (int) invoice.customer_id);
            saleStmt.setString(7, invoice.cust_contact_no);
            saleStmt.setDouble(8, invoice.gross_amount);
            saleStmt.setDouble(9, invoice.no_of_items);
            saleStmt.setDouble(10, invoice.total_discount);
            saleStmt.setDouble(11, invoice.net_total);
            saleStmt.setDouble(12, invoice.money_received);
            saleStmt.setDouble(13, invoice.card_received);
            saleStmt.setDouble(14, invoice.voucher_received);
            saleStmt.setDouble(15, invoice.total_received);
            saleStmt.setDouble(16, invoice.balance_amount);
            saleStmt.setDouble(17, invoice.total_cost);
            saleStmt.setString(18, invoice.settle_date_time);
            saleStmt.setString(19, invoice.settle_update_by);
            saleStmt.executeUpdate();
            
            ResultSet rs = saleStmt.getGeneratedKeys();
            rs.next();
            int invoiceId = rs.getInt(1);


            // Insert Sale Items
            String itemSql = "INSERT INTO sale_item "
                    + "(invoice_record_id, item_id, batch_id, qty, selling_price, "
                    + "discount, billing_price, net_amount, cost_for_unit, total_cost, "
                    + "status) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?,"
                    + "?, ?, ?, ?, ?,"
                    + "?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemSql);

            for (SaleItem item : items) {
                itemStmt.setInt(1, invoiceId);
                itemStmt.setInt(2, item.item_id);
                itemStmt.setInt(3, item.batch_id);
                itemStmt.setDouble(4, item.qty);
                itemStmt.setDouble(5, item.selling_price);
                itemStmt.setDouble(6, item.discount);
                itemStmt.setDouble(7, item.billing_price);
                itemStmt.setDouble(8, item.net_amount);
                itemStmt.setDouble(9, item.cost_for_unit);
                itemStmt.setDouble(10, item.total_cost);
                itemStmt.setInt(11, item.status);
                itemStmt.executeUpdate();
            }


            // Update inventory
            String updateStock = "UPDATE item_stock SET quantity_available = quantity_available - ? WHERE batch_id = ?";
            PreparedStatement stockStmt = conn.prepareStatement(updateStock);

            for (SaleItem item : items) {
                stockStmt.setDouble(1, item.qty);
                stockStmt.setInt(2, item.batch_id);
                stockStmt.executeUpdate();
            }


            // Insert Change Log
            String logSql = "INSERT INTO change_log "
                    + "(institute_id, terminal_id, user, timestamp, "
                    + "action, primary_db_table, pk_column_name, pk_value, description, "
                    + "is_synched) "
                    + "VALUES "
                    + "(?, ?, ?, ?,"
                    + "?, ?, ?, ?, ?,"
                    + "?)";
            PreparedStatement logStmt = conn.prepareStatement(logSql);

            logStmt.setInt(1, invoice.institute_id);
            logStmt.setInt(2, terminalId);
            logStmt.setString(3, invoice.sale_by);
            logStmt.setString(4, invoice.sale_time);
            logStmt.setString(5, "SALE");
            logStmt.setString(6, "sale_invoice");
            logStmt.setString(7, "invoice_no");
            logStmt.setString(8, invoice.invoice_no);
            logStmt.setString(9, "Sale Transaction");
            logStmt.setInt(10, 0);
            logStmt.executeUpdate();


            // If everything successful
            conn.commit();

            return true;

        } catch (SQLException e) {

            try {
                if (conn != null) {
                    conn.rollback();  // Rollback all changes
                }
            } catch (SQLException ex) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            }

            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, ex.toString());
            }
        }
    }
    
    // Insert data
    public void insertAppDataRecord(String key, String value) {
        
        String sql = "INSERT INTO app_data "
                   + "(key, value, description) "
                   + "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setString(1, key);
            pstmt.setString(2, value);
            pstmt.setString(3, "");

            // Execute insert
            pstmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    public void insertUser(UserAccount userAccount) {

        String sql = "INSERT INTO user_account "
                   + "(user_code, institute_id, user_name, email, phone, password, password_hint, hint_type, note, status, force_password_change) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, Integer.parseInt(userAccount.user_code));
            pstmt.setInt(2, userAccount.institute_id);
            pstmt.setString(3, userAccount.user_name);
            pstmt.setString(4, userAccount.email);
            pstmt.setString(5, userAccount.phone);
            pstmt.setString(6, userAccount.password);
            pstmt.setString(7, userAccount.password_hint);
            pstmt.setInt(8, userAccount.hint_type);
            pstmt.setString(9, userAccount.note);
            pstmt.setInt(10, userAccount.status);
            pstmt.setInt(11, userAccount.force_password_change);

            // Execute insert
            pstmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }    
    public void insertPermission(int instituteId, String userName, Permission permission) {
        String sql = "INSERT INTO user_permission "
                   + "(institute_id, user_name, permission) "
                   + "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, instituteId);
            pstmt.setString(2, userName);
            pstmt.setString(3, permission.name());

            // Execute insert
            pstmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    public void insertCategory(Category category) {

        String sql = "INSERT INTO category "
                   + "(category_id, institute_id, cat_name, cat_name_sin, cat_name_tam, image, description, trspp, twspp, enable) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, category.category_id);
            pstmt.setInt(2, category.institute_id);
            pstmt.setString(3, category.cat_name);
            pstmt.setString(4, category.cat_name_sin);
            pstmt.setString(5, category.cat_name_tam);
            pstmt.setString(6, category.image);
            pstmt.setString(7, category.description);
            pstmt.setDouble(8, category.trspp);
            pstmt.setDouble(9, category.twspp);
            pstmt.setInt(10, 1);

            // Execute insert
            pstmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    public void insertDiscountRule(DiscountRule discountRule) {

        String sql = "INSERT INTO discount_rule"
                + "(rule_id, institute_id, name, bill_above, only_for_category, only_for_mop, only_for_loyalty_customers, discount_percentage, fix_discount, status)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, discountRule.rule_id);
            pstmt.setInt(2, discountRule.institute_id);
            pstmt.setString(3, discountRule.name);
            pstmt.setDouble(4, discountRule.bill_above);
            pstmt.setInt(5, discountRule.only_for_category);
            pstmt.setInt(6, discountRule.only_for_mop);
            pstmt.setInt(7, discountRule.only_for_loyalty_customers);
            pstmt.setDouble(8, discountRule.discount_percentage);
            pstmt.setDouble(9, discountRule.fix_discount);
            pstmt.setInt(10, 1);

            // Execute insert
            pstmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    
    public void insertMeasureUnit(MeasureUnit messureUnit) {
        
        String sql = "INSERT INTO measure_unit "
                   + "(measure_unit_id, unit_name_eng, unit_name_sin, unit_name_tam, description) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set values
            pstmt.setInt(1, messureUnit.measure_unit_id);
            pstmt.setString(2, messureUnit.unit_name_eng);
            pstmt.setString(3, messureUnit.unit_name_sin);
            pstmt.setString(4, messureUnit.unit_name_tam);
            pstmt.setString(5, messureUnit.description);

            // Execute insert
            pstmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    public void insertChangeLog(ChangeLog changeLog) {
        String sql = "INSERT INTO change_log "
                + "(log_id, institute_id, terminal_id, user, timestamp, action, "
                + "primary_db_table, pk_column_name, pk_value, description, is_synched) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, changeLog.log_id);
            stmt.setInt(2, changeLog.institute_id);
            stmt.setInt(3, changeLog.terminal_id);
            stmt.setString(4, changeLog.user);
            stmt.setString(5, changeLog.timestamp);
            stmt.setString(6, changeLog.action);
            stmt.setString(7, changeLog.primary_db_table);
            stmt.setString(8, changeLog.pk_column_name);
            stmt.setString(9, changeLog.pk_value);
            stmt.setString(10, changeLog.description);
            stmt.setInt(11, changeLog.is_synched);

            stmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    public void insertInstitute(Institute institute) {

        String sql = "INSERT INTO institute "
                + "(institute_id, business_name, description, print_business_name, print_business_sub_name, "
                + "print_business_address_line_1, print_business_address_line_2, "
                + "print_business_address_line_3, print_business_contact, "
                + "print_footer_1, print_footer_2, service_provider_print_line, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, institute.institute_id);
            stmt.setString(2, institute.business_name);
            stmt.setString(3, institute.description);
            stmt.setString(4, institute.print_business_name);
            stmt.setString(5, institute.print_business_sub_name);
            stmt.setString(6, institute.print_business_address_line_1);
            stmt.setString(7, institute.print_business_address_line_2);
            stmt.setString(8, institute.print_business_address_line_3);
            stmt.setString(9, institute.print_business_contact);
            stmt.setString(10, institute.print_footer_1);
            stmt.setString(11, institute.print_footer_2);
            stmt.setString(12, institute.service_provider_print_line);
            stmt.setInt(13, 1);

            stmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }    
    public void insertItem(Item item) {

        String sql = "INSERT INTO item "
                + "(item_id, institute_id, barcode, item_name, item_name_sin, item_name_tam, "
                + "category_id, measure_unit_id, image_name, minimum_stock, "
                + "added_by, added_date_time, modified_by, modified_date_time, is_active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.item_id);
            stmt.setInt(2, item.institute_id);
            stmt.setString(3, item.barcode);
            stmt.setString(4, item.item_name);
            stmt.setString(5, item.item_name_sin);
            stmt.setString(6, item.item_name_tam);
            stmt.setInt(7, item.category_id);
            stmt.setInt(8, item.measure_unit_id);
            stmt.setString(9, item.image_name);
            stmt.setDouble(10, item.minimum_stock);
            stmt.setString(11, item.added_by);
            stmt.setString(12, item.added_date_time);
            stmt.setString(13, item.modified_by);
            stmt.setString(14, item.modified_date_time);
            stmt.setInt(15, 1);

            stmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    public void insertItemStock(ItemStock itemStock) {
        String sql = "INSERT INTO item_stock "
                + "(batch_id, item_id, purchase_date, quantity_purchased, quantity_available, "
                + "purchasing_price, label_price, discount, selling_price, wholesale_price, expiry_date, is_active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, itemStock.batch_id);
            stmt.setLong(2, itemStock.item_id);
            stmt.setString(3, itemStock.purchase_date);
            stmt.setDouble(4, itemStock.quantity_purchased);
            stmt.setDouble(5, itemStock.quantity_available);
            stmt.setDouble(6, itemStock.purchasing_price);
            stmt.setDouble(7, itemStock.label_price);
            stmt.setDouble(8, itemStock.discount);
            stmt.setDouble(9, itemStock.selling_price);
            stmt.setDouble(10, itemStock.wholesale_price);
            stmt.setString(11, itemStock.expiry_date);
            stmt.setInt(12, 1);

            stmt.executeUpdate();

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
        }
    }
    
    
    public void updateAppDataInvoiceNumber(String nextInvoiceNumber) {
        List<String> columnNameList = new ArrayList<>();
        columnNameList.add("value");
        List<Object> columnValueList = new ArrayList<>();
        columnValueList.add(nextInvoiceNumber);
        
        update(TableName.APP_DATA_TABLE.getTableName(), columnNameList, columnValueList, "key", "LAST_INVOICE_NUMBER");
    }    
    public void updateChangeLogSynced(String logId) {
        List<String> columnNameList = new ArrayList<>();
        columnNameList.add("is_synched");
        List<Object> columnValueList = new ArrayList<>();
        columnValueList.add(1);
        
        update(TableName.CHANGE_LOG_TABLE.getTableName(), columnNameList, columnValueList, "log_id", logId);
    }
    
    private boolean update(String tableName, List<String> columnNameList, List<Object> columnValueList, String whereColumnName, Object whereColumnvalue){        
        String columnSet = "";
        for (String colName : columnNameList) {
            columnSet += (colName + " = ? ,");
        }
        columnSet = columnSet.substring(0,columnSet.length()-2);
        
        String query = "UPDATE "+ tableName +" SET " + columnSet + " WHERE " + whereColumnName + " = ?;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            int index = 1;
            
            for (int i = 1; i <= columnValueList.size(); i++) {
                
                if (columnValueList.get(i-1) instanceof String) {
                    stmt.setString(i, (String) columnValueList.get(i-1));
                }
                else if (columnValueList.get(i-1) instanceof Integer) {
                    stmt.setInt(i, (Integer) columnValueList.get(i-1));
                }
                else if (columnValueList.get(i-1) instanceof Double) {
                    stmt.setDouble(i, (Double) columnValueList.get(i-1));
                }
                index++;
            }
            
            // Set value for WHERE clause
            
            if (whereColumnvalue instanceof String) {
                stmt.setString(index, (String) whereColumnvalue);
            }else if (whereColumnvalue instanceof Integer) {
                stmt.setInt(index, (Integer) whereColumnvalue);
            }else if (whereColumnvalue instanceof Double) {
                stmt.setDouble(index, (Double) whereColumnvalue);
            }

            return stmt.executeUpdate() > 0; // true = updated

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            return false;
        }
    }
    
    public boolean delete(String tableName, String keyColumnName, String keyValue) {
        String query = "DELETE FROM " + tableName + " WHERE " + keyColumnName + " = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, keyValue);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            return false;
        }
    }    
    public boolean delete(String tableName, String keyColumnName, int keyValue) {
        String query = "DELETE FROM " + tableName + " WHERE " + keyColumnName + " = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, keyValue);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            return false;
        }
    }    
    public boolean deleteAllData(TableName tableName) {
        String query = "DELETE FROM " + tableName.getTableName();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
            return true;

        } catch (SQLException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            return false;
        }
    }
}
