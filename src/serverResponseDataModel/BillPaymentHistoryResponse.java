package serverResponseDataModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentHistoryResponse {
    public String fromDate;
    public String toDate;
    public int page;
    public int pageSize;
    public int totalItems;
    public List<BillPaymentHistoryItem> items;
}
