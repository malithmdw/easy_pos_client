package serverDataModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import control.ApplicationDataManager;
import dataModels.Language;

/**
 * Maps one entry from the EasyReload get-providers.php response:
 * {"categoryId":"mobile","id":"sbx-reload","name":"Dialog",
 *  "si":"ඩයලොග්","ta":"டயலாக்","image":"dialog.png","sort":1}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentProviderModel {
    public String categoryId;
    public String id;
    public String name;
    public String si;
    public String ta;
    public String image;
    public int sort;

    /** Returns the localized display name based on the current application language. */
    public String getLocalizedName() {
        Language lang = ApplicationDataManager.getInstance().getApplicationLanguage();
        if (Language.SINHALA.equals(lang) && si != null && !si.isEmpty()) {
            return si;
        }
        if (Language.TAMIL.equals(lang) && ta != null && !ta.isEmpty()) {
            return ta;
        }
        return name != null ? name : (id != null ? id : "");
    }
}
