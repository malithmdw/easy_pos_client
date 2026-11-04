package serverResponseDataModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentHistoryItem {
    public String clientRef;
    public String categoryId;
    public String serviceProviderId;
    public String accountNumber;
    public double amount;
    public String dateTime;
    public String packageId;
    public String status;
    public Double previousBalance;
    public Double deductionAmount;
    public Double newBalance;
    public Double commission;
}
