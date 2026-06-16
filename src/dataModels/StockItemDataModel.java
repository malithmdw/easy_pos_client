package dataModels;

public class StockItemDataModel {

    private int item_code;
    private String barcode;
    private String item_name;
    private String item_name_sinhala;
    private String item_name_tamil;
    private String sub_name;
    private ItemCategory category;
    private MeasureUnit measureUnit;    
    private double qty;    
    private double wholeSalePrice;
    private double selling_price;
    private double discount;
    private String supp_code1;
    private String supp_code2;
    private int wholeSaleMinQty;
    private int minimumQtyForAlert;
    private String comment;
    private String last_edit_date;
    private String last_editor;
    private String exp_date;

    
    /**
     * @return the category
     */
    public ItemCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    /**
     * @return the measureUnit
     */
    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    /**
     * @param measureUnit the measureUnit to set
     */
    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }
    
    /**
     * @return the wholeSalePrice
     */
    public double getWholeSalePrice() {
        return wholeSalePrice;
    }

    /**
     * @param wholeSalePrice the wholeSalePrice to set
     */
    public void setWholeSalePrice(double wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }
    
    /**
     * @return the wholeSameMinQty
     */
    public int getWholeSaleMinQty() {
        return wholeSaleMinQty;
    }

    /**
     * @param wholeSameMinQty the wholeSameMinQty to set
     */
    public void setWholeSaleMinQty(int wholeSameMinQty) {
        this.wholeSaleMinQty = wholeSameMinQty;
    }

    /**
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * @return the item_name_sinhala
     */
    public String getItem_name_sinhala() {
        return item_name_sinhala;
    }

    /**
     * @param item_name_sinhala the item_name_sinhala to set
     */
    public void setItem_name_sinhala(String item_name_sinhala) {
        this.item_name_sinhala = item_name_sinhala;
    }

    /**
     * @return the item_name_tamil
     */
    public String getItem_name_tamil() {
        return item_name_tamil;
    }

    /**
     * @param item_name_tamil the item_name_tamil to set
     */
    public void setItem_name_tamil(String item_name_tamil) {
        this.item_name_tamil = item_name_tamil;
    }
    
    /**
     * @return the item_code
     */
    public int getItem_code() {
        return item_code;
    }

    /**
     * @param item_code the item_code to set
     */
    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    /**
     * @return the item_name
     */
    public String getItem_name() {
        return item_name;
    }

    /**
     * @param item_name the item_name to set
     */
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    /**
     * @return the sub_name
     */
    public String getSub_name() {
        return sub_name;
    }

    /**
     * @param sub_name the sub_name to set
     */
    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the selling_price
     */
    public double getSelling_price() {
        return selling_price;
    }

    /**
     * @param selling_price the selling_price to set
     */
    public void setSelling_price(String selling_price) {
        this.setSelling_price(selling_price);
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(String discount) {
        this.setDiscount(discount);
    }

    /**
     * @return the supp_code1
     */
    public String getSupp_code1() {
        return supp_code1;
    }

    /**
     * @param supp_code1 the supp_code1 to set
     */
    public void setSupp_code1(String supp_code1) {
        this.supp_code1 = supp_code1;
    }

    /**
     * @return the supp_code2
     */
    public String getSupp_code2() {
        return supp_code2;
    }

    /**
     * @param supp_code2 the supp_code2 to set
     */
    public void setSupp_code2(String supp_code2) {
        this.supp_code2 = supp_code2;
    }

    /**
     * @return the coment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param coment the coment to set
     */
    public void setComment(String coment) {
        this.comment = coment;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @param selling_price the selling_price to set
     */
    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    /**
     * @return the last_edit_date
     */
    public String getLast_edit_date() {
        return last_edit_date;
    }

    /**
     * @param last_edit_date the last_edit_date to set
     */
    public void setLast_edit_date(String last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    /**
     * @return the last_editor
     */
    public String getLast_editor() {
        return last_editor;
    }

    /**
     * @param last_editor the last_editor to set
     */
    public void setLast_editor(String last_editor) {
        this.last_editor = last_editor;
    }

    /**
     * @return the minimumQtyForAlert
     */
    public int getMinimumQtyForAlert() {
        return minimumQtyForAlert;
    }

    /**
     * @param minimumQtyForAlert the minimumQtyForAlert to set
     */
    public void setMinimumQtyForAlert(int minimumQtyForAlert) {
        this.minimumQtyForAlert = minimumQtyForAlert;
    }

    /**
     * @return the exp_date
     */
    public String getExp_date() {
        return exp_date;
    }

    /**
     * @param exp_date the exp_date to set
     */
    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }
}
