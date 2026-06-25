package appDataModels;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import serverDataModels.PurchaseInvoice;

/**
 *
 * @author MalithWanniarachchi
 */
public class PurchaseInvoiceModel {

    private long recId;
    private int instituteId;
    private String invoiceNo;
    private String systemInvoiceNumber;
    private String date;
    private String supplierCode;
    private double total;
    private double discount;
    private double netTotal;
    private boolean setAlert;
    private double paid;
    private double balance;
    private String dueToPay;
    private String alertDate;
    private String inputMethod;
    private String editBy;
    private List<PurchaseItemModel> purchaseItems;
    
    public PurchaseInvoiceModel(){}
    
    public PurchaseInvoiceModel(PurchaseInvoiceModel other) {
        if (other == null) return;

        this.recId = other.recId;
        this.instituteId = other.instituteId;
        this.invoiceNo = other.invoiceNo;
        this.systemInvoiceNumber = other.systemInvoiceNumber;
        this.date = other.date;
        this.supplierCode = other.supplierCode;
        this.total = other.total;
        this.discount = other.discount;
        this.netTotal = other.netTotal;
        this.setAlert = other.setAlert;
        this.paid = other.paid;
        this.balance = other.balance;
        this.dueToPay = other.dueToPay;
        this.alertDate = other.alertDate;
        this.inputMethod = other.inputMethod;
        this.editBy = other.editBy;

        // Deep copy of list
        if (other.purchaseItems != null) {
            this.purchaseItems = new ArrayList<>();
            for (PurchaseItemModel item : other.purchaseItems) {
                this.purchaseItems.add(new PurchaseItemModel(item)); // uses item copy constructor
            }
        }
    }

    // ✅ DTO → Model
    public PurchaseInvoiceModel(PurchaseInvoice dto) {
        this.recId = dto.rec_id;
        this.instituteId = dto.institute_id;
        this.invoiceNo = dto.invoice_no;
        this.systemInvoiceNumber = dto.system_invoice_no;
        this.date = dto.date;
        this.supplierCode = dto.supplier_code;
        this.total = dto.total;
        this.discount = dto.discount;
        this.netTotal = dto.net_total;
        this.setAlert = (dto.set_alert == 1);
        this.paid = dto.paid;
        this.balance = dto.balance;
        this.dueToPay = dto.due_to_pay;
        this.alertDate = dto.alert_date;
        this.inputMethod = dto.input_method;
        this.editBy = dto.edit_by;

        if (dto.purchase_items != null) {
            this.purchaseItems = dto.purchase_items.stream()
                    .map(PurchaseItemModel::new) // DTO → Model
                    .collect(Collectors.toList());
        } else {
            this.purchaseItems = new ArrayList<>();
        }
    }

    // ✅ Model → DTO
    public PurchaseInvoice newPurchaseInvoiceDTO() {
        PurchaseInvoice dto = new PurchaseInvoice();
        dto.rec_id = this.getRecId();
        dto.institute_id = this.getInstituteId();
        dto.invoice_no = this.getInvoiceNo();
        dto.system_invoice_no = this.systemInvoiceNumber;
        dto.date = this.getDate();
        dto.supplier_code = this.getSupplierCode();
        dto.total = this.getTotal();
        dto.discount = this.getDiscount();
        dto.net_total = this.getNetTotal();
        dto.set_alert = this.isSetAlert() ? 1 : 0;
        dto.paid = this.getPaid();
        dto.balance = this.getBalance();
        dto.due_to_pay = this.getDueToPay();
        dto.alert_date = this.getAlertDate();
        dto.input_method = this.getInputMethod();
        dto.edit_by = this.getEditBy();

        if (this.purchaseItems != null) {
            dto.purchase_items = this.purchaseItems.stream()
                    .map(PurchaseItemModel::newPurchaseItemDTO) // Model → DTO
                    .collect(Collectors.toList());
        } else {
            dto.purchase_items = new ArrayList<>();
        }

        return dto;
    }

    // ✅ Getters & Setters
    public long getRecId() { return recId; }
    public void setRecId(long recId) { this.recId = recId; }

    public int getInstituteId() { return instituteId; }
    public void setInstituteId(int instituteId) { this.instituteId = instituteId; }

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    
    public String getSystemInvoiceNumber() { return systemInvoiceNumber; }
    public void setSystemInvoiceNumber(String systemInvoiceNumber) { this.systemInvoiceNumber = systemInvoiceNumber; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getNetTotal() { return netTotal; }
    public void setNetTotal(double netTotal) { this.netTotal = netTotal; }

    public boolean isSetAlert() { return setAlert; }
    public void setSetAlert(boolean setAlert) { this.setAlert = setAlert; }

    public double getPaid() { return paid; }
    public void setPaid(double paid) { this.paid = paid; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getDueToPay() { return dueToPay; }
    public void setDueToPay(String dueToPay) { this.dueToPay = dueToPay; }

    public String getAlertDate() { return alertDate; }
    public void setAlertDate(String alertDate) { this.alertDate = alertDate; }

    public String getInputMethod() { return inputMethod; }
    public void setInputMethod(String inputMethod) { this.inputMethod = inputMethod; }

    public String getEditBy() { return editBy; }
    public void setEditBy(String editBy) { this.editBy = editBy; }

    public List<PurchaseItemModel> getPurchaseItems() { return purchaseItems; }
    public void setPurchaseItems(List<PurchaseItemModel> purchaseItems) { this.purchaseItems = purchaseItems; }
}
