package easyPOS.localization;

import control.ApplicationDataManager;
import dataModels.Language;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ApplicationMessages {

    // ── Validation ───────────────────────────────────────────────────────────
    public static final String VALIDATION_FILL_REQUIRED_FIELDS         = "validation.fill.required.fields";
    public static final String VALIDATION_SELECT_ROW                   = "validation.select.row";
    public static final String VALIDATION_SELECT_CUSTOMER              = "validation.select.customer";
    public static final String VALIDATION_SELECT_ITEM                  = "validation.select.item";
    public static final String VALIDATION_SELECT_BATCH                 = "validation.select.batch";
    public static final String VALIDATION_SELECT_ITEM_PROCEED          = "validation.select.item.proceed";
    public static final String VALIDATION_SELECT_INVOICE_RECORD        = "validation.select.invoice.record";
    public static final String VALIDATION_SELECT_OR_INSERT_INVOICE     = "validation.select.or.insert.invoice";
    public static final String VALIDATION_SELECT_CHECKBOX              = "validation.select.checkbox";
    public static final String VALIDATION_PASSWORD_CURRENT_MISMATCH    = "validation.password.current.mismatch";
    public static final String VALIDATION_PASSWORD_NEW_MISMATCH        = "validation.password.new.mismatch";
    public static final String VALIDATION_SUBMISSION_MISMATCH          = "validation.submission.mismatch";
    public static final String VALIDATION_CUSTOMER_REQUIRED_CREDIT     = "validation.customer.required.credit";
    public static final String VALIDATION_CREDIT_SALE_NO_CASH_BALANCE  = "validation.credit.sale.no.cash.balance";
    public static final String VALIDATION_SUPPLIER_DUPLICATE           = "validation.supplier.duplicate";
    public static final String VALIDATION_SUPPLIER_CODE_NOT_FOUND      = "validation.supplier.code.not.found";
    public static final String VALIDATION_BARCODE_REQUIRED             = "validation.barcode.required";
    public static final String VALIDATION_ITEM_NAME_REQUIRED           = "validation.item.name.required";
    public static final String VALIDATION_ITEM_NAME_SINHALA_REQUIRED   = "validation.item.name.sinhala.required";
    public static final String VALIDATION_CATEGORY_INCORRECT           = "validation.category.incorrect";
    public static final String VALIDATION_MEASURE_UNIT_INCORRECT       = "validation.measure.unit.incorrect";
    public static final String VALIDATION_DATE_INPUT_INVALID           = "validation.date.input.invalid";
    public static final String VALIDATION_VOUCHER_ALREADY_ADDED        = "validation.voucher.already.added";
    public static final String VALIDATION_VOUCHER_NOT_ACTIVE           = "validation.voucher.not.active";
    public static final String VALIDATION_VOUCHER_EXPIRED              = "validation.voucher.expired";
    public static final String VALIDATION_ENTER_VOUCHER_NUMBER         = "validation.enter.voucher.number";
    public static final String VALIDATION_INVOICE_EMPTY_ITEMS          = "validation.invoice.empty.items";
    public static final String VALIDATION_FILL_FIRST_FOUR_FIELDS       = "validation.fill.first.four.fields";
    public static final String VALIDATION_NO_DATA_FOUND                = "validation.no.data.found";
    public static final String VALIDATION_SET_ALERT_DATE               = "validation.set.alert.date";
    public static final String VALIDATION_PAYMENT_AMOUNT_INCORRECT     = "validation.payment.amount.incorrect";

    // ── Errors ───────────────────────────────────────────────────────────────
    public static final String ERROR_UNEXPECTED                        = "error.unexpected";
    public static final String ERROR_ITEM_NOT_FOUND                    = "error.item.not.found";
    public static final String ERROR_ITEM_NOT_FOUND_LOCAL              = "error.item.not.found.local";
    public static final String ERROR_STOCK_UNAVAILABLE                 = "error.stock.unavailable";
    public static final String ERROR_INVALID_INPUT                     = "error.invalid.input";
    public static final String ERROR_INVALID_AMOUNT                    = "error.invalid.amount";
    public static final String ERROR_PRINTER_STOPPED                   = "error.printer.stopped";
    public static final String ERROR_PRINTER_FAILED_TASK               = "error.printer.failed.task";
    public static final String ERROR_PRINTER_NOT_FOUND                 = "error.printer.not.found";
    public static final String ERROR_PRINT_BILL_FAILED                 = "error.print.bill.failed";
    public static final String ERROR_PRINT_PROCESS_FAILED              = "error.print.process.failed";
    public static final String ERROR_LOAD_FAILED                       = "error.load.failed";
    public static final String ERROR_INSERT_FAILED                     = "error.insert.failed";
    public static final String ERROR_UPDATE_FAILED                     = "error.update.failed";
    public static final String ERROR_DELETE_FAILED                     = "error.delete.failed";
    public static final String ERROR_STORE_FAILED                      = "error.store.failed";
    public static final String ERROR_TEMP_INVOICE_DELETE_FAILED        = "error.temp.invoice.delete.failed";
    public static final String ERROR_DATA_CONNECTION                   = "error.data.connection";
    public static final String ERROR_ACCOUNT_NOT_FOUND                 = "error.account.not.found";
    public static final String ERROR_DAILY_SALES_GET_FAILED            = "error.daily.sales.get.failed";
    public static final String ERROR_DAILY_SALES_INSERT_FAILED         = "error.daily.sales.insert.failed";
    public static final String ERROR_OLD_INVOICE_DELETE_FAILED         = "error.old.invoice.delete.failed";
    public static final String ERROR_REPORT_GRAPH_FAILED               = "error.report.graph.failed";
    public static final String ERROR_PROCESS_FAILED                    = "error.process.failed";
    public static final String ERROR_GENERIC                           = "error.generic";
    public static final String ERROR_NOTES_SAVE_FAILED                 = "error.notes.save.failed";
    public static final String ERROR_BACKUP_FILE_CREATE_FAILED         = "error.backup.file.create.failed";

    // ── Info / Success ───────────────────────────────────────────────────────
    public static final String INFO_SAVE_SUCCESS                       = "info.save.success";
    public static final String INFO_INSERT_SUCCESS                     = "info.insert.success";
    public static final String INFO_UPDATE_SUCCESS                     = "info.update.success";
    public static final String INFO_DELETE_SUCCESS                     = "info.delete.success";
    public static final String INFO_PASSWORD_CHANGED                   = "info.password.changed";
    public static final String INFO_ACCOUNT_DELETED                    = "info.account.deleted";
    public static final String INFO_ACCOUNT_CREATED                    = "info.account.created";
    public static final String INFO_AUTO_INVOICE_STARTED               = "info.auto.invoice.started";
    public static final String INFO_INVOICE_DATA_DELETED               = "info.invoice.data.deleted";

    public static String getMessage(String key) {
        Language lang = ApplicationDataManager.getInstance().getApplicationLanguage();
        ResourceBundle bundle;
        if (lang == Language.SINHALA) {
            try {
                bundle = ResourceBundle.getBundle("easyPOS/localization/Messages", new Locale("si", "LK"));
            } catch (MissingResourceException e) {
                bundle = ResourceBundle.getBundle("easyPOS/localization/Messages");
            }
        } else {
            bundle = ResourceBundle.getBundle("easyPOS/localization/Messages");
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public static String getFormattedMessage(String key, Object... args) {
        return MessageFormat.format(getMessage(key), args);
    }
}
