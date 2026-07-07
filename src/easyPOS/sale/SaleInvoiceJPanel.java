package easyPOS.sale;

import appDataModels.APIHeaderData;
import appDataModels.InstituteModel;
import appDataModels.ItemCardDataModel;
import appDataModels.ItemModel;
import appDataModels.ItemStockModel;
import control.ApplicationDataManager;
import control.EventManager;
import control.NumberPadKeyPressListener;
import control.RuntimeDataManager;
import control.SalesMenuItemClickListener;
import control.ServerDataSubmissionQueue;
import control.SimpleReceiptPrint;
import dataModels.BillDataModel;
import dataModels.BillItemDataModel;
import dataModels.Language;
import dataModels.MethodOfPayment;
import dataModels.ReceiptCommonData;
import easyPOS.NumberPanel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import control.EasyPosLogger;
import javax.print.PrintService;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import localDatabase.DatabaseManager;
import serverDataModels.Customer;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverResponseDataModel.CommonResponse;
import tableModels.SaleInvoiceItemTbl;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import util.DateTimeUtil;
import util.GeneralUtil;
import webService.ServerAPIConnection;
import easyPOS.localization.ApplicationMessages;

/**
 *
 * @author malit
 */
public class SaleInvoiceJPanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    
    DecimalFormat df = new DecimalFormat("#0.00");

    private easyPOS.customerdisplay.CustomerScreenFrame customerScreenFrame;

    public void setCustomerScreenFrame(easyPOS.customerdisplay.CustomerScreenFrame frame) {
        this.customerScreenFrame = frame;
    }

    private void pushToCustomerDisplay() {
        if (customerScreenFrame == null) return;
        easyPOS.customerdisplay.CustomerScreenInvoicePanel invoicePanel = customerScreenFrame.getInvoicePanel();
        easyPOS.customerdisplay.CustomerScreenLogoPanel logoPanel = customerScreenFrame.getLogoPanel();
        invoicePanel.updateTable(jTableSaleToBill.getModel());
        invoicePanel.updateSummary(
            jTextFieldSalesNoOfItms.getText(),
            jTextFieldGrossAmount.getText(),
            jTextFieldTotalDis.getText(),
            jTextFieldNetTot.getText(),
            jTextFieldMRecieve.getText(),
            jTextFieldBal.getText()
        );
        logoPanel.setCustomerName(jTextFieldCustName.getText());
    }

    BillDataModel billDataModel = new BillDataModel();
    BillDataModel lastIssuedBillDataModel = new BillDataModel();
    
    SalePanel parentSalePanel;
    
    ItemModel fetchedItem;
    ItemStockModel fetchedStockItem;
    Customer selectedCustomer;
    int nextRowNumber = 0;
    boolean isWholeSaleBill = false;
    boolean isDiscountEditable = false;
    boolean isWholesalePriceEditable = false;
    
    /**
     * Creates new form SaleInvoiceJPanel
     */
    public SaleInvoiceJPanel() {
        initComponents();
        switchLanguage();
        
        try {
            Font customFontSin_20 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(20f);
            Font customFontSin_18 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(14f);

            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())) {
                jTextFieldItmName2.setFont(customFontSin_20);
                jLabelMeasuringUnit.setFont(customFontSin_18);
            }
        } catch (FontFormatException | IOException ex) {
            EasyPosLogger.getInstance().error("", ex);
        }
        
        jTextFieldQty.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
