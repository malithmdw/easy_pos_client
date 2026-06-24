package easyPOS.sale;

import appDataModels.APIHeaderData;
import appDataModels.InstituteModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import control.ServerDataSubmissionQueue;
import control.SimpleReceiptPrint;
import dataModels.BillDataModel;
import dataModels.BillItemDataModel;
import dataModels.ReceiptCommonData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.print.PrintService;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import localDatabase.DatabaseManager;
import serverDataModels.Customer;
import serverDataModels.DiscountRule;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverDataModels.Voucher;
import serverResponseDataModel.CommonResponse;
import tableModels.SalesVoucherRedeemTbl;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;
import easyPOS.localization.ApplicationMessages;

/**
 *
 * @author MalithWanniarachchi
 */
public class SalePaymentFrame extends javax.swing.JFrame implements control.LanguageChangeListener {

    private SaleInvoiceJPanel parentPanel;
    private BillDataModel billDataModel;
    private Customer selectedCustomer;
    private SalesVoucherRedeemTbl voucherTableModel;

    /**
     * Creates new form SalePaymentFrame
     * @param parentPanel
     * @param billDataModel
     */
    public SalePaymentFrame(SaleInvoiceJPanel parentPanel, BillDataModel billDataModel) {
        initComponents();
        switchLanguage();
        this.parentPanel = parentPanel;
        this.billDataModel = billDataModel;
        setInitData();
        calculateAmounts();
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    private void setInitData(){
        salepanelBillTotalTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getTotalGrossAmount()));
        salepanelBillDisTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getBillDiscount()));
        salepanelTotalTextField.setText("");
        salepanelNetAmtTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getNetTotal()));
        salepanelPaidTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getMoneyReceive()));
        salepanelBalanceTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getCashBalance()));
        salepanelCustNumTextField.setText(billDataModel.getCustomerContactNo());
        salepanelTotalTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getNetTotal()));
        salepanelCustNameTextField.setText("");

        if (billDataModel.getCustomerContactNo() != null && !billDataModel.getCustomerContactNo().isEmpty()) {
            onlineSearchCustomerAction(billDataModel.getCustomerContactNo());
        }

        salePaymentCashRadioButton.setSelected(true);
        jTextField6.setText(util.GeneralUtil.getCurrencyString(billDataModel.getMoneyReceive()));

        jTextField9.setEnabled(true);
        jTextField9.setEditable(false);
        jTextField9.setText("0.00");
        setupVouchersTable();
        
        JTextField[] mopAmtTextFields = new JTextField[2];
        mopAmtTextFields[0] = jTextField6;
        mopAmtTextFields[1] = jTextField7;
        
        for (JTextField textField : mopAmtTextFields) {
            textField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    // Triggers when text is inserted/typed/pasted
                    calculateAmounts();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    // Triggers when text is deleted/removed/cut
                    calculateAmounts();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // Triggers only on style changes (not used in plain JTextField)
                }
            });
        }
    }

    private void setupVouchersTable() {
        voucherTableModel = new SalesVoucherRedeemTbl();
        vouchersTable.setModel(voucherTableModel);

        vouchersTable.getColumnModel().getColumn(2).setCellRenderer(
            (javax.swing.table.TableCellRenderer) (table, value, isSelected, hasFocus, row, column) -> {
                javax.swing.JButton btn = new javax.swing.JButton("Remove");
                btn.setFont(new java.awt.Font("Segoe UI", 0, 11));
                return btn;
            }
        );

        vouchersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = vouchersTable.rowAtPoint(e.getPoint());
                int col = vouchersTable.columnAtPoint(e.getPoint());
                if (col == 2 && row >= 0) {
                    voucherTableModel.removeRow(row);
                    updateVoucherTotalField();
                }
            }
        });
    }

    private void updateVoucherTotalField() {
        double total = voucherTableModel.getTotalVoucherAmount();
        jTextField9.setText(util.GeneralUtil.getCurrencyString(total));
        calculateAmounts();
    }

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/sale/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge2.registerFont(customFont2);

            jLabel1.setFont(customFont);
            jLabel2.setFont(customFont);
            jLabel3.setFont(customFont);
            jLabel4.setFont(customFont);
            jLabel5.setFont(customFont);
            jLabel6.setFont(customFont);
            jLabel7.setFont(customFont);
            jLabel8.setFont(customFont2);
            jLabel9.setFont(customFont);
            jLabel10.setFont(customFont);
            salePaymentCashRadioButton.setFont(customFont);
            salePaymentCardRadioButton.setFont(customFont);
            jLabel11.setFont(customFont);
            salePaymentVoucherRadioButton.setFont(customFont);
            paymentMethodInputLabel.setFont(customFont);
            paymentMethodInputLabel1.setFont(customFont);
            jButton1.setFont(customFont);
            jButton2.setFont(customFont);

        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        jLabel1.setText(resourceBundle.getString("SalePaymentFrame.jLabel1.text"));
        jLabel2.setText(resourceBundle.getString("SalePaymentFrame.jLabel2.text"));
        jLabel3.setText(resourceBundle.getString("SalePaymentFrame.jLabel3.text"));
        jLabel4.setText(resourceBundle.getString("SalePaymentFrame.jLabel4.text"));
        jLabel5.setText(resourceBundle.getString("SalePaymentFrame.jLabel5.text"));
        jLabel6.setText(resourceBundle.getString("SalePaymentFrame.jLabel6.text"));
        jLabel7.setText(resourceBundle.getString("SalePaymentFrame.jLabel7.text"));
        jLabel8.setText(resourceBundle.getString("SalePaymentFrame.jLabel8.text"));
        jLabel9.setText(resourceBundle.getString("SalePaymentFrame.jLabel9.text"));
        jLabel10.setText(resourceBundle.getString("SalePaymentFrame.jLabel10.text"));
        salePaymentCashRadioButton.setText(resourceBundle.getString("SalePaymentFrame.salePaymentCashRadioButton.text"));
        salePaymentCardRadioButton.setText(resourceBundle.getString("SalePaymentFrame.salePaymentCardRadioButton.text"));
        jLabel11.setText(resourceBundle.getString("SalePaymentFrame.salePaymentCreditRadioButton.text"));
        salePaymentVoucherRadioButton.setText(resourceBundle.getString("SalePaymentFrame.salePaymentVoucherRadioButton.text"));
        paymentMethodInputLabel.setText(resourceBundle.getString("SalePaymentFrame.paymentMethodInputLabel.text"));
        paymentMethodInputLabel1.setText(resourceBundle.getString("SalePaymentFrame.paymentMethodInputLabel1.text"));
        jButton1.setText(resourceBundle.getString("SalePaymentFrame.jButton1.text"));
        jButton2.setText(resourceBundle.getString("SalePaymentFrame.jButton2.text"));
    }
    
    private double calcRuleDiscount(boolean applyChangesForBillData){
        ArrayList<Integer> selectedMOPIDs = new ArrayList<>();
        ArrayList<DiscountRule> discountRulesApplied = new ArrayList<>();
        String cardType = "OTHER";

        if (salePaymentCashRadioButton.isSelected()) {
            selectedMOPIDs.add(1);
        }
        if (salePaymentCardRadioButton.isSelected()) {                
            selectedMOPIDs.add(2);

            if (visaRadioButton.isSelected()) {
                cardType = "VISA";
            }else if (masterRadioButton.isSelected()) {
                cardType = "MASTER";
            }else if (amexRadioButton.isSelected()) {
                cardType = "AMEX";
            }else if (upiRadioButton.isSelected()) {
                cardType = "UPI";
            }else if (jcbRadioButton.isSelected()) {
                cardType = "JCB";
            }
            
            if (applyChangesForBillData) {
                billDataModel.setCardType(cardType);
                billDataModel.setCardRef(cardLast4DigInputTextField.getText());
            }
        }
        if (salePaymentVoucherRadioButton.isSelected()) {
            selectedMOPIDs.add(3);
        }

        double totalRuleDiscount = 0;

        for(DiscountRule disRule : localDatabase.DatabaseManager.getInstance().getDiscountRules())
        {
            for (Integer selectedMOPID : selectedMOPIDs) {
                if (billDataModel.getNetTotal() > disRule.bill_above // Bill Above
                        && (disRule.only_for_mop == 0 || selectedMOPID == disRule.only_for_mop) // MOP
                        && (disRule.only_for_loyalty_customers == 0 || (disRule.only_for_loyalty_customers == 1 && selectedCustomer != null))// loyalty customer check
                        ) {
                    // Category check
                    if (disRule.only_for_category == 0) {
                        // Apply for ALL CATEGORIES
                        double totalRulePercDis = (billDataModel.getNetTotal()*disRule.discount_percentage/100);
                        double totalRuleFixDis = disRule.fix_discount;
                        totalRuleDiscount = totalRulePercDis + totalRuleFixDis;
                        discountRulesApplied.add(disRule);
                        
                        if (applyChangesForBillData) {
                            for (BillItemDataModel billItem : billDataModel.getBillItems()) {
//                                billItem.setDiscountPerone(0);/
//                                billItem.setNetAmount(0);/
                            }
                        }
                    }
                    else{
                        // Apply for Selected CATEGORIES
                        ArrayList<BillItemDataModel> billItemsOfCategory = new ArrayList<>();
                        
                        double categorynetTotal = 0;
                        for (BillItemDataModel billItem : billDataModel.getBillItems()) {
                            if (disRule.only_for_category == localDatabase.DatabaseManager.getInstance().getItemByItemId(billItem.getItemId()).getCategoryId()) {
                                categorynetTotal = categorynetTotal + billItem.getNetAmount();
                                billItemsOfCategory.add(billItem);
                            }
                        }

                        if (categorynetTotal > disRule.bill_above && categorynetTotal > 0) {
                            
                            double totalRulePercDis = (categorynetTotal * disRule.discount_percentage/100);
                            double totalRuleFixDis = disRule.fix_discount;
                            totalRuleDiscount = totalRulePercDis + totalRuleFixDis;
                            discountRulesApplied.add(disRule);
                                
                            if (applyChangesForBillData) {
                                for (BillItemDataModel billItem : billItemsOfCategory) {
//                                    billItem.setDiscountPerone(billItem.getDiscountPerone() + ());
//                                    billItem.setNetAmount(0);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (applyChangesForBillData) {
            billDataModel.setDiscountRulesApplied(discountRulesApplied);
        }
        return totalRuleDiscount;
    }

    private void calculateAmounts(){

        double cashAmount = 0;
        double cardAmount = 0;
        double voucherAmount = 0;

        try{
            if (jTextField6.getText() != null && !jTextField6.getText().isEmpty()) {
                cashAmount = Double.parseDouble(jTextField6.getText());
            }
            if (jTextField7.getText() != null && !jTextField7.getText().isEmpty()) {
                cardAmount = Double.parseDouble(jTextField7.getText());
            }
            if (jTextField9.getText() != null && !jTextField9.getText().isEmpty()) {
                voucherAmount = Double.parseDouble(jTextField9.getText());
            }

            salepanelTotalTextField.setText("");
            salepanelPaidTextField.setText("");
            salepanelBalanceTextField.setText("");
            
            double totalRuleDiscount = calcRuleDiscount(false);

            double newNetAmount = billDataModel.getNetTotal() - totalRuleDiscount;
            double totalPaid = cashAmount + cardAmount + voucherAmount;
            salepanelTotalTextField.setText(util.GeneralUtil.getCurrencyString(billDataModel.getNetTotal()));
            salepanelRuleDisTextField.setText(util.GeneralUtil.getCurrencyString(totalRuleDiscount));
            salepanelNetAmtTextField.setText(util.GeneralUtil.getCurrencyString(newNetAmount));
            salepanelTotalPaidTextField.setText(util.GeneralUtil.getCurrencyString(totalPaid));
            salepanelPaidTextField.setText(util.GeneralUtil.getCurrencyString(cashAmount));
                        
            if (newNetAmount > totalPaid) {
                // Credit sale
                double creditAmount = newNetAmount - totalPaid;
                salepanelCreditTextField.setText(util.GeneralUtil.getCurrencyString(creditAmount));                
                salepanelCreditTextField.setForeground(Color.red);
            }
            else{
                salepanelCreditTextField.setText(util.GeneralUtil.getCurrencyString(0));
                salepanelCreditTextField.setForeground(Color.black);
            }
            
            if (newNetAmount > cashAmount) {
                // No balance
                salepanelBalanceTextField.setText(util.GeneralUtil.getCurrencyString(0));
            }
            else{
                double toBePaidByCash = newNetAmount - (cardAmount + voucherAmount);                
                salepanelBalanceTextField.setText(util.GeneralUtil.getCurrencyString(cashAmount - toBePaidByCash));
            }
        }catch(NumberFormatException e){

        }
    }

    private void arrangeUIBasedOnMOPSelection()
    {
        //Voucher
        paymentMethodInputLabel.setVisible(false);
        voucherNumInputTextField.setVisible(false);
        voucherSearchButton.setVisible(false);
        jScrollPaneVoucherTbl.setVisible(false);
        vouchersTable.setVisible(false);
        // Card
        paymentMethodInputLabel1.setVisible(false);
        cardLast4DigInputTextField.setVisible(false);
        visaRadioButton.setVisible(false);
        masterRadioButton.setVisible(false);
        amexRadioButton.setVisible(false);
        upiRadioButton.setVisible(false);
        jcbRadioButton.setVisible(false);
        otherRadioButton.setVisible(false);

        if (salePaymentCashRadioButton.isSelected()) {

        }
        if (salePaymentCardRadioButton.isSelected()) {
            paymentMethodInputLabel1.setVisible(true);
            cardLast4DigInputTextField.setVisible(true);
            visaRadioButton.setVisible(true);
            masterRadioButton.setVisible(true);
            amexRadioButton.setVisible(true);
            upiRadioButton.setVisible(true);
            jcbRadioButton.setVisible(true);
            otherRadioButton.setVisible(true);
        }
        if (salePaymentVoucherRadioButton.isSelected()) {
            paymentMethodInputLabel.setVisible(true);
            voucherNumInputTextField.setVisible(true);
            voucherSearchButton.setVisible(true);
            jScrollPaneVoucherTbl.setVisible(true);
            vouchersTable.setVisible(true);
        }
    }

    private boolean validateUIInputs(){

        if (Double.parseDouble(salepanelBalanceTextField.getText()) < 0) {
            EasyPOSMessageDialog.showLocalizedWarning(parentPanel, ApplicationMessages.VALIDATION_CUSTOMER_REQUIRED_CREDIT);
            return false;
        }

        double cashAmountValue = 0;
        double cardAmountValue = 0;
        double voucherAmountValue = 0;

        try{
            cashAmountValue = Double.parseDouble(jTextField6.getText());
            cardAmountValue = Double.parseDouble(jTextField7.getText());
            voucherAmountValue = Double.parseDouble(jTextField9.getText());
        }catch(NumberFormatException e){
        }
        
        double totalPaidFromMOPSection = cashAmountValue + cardAmountValue + voucherAmountValue;
        
        double billTotalOld = Double.parseDouble(salepanelBillTotalTextField.getText());
        double billDiscountOld = Double.parseDouble(salepanelBillDisTextField.getText());
        double billTotal = Double.parseDouble(salepanelTotalTextField.getText());        
        double ruleDiscountValue = Double.parseDouble(salepanelRuleDisTextField.getText());
        double newNetAmount = Double.parseDouble(salepanelNetAmtTextField.getText());
        double totalPaid = Double.parseDouble(salepanelTotalPaidTextField.getText());
        double cashPaid = Double.parseDouble(salepanelPaidTextField.getText());
        double cashBalance = Double.parseDouble(salepanelBalanceTextField.getText());
        double credit = Double.parseDouble(salepanelCreditTextField.getText());
        
//        if (totalPaidFromMOPSection != totalPaid) {
//            JOptionPane.showMessageDialog(parentPanel, "Calculation Error. total of MOP inputs does not match with the RHS values");
//            return false;
//        }

        if(billTotal != (billTotalOld - billDiscountOld))
        {
            EasyPOSMessageDialog.showRawError(parentPanel, "Calculation Error. [billTotal != (billTotalOld - billDiscountOld)]");
            return false;
        }

        if(newNetAmount != (billTotal - ruleDiscountValue))
        {
            EasyPOSMessageDialog.showRawError(parentPanel, "Calculation Error. [newNetAmount != (billTotal - ruleDiscountValue)]");
            return false;
        }

        if(newNetAmount > (totalPaid + credit))
        {
            EasyPOSMessageDialog.showRawError(parentPanel, "Calculation Error. [newNetAmount > (totalPaid + credit)]");
            return false;
        }

        if (credit > 0 && cashBalance > 0) {
            EasyPOSMessageDialog.showLocalizedWarning(parentPanel, ApplicationMessages.VALIDATION_CREDIT_SALE_NO_CASH_BALANCE);
            return false;
        }

        if (cashPaid != cashAmountValue) {
            EasyPOSMessageDialog.showRawError(parentPanel, "Calculation Error. [cashAmountValue(LHS) != cashPaid(RHS)]");
            return false;
        }


        return true;
    }

    private void btnActionProcessBill()
    {
        try{
            // Setup Loyalty/Customer data
            if (selectedCustomer != null) {
                billDataModel.setCustomerId(selectedCustomer.customer_id);
            }

            // Setup Payment method
            if (salePaymentCashRadioButton.isSelected()) {
                billDataModel.setMoneyReceive(Double.parseDouble(jTextField6.getText()));
            } else if (salePaymentCardRadioButton.isSelected()) {
                billDataModel.setCardReceive(Double.parseDouble(jTextField7.getText()));
            }else if (salePaymentVoucherRadioButton.isSelected()) {
                billDataModel.setVoucherReceive(Double.parseDouble(jTextField9.getText()));
            }

            // Setup Special Discount related changes
            billDataModel.setRuleDiscount(Double.parseDouble(salepanelTotalTextField.getText()));
            billDataModel.setNetTotal(Double.parseDouble(salepanelNetAmtTextField.getText()));
            billDataModel.setMoneyReceive(Double.parseDouble(jTextField6.getText()));
            billDataModel.setCashBalance(Double.parseDouble(salepanelBalanceTextField.getText()));
            billDataModel.setCreditAmount(Double.parseDouble(salepanelCreditTextField.getText()));
            
            calcRuleDiscount(true);
                    
            // Print bill
            if (printBill(billDataModel)) {
                    // Show print preview

                    // Insert data to sales table & sale items table
                    submitToDB(billDataModel);
                    
                    // Redeem vouchers if used
                    if (!billDataModel.getVoucherList().isEmpty()) {
                        String cashierName = billDataModel.getCashierName();
                        for (Voucher voucher : billDataModel.getVoucherList()) {
                            final String vId = String.valueOf(voucher.voucher_id);
                            ServerDataSubmissionQueue.getInstance().notifyAction(() -> {
                                APIHeaderData hdr = new APIHeaderData();
                                hdr.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                                hdr.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());
                                ServerAPIConnection.getInstance(hdr).redeemVoucher(vId, cashierName);
                            });
                        }
                    }

                    // Update invoice number in local DB
                    DatabaseManager.getInstance().updateAppDataInvoiceNumber(ApplicationDataManager.getInstance().getNextInvoiceNumber());

                    // Submit to server process
                    ServerDataSubmissionQueue.getInstance().notifyAction(() -> ServerDataSubmissionQueue.getInstance().doChangeLogSaleDataSync());

                    // Reset for new customer
                    parentPanel.resetUIForNewCustomer();

                    // Close this window
                    this.dispose();
            }else{
                EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINTER_STOPPED);
            }

        }catch(HeadlessException | IllegalArgumentException e){
            EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINT_BILL_FAILED);
        }
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
        invoice.total_discount = billDataModel.getBillDiscount() + billDataModel.getRuleDiscount();
        invoice.net_total = billDataModel.getNetTotal();
        invoice.money_received = billDataModel.getMoneyReceive();
        invoice.card_received = billDataModel.getCardReceive();
        invoice.voucher_received = billDataModel.getVoucherReceive();
        invoice.total_received = (invoice.money_received + invoice.card_received + invoice.voucher_received);
        invoice.balance_amount = billDataModel.getCreditAmount();
        invoice.settle_date_time = saleDateTime;
        invoice.settle_update_by = billDataModel.getCashierName();

        invoice.invoice_type = 1; // Retail sale
        String discountRuleData = "";
        for (DiscountRule discountRule : billDataModel.getDiscountRulesApplied()) {
            discountRuleData += discountRule.rule_id + "|";
        }
        invoice.cash_ref = discountRuleData;
        invoice.card_ref = billDataModel.getCardRef();
        
        String voucherData = "";
        for (Voucher voucher : billDataModel.getVoucherList()) {
            voucherData += voucher.voucher_id + "|";
        }
        invoice.voucher_ref = voucherData;

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

    private void onlineSearchVoucherAction(String voucherId) {
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

                return ServerAPIConnection.getInstance(aPIHeaderData).getVoucher(voucherId);
            }

            @Override
            protected void done() {
                loader.stop(); // hide loader
                try {
                    CommonResponse response = get();

                    voucherNumInputTextField.setText("");

                    if (response.getAPIResponse().isSuccess()) {
                        Voucher voucher = (serverDataModels.Voucher) response.getData();
                        if (voucher != null) {
                            if (voucherTableModel.isDuplicateVoucher(voucher.voucher_id)) {
                                EasyPOSMessageDialog.showLocalizedWarning(SalePaymentFrame.this.getRootPane(), ApplicationMessages.VALIDATION_VOUCHER_ALREADY_ADDED);
                                return;
                            }

                            if (voucher.voucher_status != 1) {
                                EasyPOSMessageDialog.showLocalizedWarning(SalePaymentFrame.this.getRootPane(), ApplicationMessages.VALIDATION_VOUCHER_NOT_ACTIVE);
                                return;
                            }

                            try {
                                java.time.LocalDate expireDate = java.time.LocalDate.parse(voucher.expire_date);
                                if (expireDate.isBefore(java.time.LocalDate.now())) {
                                    EasyPOSMessageDialog.showLocalizedWarning(SalePaymentFrame.this.getRootPane(), ApplicationMessages.VALIDATION_VOUCHER_EXPIRED);
                                    return;
                                }
                            } catch (java.time.format.DateTimeParseException e) {
                                // If expire_date format is unparseable, proceed without blocking
                            }

                            voucherTableModel.addVoucher(voucher);
                            voucherNumInputTextField.setText("");
                            updateVoucherTotalField();
                        }
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(SalePaymentFrame.this.getRootPane(), response.getAPIResponse());
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(SalePaymentFrame.this.getRootPane(), ex.getMessage());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
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

                    salepanelCustNameTextField.setText("");
                    selectedCustomer = null;

                    if (response.getAPIResponse().isSuccess()) {
                        selectedCustomer = (serverDataModels.Customer) response.getData();
                        if (selectedCustomer != null) {
                            salepanelCustNameTextField.setText(selectedCustomer.customer_name);
                        }
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(SalePaymentFrame.this.getRootPane(), response.getAPIResponse());
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(SalePaymentFrame.this.getRootPane(), ex.getMessage());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardTypeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        salepanelBillTotalTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        salepanelBillDisTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        salepanelTotalTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        salepanelPaidTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        salepanelBalanceTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        salepanelNetAmtTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        salepanelRuleDisTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        salepanelTotalPaidTextField = new javax.swing.JTextField();
        salepanelCreditTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        salePaymentCashRadioButton = new javax.swing.JRadioButton();
        salePaymentCardRadioButton = new javax.swing.JRadioButton();
        salePaymentVoucherRadioButton = new javax.swing.JRadioButton();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        voucherNumInputTextField = new javax.swing.JTextField();
        paymentMethodInputLabel = new javax.swing.JLabel();
        voucherSearchButton = new javax.swing.JButton();
        jScrollPaneVoucherTbl = new javax.swing.JScrollPane();
        vouchersTable = new javax.swing.JTable();
        visaRadioButton = new javax.swing.JRadioButton();
        masterRadioButton = new javax.swing.JRadioButton();
        amexRadioButton = new javax.swing.JRadioButton();
        jcbRadioButton = new javax.swing.JRadioButton();
        upiRadioButton = new javax.swing.JRadioButton();
        otherRadioButton = new javax.swing.JRadioButton();
        paymentMethodInputLabel1 = new javax.swing.JLabel();
        cardLast4DigInputTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        salepanelCustNumTextField = new javax.swing.JTextField();
        salepanelCustNameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Bill Summary"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Bill Total :");

        salepanelBillTotalTextField.setEditable(false);
        salepanelBillTotalTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelBillTotalTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Bill Discount :");

        salepanelBillDisTextField.setEditable(false);
        salepanelBillDisTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelBillDisTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Total :");

        salepanelTotalTextField.setEditable(false);
        salepanelTotalTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelTotalTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Cash Paid :");

        salepanelPaidTextField.setEditable(false);
        salepanelPaidTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelPaidTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Cash Balance :");

        salepanelBalanceTextField.setEditable(false);
        salepanelBalanceTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelBalanceTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("NET AMOUNT :");

        salepanelNetAmtTextField.setEditable(false);
        salepanelNetAmtTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelNetAmtTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Special Discount :");

        salepanelRuleDisTextField.setEditable(false);
        salepanelRuleDisTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelRuleDisTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Total Paid :");

        salepanelTotalPaidTextField.setEditable(false);
        salepanelTotalPaidTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelTotalPaidTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        salepanelCreditTextField.setEditable(false);
        salepanelCreditTextField.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        salepanelCreditTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("Credit Amount :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(salepanelBillDisTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(salepanelBillTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(salepanelTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salepanelRuleDisTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salepanelNetAmtTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)
                        .addComponent(salepanelTotalPaidTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(salepanelCreditTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(salepanelPaidTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(salepanelBalanceTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(salepanelBillTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(salepanelBillDisTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(salepanelTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(salepanelRuleDisTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(salepanelNetAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(salepanelTotalPaidTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(salepanelPaidTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(salepanelBalanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salepanelCreditTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Payment Method"));

        salePaymentCashRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salePaymentCashRadioButton.setText("Cash");
        salePaymentCashRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                salePaymentCashRadioButtonStateChanged(evt);
            }
        });

        salePaymentCardRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salePaymentCardRadioButton.setText("Card");
        salePaymentCardRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                salePaymentCardRadioButtonStateChanged(evt);
            }
        });

        salePaymentVoucherRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salePaymentVoucherRadioButton.setText("Voucher");
        salePaymentVoucherRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                salePaymentVoucherRadioButtonStateChanged(evt);
            }
        });

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField9.setEnabled(false);

        voucherNumInputTextField.setAutoscrolls(false);

        paymentMethodInputLabel.setText("Voucher Number :");

        voucherSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        voucherSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voucherSearchButtonActionPerformed(evt);
            }
        });

        vouchersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneVoucherTbl.setViewportView(vouchersTable);

        cardTypeButtonGroup.add(visaRadioButton);
        visaRadioButton.setText("VISA");

        cardTypeButtonGroup.add(masterRadioButton);
        masterRadioButton.setText("Master");

        cardTypeButtonGroup.add(amexRadioButton);
        amexRadioButton.setText("AMEX");

        cardTypeButtonGroup.add(jcbRadioButton);
        jcbRadioButton.setText("JCB");

        cardTypeButtonGroup.add(upiRadioButton);
        upiRadioButton.setText("UPI");

        cardTypeButtonGroup.add(otherRadioButton);
        otherRadioButton.setText("Other");

        paymentMethodInputLabel1.setText("Card num 4 digit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(salePaymentVoucherRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paymentMethodInputLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(voucherNumInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(voucherSearchButton)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField9)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(salePaymentCashRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(salePaymentCardRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneVoucherTbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(visaRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(amexRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbRadioButton)
                                    .addComponent(masterRadioButton, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(otherRadioButton)
                                    .addComponent(upiRadioButton))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(paymentMethodInputLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardLast4DigInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salePaymentCashRadioButton)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salePaymentCardRadioButton)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salePaymentVoucherRadioButton)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentMethodInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(voucherSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(voucherNumInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneVoucherTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paymentMethodInputLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardLast4DigInputTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(visaRadioButton)
                    .addComponent(masterRadioButton)
                    .addComponent(upiRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amexRadioButton)
                    .addComponent(jcbRadioButton)
                    .addComponent(otherRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Loyalty"));

        salepanelCustNumTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        salepanelCustNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelCustNameTextField.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Customer Number :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Customer Name :");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(salepanelCustNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton3))
                    .addComponent(salepanelCustNameTextField))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(salepanelCustNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salepanelCustNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(validateUIInputs()){
            btnActionProcessBill();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        onlineSearchCustomerAction(salepanelCustNumTextField.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void salePaymentCashRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_salePaymentCashRadioButtonStateChanged
        if (salePaymentCashRadioButton.isSelected()) {
            jTextField6.setEditable(true);
        }
        else{
            jTextField6.setText("");
            jTextField6.setEditable(false);
        }
        arrangeUIBasedOnMOPSelection();
        calculateAmounts();
    }//GEN-LAST:event_salePaymentCashRadioButtonStateChanged

    private void salePaymentCardRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_salePaymentCardRadioButtonStateChanged
        if (salePaymentCardRadioButton.isSelected()) {
            jTextField7.setEditable(true);
        }
        else{
            jTextField7.setText("");
            jTextField7.setEditable(false);
        }
        arrangeUIBasedOnMOPSelection();
        calculateAmounts();
    }//GEN-LAST:event_salePaymentCardRadioButtonStateChanged

    private void salePaymentVoucherRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_salePaymentVoucherRadioButtonStateChanged
        if (salePaymentVoucherRadioButton.isSelected()) {
            updateVoucherTotalField();
        }
        else{
            jTextField9.setText("0.00");
        }
        arrangeUIBasedOnMOPSelection();
        calculateAmounts();
    }//GEN-LAST:event_salePaymentVoucherRadioButtonStateChanged

    private void voucherSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voucherSearchButtonActionPerformed
        String voucherName = voucherNumInputTextField.getText().trim();
        if (voucherName.isEmpty()) {
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_ENTER_VOUCHER_NUMBER);
            return;
        }

        onlineSearchVoucherAction(voucherName);
    }//GEN-LAST:event_voucherSearchButtonActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalePaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalePaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalePaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalePaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SalePaymentFrame().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton amexRadioButton;
    private javax.swing.JTextField cardLast4DigInputTextField;
    private javax.swing.ButtonGroup cardTypeButtonGroup;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPaneVoucherTbl;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JRadioButton jcbRadioButton;
    private javax.swing.JRadioButton masterRadioButton;
    private javax.swing.JRadioButton otherRadioButton;
    private javax.swing.JLabel paymentMethodInputLabel;
    private javax.swing.JLabel paymentMethodInputLabel1;
    private javax.swing.JRadioButton salePaymentCardRadioButton;
    private javax.swing.JRadioButton salePaymentCashRadioButton;
    private javax.swing.JRadioButton salePaymentVoucherRadioButton;
    private javax.swing.JTextField salepanelBalanceTextField;
    private javax.swing.JTextField salepanelBillDisTextField;
    private javax.swing.JTextField salepanelBillTotalTextField;
    private javax.swing.JTextField salepanelCreditTextField;
    private javax.swing.JTextField salepanelCustNameTextField;
    private javax.swing.JTextField salepanelCustNumTextField;
    private javax.swing.JTextField salepanelNetAmtTextField;
    private javax.swing.JTextField salepanelPaidTextField;
    private javax.swing.JTextField salepanelRuleDisTextField;
    private javax.swing.JTextField salepanelTotalPaidTextField;
    private javax.swing.JTextField salepanelTotalTextField;
    private javax.swing.JRadioButton upiRadioButton;
    private javax.swing.JRadioButton visaRadioButton;
    private javax.swing.JTextField voucherNumInputTextField;
    private javax.swing.JButton voucherSearchButton;
    private javax.swing.JTable vouchersTable;
    // End of variables declaration//GEN-END:variables
}
