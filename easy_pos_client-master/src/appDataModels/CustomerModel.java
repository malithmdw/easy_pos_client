
package appDataModels;

import serverDataModels.Customer;

/**
 *
 * @author MalithWanniarachchi
 */
public class CustomerModel {
    private int customerId;
    private int instituteId;
    private String customerName;
    private String contactNumber;
    private String email;
    private double accountBalance;
    private boolean active;
    private String addedBy;
    private String addedTime;
    
    public CustomerModel(){}

    // ✅ DTO → Model
    public CustomerModel(Customer dto) {
        this.customerId = dto.customer_id;
        this.instituteId = dto.institute_id;
        this.customerName = dto.customer_name;
        this.contactNumber = dto.contact_number;
        this.email = dto.email;
        this.accountBalance = dto.account_balance;
        this.active = (dto.is_active == 1);
        this.addedBy = dto.added_by;
        this.addedTime = dto.added_time;
    }

    // ✅ Model → DTO
    public Customer newCustomerDTO() {
        Customer dto = new Customer();
        dto.customer_id = this.getCustomerId();
        dto.institute_id = this.getInstituteId();
        dto.customer_name = this.getCustomerName();
        dto.contact_number = this.getContactNumber();
        dto.email = this.getEmail();
        dto.account_balance = this.getAccountBalance();
        dto.is_active = this.isActive() ? 1 : 0;
        dto.added_by = this.getAddedBy();
        dto.added_time = this.getAddedTime();
        return dto;
    }

    // ✅ Getters & Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getInstituteId() { return instituteId; }
    public void setInstituteId(int instituteId) { this.instituteId = instituteId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getAddedBy() { return addedBy; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }

    public String getAddedTime() { return addedTime; }
    public void setAddedTime(String addedTime) { this.addedTime = addedTime; }
}
