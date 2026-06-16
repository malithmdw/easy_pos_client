/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataModels;

/**
 *
 * @author malit
 */
public class MeasureUnit {
    
    private int measureUnitId;
    private String unitNameEng;
    private String unitNameSin;
    private String unitNameTam;
    private String description;
    
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
