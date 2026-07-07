package serverDataModels;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author MalithWanniarachchi
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerLedger {
    
    public enum LEDGER_TYPE{DEBIT, CREDIT;};
    
    public long record_id; 
    public long customer_id; 
    public String tran_id; 
    public int invoice_number; 
    public String description; 
    public LEDGER_TYPE ledger_type; 
    public double transaction_amount;
    public String transaction_date; 
    public String accept_by;
}
