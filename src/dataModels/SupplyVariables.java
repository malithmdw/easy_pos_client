
package dataModels;

public class SupplyVariables {

    
    private String date;
    private String supplierCode;
    private String invoice_no;
    private double total;
    private double discount;
    private double net_total;
    private int set_alert;
    private double paid;
    private double balance;
    private String due_to_pay;
    private String alert_date;
    private String inputMethod;
    private String editBy;

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the supplierCode
     */
    public String getSupplier_code() {
        return supplierCode;
    }

    /**
     * @param supplier_code the supplierCode to set
     */
    public void setSupplier_code(String supplier_code) {
        this.supplierCode = supplier_code;
    }

    /**
     * @return the invoice_no
     */
    public String getInvoice_no() {
        return invoice_no;
    }

    /**
     * @param invoice_no the invoice_no to set
     */
    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @return the net_total
     */
    public double getNet_total() {
        return net_total;
    }

    /**
     * @param net_total the net_total to set
     */
    public void setNet_total(double net_total) {
        this.net_total = net_total;
    }

    /**
     * @return the set_alert
     */
    public int getSet_alert() {
        return set_alert;
    }

    /**
     * @param set_alert the set_alert to set
     */
    public void setSet_alert(int set_alert) {
        this.set_alert = set_alert;
    }

    /**
     * @return the paid
     */
    public double getPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(double paid) {
        this.paid = paid;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the due_to_pay
     */
    public String getDue_to_pay() {
        return due_to_pay;
    }

    /**
     * @param due_to_pay the due_to_pay to set
     */
    public void setDue_to_pay(String due_to_pay) {
        this.due_to_pay = due_to_pay;
    }

    /**
     * @return the alert_date
     */
    public String getAlert_date() {
        return alert_date;
    }

    /**
     * @param alert_date the alert_date to set
     */
    public void setAlert_date(String alert_date) {
        this.alert_date = alert_date;
    }

    /**
     * @return the inputMethod
     */
    public String getInputMethod() {
        return inputMethod;
    }

    /**
     * @param inputMethod the inputMethod to set
     */
    public void setInputMethod(String inputMethod) {
        this.inputMethod = inputMethod;
    }

    /**
     * @return the editBy
     */
    public String getEditBy() {
        return editBy;
    }

    /**
     * @param editBy the editBy to set
     */
    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
    
    
}
