package dataModels;

import java.util.ArrayList;
import java.util.List;

public class BillDataModel {

    private String date;
    private String time;
    private String invoiceNumber;
    private String billNumber;
    private String cashierName;
    private String cashierId;
    private int customerId;
    private String customerContactNo;
    private List<BillItemDataModel> billItems = new ArrayList<>();
    
    private double totalGrossAmount;
    private double noOfItems;
    private double totalDiscount;
    private double ruleDiscount;
    private double netTotal;
    private double moneyReceive;
    private double cardReceive;
    private double voucherReceive;
    private double balance;
    private MethodOfPayment methodOfPayment;

    public BillDataModel(BillDataModel billDataModel) {
        date = billDataModel.date;
        time = billDataModel.time;
        invoiceNumber = billDataModel.invoiceNumber;
        cashierName = billDataModel.cashierName;
        cashierId = billDataModel.cashierId;
        customerId = billDataModel.customerId;
        billItems = new ArrayList<>();
        for (BillItemDataModel billItem : billDataModel.billItems) {
            billItems.add(new BillItemDataModel(billItem));
        }

        totalGrossAmount = billDataModel.totalGrossAmount;
        noOfItems = billDataModel.noOfItems;
        totalDiscount = billDataModel.totalDiscount;
        ruleDiscount = billDataModel.ruleDiscount;
        netTotal = billDataModel.netTotal;
        moneyReceive = billDataModel.moneyReceive;
        balance = billDataModel.balance;
        methodOfPayment = billDataModel.methodOfPayment;
    }

    public BillDataModel() {
        
    }
    
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
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the cashierName
     */
    public String getCashierName() {
        return cashierName;
    }

    /**
     * @param cashierName the cashierName to set
     */
    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    /**
     * @return the cashierId
     */
    public String getCashierId() {
        return cashierId;
    }

    /**
     * @param cashierId the cashierId to set
     */
    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    /**
     * @return the customerName
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the billItems
     */
    public List<BillItemDataModel> getBillItems() {
        return billItems;
    }

    /**
     * @param billItems the billItems to set
     */
    public void setBillItems(List<BillItemDataModel> billItems) {
        this.billItems = billItems;
    }

    /**
     * @return the totalGrossAmount
     */
    public double getTotalGrossAmount() {
        return totalGrossAmount;
    }

    /**
     * @param totalGrossAmount the totalGrossAmount to set
     */
    public void setTotalGrossAmount(double totalGrossAmount) {
        this.totalGrossAmount = totalGrossAmount;
    }

    /**
     * @return the noOfItems
     */
    public double getNoOfItems() {
        return noOfItems;
    }

    /**
     * @param noOfItems the noOfItems to set
     */
    public void setNoOfItems(double noOfItems) {
        this.noOfItems = noOfItems;
    }

    /**
     * @return the totalDiscount
     */
    public double getTotalDiscount() {
        return totalDiscount;
    }

    /**
     * @param totalDiscount the totalDiscount to set
     */
    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    /**
     * @return the netTotal
     */
    public double getNetTotal() {
        return netTotal;
    }

    /**
     * @param netTotal the netTotal to set
     */
    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    /**
     * @return the moneyReceive
     */
    public double getMoneyReceive() {
        return moneyReceive;
    }

    /**
     * @param moneyReceive the moneyReceive to set
     */
    public void setMoneyReceive(double moneyReceive) {
        this.moneyReceive = moneyReceive;
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
     * @return the methodOfPayment
     */
    public MethodOfPayment getMethodOfPayment() {
        return methodOfPayment;
    }

    /**
     * @param methodOfPayment the methodOfPayment to set
     */
    public void setMethodOfPayment(MethodOfPayment methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }
    
    /**
     * @return the billNumber
     */
    public String getBillNumber() {
        return billNumber;
    }

    /**
     * @param billNumber the billNumber to set
     */
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
    /**
     * @return the customerContactNo
     */
    public String getCustomerContactNo() {
        return customerContactNo;
    }

    /**
     * @param customerContactNo the customerContactNo to set
     */
    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    /**
     * @return the cardReceive
     */
    public double getCardReceive() {
        return cardReceive;
    }

    /**
     * @param cardReceive the cardReceive to set
     */
    public void setCardReceive(double cardReceive) {
        this.cardReceive = cardReceive;
    }

    /**
     * @return the voucherReceive
     */
    public double getVoucherReceive() {
        return voucherReceive;
    }

    /**
     * @param voucherReceive the voucherReceive to set
     */
    public void setVoucherReceive(double voucherReceive) {
        this.voucherReceive = voucherReceive;
    }

    /**
     * @return the ruleDiscount
     */
    public double getRuleDiscount() {
        return ruleDiscount;
    }

    /**
     * @param ruleDiscount the ruleDiscount to set
     */
    public void setRuleDiscount(double ruleDiscount) {
        this.ruleDiscount = ruleDiscount;
    }
}
