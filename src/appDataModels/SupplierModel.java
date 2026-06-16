
package appDataModels;

import serverDataModels.Supplier;

/**
 *
 * @author MalithWanniarachchi
 */
public class SupplierModel {

    private int supplierCode;
    private int instituteId;
    private String supplierName;
    private String supplierAddress;
    private String company;
    private String phoneNo1;
    private String nicNo;
    private String accNo;
    private String bank;
    private String phoneNo;
    private int status;
    
    public SupplierModel(Supplier sup) {
        this.supplierCode = sup.supplier_code;
        this.instituteId = sup.institute_id;
        this.supplierName = sup.supplier_name;
        this.supplierAddress = sup.supplier_address;
        this.company = sup.company;
        this.phoneNo1 = sup.phone_no1;
        this.nicNo = sup.nic_no;
        this.accNo = sup.acc_no;
        this.bank = sup.bank;
        this.phoneNo = sup.phone_no;
        this.status = sup.status;
    }
    
    // Convert Application Model -> API DTO
    public Supplier newSupplierDto() {
        Supplier dto = new Supplier();
        dto.supplier_code = this.getSupplierCode();
        dto.institute_id = this.getInstituteId();
        dto.supplier_name = this.getSupplierName();
        dto.supplier_address = this.getSupplierAddress();
        dto.company = this.getCompany();
        dto.phone_no1 = this.getPhoneNo1();
        dto.nic_no = this.getNicNo();
        dto.acc_no = this.getAccNo();
        dto.bank = this.getBank();
        dto.phone_no = this.getPhoneNo();
        dto.status = this.getStatus();
        return dto;        
    }
   
    public int getSupplierCode() { return supplierCode; }
    public void setSupplierCode(int supplierCode) {  this.supplierCode = supplierCode; }

    public int getInstituteId() { return instituteId; }
    public void setInstituteId(int instituteId) { this.instituteId = instituteId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierAddress() { return supplierAddress; }
    public void setSupplierAddress(String supplierAddress) { this.supplierAddress = supplierAddress; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPhoneNo1() { return phoneNo1; }
    public void setPhoneNo1(String phoneNo1) { this.phoneNo1 = phoneNo1; }

    public String getNicNo() { return nicNo; }
    public void setNicNo(String nicNo) { this.nicNo = nicNo; }

    public String getAccNo() { return accNo; }
    public void setAccNo(String accNo) { this.accNo = accNo; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

}
