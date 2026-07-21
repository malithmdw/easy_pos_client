package serverDataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author MalithWanniarachchi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBrand {
    public int brand_id;
    public String brand_name;
    public String brand_name_sin;
    public String brand_name_tam;
    public String icon_name;
    public int status;
}
