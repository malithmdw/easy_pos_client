
package appDataModels;

import serverDataModels.CustomerLedger;

/**
 *
 * @author MalithWanniarachchi
 */
public class CustomerLedgerModel {
    private long recordId;
    private long customerId;
    private String tranId;
    private int invoiceNumber;
    private String description;
    private LedgerType ledgerType;
    private double transactionAmount;
    private String transactionDate;
    private String acceptBy;

    // Define enum cleanly for model
    public enum LedgerType { DEBIT, CREDIT }

    // ✅ DTO → Model
    public CustomerLedgerModel(CustomerLedger dto) {
        this.recordId = dto.record_id;
        this.customerId = dto.customer_id;
        this.tranId = dto.tran_id;
        this.invoiceNumber = dto.invoice_number;
        this.description = dto.description;
        this.ledgerType = (dto.ledger_type == CustomerLedger.LEDGER_TYPE.DEBIT) 
                            ? LedgerType.DEBIT : LedgerType.CREDIT;
        this.transactionAmount = dto.transaction_amount;
        this.transactionDate = dto.transaction_date;
        this.acceptBy = dto.accept_by;
    }

    // ✅ Model → DTO
    public CustomerLedger newCustomerLedgerDTO() {
        CustomerLedger dto = new CustomerLedger();
        dto.record_id = this.getRecordId();
        dto.customer_id = this.getCustomerId();
        dto.tran_id = this.getTranId();
        dto.invoice_number = this.getInvoiceNumber();
        dto.description = this.getDescription();
        dto.ledger_type = (this.getLedgerType() == LedgerType.DEBIT) 
                            ? CustomerLedger.LEDGER_TYPE.DEBIT 
                            : CustomerLedger.LEDGER_TYPE.CREDIT;
        dto.transaction_amount = this.getTransactionAmount();
        dto.transaction_date = this.getTransactionDate();
        dto.accept_by = this.getAcceptBy();
        return dto;
    }

    // ✅ Getters & Setters
    public long getRecordId() { return recordId; }
    public void setRecordId(long recordId) { this.recordId = recordId; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public String getTranId() { return tranId; }
    public void setTranId(String tranId) { this.tranId = tranId; }

    public int getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(int invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LedgerType getLedgerType() { return ledgerType; }
    public void setLedgerType(LedgerType ledgerType) { this.ledgerType = ledgerType; }

    public double getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(double transactionAmount) { this.transactionAmount = transactionAmount; }

    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    public String getAcceptBy() { return acceptBy; }
    public void setAcceptBy(String acceptBy) { this.acceptBy = acceptBy; }
}