//                System.out.println("Focus gained → cursor is inside this field");
                EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.NUMBER_PAD);
            }

            @Override
            public void focusLost(FocusEvent e) {
//                System.out.println("Focus lost");
            }
        });
        
        EventManager.getInstance().addSalesPanelCustomNumberPadKeyEventListener(new NumberPadKeyPressListener() {
            @Override
            public void onKeyPressed(NumberPadKeyPressListener.NumberPadButton pressed) {
                if (jTextFieldCustNo.isFocusOwner()) {
                    if (NumberPadKeyPressListener.NumberPadButton.BUTTON_OK.equals(pressed)) {
                        fireKeyPressEvent(jTextFieldCustNo);
                        return;
                    }
                    jTextFieldCustNo.setText(NumberPanel.getNum(jTextFieldCustNo.getText(), pressed));
                    jTextFieldCustNo.setRequestFocusEnabled(true);
                }else if (jTextFieldItmCode.isFocusOwner()) {
                    if (NumberPadKeyPressListener.NumberPadButton.BUTTON_OK.equals(pressed)) {
                        fireKeyPressEvent(jTextFieldItmCode);
                        return;
                    }
                    jTextFieldItmCode.setText(NumberPanel.getNum(jTextFieldItmCode.getText(), pressed));
                }else if (jTextFieldItmDis.isFocusOwner()) {
                    if (NumberPadKeyPressListener.NumberPadButton.BUTTON_OK.equals(pressed)) {
                        fireKeyPressEvent(jTextFieldItmDis);
                        return;
                    }
                    jTextFieldItmDis.setText(NumberPanel.getDouble(jTextFieldItmDis.getText(), pressed));
                }else if (jTextFieldQty.isFocusOwner()) {
                    if (NumberPadKeyPressListener.NumberPadButton.BUTTON_OK.equals(pressed)) {
                        fireKeyPressEvent(jTextFieldQty);
                        return;
                    }
                    jTextFieldQty.setText(NumberPanel.getDouble(jTextFieldQty.getText(), pressed));
                    calculateAmountOnTypeInput();
                }else if (jTextFieldWholesalePrice.isFocusOwner()) {
                    if (NumberPadKeyPressListener.NumberPadButton.BUTTON_OK.equals(pressed)) {
                        fireKeyPressEvent(jTextFieldWholesalePrice);
                        return;
                    }
                    jTextFieldWholesalePrice.setText(NumberPanel.getDouble(jTextFieldWholesalePrice.getText(), pressed));
                }else if (jTextFieldMRecieve.isFocusOwner()) {
                    if (NumberPadKeyPressListener.NumberPadButton.BUTTON_OK.equals(pressed)) {
                        fireKeyPressEvent(jTextFieldMRecieve);
                        return;
                    }
                    jTextFieldMRecieve.setText(NumberPanel.getDouble(jTextFieldMRecieve.getText(), pressed));
                }
            }
        });
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    public void setParentSalePanel(SalePanel salePanel) {
        this.parentSalePanel = salePanel;
    }
        
    private void fireKeyPressEvent(javax.swing.JTextField field)
    {
        KeyEvent enterPress = new KeyEvent(
                field,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_ENTER,
                '\n'
        );
        field.dispatchEvent(enterPress);
    }

    public void setNextInvoiceNumber() {
        // Fetch bill number
        String formattedNextInvNo = ApplicationDataManager.getInstance().getNextInvoiceNumber();
        jTextFieldInvNo.setText(formattedNextInvNo);
    }
    
    public void printLastBill(){
        printBill(lastIssuedBillDataModel);
    }
    
    
    
    private void fetchItem()
    {
        clearFieldsItemPanelExceptBarcode();
        ItemModel itemModel = DatabaseManager.getInstance().getItemByBarcode(jTextFieldItmCode.getText());
        
        if (itemModel == null) {
            long itemId;
            try {
                itemId = Long.parseLong(jTextFieldItmCode.getText());
                itemModel = DatabaseManager.getInstance().getItemByItemId(itemId);
            } catch (NumberFormatException e) {
            }            
        }
        
        if (itemModel == null) {
            EasyPOSMessageDialog.showLocalizedError(null, ApplicationMessages.ERROR_ITEM_NOT_FOUND);
        }
        else if (itemModel.getStock().isEmpty()) {
            EasyPOSMessageDialog.showLocalizedError(null, ApplicationMessages.ERROR_STOCK_UNAVAILABLE);            
        }
        else if (itemModel.getStock().size() == 1) {
            loadItemToAddToBill(itemModel, itemModel.getStock().get(0));
        }
        else {
            // Display window to select
            List<ItemCardDataModel> itemCardDataModels = new ArrayList<>();
                
            for (ItemStockModel itemStock : itemModel.getStock()) {
                ItemCardDataModel icdm = new ItemCardDataModel();
                icdm.setCardType(ItemCardDataModel.CardType.STOCK_ITEM);
                icdm.setImageName(itemModel.getImageName());
                icdm.setItemModel(itemModel);
                icdm.setItemNameText(itemModel.getItemName());
                icdm.setItemPriceText(GeneralUtil.getCurrencyString(itemStock.getSellingPrice()));
                icdm.setItemStockModel(itemStock);

                itemCardDataModels.add(icdm);
            }
                        
            parentSalePanel.loadItemCardData(itemCardDataModels);
        }
    }
    
    public void loadItemToAddToBill(ItemModel itemModel, ItemStockModel itemStockModel)
    {
        fetchedItem = itemModel;
        fetchedStockItem = itemStockModel;
        setFieldsForAddToBillPanel(itemModel, itemStockModel);//load fields
        jTextFieldQty.requestFocus();//move cursor
    }
    
    private void setFieldsForAddToBillPanel(ItemModel im, ItemStockModel ism){
        jTextFieldItmCode.setText(im.getBarcode());
        jTextFieldItmName1.setText(im.getItemName());
        jTextFieldItmName2.setText(im.getItemNameSin());
        jTextFieldUnitPrice.setText(df.format(ism.getSellingPrice()));
        jTextFieldItmDis.setText(df.format(ism.getDiscount()));
        jTextFieldWholesalePrice.setText(df.format(ism.getSellingPrice()));
        jLabelMeasuringUnit.setText(Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())?
                im.getMeasureUnitModel().getUnitNameSin():
                im.getMeasureUnitModel().getUnitNameEng());
    }  
    
    private void calculateAmountOnTypeInput(){
        if (fetchedStockItem != null) {
            double unitPrice = fetchedStockItem.getSellingPrice();
            
            try{
                double qty = Double.parseDouble(jTextFieldQty.getText());            
                double discountPerOneQty = Double.parseDouble(jTextFieldItmDis.getText());
                double wholeSalePrice = Double.parseDouble(jTextFieldWholesalePrice.getText());

                double amount = -1;

                if (isWholeSaleBill) 
                {
                    amount = wholeSalePrice * qty;
                }
                else
                {
                    amount = (unitPrice - discountPerOneQty) * qty;
                }

                if (amount <= 0) {
                    jTextFieldAmount.setText("");
                }
                else
                {
                    jTextFieldAmount.setText(df.format(amount));                    
                }

            }
            catch(NumberFormatException e)
            {
                jTextFieldAmount.setText("");
            }
        }        
    }
    
    private void validateAndAddToBill(){
        if (fetchedStockItem != null) {
            double unitPrice = fetchedStockItem.getSellingPrice();
            
            try{
                // Capture item values
                double qty = Double.parseDouble(jTextFieldQty.getText());            
                double discountPerOneQty = Double.parseDouble(jTextFieldItmDis.getText());
                double wholeSalePrice = Double.parseDouble(jTextFieldWholesalePrice.getText());

                // Validate
                double amount = -1;
                double netAmount = -1;

                if (isWholeSaleBill) 
                {
                    amount = wholeSalePrice;
                }
                else
                {
                    amount = (unitPrice - discountPerOneQty);
                }
                
                netAmount = amount * qty;

                if (netAmount <= 0)
                {
                    EasyPOSMessageDialog.showLocalizedError(null, ApplicationMessages.ERROR_INVALID_AMOUNT);
                    return;
                }
                
                BillItemDataModel alreadyAddedBillItem = null;
                
                // Check the item already added -> if values are same, add to the same record
                for (BillItemDataModel billItem : billDataModel.getBillItems()) {
                    if (billItem.getBatchId()== fetchedStockItem.getBatchId()&& 
                            billItem.getAmount() == amount) {
                        // Same item, update quentity
                        alreadyAddedBillItem = billItem;
                        break;
                    }
                }
                
                if (alreadyAddedBillItem != null) {
                    alreadyAddedBillItem.setQty(qty + alreadyAddedBillItem.getQty());
                    alreadyAddedBillItem.setNetAmount(alreadyAddedBillItem.getQty() * amount);
                    
                } else {
                    // create bill item & add
                    BillItemDataModel billItem = new BillItemDataModel();
                    billItem.setItemId(fetchedItem.getItemId());
                    billItem.setBatchId((int)fetchedStockItem.getBatchId());
                    billItem.setItemName(fetchedItem.getItemName());
                    billItem.setItemNameSin(fetchedItem.getItemNameSin());
                    billItem.setItemNameTam(fetchedItem.getItemNameTam());
                    billItem.setUnitPrice(unitPrice);
                    billItem.setDiscountPerone(discountPerOneQty);
                    billItem.setQty(qty);
                    billItem.setAmount(amount);
                    billItem.setNetAmount(netAmount);
                    billDataModel.getBillItems().add(billItem);
                }
                
                
                
                // display totals
                arrangeTotalValues();
                
                // Update table
                updateItemUITable();
                
                // Clear item data panel
                clearFieldsItemPanel();
                jTextFieldItmCode.requestFocus();
            }
            catch(NumberFormatException e)
            {
                jTextFieldAmount.setText("");
            }
        }        
    }
    
    private void updateItemUITable()
    {
        SaleInvoiceItemTbl saleInvoiceItemTbl = new SaleInvoiceItemTbl(billDataModel.getBillItems());
        jTableSaleToBill.setModel(saleInvoiceItemTbl);
        
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(jLabelInvoiceNo.RIGHT);
        
        for(int i=3; i<7; i++){
            jTableSaleToBill.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
        
        jTableSaleToBill.setRowHeight(35);
        pushToCustomerDisplay();
    }

    private void arrangeTotalValues()
    {
        double totalQty = 0;
        double totalDiscount = 0;
        double totalGrossAmount = 0;
        double totalNetAmount = 0;
        
        for (BillItemDataModel billItem : billDataModel.getBillItems()) {
            
            double discount = (billItem.getQty() * billItem.getDiscountPerone());
            double grossAmount = (billItem.getQty() * billItem.getUnitPrice());
            
            totalQty += billItem.getQty();
            totalDiscount += discount;
            totalGrossAmount += grossAmount;
            totalNetAmount += (grossAmount - discount);
        }
        
        jTextFieldGrossAmount.setText(df.format(totalGrossAmount));
        jTextFieldSalesNoOfItms.setText(df.format(totalQty));
        jTextFieldTotalDis.setText(df.format(totalDiscount));
        jTextFieldNetTot.setText(df.format(totalNetAmount));
        pushToCustomerDisplay();
    }

    private void btnActionProcessBill(){
        if(jTextFieldGrossAmount.getText().equals("")||jTextFieldTotalDis.getText().equals("")||jTextFieldSalesNoOfItms.getText().equals("")||
                jTextFieldNetTot.getText().equals("")||jTextFieldMRecieve.getText().equals("")||jTextFieldBal.getText().equals("")||
                jTextFieldInvNo.getText().equals("")){
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_FILL_REQUIRED_FIELDS);
        }else{            
            try{
                // Prepare Bill data model
                int custId = 0;
                if (selectedCustomer != null) {
                    custId = selectedCustomer.customer_id;
                }
                billDataModel.setCashBalance(Double.parseDouble(jTextFieldBal.getText()));
                billDataModel.setCashierId(ApplicationDataManager.getInstance().getLoggedInUser().getUserCode());
                billDataModel.setCashierName(ApplicationDataManager.getInstance().getLoggedInUser().getUserName());
                billDataModel.setCustomerId(custId);
                billDataModel.setCustomerContactNo(jTextFieldCustNo.getText());
                billDataModel.setDate(DateTimeUtil.getTodayDateDBFormat());
                billDataModel.setInvoiceNumber(ApplicationDataManager.getInstance().getNextTransactionId());
                billDataModel.setBillNumber(ApplicationDataManager.getInstance().getNextInvoiceNumber());
                billDataModel.setMethodOfPayment(MethodOfPayment.CASH);
                billDataModel.setMoneyReceive(Double.parseDouble(jTextFieldMRecieve.getText()));
                billDataModel.setNetTotal(Double.parseDouble(jTextFieldNetTot.getText()));
                billDataModel.setNoOfItems(Double.parseDouble(jTextFieldSalesNoOfItms.getText()));
                billDataModel.setTime(DateTimeUtil.getCurrentTimeHHmmss());
                billDataModel.setBillDiscount(Double.parseDouble(jTextFieldTotalDis.getText()));
                billDataModel.setTotalGrossAmount(Double.parseDouble(jTextFieldGrossAmount.getText()));
                
                // Print bill
                if (printBill(billDataModel)) {
                        // Show print preview
                        
                        // Insert data to sales table & sale items table
                        submitToDB(billDataModel);
                        
                        // Update invoice number in local DB
                        DatabaseManager.getInstance().updateAppDataInvoiceNumber(ApplicationDataManager.getInstance().getNextInvoiceNumber());
                        
                        // Submit to server process
                        ServerDataSubmissionQueue.getInstance().notifyAction(() -> ServerDataSubmissionQueue.getInstance().doChangeLogSaleDataSync());                        
                        
                        // Reset for new customer
                        resetUIForNewCustomer();
                        
                }else{
                    EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINTER_STOPPED);
                }

            }catch(HeadlessException | IllegalArgumentException e){
                EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINT_BILL_FAILED);
            }
        }
    }

    private boolean btnActionProcessBill2(){
        if(jTextFieldGrossAmount.getText().equals("")||jTextFieldTotalDis.getText().equals("")||jTextFieldSalesNoOfItms.getText().equals("")||
                jTextFieldNetTot.getText().equals("")||jTextFieldMRecieve.getText().equals("")||jTextFieldBal.getText().equals("")||
                jTextFieldInvNo.getText().equals("")){
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_FILL_REQUIRED_FIELDS);
        }else{            
            try{
                // Prepare Bill data model
                int custId = 0;
                if (selectedCustomer != null) {
                    custId = selectedCustomer.customer_id;
                }
                billDataModel.setCashBalance(Double.parseDouble(jTextFieldBal.getText()));
                billDataModel.setCashierId(ApplicationDataManager.getInstance().getLoggedInUser().getUserCode());
                billDataModel.setCashierName(ApplicationDataManager.getInstance().getLoggedInUser().getUserName());
                billDataModel.setCustomerId(custId);
                billDataModel.setCustomerContactNo(jTextFieldCustNo.getText());
                billDataModel.setDate(DateTimeUtil.getTodayDateDBFormat());
                billDataModel.setInvoiceNumber(ApplicationDataManager.getInstance().getNextTransactionId());
                billDataModel.setBillNumber(ApplicationDataManager.getInstance().getNextInvoiceNumber());
                billDataModel.setMethodOfPayment(MethodOfPayment.CASH);
                billDataModel.setMoneyReceive(Double.parseDouble(jTextFieldMRecieve.getText()));
                billDataModel.setNetTotal(Double.parseDouble(jTextFieldNetTot.getText()));
                billDataModel.setNoOfItems(Double.parseDouble(jTextFieldSalesNoOfItms.getText()));
                billDataModel.setTime(DateTimeUtil.getCurrentTimeHHmmss());
                billDataModel.setBillDiscount(Double.parseDouble(jTextFieldTotalDis.getText()));
                billDataModel.setTotalGrossAmount(Double.parseDouble(jTextFieldGrossAmount.getText()));
                
                return true;
            }catch(HeadlessException | IllegalArgumentException e){
                EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINT_BILL_FAILED);
            }
        }
        return false;
    }
            
    public void resetUIForNewCustomer(){
        lastIssuedBillDataModel = new BillDataModel(billDataModel);
        billDataModel = new BillDataModel();
        clearFieldsItemPanel();
        clearFieldsTotalPanel();
        updateItemUITable();
        ApplicationDataManager.getInstance().updateInvoiceNumberUtilized();
        setNextInvoiceNumber();
        jTextFieldItmCode.requestFocus();
        selectedCustomer = null;
    }

    private boolean printBill(BillDataModel billDataModel)
    {
        PrinterJob job = PrinterJob.getPrinterJob();

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService selectedPrinter = null;

        String printerName = ApplicationDataManager.getInstance().getReceiptPrinterName(); // your printer name
        
        InstituteModel instituteModel = RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute();
        ReceiptCommonData receiptCommonData = new ReceiptCommonData();
        receiptCommonData.setBusinessName(instituteModel.getPrintBusinessName());
        receiptCommonData.setBusinessSubName(instituteModel.getPrintBusinessSubName());
        receiptCommonData.setAddressLine1(instituteModel.getPrintBusinessAddressLine1());        
        receiptCommonData.setAddressLine2(instituteModel.getPrintBusinessAddressLine2());
        receiptCommonData.setAddressLine3(instituteModel.getPrintBusinessAddressLine3());
        receiptCommonData.setContactDataLine(instituteModel.getPrintBusinessContact());
        receiptCommonData.setFooterLine1(instituteModel.getPrintFooter1());
        receiptCommonData.setFooterLine2(instituteModel.getPrintFooter2());
        receiptCommonData.setServiceProviderLine(instituteModel.getServiceProviderPrintLine());

        for (PrintService service : printServices) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                selectedPrinter = service;
                break;
            }
        }

        if (selectedPrinter != null) {
            try {

                job.setPrintService(selectedPrinter);

//                PageFormat pf = new PageFormat();
//                Paper paper = new Paper();
//
//                paper.setSize(760, 800);
//                paper.setImageableArea(5, 2, 450, 420);
//
//                pf.setPaper(paper);
//                pf.setOrientation(PageFormat.PORTRAIT);
//
//                job.setPrintable(new SimpleReceiptPrint(billDataModel, receiptCommonData), pf);
                PageFormat pf = new PageFormat();
                Paper paper = new Paper();

                paper.setSize(576, 2000); // width, height
                paper.setImageableArea(0, 0, 576, 2000);

                pf.setPaper(paper);
                pf.setOrientation(PageFormat.PORTRAIT);

                job.setPrintable(new SimpleReceiptPrint(billDataModel, receiptCommonData), pf);

                job.print(); // 🔹 No dialog

                return true;

            } catch (PrinterException e) {
                EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINTER_FAILED_TASK);
            }
        } else {
            EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINTER_NOT_FOUND);
        }
        return false;
    }
        
    private void clearFieldsItemPanel(){
        jTextFieldItmCode.setText("");
        jTextFieldItmName1.setText("");
        jTextFieldItmName2.setText("");
        jTextFieldQty.setText("");
        jTextFieldUnitPrice.setText("");
        jTextFieldItmDis.setText("");
        jTextFieldMRecieve.setText("");
        jTextFieldBal.setText("");
        jLabelMeasuringUnit.setText("");
        jTextFieldWholesalePrice.setText("");
        jTextFieldAmount.setText("");
        jTextFieldCustNo.setText("");
        jTextFieldCustName.setText("");
        selectedCustomer = null;
    }
    
    private void clearFieldsItemPanelExceptBarcode(){
        jTextFieldItmName1.setText("");
        jTextFieldItmName2.setText("");
        jTextFieldQty.setText("");
        jTextFieldUnitPrice.setText("");
        jTextFieldItmDis.setText("");
        jTextFieldMRecieve.setText("");
        jTextFieldBal.setText("");
        jLabelMeasuringUnit.setText("");
        jTextFieldWholesalePrice.setText("");
        jTextFieldAmount.setText("");
    } 
        
    private void clearFieldsTotalPanel(){
        jTextFieldGrossAmount.setText("");
        jTextFieldSalesNoOfItms.setText("");
        jTextFieldTotalDis.setText("");
        jTextFieldNetTot.setText("");
        jTextFieldMRecieve.setText("");
        jTextFieldBal.setText("");
        pushToCustomerDisplay();
    }
    
    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/sale/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            //create the font to use. Specify the size!

            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);
            jLabelInvoiceNo.setFont(customFont1);
            jLabelCustomerNo.setFont(customFont1);
            jLabelItemName.setFont(customFont1);
            jLabelItemcode.setFont(customFont1);
            jLabelUnitPrice.setFont(customFont1);
            jLabelDiscount.setFont(customFont1);
            jLabelWholesalePrice.setFont(customFont1);
            jLabelQty.setFont(customFont1);
            jLabelAmount.setFont(customFont1);

            ge.registerFont(customFont2);
            jLabelTotalGrossAmount.setFont(customFont2);
            jLabelNoOfItems.setFont(customFont2);
            jLabelTotalDiscount.setFont(customFont2);
            jLabelNetTotal.setFont(customFont2);
            jLabelMoneyReceived.setFont(customFont2);
            jLabelBalance.setFont(customFont2);

            jButtonIssueBill.setFont(customFont1);


        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        //use the font
        
        jLabelInvoiceNo.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelInvoiceNo.text"));
        jLabelCustomerNo.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelCustomerNo.text"));
        jLabelItemName.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelItemName.text"));
        jLabelItemcode.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelItemcode.text"));
        jLabelUnitPrice.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelUnitPrice.text"));
        jLabelDiscount.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelDiscount.text"));
        jLabelWholesalePrice.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelWholesalePrice.text"));
        jLabelQty.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelQty.text"));
        jLabelAmount.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelAmount.text"));   
                
        jLabelTotalGrossAmount.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelTotalGrossAmount.text"));
        jLabelNoOfItems.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelNoOfItems.text"));
        jLabelTotalDiscount.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelTotalDiscount.text"));
        jLabelNetTotal.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelNetTotal.text"));
        jLabelMoneyReceived.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelMoneyReceived.text"));
        jLabelBalance.setText(resourceBundle.getString("SaleInvoiceJPanel.jLabelBalance.text"));
        
        jButtonIssueBill.setText(resourceBundle.getString("SaleInvoiceJPanel.jButtonIssueBill.text"));
    }
    
    private void submitToDB(BillDataModel billDataModel){
        // Add to sales
        SaleInvoice invoice = new SaleInvoice();
        List<SaleItem> items = new ArrayList<>();
        
        String saleDateTime = billDataModel.getDate() + " " + billDataModel.getTime();
        
        invoice.rec_id = 0;
        invoice.institute_id = Integer.parseInt(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
        invoice.invoice_no = billDataModel.getInvoiceNumber();
        invoice.sale_time = saleDateTime;
        invoice.sale_by = billDataModel.getCashierName();
        invoice.customer_id = billDataModel.getCustomerId();
        invoice.cust_contact_no = billDataModel.getCustomerContactNo();
        invoice.gross_amount = billDataModel.getTotalGrossAmount();
        invoice.no_of_items = billDataModel.getNoOfItems();
        invoice.total_discount = billDataModel.getBillDiscount(); 
        invoice.net_total = billDataModel.getNetTotal();
        invoice.money_received = billDataModel.getMoneyReceive();
        invoice.card_received = billDataModel.getCardReceive();
        invoice.voucher_received = billDataModel.getVoucherReceive();
        invoice.total_received = (invoice.money_received + invoice.card_received + invoice.voucher_received);
        invoice.balance_amount = billDataModel.getCashBalance();
        invoice.settle_date_time = saleDateTime;
        invoice.settle_update_by = billDataModel.getCashierName();
        
        invoice.invoice_type = 1; // Retail sale
        invoice.cash_ref = "";
        invoice.card_ref = "";
        invoice.voucher_ref = "";
        
        double totalCost = 0;
        for (BillItemDataModel billItem : billDataModel.getBillItems()) {
            SaleItem si = new SaleItem();
            si.rec_id = 0;
            si.invoice_record_id = 0;
            si.item_id = billItem.getItemId();
            si.batch_id = billItem.getBatchId();
            si.qty = billItem.getQty();
            si.selling_price = billItem.getUnitPrice();
            si.discount = billItem.getDiscountPerone();
            si.billing_price = billItem.getAmount();
            si.net_amount = billItem.getAmount();
            si.cost_for_unit = billItem.getCostOfUnit();
            si.total_cost = si.cost_for_unit * si.qty;
            si.status = 1;
                               
            totalCost = totalCost +si.total_cost;            
            
            items.add(si); 
        }
        
        invoice.total_cost = totalCost;
        invoice.sale_items = items;
        
        int terminalId = Integer.parseInt(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());
        
        DatabaseManager.getInstance().processSale(
                invoice, 
                items,
                terminalId);        
    }
    
    private void onlineSearchCustomerAction(String customerContact) {
        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);

        SwingWorker<CommonResponse, Void> worker;
        worker = new SwingWorker<CommonResponse, Void>() {
            @Override
            protected CommonResponse doInBackground() {
                loader.start(); // show loader
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                return ServerAPIConnection.getInstance(aPIHeaderData).getCustomer(customerContact);
            }

            @Override
            protected void done() {
                loader.stop(); // hide loader
                try {
                    CommonResponse response = get();

                    jTextFieldCustName.setText("");
                    selectedCustomer = null;

                    if (response.getAPIResponse().isSuccess()) {
                        selectedCustomer = (serverDataModels.Customer) response.getData();
                        if (selectedCustomer != null) {
                            jTextFieldCustName.setText(selectedCustomer.customer_name);
                        }
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(jPanelSaleInvoiceBase.getRootPane(), response.getAPIResponse());
                    }
                    pushToCustomerDisplay();

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(jPanelSaleInvoiceBase.getRootPane(), ex.getMessage());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSaleInvoiceBase = new javax.swing.JPanel();
        jLabelItemcode = new javax.swing.JLabel();
        jLabelAmount = new javax.swing.JLabel();
        jLabelUnitPrice = new javax.swing.JLabel();
        jTextFieldItmCode = new javax.swing.JTextField();
        jTextFieldQty = new javax.swing.JTextField();
        jTextFieldUnitPrice = new javax.swing.JTextField();
        jLabelWholesalePrice = new javax.swing.JLabel();
        jTextFieldItmName1 = new javax.swing.JTextField();
        jTextFieldItmName2 = new javax.swing.JTextField();
        jTextFieldItmDis = new javax.swing.JTextField();
        jLabelDiscount = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jTextFieldAmount = new javax.swing.JTextField();
        jLabelQty = new javax.swing.JLabel();
        jButtonAddToBill = new javax.swing.JButton();
        jButtonSellCancel = new javax.swing.JButton();
        jTextFieldInvNo = new javax.swing.JTextField();
        jLabelInvoiceNo = new javax.swing.JLabel();
        jTextFieldWholesalePrice = new javax.swing.JTextField();
        jLabelMeasuringUnit = new javax.swing.JLabel();
        jLabelItemName = new javax.swing.JLabel();
        jLabelCustomerNo = new javax.swing.JLabel();
        jTextFieldCustNo = new javax.swing.JTextField();
        jTextFieldCustName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSaleToBill = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabelNetTotal = new javax.swing.JLabel();
        jLabelMoneyReceived = new javax.swing.JLabel();
        jLabelBalance = new javax.swing.JLabel();
        jTextFieldNetTot = new javax.swing.JTextField();
        jTextFieldMRecieve = new javax.swing.JTextField();
        jTextFieldBal = new javax.swing.JTextField();
        jButtonIssueBill = new javax.swing.JButton();
        jLabelTotalDiscount = new javax.swing.JLabel();
        jTextFieldTotalDis = new javax.swing.JTextField();
        jTextFieldSalesNoOfItms = new javax.swing.JTextField();
        jLabelNoOfItems = new javax.swing.JLabel();
        jTextFieldGrossAmount = new javax.swing.JTextField();
        jLabelTotalGrossAmount = new javax.swing.JLabel();
        jButton34 = new javax.swing.JButton();
        jButtonCancellBill = new javax.swing.JButton();

        jPanelSaleInvoiceBase.setBackground(new java.awt.Color(153, 153, 153));
        jPanelSaleInvoiceBase.setAutoscrolls(true);
        jPanelSaleInvoiceBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanelSaleInvoiceBaseKeyPressed(evt);
            }
        });

        jLabelItemcode.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelItemcode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/sale/Bundle"); // NOI18N
        jLabelItemcode.setText(bundle.getString("SaleInvoiceJPanel.jLabelItemcode.text")); // NOI18N

        jLabelAmount.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAmount.setText(bundle.getString("SaleInvoiceJPanel.jLabelAmount.text")); // NOI18N

        jLabelUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelUnitPrice.setText(bundle.getString("SaleInvoiceJPanel.jLabelUnitPrice.text")); // NOI18N

        jTextFieldItmCode.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldItmCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldItmCodeKeyPressed(evt);
            }
        });

        jTextFieldQty.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldQtyKeyReleased(evt);
            }
        });

        jTextFieldUnitPrice.setEditable(false);
        jTextFieldUnitPrice.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldUnitPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelWholesalePrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelWholesalePrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelWholesalePrice.setText(bundle.getString("SaleInvoiceJPanel.jLabelWholesalePrice.text")); // NOI18N

        jTextFieldItmName1.setEditable(false);
        jTextFieldItmName1.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldItmName1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jTextFieldItmName2.setEditable(false);
        jTextFieldItmName2.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldItmName2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jTextFieldItmDis.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldItmDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldItmDis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldItmDisActionPerformed(evt);
            }
        });
        jTextFieldItmDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldItmDisKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldItmDisKeyReleased(evt);
            }
        });

        jLabelDiscount.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelDiscount.setText(bundle.getString("SaleInvoiceJPanel.jLabelDiscount.text")); // NOI18N

        jTextFieldAmount.setEditable(false);
        jTextFieldAmount.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldAmount.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelQty.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelQty.setText(bundle.getString("SaleInvoiceJPanel.jLabelQty.text")); // NOI18N

        jButtonAddToBill.setBackground(new java.awt.Color(153, 153, 153));
        jButtonAddToBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus_icon.png"))); // NOI18N
        jButtonAddToBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToBillActionPerformed(evt);
            }
        });
        jButtonAddToBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonAddToBillKeyPressed(evt);
            }
        });

        jButtonSellCancel.setBackground(new java.awt.Color(153, 153, 153));
        jButtonSellCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close_icon.png"))); // NOI18N
        jButtonSellCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSellCancelActionPerformed(evt);
            }
        });
        jButtonSellCancel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonSellCancelKeyPressed(evt);
            }
        });

        jTextFieldInvNo.setEditable(false);
        jTextFieldInvNo.setBackground(new java.awt.Color(153, 153, 153));
        jTextFieldInvNo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        jLabelInvoiceNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelInvoiceNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelInvoiceNo.setText(bundle.getString("SaleInvoiceJPanel.jLabelInvoiceNo.text")); // NOI18N

        jTextFieldWholesalePrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldWholesalePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldWholesalePrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldWholesalePriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldWholesalePriceKeyReleased(evt);
            }
        });

        jLabelMeasuringUnit.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelMeasuringUnit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelMeasuringUnit.setText(bundle.getString("SaleInvoiceJPanel.jLabelMeasuringUnit.text")); // NOI18N

        jLabelItemName.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelItemName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelItemName.setText(bundle.getString("SaleInvoiceJPanel.jLabelItemName.text")); // NOI18N

        jLabelCustomerNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelCustomerNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCustomerNo.setText(bundle.getString("SaleInvoiceJPanel.jLabelCustomerNo.text")); // NOI18N

        jTextFieldCustNo.setBackground(new java.awt.Color(153, 153, 153));
        jTextFieldCustNo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldCustNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustNoActionPerformed(evt);
            }
        });

        jTextFieldCustName.setEditable(false);
        jTextFieldCustName.setBackground(new java.awt.Color(153, 153, 153));
        jTextFieldCustName.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldCustName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCustNameKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSaleInvoiceBaseLayout = new javax.swing.GroupLayout(jPanelSaleInvoiceBase);
        jPanelSaleInvoiceBase.setLayout(jPanelSaleInvoiceBaseLayout);
        jPanelSaleInvoiceBaseLayout.setHorizontalGroup(
            jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelDiscount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                                .addComponent(jLabelMeasuringUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldItmDis, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(368, 368, 368))))
                    .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelItemcode, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(jLabelInvoiceNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jTextFieldItmCode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabelCustomerNo, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(jLabelItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jTextFieldWholesalePrice))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonAddToBill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonSellCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                                .addComponent(jTextFieldCustNo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCustName, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                            .addComponent(jTextFieldItmName2)
                            .addComponent(jTextFieldItmName1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelSaleInvoiceBaseLayout.setVerticalGroup(
            jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSaleInvoiceBaseLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelInvoiceNo)
                    .addComponent(jTextFieldInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabelCustomerNo)
                    .addComponent(jTextFieldCustNo, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jTextFieldCustName, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldItmName1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelItemName)
                    .addComponent(jLabelItemcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMeasuringUnit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelUnitPrice)
                        .addComponent(jTextFieldItmName2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldItmDis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelWholesalePrice))
                    .addComponent(jLabelDiscount)
                    .addComponent(jButtonAddToBill, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSellCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelSaleInvoiceBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelQty)
                        .addComponent(jLabelAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jLabel77)
                .addContainerGap())
        );

        jTableSaleToBill.setBackground(new java.awt.Color(231, 230, 230));
        jTableSaleToBill.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTableSaleToBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Item Code", "      Item Name", "      Unit Price", "            Qty", "    Discount Per 1Qty", "           Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSaleToBill.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableSaleToBill.setShowHorizontalLines(true);
        jTableSaleToBill.getTableHeader().setReorderingAllowed(false);
        jTableSaleToBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSaleToBillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSaleToBill);

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabelNetTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNetTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNetTotal.setText(bundle.getString("SaleInvoiceJPanel.jLabelNetTotal.text")); // NOI18N

        jLabelMoneyReceived.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMoneyReceived.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelMoneyReceived.setText(bundle.getString("SaleInvoiceJPanel.jLabelMoneyReceived.text")); // NOI18N

        jLabelBalance.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelBalance.setText(bundle.getString("SaleInvoiceJPanel.jLabelBalance.text")); // NOI18N

        jTextFieldNetTot.setEditable(false);
        jTextFieldNetTot.setBackground(new java.awt.Color(255, 204, 255));
        jTextFieldNetTot.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextFieldNetTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextFieldMRecieve.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldMRecieve.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldMRecieve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMRecieveKeyPressed(evt);
            }
        });

        jTextFieldBal.setEditable(false);
        jTextFieldBal.setBackground(new java.awt.Color(204, 255, 255));
        jTextFieldBal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextFieldBal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButtonIssueBill.setBackground(new java.awt.Color(0, 51, 204));
        jButtonIssueBill.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jButtonIssueBill.setText(bundle.getString("SaleInvoiceJPanel.jButtonIssueBill.text")); // NOI18N
        jButtonIssueBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIssueBillActionPerformed(evt);
            }
        });
        jButtonIssueBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonIssueBillKeyPressed(evt);
            }
        });

        jLabelTotalDiscount.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelTotalDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalDiscount.setText(bundle.getString("SaleInvoiceJPanel.jLabelTotalDiscount.text")); // NOI18N

        jTextFieldTotalDis.setEditable(false);
        jTextFieldTotalDis.setBackground(new java.awt.Color(153, 204, 255));
        jTextFieldTotalDis.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextFieldTotalDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextFieldSalesNoOfItms.setEditable(false);
        jTextFieldSalesNoOfItms.setBackground(new java.awt.Color(153, 204, 255));
        jTextFieldSalesNoOfItms.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextFieldSalesNoOfItms.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelNoOfItems.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelNoOfItems.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNoOfItems.setText(bundle.getString("SaleInvoiceJPanel.jLabelNoOfItems.text")); // NOI18N

        jTextFieldGrossAmount.setEditable(false);
        jTextFieldGrossAmount.setBackground(new java.awt.Color(153, 204, 255));
        jTextFieldGrossAmount.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTextFieldGrossAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelTotalGrossAmount.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelTotalGrossAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalGrossAmount.setText(bundle.getString("SaleInvoiceJPanel.jLabelTotalGrossAmount.text")); // NOI18N

        jButton34.setBackground(new java.awt.Color(153, 153, 153));
        jButton34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trashbin_icon.png"))); // NOI18N
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        jButton34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton34KeyPressed(evt);
            }
        });

        jButtonCancellBill.setBackground(new java.awt.Color(153, 153, 153));
        jButtonCancellBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close_red_icon.png"))); // NOI18N
        jButtonCancellBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancellBillActionPerformed(evt);
            }
        });
        jButtonCancellBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonCancellBillKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelTotalGrossAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(jLabelNoOfItems, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTotalDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldSalesNoOfItms, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextFieldGrossAmount)
                    .addComponent(jTextFieldTotalDis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelMoneyReceived, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(jLabelNetTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldMRecieve, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextFieldNetTot)
                    .addComponent(jTextFieldBal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancellBill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonIssueBill, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCancellBill, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addComponent(jButtonIssueBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotalGrossAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldGrossAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelNetTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldNetTot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldSalesNoOfItms, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelMoneyReceived)
                                .addComponent(jTextFieldMRecieve, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelNoOfItems, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldTotalDis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldBal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelTotalDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanelSaleInvoiceBase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 858, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelSaleInvoiceBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableSaleToBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSaleToBillMouseClicked
//        if(evt.isAltDown()){
//            String cur=jTextFieldWithoutDis.getText();
//            String s=new Integer(jTableSaleToBill.getSelectedRow()+1).toString();
//            if(jTextFieldWithoutDis.getText().equals("")){
//                jTextFieldWithoutDis.setText(s);
//            }else{
//                jTextFieldWithoutDis.setText(cur+","+s);
//            }
//        }
    }//GEN-LAST:event_jTableSaleToBillMouseClicked

    private void jTextFieldMRecieveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMRecieveKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
                double netTotal=Double.parseDouble(jTextFieldNetTot.getText());
                double moneyRecieve=Double.parseDouble(jTextFieldMRecieve.getText());
                if(jTextFieldNetTot.getText().equals("")){
                    netTotal=0;
                }
                if(jTextFieldMRecieve.getText().equals("")){
                    moneyRecieve=0;
                }
                double balance=moneyRecieve-netTotal;
                jTextFieldBal.setText(df.format(balance));
            }catch(NumberFormatException e){

            }
            pushToCustomerDisplay();
        }else if(evt.getKeyCode()==KeyEvent.VK_F1){
            btnActionProcessBill();
            jTextFieldItmCode.requestFocus();
        }

    }//GEN-LAST:event_jTextFieldMRecieveKeyPressed

    private void jButtonIssueBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIssueBillActionPerformed
        if (btnActionProcessBill2()) {
            SalePaymentFrame salePaymentFrame = new SalePaymentFrame(this, billDataModel);
            salePaymentFrame.setDefaultCloseOperation(SalePaymentFrame.DISPOSE_ON_CLOSE);
            salePaymentFrame.setVisible(true);            
        }        
    }//GEN-LAST:event_jButtonIssueBillActionPerformed

    private void jButtonIssueBillKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonIssueBillKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonIssueBillKeyPressed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        try{

            // Fetch the item
            int rowNumForRemove=jTableSaleToBill.getSelectedRow();            
            BillItemDataModel bilItemToRemove = billDataModel.getBillItems().get(rowNumForRemove);
            
            // Remove item from bill
            billDataModel.getBillItems().remove(bilItemToRemove);
            
            // Update the table & total values
            updateItemUITable();
            arrangeTotalValues();

        }catch(Exception e){
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_SELECT_ROW);
        }

    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton34KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton34KeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_DELETE){
//            clearFieldsSupplierAddToBill();
//        }
    }//GEN-LAST:event_jButton34KeyPressed

    private void jPanelSaleInvoiceBaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanelSaleInvoiceBaseKeyPressed

    }//GEN-LAST:event_jPanelSaleInvoiceBaseKeyPressed

    private void jButtonSellCancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonSellCancelKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //            clearFieldsSupplierAddToBill();
            //        }
    }//GEN-LAST:event_jButtonSellCancelKeyPressed

    private void jButtonSellCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSellCancelActionPerformed
        clearFieldsItemPanel();
        jTextFieldItmCode.requestFocus();
    }//GEN-LAST:event_jButtonSellCancelActionPerformed

    private void jButtonAddToBillKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonAddToBillKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAddToBillKeyPressed

    private void jButtonAddToBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddToBillActionPerformed

        if (jTextFieldQty.getText().isEmpty()) {
            jTextFieldQty.setText("1");
        }
        validateAndAddToBill();
    }//GEN-LAST:event_jButtonAddToBillActionPerformed

    private void jTextFieldItmDisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldItmDisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldItmCode.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            jTextFieldItmCode.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldItmCode.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldUnitPrice.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldQty.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_F1){
            jTextFieldMRecieve.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldItmDisKeyPressed

    private void jTextFieldItmDisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldItmDisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldItmDisActionPerformed

    private void jTextFieldItmCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldItmCodeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && !jTextFieldItmCode.getText().isEmpty())
        {
            fetchItem();
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_F1){
            jTextFieldMRecieve.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldItmCodeKeyPressed

    private void jTextFieldWholesalePriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldWholesalePriceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldWholesalePriceKeyPressed

    private void jTextFieldQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyKeyReleased
        calculateAmountOnTypeInput();
    }//GEN-LAST:event_jTextFieldQtyKeyReleased

    private void jTextFieldItmDisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldItmDisKeyReleased
        calculateAmountOnTypeInput();
    }//GEN-LAST:event_jTextFieldItmDisKeyReleased

    private void jTextFieldWholesalePriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldWholesalePriceKeyReleased
        calculateAmountOnTypeInput();
    }//GEN-LAST:event_jTextFieldWholesalePriceKeyReleased

    private void jTextFieldQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && !jTextFieldItmCode.getText().isEmpty())
        {
            validateAndAddToBill();
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldItmName1.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_F1){
            jTextFieldMRecieve.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldQtyKeyPressed

    private void jButtonCancellBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancellBillActionPerformed
        clearFieldsItemPanel();
        billDataModel = new BillDataModel();
        updateItemUITable();
        clearFieldsTotalPanel();
    }//GEN-LAST:event_jButtonCancellBillActionPerformed

    private void jButtonCancellBillKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonCancellBillKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCancellBillKeyPressed

    private void jTextFieldCustNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustNameKeyPressed

    private void jTextFieldCustNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustNoActionPerformed
        // find the customer number and display the customer name
        onlineSearchCustomerAction(jTextFieldCustNo.getText());
    }//GEN-LAST:event_jTextFieldCustNoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButtonAddToBill;
    private javax.swing.JButton jButtonCancellBill;
    private javax.swing.JButton jButtonIssueBill;
    private javax.swing.JButton jButtonSellCancel;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabelAmount;
    private javax.swing.JLabel jLabelBalance;
    private javax.swing.JLabel jLabelCustomerNo;
    private javax.swing.JLabel jLabelDiscount;
    private javax.swing.JLabel jLabelInvoiceNo;
    private javax.swing.JLabel jLabelItemName;
    private javax.swing.JLabel jLabelItemcode;
    private javax.swing.JLabel jLabelMeasuringUnit;
    private javax.swing.JLabel jLabelMoneyReceived;
    private javax.swing.JLabel jLabelNetTotal;
    private javax.swing.JLabel jLabelNoOfItems;
    private javax.swing.JLabel jLabelQty;
    private javax.swing.JLabel jLabelTotalDiscount;
    private javax.swing.JLabel jLabelTotalGrossAmount;
    private javax.swing.JLabel jLabelUnitPrice;
    private javax.swing.JLabel jLabelWholesalePrice;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelSaleInvoiceBase;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSaleToBill;
    private javax.swing.JTextField jTextFieldAmount;
    private javax.swing.JTextField jTextFieldBal;
    private javax.swing.JTextField jTextFieldCustName;
    private javax.swing.JTextField jTextFieldCustNo;
    private javax.swing.JTextField jTextFieldGrossAmount;
    private javax.swing.JTextField jTextFieldInvNo;
    private javax.swing.JTextField jTextFieldItmCode;
    private javax.swing.JTextField jTextFieldItmDis;
    private javax.swing.JTextField jTextFieldItmName1;
    private javax.swing.JTextField jTextFieldItmName2;
    private javax.swing.JTextField jTextFieldMRecieve;
    private javax.swing.JTextField jTextFieldNetTot;
    private javax.swing.JTextField jTextFieldQty;
    private javax.swing.JTextField jTextFieldSalesNoOfItms;
    private javax.swing.JTextField jTextFieldTotalDis;
    private javax.swing.JTextField jTextFieldUnitPrice;
    private javax.swing.JTextField jTextFieldWholesalePrice;
    // End of variables declaration//GEN-END:variables

}
