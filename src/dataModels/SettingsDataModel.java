package dataModels;


public class SettingsDataModel {
    private String userName;
    private String password;
    private int accountType;
    
    private int stocks;
    private int editingStocks;
    private int deletingStocks;
    private int addingStocks;
    private int supplies;
    private int addingSuppliers;
    private int editingSuppliers;
    private int deletingSuppliers;
    private int reports;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the accountType
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the stocks
     */
    public int getStocks() {
        return stocks;
    }

    /**
     * @param stocks the stocks to set
     */
    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    /**
     * @return the editingStocks
     */
    public int getEditingStocks() {
        return editingStocks;
    }

    /**
     * @param editingStocks the editingStocks to set
     */
    public void setEditingStocks(int editingStocks) {
        this.editingStocks = editingStocks;
    }

    /**
     * @return the deletingStocks
     */
    public int getDeletingStocks() {
        return deletingStocks;
    }

    /**
     * @param deletingStocks the deletingStocks to set
     */
    public void setDeletingStocks(int deletingStocks) {
        this.deletingStocks = deletingStocks;
    }

    /**
     * @return the addingStocks
     */
    public int getAddingStocks() {
        return addingStocks;
    }

    /**
     * @param addingStocks the addingStocks to set
     */
    public void setAddingStocks(int addingStocks) {
        this.addingStocks = addingStocks;
    }

    /**
     * @return the supplies
     */
    public int getSupplies() {
        return supplies;
    }

    /**
     * @param supplies the supplies to set
     */
    public void setSupplies(int supplies) {
        this.supplies = supplies;
    }

    /**
     * @return the addingSuppliers
     */
    public int getAddingSuppliers() {
        return addingSuppliers;
    }

    /**
     * @param addingSuppliers the addingSuppliers to set
     */
    public void setAddingSuppliers(int addingSuppliers) {
        this.addingSuppliers = addingSuppliers;
    }

    /**
     * @return the editingSuppliers
     */
    public int getEditingSuppliers() {
        return editingSuppliers;
    }

    /**
     * @param editingSuppliers the editingSuppliers to set
     */
    public void setEditingSuppliers(int editingSuppliers) {
        this.editingSuppliers = editingSuppliers;
    }

    /**
     * @return the deletingSuppliers
     */
    public int getDeletingSuppliers() {
        return deletingSuppliers;
    }

    /**
     * @param deletingSuppliers the deletingSuppliers to set
     */
    public void setDeletingSuppliers(int deletingSuppliers) {
        this.deletingSuppliers = deletingSuppliers;
    }

    /**
     * @return the reports
     */
    public int getReports() {
        return reports;
    }

    /**
     * @param reports the reports to set
     */
    public void setReports(int reports) {
        this.reports = reports;
    }
    
}
