package appDataModels;

import serverDataModels.MeasureUnit;

/**
 *
 * @author MalithWanniarachchi
 */
public class MeasureUnitModel {
    
    private int measureUnitId; 
    private String unitNameEng; 
    private String unitNameSin; 
    private String unitNameTam; 
    private String description;
    
    public MeasureUnitModel(MeasureUnit measureUnit) {
        this.measureUnitId = measureUnit.measure_unit_id; 
        this.unitNameEng = measureUnit.unit_name_eng; 
        this.unitNameSin = measureUnit.unit_name_sin; 
        this.unitNameTam = measureUnit.unit_name_tam;
        this.description = measureUnit.description;
    }
    
    public MeasureUnit newMeasureUnitDTO() {
        MeasureUnit dto = new MeasureUnit();
        dto.measure_unit_id   = this.getMeasureUnitId();
        dto.unit_name_eng  = this.getUnitNameEng();
        dto.unit_name_sin      = this.getUnitNameSin();
        dto.unit_name_tam  = this.getUnitNameTam();
        dto.description   = this.getDescription();
        
        return dto;
    }
    
    /**
     * @return the measureUnitId
     */
    public int getMeasureUnitId() {
        return measureUnitId;
    }

    /**
     * @param measureUnitId the measureUnitId to set
     */
    public void setMeasureUnitId(int measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    /**
     * @return the unitNameEng
     */
    public String getUnitNameEng() {
        return unitNameEng;
    }

    /**
     * @param unitNameEng the unitNameEng to set
     */
    public void setUnitNameEng(String unitNameEng) {
        this.unitNameEng = unitNameEng;
    }

    /**
     * @return the unitNameSin
     */
    public String getUnitNameSin() {
        return unitNameSin;
    }

    /**
     * @param unitNameSin the unitNameSin to set
     */
    public void setUnitNameSin(String unitNameSin) {
        this.unitNameSin = unitNameSin;
    }

    /**
     * @return the unitNameTam
     */
    public String getUnitNameTam() {
        return unitNameTam;
    }

    /**
     * @param unitNameTam the unitNameTam to set
     */
    public void setUnitNameTam(String unitNameTam) {
        this.unitNameTam = unitNameTam;
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
}
