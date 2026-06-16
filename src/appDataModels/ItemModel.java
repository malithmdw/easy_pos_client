package appDataModels;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import serverDataModels.Item;

/**
 *
 * @author MalithWanniarachchi
 */
public class ItemModel {

    private int itemId;
    private int instituteId;
    private String barcode;
    private String itemName;
    private String itemNameSin;
    private String itemNameTam;
    private int categoryId;
    private int measureUnitId;
    private String imageName;
    private double minimumStock;
    private String addedBy;
    private String addedDateTime;
    private String modifiedBy;
    private String modifiedDateTime;
    private boolean active;
    private CategoryModel categoryModel;
    private MeasureUnitModel measureUnitModel;
    
    private List<ItemStockModel> stock;

    // Constructor: Map API DTO -> Application Model
    public ItemModel(Item item) {
        this.itemId = item.item_id;
        this.instituteId = item.institute_id;
        this.barcode = item.barcode;
        this.itemName = item.item_name;
        this.itemNameSin = item.item_name_sin;
        this.itemNameTam = item.item_name_tam;
        this.categoryId = item.category_id;
        this.measureUnitId = item.measure_unit_id;
        this.imageName = item.image_name;
        this.minimumStock = item.minimum_stock;
        this.addedBy = item.added_by;
        this.addedDateTime = item.added_date_time;
        this.modifiedBy = item.modified_by;
        this.modifiedDateTime = item.modified_date_time;
        this.active = (item.is_active == 1);
        
        if (item.category != null) {
            CategoryModel cm = new CategoryModel(item.category);
            this.categoryModel = cm;
        }
        
        if (item.measureUnit != null) {
            MeasureUnitModel mum = new MeasureUnitModel(item.measureUnit);
            this.measureUnitModel = mum;
        }
        
        if (item.stock != null) {
            this.stock = item.stock.stream()
                    .map(ItemStockModel::new)     // convert DTO → Model
                    .collect(Collectors.toList()); // ✅ Java 8 way
        } else {
            this.stock = new ArrayList<>();
        }
    }

    // Convert Application Model -> API DTO
    public Item newItemDTO() {
        Item dto = new Item();
        dto.item_id = this.getItemId();
        dto.institute_id = this.getInstituteId();
        dto.barcode = this.getBarcode();
        dto.item_name = this.getItemName();
        dto.item_name_sin = this.getItemNameSin();
        dto.item_name_tam = this.getItemNameTam();
        dto.category_id = this.getCategoryId();
        dto.measure_unit_id = this.getMeasureUnitId();
        dto.image_name = this.getImageName();
        dto.minimum_stock = this.getMinimumStock();
        dto.added_by = this.getAddedBy();
        dto.added_date_time = this.getAddedDateTime();
        dto.modified_by = this.getModifiedBy();
        dto.modified_date_time = this.getModifiedDateTime();
        dto.is_active = this.isActive() ? 1 : 0;
        
        if (this.categoryModel != null) {
            dto.category = this.categoryModel.newCategoryDTO();
        }
        
        if (this.measureUnitModel != null) {
            dto.measureUnit = this.measureUnitModel.newMeasureUnitDTO();
        }
        
        if (this.stock != null) {
            dto.stock = this.stock.stream()
                    .map(ItemStockModel::newItemStockDTO) // convert Model → DTO
                    .collect(Collectors.toList());        // ✅ Java 8 way
        } else {
            dto.stock = new ArrayList<>();
        }
        
        return dto;
    }

    // Getters & Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getInstituteId() { return instituteId; }
    public void setInstituteId(int instituteId) { this.instituteId = instituteId; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemNameSin() { return itemNameSin; }
    public void setItemNameSin(String itemNameSin) { this.itemNameSin = itemNameSin; }

    public String getItemNameTam() { return itemNameTam; }
    public void setItemNameTam(String itemNameTam) { this.itemNameTam = itemNameTam; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getMeasureUnitId() { return measureUnitId; }
    public void setMeasureUnitId(int measureUnitId) { this.measureUnitId = measureUnitId; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public double getMinimumStock() { return minimumStock; }
    public void setMinimumStock(double minimumStock) { this.minimumStock = minimumStock; }

    public String getAddedBy() { return addedBy; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }

    public String getAddedDateTime() { return addedDateTime; }
    public void setAddedDateTime(String addedDateTime) { this.addedDateTime = addedDateTime; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public String getModifiedDateTime() { return modifiedDateTime; }
    public void setModifiedDateTime(String modifiedDateTime) { this.modifiedDateTime = modifiedDateTime; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public CategoryModel getCategoryModel() {   return categoryModel; }
    public void setCategoryModel(CategoryModel categoryModel) { this.categoryModel = categoryModel; }

    public MeasureUnitModel getMeasureUnitModel() { return measureUnitModel; }
    public void setMeasureUnitModel(MeasureUnitModel measureUnitModel) { this.measureUnitModel = measureUnitModel; }
    
    public List<ItemStockModel> getStock() { return stock; }
    public void setStock(List<ItemStockModel> stock) { this.stock = stock; }
    
}
