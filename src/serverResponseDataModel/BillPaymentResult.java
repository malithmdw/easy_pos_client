package serverResponseDataModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Maps the data payload returned by the EasyReload process-payment.php API:
 * {"clientRef","categoryId","serviceProviderId","accountNumber","amount","packageId","dateTime","status"}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentResult {
    public String clientRef;
    public String categoryId;
    public String serviceProviderId;
    public String accountNumber;
    public double amount;
    public String packageId;
    public String dateTime;
    public String status;
}
