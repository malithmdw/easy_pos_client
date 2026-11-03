package serverDataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import control.ApplicationDataManager;
import dataModels.Language;

/**
 * Maps one entry from the EasyReload get-category.php response:
 * {"id":"mobile","en":"Reload","si":"රීලෝඩ්","ta":"மீண்டும் ஏற்றவும்",
 *  "image":"icon_reload.jpg","accountType":"MOBILE"}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentCategoryModel {
    public String id;
    public String en;
    public String si;
    public String ta;
    public String image;
    public String accountType;

    /** Returns the localized display name based on the current application language. */
    public String getLocalizedName() {
        Language lang = ApplicationDataManager.getInstance().getApplicationLanguage();
        if (Language.SINHALA.equals(lang) && si != null && !si.isEmpty()) {
            return si;
        }
        if (Language.TAMIL.equals(lang) && ta != null && !ta.isEmpty()) {
            return ta;
        }
        return en != null ? en : (id != null ? id : "");
    }
}
