package serverDataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineOrder {
    public int order_id;
    public String order_number;
    public String customer_name;
    public String address_line_1;
    public String address_line_2;
    public String address_line_3;
    public String contact_number;
    public String order_status;
    public String order_date;
    public double total_amount;
    public String notes;
}
