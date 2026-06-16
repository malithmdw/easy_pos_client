
package appDataModels;

import serverDataModels.Institute;

/**
 *
 * @author MalithWanniarachchi
 */
public class InstituteModel {

    private int instituteId;
    private String businessName;
    private String description;
    private String printBusinessName;
    private String printBusinessSubName;
    private String printBusinessAddressLine1;
    private String printBusinessAddressLine2;
    private String printBusinessAddressLine3;
    private String printBusinessContact;
    private String printFooter1;
    private String printFooter2;
    private String serviceProviderPrintLine;

    // ✅ DTO → Model
    public InstituteModel(Institute dto) {
        this.instituteId = dto.institute_id;
        this.businessName = dto.business_name;
        this.description = dto.description;
        this.printBusinessName = dto.print_business_name;
        this.printBusinessSubName = dto.print_business_sub_name;
        this.printBusinessAddressLine1 = dto.print_business_address_line_1;
        this.printBusinessAddressLine2 = dto.print_business_address_line_2;
        this.printBusinessAddressLine3 = dto.print_business_address_line_3;
        this.printBusinessContact = dto.print_business_contact;
        this.printFooter1 = dto.print_footer_1;
        this.printFooter2 = dto.print_footer_2;
        this.serviceProviderPrintLine = dto.service_provider_print_line;
    }

    // ✅ Model → DTO
    public Institute newInstituteDTO() {
        Institute dto = new Institute();
        dto.institute_id = this.getInstituteId();
        dto.business_name = this.getBusinessName();
        dto.description = this.getDescription();
        dto.print_business_name = this.getPrintBusinessName();
        dto.print_business_sub_name = this.getPrintBusinessSubName();
        dto.print_business_address_line_1 = this.getPrintBusinessAddressLine1();
        dto.print_business_address_line_2 = this.getPrintBusinessAddressLine2();
        dto.print_business_address_line_3 = this.getPrintBusinessAddressLine3();
        dto.print_business_contact = this.getPrintBusinessContact();
        dto.print_footer_1 = this.getPrintFooter1();
        dto.print_footer_2 = this.getPrintFooter2();
        dto.service_provider_print_line = this.getServiceProviderPrintLine();
        return dto;
    }

    // ✅ Getters & Setters
    public int getInstituteId() { return instituteId; }
    public void setInstituteId(int instituteId) { this.instituteId = instituteId; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrintBusinessName() { return printBusinessName; }
    public void setPrintBusinessName(String printBusinessName) { this.printBusinessName = printBusinessName; }

    public String getPrintBusinessAddressLine1() { return printBusinessAddressLine1; }
    public void setPrintBusinessAddressLine1(String printBusinessAddressLine1) { this.printBusinessAddressLine1 = printBusinessAddressLine1; }

    public String getPrintBusinessAddressLine2() { return printBusinessAddressLine2; }
    public void setPrintBusinessAddressLine2(String printBusinessAddressLine2) { this.printBusinessAddressLine2 = printBusinessAddressLine2; }

    public String getPrintBusinessAddressLine3() { return printBusinessAddressLine3; }
    public void setPrintBusinessAddressLine3(String printBusinessAddressLine3) { this.printBusinessAddressLine3 = printBusinessAddressLine3; }

    public String getPrintBusinessContact() { return printBusinessContact; }
    public void setPrintBusinessContact(String printBusinessContact) { this.printBusinessContact = printBusinessContact; }

    public String getPrintFooter1() { return printFooter1; }
    public void setPrintFooter1(String printFooter1) { this.printFooter1 = printFooter1; }

    public String getPrintFooter2() { return printFooter2; }
    public void setPrintFooter2(String printFooter2) { this.printFooter2 = printFooter2; }
    
    public String getServiceProviderPrintLine() { return serviceProviderPrintLine;}
    public void setServiceProviderPrintLine(String serviceProviderPrintLine) { this.serviceProviderPrintLine = serviceProviderPrintLine; }

    public String getPrintBusinessSubName() {  return printBusinessSubName; }
    public void setPrintBusinessSubName(String printBusinessSubName) { this.printBusinessSubName = printBusinessSubName;  }
}
