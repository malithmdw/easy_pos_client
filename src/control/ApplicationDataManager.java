
package control;

import appDataModels.InstituteModel;
import appDataModels.UserAccountModel;
import dataModels.Language;
import appDataModels.Permission;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import util.DateTimeUtil;

/**
 *
 * @author malit
 */
public class ApplicationDataManager {

    private static ApplicationDataManager INSTANCE;
    
    Locale localeSinhala = new Locale("si", "LK");
    ResourceBundle resourceBundleSinhala = ResourceBundle.getBundle("easyPOS/Bundle", localeSinhala);
    File fontFile = null;//new File("src\\resources\\NotoSansSinhala-VariableFont_wdth,wght.ttf");
    
    public static final String CATEGORY_LOCAL_FOLDER_PATH = "C:/EasyPOS/Images/Category/";
    public static final String ITEM_LOCAL_FOLDER_PATH = "C:/EasyPOS/Images/Item/";
    public static final String RECEIPT_LOGO_LOCAL_FOLDER_PATH = "C:/EasyPOS/Images/Other/";
    
        
    public static ApplicationDataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationDataManager();
        }
        return INSTANCE;
    }
    
    private UserAccountModel loggedInUser;
    private List<Permission> permissionsOfLoggedInUser;
    private ArrayList<InstituteModel> institutions;
    private Language applicationLanguage=Language.SINHALA;
    private Language receiptContentLanguage=Language.SINHALA;
    private Language receiptBusinessDataLanguage=Language.SINHALA;
    private Language receiptItemLanguage=Language.SINHALA;
    private Language receiptFooterLanguage=Language.SINHALA;
    private String receiptPrinterName = "BIXOLON SRP-E302";
    private String labelPrinterName = "ZDesigner ZD888-203dpi ZPL";
    private int lastInvoiceNumber = 0;
    private File receiptLogo;
    
    
    /**
     * @return the appData
     */
    public HashMap<String, String> getAppData() {
        HashMap<String, String> appData = new HashMap<>();
        appData.put("APPLICATION_LANGUAGE", applicationLanguage.name());
        appData.put("RECEIPT_BUSINESS_DATA_LANGUAGE", getReceiptBusinessDataLanguage().name());
        appData.put("RECEIPT_CONTENT_LANGUAGE", receiptContentLanguage.name());
        appData.put("RECEIPT_ITEM_LANGUAGE", receiptItemLanguage.name());
        appData.put("RECEIPT_FOOTER_LANGUAGE", getReceiptFooterLanguage().name());
        appData.put("LAST_INVOICE_NUMBER", Integer.toString(lastInvoiceNumber));
        appData.put("RECEIPT_PRINTER_NAME", receiptPrinterName);
        appData.put("LABEL_PRINTER_NAME", labelPrinterName);
        return appData;
    }
    
    public String getReceiptLogoName(int instituteId){
        return "Receipt_logo_" + instituteId + ".png";
    }

    /**
     * @param appData the appData to set
     */
    public void setAppData(HashMap<String, String> appData) {
        for (Map.Entry<String, String> entry : appData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "APPLICATION_LANGUAGE":
                    applicationLanguage = Language.valueOf(value);
                    break;
                case "RECEIPT_CONTENT_LANGUAGE":
                    receiptContentLanguage = Language.valueOf(value);
                    break;
                case "RECEIPT_ITEM_LANGUAGE":
                    applicationLanguage = Language.valueOf(value);
                    break;
                case "RECEIPT_BUSINESS_DATA_LANGUAGE":
                    setReceiptBusinessDataLanguage(Language.valueOf(value));
                    break;
                case "RECEIPT_FOOTER_LANGUAGE":
                    setReceiptFooterLanguage(Language.valueOf(value));
                    break;
                case "LAST_INVOICE_NUMBER":
                    lastInvoiceNumber = Integer.parseInt(value);
                    break;
                case "RECEIPT_PRINTER_NAME":
                    receiptPrinterName =  value;
                    break;
                case "LABEL_PRINTER_NAME":
                    labelPrinterName =  value;
                    break;
        
                default:
                    System.err.println("NO APP DATA VALUE FOUND");
            }
        }
    }
    
    public String getNextInvoiceNumber()
    {
        int nextInvNo = lastInvoiceNumber + 1;
        if (nextInvNo > 9999) {
            nextInvNo = 1;
        }
               
        String formattedNextInvNo = (String.format("%1$" + 5 + "s", nextInvNo)).replace(" ", "0");
        
        return formattedNextInvNo;
    }
        
    public String getNextTransactionId()
    {
        //0001000001202509200001
        //0001-000001-20250920-00001
        //INSTN-TERM-DATE-SEQUENCE
        
        String formattedInstnId = (String.format("%1$" + 4 + "s", RuntimeDataManager.getInstance().getRuntimeData().getInstituteId())).replace(" ", "0");
        String formattedTermId = (String.format("%1$" + 6 + "s", RuntimeDataManager.getInstance().getRuntimeData().getTerminalId())).replace(" ", "0");
        String formattedNextInvNo = ApplicationDataManager.getInstance().getNextInvoiceNumber();
        String formattedDate = DateTimeUtil.getTodayDateDBFormat().replace("-", "");
        
        return (formattedInstnId + formattedTermId + formattedDate + formattedNextInvNo);
    }
    
    
    public File getSinhalaFontFile(){
        
        if (fontFile == null) {
            try {
                fontFile = loadFontFile();
            } catch (IOException ex) {
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, ex.toString());
            }
        }
        return fontFile;
    }
    
    private File loadFontFile() throws IOException {
        String resourcePath = "resources/NotoSansSinhala-VariableFont_wdth,wght.ttf";
        // Load resource as InputStream
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new FileNotFoundException("Font resource not found: " + resourcePath);
        }

        // Create a temporary file
        File tempFile = File.createTempFile("tempFont", ".ttf");
        tempFile.deleteOnExit(); // delete on JVM exit

        // Copy the resource content to the temp file
        try (FileOutputStream os = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = is.read(buffer)) != -1) {
                os.write(buffer, 0, read);
            }
        }

        return tempFile;
    }
    /**
     * @return the institutions
     */
    public ArrayList<InstituteModel> getInstitutions() {
        return institutions;
    }

    /**
     * @param institutions the institutions to set
     */
    public void setInstitutions(ArrayList<InstituteModel> institutions) {
        this.institutions = institutions;
    }

    /**
     * @return the permissionsOfLoggedInUser
     */
    public List<Permission> getPermissionsOfLoggedInUser() {
        return permissionsOfLoggedInUser;
    }

    /**
     * @param permissionsOfLoggedInUser the permissionsOfLoggedInUser to set
     */
    public void setPermissionsOfLoggedInUser(List<Permission> permissionsOfLoggedInUser) {
        this.permissionsOfLoggedInUser = permissionsOfLoggedInUser;
    }
    /**
     * @return the loggedInUser
     */
    public UserAccountModel getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * @param loggedInUser the loggedInUser to set
     */
    public void setLoggedInUser(UserAccountModel loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * @return the applicationLanguage
     */
    public Language getApplicationLanguage() {
        return applicationLanguage;
    }

    /**
     * @param applicationLanguage the applicationLanguage to set
     */
    public void setApplicationLanguage(Language applicationLanguage) {
        this.applicationLanguage = applicationLanguage;
    }

    /**
     * @return the receiptContentLanguage
     */
    public Language getReceiptContentLanguage() {
        return receiptContentLanguage;
    }

    /**
     * @param receiptContentLanguage the receiptContentLanguage to set
     */
    public void setReceiptContentLanguage(Language receiptContentLanguage) {
        this.receiptContentLanguage = receiptContentLanguage;
    }

    /**
     * @return the receiptItemLanguage
     */
    public Language getReceiptItemLanguage() {
        return receiptItemLanguage;
    }

    /**
     * @param receiptItemLanguage the receiptItemLanguage to set
     */
    public void setReceiptItemLanguage(Language receiptItemLanguage) {
        this.receiptItemLanguage = receiptItemLanguage;
    }
    
    public ResourceBundle getResourceBundle()
    {
        switch (applicationLanguage) {
            case SINHALA:
                return resourceBundleSinhala;
            case TAMIL:
                return resourceBundleSinhala;
            default:
                return resourceBundleSinhala;
        }
    }

    public void updateInvoiceNumberUtilized() {
        lastInvoiceNumber++;
    }
    
    
    /**
     * @return the receiptBusinessDataLanguage
     */
    public Language getReceiptBusinessDataLanguage() {
        return receiptBusinessDataLanguage;
    }

    /**
     * @param receiptBusinessDataLanguage the receiptBusinessDataLanguage to set
     */
    public void setReceiptBusinessDataLanguage(Language receiptBusinessDataLanguage) {
        this.receiptBusinessDataLanguage = receiptBusinessDataLanguage;
    }

    /**
     * @return the receiptFooterLanguage
     */
    public Language getReceiptFooterLanguage() {
        return receiptFooterLanguage;
    }

    /**
     * @param receiptFooterLanguage the receiptFooterLanguage to set
     */
    public void setReceiptFooterLanguage(Language receiptFooterLanguage) {
        this.receiptFooterLanguage = receiptFooterLanguage;
    }

    
    /**
     * @return the printerName
     */
    public String getReceiptPrinterName() {
        return receiptPrinterName;
    }

    /**
     * @param printerName the printerName to set
     */
    public void setReceiptPrinterName(String printerName) {
        this.receiptPrinterName = printerName;
    }

    public File getReceiptLogo() {
        return receiptLogo;
    }

    public void setReceiptLogo(File receiptLogo) {
        this.receiptLogo = receiptLogo;
    }

    /**
     * @return the labelPrinterName
     */
    public String getLabelPrinterName() {
        return labelPrinterName;
    }

    /**
     * @param labelPrinterName the labelPrinterName to set
     */
    public void setLabelPrinterName(String labelPrinterName) {
        this.labelPrinterName = labelPrinterName;
    }
}
