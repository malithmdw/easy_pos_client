package serverDataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineOrder {
    public int id;
    public String order_no;
    public String total_amount;
    public String order_status;
    public String mop;
    public String notes;
    public String admin_note;
    public String status_updated_at;
    public String created_at;
    public String updated_at;
    public String customer_name;
    public String contact_number;
    public String contact_number_2;
    public String username;
    public String address_line1;
    public String address_line2;
    public String address_line3;
    public String district_name;
    public String province_name;
}
