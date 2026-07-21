
package appDataModels;

import serverDataModels.Category;


/**
 *
 * @author MalithWanniarachchi
 */
public class CategoryModel {

    private int categoryId; 
    private int instituteId; 
    private String catName; 
    private String catNameSin; 
    private String catNameTam;
    private String imageName;
    private String description;
    private double trspp;
    private double twspp;

    public CategoryModel(Category category) {
        this.categoryId = category.category_id;
        this.instituteId = category.institute_id;
        this.catName = category.cat_name;
        this.catNameSin = category.cat_name_sin;
        this.catNameTam = category.cat_name_tam;
        this.description = category.description;
        this.imageName = category.image;
        this.trspp = category.trspp;
        this.twspp = category.twspp;
    }

    public Category newCategoryDTO() {
        Category dto = new Category();
        dto.category_id   = this.getCategoryId();
        dto.institute_id  = this.getInstituteId();
        dto.cat_name      = this.getCatName();
        dto.cat_name_sin  = this.getCatNameSin();
        dto.cat_name_tam  = this.getCatNameTam();
        dto.description   = this.getDescription();
        dto.image = this.getImageName();
        dto.trspp = this.getTrspp();
        dto.twspp = this.getTwspp();

        return dto;
    }
    
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
     * @return the instituteId
     */
    public int getInstituteId() {
        return instituteId;
    }

    /**
     * @param instituteId the instituteId to set
     */
    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    /**
     * @return the catName
     */
    public String getCatName() {
        return catName;
    }

    /**
     * @param catName the catName to set
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * @return the catNameSin
     */
    public String getCatNameSin() {
        return catNameSin;
    }

    /**
     * @param catNameSin the catNameSin to set
     */
    public void setCatNameSin(String catNameSin) {
        this.catNameSin = catNameSin;
    }

    /**
     * @return the catNameTam
     */
    public String getCatNameTam() {
        return catNameTam;
    }

    /**
     * @param catNameTam the catNameTam to set
     */
    public void setCatNameTam(String catNameTam) {
        this.catNameTam = catNameTam;
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
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the trspp
     */
    public double getTrspp() {
        return trspp;
    }

    /**
     * @param trspp the trspp to set
     */
    public void setTrspp(double trspp) {
        this.trspp = trspp;
    }

    /**
     * @return the twspp
     */
    public double getTwspp() {
        return twspp;
    }

    /**
     * @param twspp the twspp to set
     */
    public void setTwspp(double twspp) {
        this.twspp = twspp;
    }
}
