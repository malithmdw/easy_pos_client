/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataModels;

/**
 *
 * @author malit
 */
public class ItemCategory {

    private int categoryId;
    private String categoryName;
    private String categoryNameSin;    
    private String categoryNameTam;
    private String description;
    private boolean enable;
    
    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the categoryNameSin
     */
    public String getCategoryNameSin() {
        return categoryNameSin;
    }

    /**
     * @param categoryNameSin the categoryNameSin to set
     */
    public void setCategoryNameSin(String categoryNameSin) {
        this.categoryNameSin = categoryNameSin;
    }

    /**
     * @return the categoryNameTam
     */
    public String getCategoryNameTam() {
        return categoryNameTam;
    }

    /**
     * @param categoryNameTam the categoryNameTam to set
     */
    public void setCategoryNameTam(String categoryNameTam) {
        this.categoryNameTam = categoryNameTam;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
