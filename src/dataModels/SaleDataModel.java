
package dataModels;


public class SaleDataModel {
    private int invoiceNo;
    private String saleDate;
    private String saleTime;
    private String saleBy;
    
    private String itemCode;
    private int saleQty;
    
    private double grossAmount;
    private double discount;
    private double extraDiscount;
    private double netTotal;
    private double moneyRecieve;
    private double costOfBill;
//////for other sales data tables ex: sales daily,sales monthly..
    private String date;
    private double income;
    private double cost;
    private double profit;
    private String monthName;
    private String yearName;
    
    /**
     * @return the invoice_no
     */
    public int getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoice_no the invoice_no to set
     */
    public void setInvoiceNo(int invoice_no) {
        this.invoiceNo = invoice_no;
    }

    /**
     * @return the sale_date
     */
    public String getSaleDate() {
        return saleDate;
    }

    /**
     * @param sale_date the sale_date to set
     */
    public void setSaleDate(String sale_date) {
        this.saleDate = sale_date;
    }

    /**
     * @return the sale_time
     */
    public String getSaleTime() {
        return saleTime;
    }

    /**
     * @param sale_time the sale_time to set
     */
    public void setSaleTime(String sale_time) {
        this.saleTime = sale_time;
    }

    /**
     * @return the sale_by
     */
    public String getSaleBy() {
        return saleBy;
    }

    /**
     * @param sale_by the sale_by to set
     */
    public void setSaleBy(String sale_by) {
        this.saleBy = sale_by;
    }

    /**
     * @return the item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param item_code the item_code to set
     */
    public void setItemCode(String item_code) {
        this.itemCode = item_code;
    }

    /**
     * @return the sale_qty
     */
    public int getSaleQty() {
        return saleQty;
    }

    /**
     * @param sale_qty the sale_qty to set
     */
    public void setSaleQty(int sale_qty) {
        this.saleQty = sale_qty;
    }

    /**
     * @return the gross_amount
     */
    public double getGrossAmount() {
        return grossAmount;
    }

    /**
     * @param gross_amount the gross_amount to set
     */
    public void setGrossAmount(double gross_amount) {
        this.grossAmount = gross_amount;
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
     * @return the extra_discount
     */
    public double getExtraDiscount() {
        return extraDiscount;
    }

    /**
     * @param extra_discount the extra_discount to set
     */
    public void setExtraDiscount(double extra_discount) {
        this.extraDiscount = extra_discount;
    }

    /**
     * @return the net_total
     */
    public double getNetTotal() {
        return netTotal;
    }

    /**
     * @param net_total the net_total to set
     */
    public void setNetTotal(double net_total) {
        this.netTotal = net_total;
    }

    /**
     * @return the money_recieve
     */
    public double getMoneyRecieve() {
        return moneyRecieve;
    }

    /**
     * @param money_recieve the money_recieve to set
     */
    public void setMoneyRecieve(double money_recieve) {
        this.moneyRecieve = money_recieve;
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
     * @return the income
     */
    public double getIncome() {
        return income;
    }

    /**
     * @param income the income to set
     */
    public void setIncome(double income) {
        this.income = income;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return the profit
     */
    public double getProfit() {
        return profit;
    }

    /**
     * @param profit the profit to set
     */
    public void setProfit(double profit) {
        this.profit = profit;
    }

    /**
     * @return the costOfBill
     */
    public double getCostOfBill() {
        return costOfBill;
    }

    /**
     * @param costOfBill the costOfBill to set
     */
    public void setCostOfBill(double costOfBill) {
        this.costOfBill = costOfBill;
    }

    /**
     * @return the monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName the monthName to set
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    /**
     * @return the yearName
     */
    public String getYearName() {
        return yearName;
    }

    /**
     * @param yearName the yearName to set
     */
    public void setYearName(String yearName) {
        this.yearName = yearName;
    }
}
