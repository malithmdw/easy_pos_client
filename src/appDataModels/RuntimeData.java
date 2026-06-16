package appDataModels;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MalithWanniarachchi
 */
public class RuntimeData {

    private String instituteId = "1";
    private String terminalId = "01";
    private String authToken;
    private UserAccountModel user;
    private InstituteModel selectedInstitute;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    
    /**
     * @return the categoryModelList
     */
    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    /**
     * @param categoryModelList the categoryModelList to set
     */
    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    /**
     * @return the instituteId
     */
    public String getInstituteId() {
        return instituteId;
    }

    /**
     * @param instituteId the instituteId to set
     */
    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    /**
     * @return the terminalId
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * @param terminalId the terminalId to set
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    
    /**
     * @return the user
     */
    public UserAccountModel getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserAccountModel user) {
        this.user = user;
    }

    public void setSelectedInstitute(InstituteModel instituteModel) {
        this.selectedInstitute = instituteModel;
    }
    
    /**
     * @return the selectedInstitute
     */
    public InstituteModel getSelectedInstitute() {
        return selectedInstitute;
    }

}
