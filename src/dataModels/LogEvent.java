package dataModels;

/**
 *
 * @author malit
 */
public enum LogEvent {
    CHANGE_USER_PROFILE, // Update my profile
    CHANGE_USER_ACCOUNT, // Update any user account
    CHANGE_USER_PERMISSION, // Change any permission of any user
    CHANGE_SUPPLIER, //Chnage supplier data
    CHANGE_STOCK_ITEM, //Chnage stock item data
    DO_SALE,
    DO_INVOICE,
    DO_EXCHANGE_RETURN_VOUCHER,
    ITEM_RETURN_UPDATE,
    DISPOSE_STOCK,
    INSTITUTE_DATA_UPDATE,
    CATEGORY_DATA_UPDATE,//MASTER DATA
    MEASURE_UNIT_UPDATE;//MASTER DATA
}
