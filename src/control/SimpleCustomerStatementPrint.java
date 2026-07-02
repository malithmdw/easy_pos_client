
package control;

import appDataModels.CustomerLedgerModel;
import appDataModels.CustomerModel;
import dataModels.Language;
import dataModels.ReceiptCommonData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import util.DateTimeUtil;

/**
 *
 * @author MalithWanniarachchi
 */
public class SimpleCustomerStatementPrint implements Printable{
    
    private final Font FONT_ARIAL_P_9 = new Font("Arial", Font.PLAIN , 9);
    private final Font FONT_TIMES_B_12 = new Font("Times New Roman", Font.BOLD, 12);
    private final Font FONT_TIMES_B_10 = new Font("Times New Roman", Font.BOLD, 10);
    private final Font FONT_TIMES_P_9 = new Font("Times New Roman", Font.PLAIN, 9);
    private final Font FONT_MONOS_P_9 = new Font("Monospaced", Font.PLAIN, 9);
    private final Font FONT_MONOS_B_9 = new Font("Monospaced", Font.BOLD, 9);
    private final Font FONT_LUCID_P_14 = new Font("Lucida Calligraphy", Font.PLAIN, 14);
    
    private List<CustomerLedgerModel> customerLedgerModels;
    private ReceiptCommonData receiptCommonData;
    private CustomerModel customerModel;
    private String cashierName;
    
    public SimpleCustomerStatementPrint(
            CustomerModel customerModel,
            List<CustomerLedgerModel> customerLedgerModels, 
            ReceiptCommonData receiptCommonData, 
            String cashierName) {
        this.customerModel = customerModel;
        this.customerLedgerModels = customerLedgerModels;
        this.receiptCommonData = receiptCommonData;
        this.cashierName = cashierName;
    }
        
    private void printDashSeparator(Graphics2D g2, int x, int y){
        String dashedLineBreak = "-----------------------------------------------------------\n";
        g2.setFont(FONT_MONOS_P_9);
        g2.drawString(dashedLineBreak, x, y);
    }

    @Override
    public int print(Graphics g, PageFormat Pf, int pageIndex) throws PrinterException{
        if (pageIndex > 0) 
            return NO_SUCH_PAGE;
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(Color.black);
                
        Font FONT_SINHAL_P_9 = FONT_ARIAL_P_9;
        Font FONT_SINHAL_B_9 = FONT_MONOS_B_9;
        Font FONT_SINHAL_B_10 = FONT_TIMES_B_10;
        Font FONT_SINHAL_B_12 = FONT_TIMES_B_12;
        
        try {
            
            FONT_SINHAL_P_9 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(9f);
            
            FONT_SINHAL_B_9 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(Font.BOLD, 9f);
            
            FONT_SINHAL_B_10 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(Font.BOLD, 10f);
            
            FONT_SINHAL_B_12 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()
            ).deriveFont(Font.BOLD, 12f);

        } catch (FontFormatException | IOException e) {
            System.err.println(e);
        }
                
        int x = 5;//13 - for Xprinter, 5- for Bixlon
        int y = 12;
        
        BufferedImage logo = null;
        try {
            if (ApplicationDataManager.getInstance().getReceiptLogo() != null) {                
                logo = ImageIO.read(ApplicationDataManager.getInstance().getReceiptLogo());
            }
        } catch (IOException ex) {
            EasyPosLogger.getInstance().error("", ex);
        }

        if (logo != null) {
            int printWidth = 200; // width for 80mm printer
            int scaledHeight = (logo.getHeight() * printWidth) / logo.getWidth();

            g2.drawImage(logo, 0, 0, printWidth, scaledHeight, null);

            y = y + scaledHeight;
        }
        
        // Header
        String header = receiptCommonData.getBusinessName();
        String subHeader = receiptCommonData.getBusinessSubName();
        
