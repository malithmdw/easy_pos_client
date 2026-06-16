
package appDataModels;


/**
 *
 * @author MalithWanniarachchi
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import serverDataModels.SaleInvoice;

public class SaleInvoiceModel {

    private long recId;
    private int instituteId;
    private String invoiceNo;
    private int invoiceType;
    private String saleTime;
    private String saleBy;
    private long customerId;
    private String custContactNo;
    private double grossAmount;
    private double noOfItems;
    private double totalDiscount;
    private double netTotal;
    private double moneyReceived;
    private double cardRecieved; 
    private double voucherReceived;
    private double totalReceived;
    private double balanceAmount;
    private double totalCost;
    private String settleDateTime;
    private String settleUpdateBy;
    private String cashRef;
    private String cardRef;
    private String voucherRef;
    private List<SaleItemModel> saleItemModels;

    // ✅ DTO → Model constructor
    public SaleInvoiceModel(SaleInvoice dto) {
        this.recId = dto.rec_id;
        this.instituteId = dto.institute_id;
        this.invoiceNo = dto.invoice_no;
        this.invoiceType = dto.invoice_type;
        this.saleTime = dto.sale_time;
        this.saleBy = dto.sale_by;
        this.customerId = dto.customer_id;
        this.custContactNo = dto.cust_contact_no;        
        this.grossAmount = dto.gross_amount;
        this.noOfItems = dto.no_of_items;
        this.totalDiscount = dto.total_discount;
        this.netTotal = dto.net_total;
        this.moneyReceived = dto.money_received;
        this.cardRecieved = dto.card_received;        
        this.voucherReceived = dto.voucher_received;
        this.totalReceived = dto.total_received;
        this.balanceAmount = dto.balance_amount;
        this.totalCost = dto.total_cost;
        this.settleDateTime = dto.settle_date_time;
        this.settleUpdateBy = dto.settle_update_by;
        this.cashRef = dto.cash_ref;
        this.cardRef = dto.card_ref;
        this.voucherRef = dto.voucher_ref;

        if (dto.sale_items != null) {
            this.saleItemModels = dto.sale_items.stream()
                    .map(SaleItemModel::new)   // DTO → Model
                    .collect(Collectors.toList());
        } else {
            this.saleItemModels = new ArrayList<>();
        }
    }

    // ✅ Model → DTO
    public SaleInvoice newSaleInvoiceDTO() {
        SaleInvoice dto = new SaleInvoice();
        dto.rec_id = this.getRecId();
        dto.institute_id = this.getInstituteId();
        dto.invoice_no = this.getInvoiceNo();
        dto.invoice_type = this.getInvoiceType();
        dto.sale_time = this.getSaleTime();
        dto.sale_by = this.getSaleBy();
        dto.customer_id = this.getCustomerId();
        dto.cust_contact_no = this.getCustContactNo();
        dto.gross_amount = this.getGrossAmount();
        dto.no_of_items = this.getNoOfItems();
        dto.total_discount = this.getTotalDiscount();
        dto.net_total = this.getNetTotal();
        dto.money_received = this.getMoneyReceived();
        dto.card_received = this.getCardRecieved();
        dto.voucher_received = this.getVoucherReceived();
        dto.total_received = this.getTotalReceived();
        dto.balance_amount = this.getBalanceAmount();
        dto.total_cost = this.getTotalCost();
        dto.settle_date_time = this.getSettleDateTime();
        dto.settle_update_by = this.getSettleUpdateBy();
        dto.cash_ref = this.getCashRef();
        dto.card_ref = this.getCardRef();
        dto.voucher_ref = this.getVoucherRef();

        if (this.saleItemModels != null) {
            dto.sale_items = this.saleItemModels.stream()
                    .map(SaleItemModel::newSaleItemDTO)  // Model → DTO
                    .collect(Collectors.toList());
        } else {
            dto.sale_items = new ArrayList<>();
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

    public int getInvoiceType() { return invoiceType; }
    public void setInvoiceType(int invoiceType) { this.invoiceType = invoiceType; }

    public String getSaleTime() { return saleTime; }
    public void setSaleTime(String saleTime) { this.saleTime = saleTime; }

    public String getSaleBy() { return saleBy; }
    public void setSaleBy(String saleBy) { this.saleBy = saleBy; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public double getGrossAmount() { return grossAmount; }
    public void setGrossAmount(double grossAmount) { this.grossAmount = grossAmount; }

    public double getNoOfItems() { return noOfItems; }
    public void setNoOfItems(double noOfItems) { this.noOfItems = noOfItems; }

    public double getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(double totalDiscount) { this.totalDiscount = totalDiscount; }

    public double getNetTotal() { return netTotal; }
    public void setNetTotal(double netTotal) { this.netTotal = netTotal; }

    public double getMoneyReceived() { return moneyReceived; }
    public void setMoneyReceived(double moneyReceived) { this.moneyReceived = moneyReceived; }

    public double getVoucherReceived() { return voucherReceived; }
    public void setVoucherReceived(double voucherReceived) { this.voucherReceived = voucherReceived; }

    public double getTotalReceived() { return totalReceived; }
    public void setTotalReceived(double totalReceived) { this.totalReceived = totalReceived; }

    public double getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(double balanceAmount) { this.balanceAmount = balanceAmount; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getSettleDateTime() { return settleDateTime; }
    public void setSettleDateTime(String settleDateTime) { this.settleDateTime = settleDateTime; }

    public String getSettleUpdateBy() { return settleUpdateBy; }
    public void setSettleUpdateBy(String settleUpdateBy) { this.settleUpdateBy = settleUpdateBy; }

    public List<SaleItemModel> getSaleItemModels() { return saleItemModels; }
    public void setSaleItemModels(List<SaleItemModel> saleItemModels) { this.saleItemModels = saleItemModels; }

    public double getCardRecieved() { return cardRecieved; }
    public void setCardRecieved(double cardRecieved) { this.cardRecieved = cardRecieved; }

    public String getCustContactNo() { return custContactNo; }
    public void setCustContactNo(String custContactNo) { this.custContactNo = custContactNo; }
    
    public String getCashRef() { return cashRef; }
    public void setCashRef(String cashRef) { this.cashRef = cashRef; }
    
    public String getCardRef() { return cardRef; }
    public void setCardRef(String cardRef) { this.cardRef = cardRef; }

    public String getVoucherRef() { return voucherRef; }
    public void setVoucherRef(String voucherRef) { this.voucherRef = voucherRef; }

}