        if (header != null && !header.isEmpty()) {
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage())) {
                g2.setFont(FONT_SINHAL_B_12);
            }else{
                g2.setFont(FONT_TIMES_B_12);
            } 
            g2.drawString(header + "\n", x,y);
            y = y + 10;
        }
        if (subHeader != null && !subHeader.isEmpty()) {
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage())) {
                g2.setFont(FONT_SINHAL_B_10);
            }else{
                g2.setFont(FONT_TIMES_B_10);
            }
            g2.drawString(subHeader + "\n", x,y);
            y = y + 10;
        }
        
        y = y + 5;
        
        // Address
        String[] address = new String[3];
        address[0] = receiptCommonData.getAddressLine1();        
        address[1] = receiptCommonData.getAddressLine2();
        address[2] = receiptCommonData.getAddressLine3();
        
        for (String addres : address) {
            if (addres == null || addres.isEmpty()) {
                continue;
            }
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage())) {
                g2.setFont(FONT_SINHAL_P_9);
            }else{
                g2.setFont(FONT_ARIAL_P_9);
            }
            g2.drawString(addres + "\n", x, y);
            y = y + 13;
        }
        
        // Telephone number
        String telephoneNo = receiptCommonData.getContactDataLine();
        
        if (telephoneNo != null && !telephoneNo.isEmpty()) {
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage())) {
                g2.setFont(FONT_SINHAL_P_9);
            }else{
                g2.setFont(FONT_ARIAL_P_9);
            }
            g2.drawString(telephoneNo + "\n", x,y);
            y = y + 13;
        }
        
        // Top details
        String[] topData = new String[3];  
        
        if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptContentLanguage())) {
            topData[0] = DateTimeUtil.getTodayDateDBFormat()+ " " + DateTimeUtil.getCurrentTimeHHmmss() + "\n";
            topData[1] = "අයකැමි: "+ cashierName +"\n";
                        
            topData[2] = "දිනය   බිල්පත් අංකය    හර ණය   මුදල\n";  

            g2.setFont(FONT_SINHAL_P_9);
        }else{
            topData[0] = DateTimeUtil.getTodayDateDBFormat()+ " " + DateTimeUtil.getCurrentTimeHHmmss() + "\n";
            topData[1] = "Cashier: "+ cashierName +"\n";
            topData[2] = "DateTime   BillNo    Ledger   Amount\n";  

            g2.setFont(FONT_MONOS_P_9);
        }
        
        for (String topData1 : topData) {
            g2.drawString(topData1, x, y);
            y = y + 11;
        }
        
        printDashSeparator(g2, x, y);
        y = y + 11;
        
        // Items
        for (int i = 0; i < page().length; i++) {
            if (i%2 == 0) {
                if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptItemLanguage())) {
                    g2.setFont(FONT_SINHAL_P_9);
                }else{
                    g2.setFont(FONT_MONOS_P_9);
                }
            }
            else
            {
                g2.setFont(FONT_MONOS_P_9);
            }
            
            g2.drawString(page()[i] + "\n", x,y);
            y = y + 10;
        }
        
        printDashSeparator(g2, x, y);
        y = y + 11;
        
        // Bill summary
        String billSummaryEnglish = String.format(""
                    + "Customer Contact : %12s\n"
                    + "Customer Name    : %12s\n"
                    + "Balance          : %12s\n\n"
                    + "(Last 10 Transactions Only)",
                customerModel.getContactNumber(), 
                customerModel.getCustomerName(),
                util.GeneralUtil.getCurrencyString(customerModel.getAccountBalance()));
        
        String billSummarySin = String.format(""
                    + "පාරිභෝගික හැඳුනුම් අංකය: %12s\n"
                    + "පාරිභෝගික නම    : %12s\n"
                    + "ශේෂය          : %12s\n\n"
                    + "(අවසන් ගනුදෙනු 10 පමණි)",
                customerModel.getContactNumber(), 
                customerModel.getCustomerName(),
                util.GeneralUtil.getCurrencyString(customerModel.getAccountBalance()));
        
        String[] billSummary;
        Font billSummaryFont;
        Font billTotalFont;
        
        if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptContentLanguage())) {
            billSummary = billSummarySin.split("\n");
            billSummaryFont = FONT_SINHAL_P_9;
            billTotalFont = FONT_SINHAL_B_10;
        }else{
            billSummary = billSummaryEnglish.split("\n");
            billSummaryFont = FONT_MONOS_P_9;
            billTotalFont = FONT_MONOS_B_9;
        }
        
        g2.setFont(billSummaryFont);
        for (int i = 0; i < billSummary.length; i++) {            
            if (i == 2) {
                // Bold the Net Amount
                g2.setFont(billTotalFont);
                g2.drawString(billSummary[i], x,y);
                y = y + 11;
                g2.setFont(billSummaryFont);
                continue;
            }
            g2.drawString(billSummary[i], x,y);
            y = y + 11;
        }
                
        // Footer
        String[] footer = new String[3];
        footer[0] = receiptCommonData.getFooterLine1();
        footer[1] = receiptCommonData.getFooterLine2();
        footer[2] = receiptCommonData.getServiceProviderLine();
        
        for (String footer1 : footer) {
            if (footer1 == null || footer1.isEmpty()) {
                continue;
            }                
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptFooterLanguage())) {
                g2.setFont(FONT_SINHAL_P_9);
            } else {
                g2.setFont(FONT_TIMES_P_9);
            }
            g2.drawString(footer1 + "\n", x, y);
            y = y + 10;
        }

        return PAGE_EXISTS;
    }
     
    String[] page(){
        String[] pag=new String[customerLedgerModels.size()*2];
        int i=0;
        
        for (CustomerLedgerModel customerLedgerModel : customerLedgerModels) {
            
            String trantype;
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getReceiptItemLanguage())) {
                switch (customerLedgerModel.getLedgerType()) {
                    case CREDIT:
                        trantype = "ගෙවා ඇත";
                        break;
                    case DEBIT:
                        trantype = "ණය විකිණීම";
                        break;
                    default:
                        trantype = "";
                }
                
            } else {
                switch (customerLedgerModel.getLedgerType()) {
                    case CREDIT:
                        trantype = "PAID";
                        break;
                    case DEBIT:
                        trantype = "CREDIT SALE";
                        break;
                    default:
                        trantype = "";
                }
            }
            
            String itemLine1 = customerLedgerModel.getTransactionDate() + " " + customerLedgerModel.getTranId();
            String itemLine2 = String.format("%20s %12s",
                    trantype,
                    customerLedgerModel.getTransactionAmount());
            
            pag[i * 2] = itemLine1;
            pag[(i * 2) + 1] = itemLine2;
            i++;
        }
        return pag;
    }
    
}
