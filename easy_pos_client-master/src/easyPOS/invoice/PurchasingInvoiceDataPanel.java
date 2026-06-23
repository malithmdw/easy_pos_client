
package easyPOS.invoice;

import appDataModels.APIHeaderData;
import appDataModels.PurchaseInvoiceModel;
import appDataModels.PurchaseItemModel;
import appDataModels.SupplierModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import serverResponseDataModel.CommonResponse;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class PurchasingInvoiceDataPanel extends javax.swing.JPanel {

    /**
     * Creates new form InvoiceOverviewPanel
     */
    private PurchaseInvoiceModel purchaseInvoiceModel;
    private InvoicePanelActions invoicePanelActions;
    private List<SupplierModel> supplierModels;
    
    public PurchasingInvoiceDataPanel() {
        initComponents();
        switchLanguage();
    }
    
    public void showPanel(PurchaseInvoiceModel purchaseInvoiceModel, List<SupplierModel> supplierModels, InvoicePanelActions invoicePanelActions)
    {
        this.supplierModels = supplierModels;
        this.purchaseInvoiceModel = purchaseInvoiceModel;
        this.invoicePanelActions = invoicePanelActions;
        
        // partially completed invoice
        if (purchaseInvoiceModel != null) {
            
            // Set data to UI
            setDataToUI(purchaseInvoiceModel);
            
            // Load relevant Supplier to the List
            if (supplierModels != null && !supplierModels.isEmpty()) {
                List<String> dropdownItems = new ArrayList<>();
                for (SupplierModel supplierModel : supplierModels) {
                    if (supplierModel.getSupplierCode() == Integer.parseInt(purchaseInvoiceModel.getSupplierCode())) {
                        dropdownItems.add(supplierModel.getSupplierName());
                        break;
                    }                    
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropdownItems.toArray(new String[0]));
                jComboBoxInvSupplier.setModel(model);
            }
            else{
                List<String> dropdownItems = new ArrayList<>();
                dropdownItems.add(purchaseInvoiceModel.getSupplierCode());
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropdownItems.toArray(new String[0]));
                jComboBoxInvSupplier.setModel(model);
            }
        }
        // New invoice
        else
        {            
            clearAllFields();
            jTextFieldInvSysInvNo.setText(ApplicationDataManager.getInstance().getNextTransactionId());
            
            // Load all suppliers to the list
            if (supplierModels != null && !supplierModels.isEmpty()) {
                // New invoice
                List<String> dropdownItems = new ArrayList<>();
                for (SupplierModel supplierModel : supplierModels) {
                    dropdownItems.add(supplierModel.getSupplierName());
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropdownItems.toArray(new String[0]));
                jComboBoxInvSupplier.setModel(model);
            }
        }
    }
    
    private void clearAllFields(){        
        //jComboBoxInvSupplier.setText(pi.getSupplierCode());
        jTextFieldInvNo.setText("");
        jTextFieldInvSysInvNo.setText("");
        jTextFieldInvTotal.setText("");
        jTextFieldInvDis.setText("");
        jTextFieldInvnetTot.setText("");
        jTextFieldInvPaid.setText("");
        jTextFieldInvBal.setText("");
        jDateChooserPIDataDate.setDate(util.DateTimeUtil.todayDate());
        jDateChooserPIDataDueDate.setDate(null);
        jDateChooserPIDataAlrtDate.setDate(null);
    }
    
    private void setDataToUI(PurchaseInvoiceModel pi){        
        //jComboBoxInvSupplier.setText(pi.getSupplierCode());
        jTextFieldInvNo.setText(pi.getInvoiceNo());
        jTextFieldInvSysInvNo.setText(pi.getSystemInvoiceNumber());
        jTextFieldInvTotal.setText(util.GeneralUtil.getCurrencyString(pi.getTotal()));
        jTextFieldInvDis.setText(util.GeneralUtil.getCurrencyString(pi.getDiscount()));
        jTextFieldInvnetTot.setText(util.GeneralUtil.getCurrencyString(pi.getNetTotal()));
        jTextFieldInvPaid.setText(util.GeneralUtil.getCurrencyString(pi.getPaid()));
        jTextFieldInvBal.setText(util.GeneralUtil.getCurrencyString(pi.getBalance()));
        jDateChooserPIDataDate.setDate(util.DateTimeUtil.getDateFromDBFormatString(pi.getDate()));
        jDateChooserPIDataDueDate.setDate(util.DateTimeUtil.getDateFromDBFormatString(pi.getAlertDate()));
        jDateChooserPIDataAlrtDate.setDate(util.DateTimeUtil.getDateFromDBFormatString(pi.getDueToPay()));
    }
    
    private PurchaseInvoiceModel getDataFromUI(){
        PurchaseInvoiceModel pim = new PurchaseInvoiceModel();
        pim.setInstituteId(Integer.parseInt(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId()));
        pim.setSupplierCode(Integer.toString(supplierModels.get(jComboBoxInvSupplier.getSelectedIndex()).getSupplierCode()));
        pim.setInvoiceNo(jTextFieldInvNo.getText());
        pim.setSystemInvoiceNumber(jTextFieldInvSysInvNo.getText());
        pim.setTotal(util.GeneralUtil.currencyStringToDouble(jTextFieldInvTotal.getText()));
        pim.setDiscount(util.GeneralUtil.currencyStringToDouble(jTextFieldInvDis.getText()));
        pim.setNetTotal(util.GeneralUtil.currencyStringToDouble(jTextFieldInvnetTot.getText()));
        pim.setPaid(util.GeneralUtil.currencyStringToDouble(jTextFieldInvPaid.getText()));
        pim.setBalance(util.GeneralUtil.currencyStringToDouble(jTextFieldInvBal.getText()));
        pim.setDate(util.DateTimeUtil.getDBFormatStringFromDate(jDateChooserPIDataDate.getDate()));
        pim.setDueToPay(util.DateTimeUtil.getDBFormatStringFromDate(jDateChooserPIDataDueDate.getDate()));
        pim.setAlertDate(util.DateTimeUtil.getDBFormatStringFromDate(jDateChooserPIDataAlrtDate.getDate()));
        pim.setInputMethod("1");
        
        if (purchaseInvoiceModel == null) {
            pim.setPurchaseItems(new ArrayList<>());
        } else {
            List<PurchaseItemModel> pItms = new ArrayList<>();
            pItms.addAll(purchaseInvoiceModel.getPurchaseItems());
            pim.setPurchaseItems(pItms);
        }
        
        return pim;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPIDataDate = new javax.swing.JLabel();
        jLabelPIDataDiscount = new javax.swing.JLabel();
        jLabelPIDataNetTot = new javax.swing.JLabel();
        jLabelPIDataSupplier = new javax.swing.JLabel();
        jLabelPIDataTotal = new javax.swing.JLabel();
        jLabelPIDataAlertDate = new javax.swing.JLabel();
        jLabelPIDataPaid = new javax.swing.JLabel();
        jLabelPIDataPaymentDueDate = new javax.swing.JLabel();
        jLabelPIDataBalance = new javax.swing.JLabel();
        jLabelPIDataInvNo = new javax.swing.JLabel();
        jComboBoxInvSupplier = new javax.swing.JComboBox<>();
        jTextFieldInvNo = new javax.swing.JTextField();
        jTextFieldInvTotal = new javax.swing.JTextField();
        jTextFieldInvDis = new javax.swing.JTextField();
        jTextFieldInvnetTot = new javax.swing.JTextField();
        jTextFieldInvPaid = new javax.swing.JTextField();
        jTextFieldInvBal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButtonPIDataSave = new javax.swing.JButton();
        jLabelPIDataSysInvNo = new javax.swing.JLabel();
        jTextFieldInvSysInvNo = new javax.swing.JTextField();
        jDateChooserPIDataDueDate = new com.toedter.calendar.JDateChooser();
        jDateChooserPIDataAlrtDate = new com.toedter.calendar.JDateChooser();
        jDateChooserPIDataDate = new com.toedter.calendar.JDateChooser();

        jLabelPIDataDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/invoice/Bundle"); // NOI18N
        jLabelPIDataDate.setText(bundle.getString("PurchasingInvoiceDataPanel.Date")); // NOI18N

        jLabelPIDataDiscount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataDiscount.setText(bundle.getString("PurchasingInvoiceDataPanel.Discount")); // NOI18N

        jLabelPIDataNetTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataNetTot.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataNetTot.setText(bundle.getString("PurchasingInvoiceDataPanel.NetTotal")); // NOI18N

        jLabelPIDataSupplier.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataSupplier.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataSupplier.setText(bundle.getString("PurchasingInvoiceDataPanel.Supplier")); // NOI18N

        jLabelPIDataTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataTotal.setText(bundle.getString("PurchasingInvoiceDataPanel.Total")); // NOI18N

        jLabelPIDataAlertDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataAlertDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataAlertDate.setText(bundle.getString("PurchasingInvoiceDataPanel.AlertDate")); // NOI18N

        jLabelPIDataPaid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataPaid.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataPaid.setText(bundle.getString("PurchasingInvoiceDataPanel.Paid")); // NOI18N

        jLabelPIDataPaymentDueDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataPaymentDueDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataPaymentDueDate.setText(bundle.getString("PurchasingInvoiceBasePanel.PaymentDueDate")); // NOI18N

        jLabelPIDataBalance.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataBalance.setText(bundle.getString("PurchasingInvoiceDataPanel.Balance")); // NOI18N

        jLabelPIDataInvNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataInvNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataInvNo.setText(bundle.getString("PurchasingInvoiceDataPanel.InvoiceNo")); // NOI18N

        jComboBoxInvSupplier.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBoxInvSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextFieldInvNo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldInvNo.setToolTipText("");

        jTextFieldInvTotal.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldInvTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvTotal.setToolTipText("");

        jTextFieldInvDis.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldInvDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvDis.setToolTipText("");

        jTextFieldInvnetTot.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldInvnetTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvnetTot.setToolTipText("");

        jTextFieldInvPaid.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldInvPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvPaid.setToolTipText("");

        jTextFieldInvBal.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldInvBal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvBal.setToolTipText("");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus_icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonPIDataSave.setText(bundle.getString("PurchasingInvoiceDataPanel.SaveChanges")); // NOI18N
        jButtonPIDataSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPIDataSaveActionPerformed(evt);
            }
        });

        jLabelPIDataSysInvNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIDataSysInvNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIDataSysInvNo.setText(bundle.getString("PurchasingInvoiceDataPanel.SystemInvNo")); // NOI18N

        jTextFieldInvSysInvNo.setEditable(false);
        jTextFieldInvSysInvNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextFieldInvSysInvNo.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelPIDataPaymentDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataAlertDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataPaid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataNetTot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataSysInvNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelPIDataDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelPIDataSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPIDataSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldInvNo)
                    .addComponent(jTextFieldInvSysInvNo)
                    .addComponent(jTextFieldInvTotal)
                    .addComponent(jTextFieldInvDis)
                    .addComponent(jTextFieldInvnetTot)
                    .addComponent(jTextFieldInvPaid)
                    .addComponent(jTextFieldInvBal)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jComboBoxInvSupplier, 0, 160, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooserPIDataDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooserPIDataAlrtDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooserPIDataDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelPIDataSupplier)
                        .addComponent(jComboBoxInvSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPIDataDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooserPIDataDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldInvNo)
                    .addComponent(jLabelPIDataInvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvSysInvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIDataSysInvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIDataTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvDis, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIDataDiscount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvnetTot, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIDataNetTot))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIDataPaid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvBal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIDataBalance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPIDataPaymentDueDate)
                    .addComponent(jDateChooserPIDataDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPIDataAlertDate)
                    .addComponent(jDateChooserPIDataAlrtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPIDataSave)
                .addContainerGap(7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        invoicePanelActions.addNewActionPerformed();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonPIDataSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPIDataSaveActionPerformed
        addOrUpdateInvoiceAction(getDataFromUI());
    }//GEN-LAST:event_jButtonPIDataSaveActionPerformed

    private void addOrUpdateInvoiceAction(PurchaseInvoiceModel pim)
    {
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

                return ServerAPIConnection.getInstance(aPIHeaderData).addUpdatePurchasingInvoice(pim.newPurchaseInvoiceDTO());
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                
                
                CommonResponse commonResponse;
                try {
                    commonResponse = get();
                    if (commonResponse.getAPIResponse().isSuccess()) {
                        clearAllFields();
                        invoicePanelActions.successfullyUpdated();
                    }
                    else{
                        EasyPOSMessageDialog.showErrorMessageDialog(PurchasingInvoiceDataPanel.this, commonResponse.getAPIResponse());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(PendingPurchasingInvoicesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    public PurchaseInvoiceModel getInsertedPurchaseInvoiceModel(){
        return purchaseInvoiceModel;
    }

    private void switchLanguage() {
        Locale locale = new Locale("si", "LK");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/invoice/Bundle", locale);
                        
        try {
            //create the font to use. Specify the size!
            
            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);            
            jLabelPIDataSupplier.setFont(customFont1);
            jLabelPIDataDate.setFont(customFont1);
            jLabelPIDataInvNo.setFont(customFont1);
            jLabelPIDataSysInvNo.setFont(customFont1);
            jLabelPIDataTotal.setFont(customFont1);
            jLabelPIDataDiscount.setFont(customFont1);
            jLabelPIDataNetTot.setFont(customFont1);
            jLabelPIDataPaid.setFont(customFont1);
            jLabelPIDataBalance.setFont(customFont1);
            jLabelPIDataPaymentDueDate.setFont(customFont1);
            jLabelPIDataAlertDate.setFont(customFont1);
            
            jButtonPIDataSave.setFont(customFont1);
            
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
 
        //use the font
        jLabelPIDataSupplier.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Supplier"));
        jLabelPIDataDate.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Date"));
        jLabelPIDataInvNo.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.InvoiceNo"));
        jLabelPIDataSysInvNo.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.SystemInvNo"));
        jLabelPIDataTotal.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Total"));
        jLabelPIDataDiscount.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Discount"));
        jLabelPIDataNetTot.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.NetTotal"));
        jLabelPIDataPaid.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Paid"));
        jLabelPIDataBalance.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Balance"));
        jLabelPIDataPaymentDueDate.setText(resourceBundle.getString("PurchasingInvoiceBasePanel.PaymentDueDate"));
        jLabelPIDataAlertDate.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.AlertDate"));

        jButtonPIDataSave.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.SaveChanges"));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonPIDataSave;
    private javax.swing.JComboBox<String> jComboBoxInvSupplier;
    private com.toedter.calendar.JDateChooser jDateChooserPIDataAlrtDate;
    private com.toedter.calendar.JDateChooser jDateChooserPIDataDate;
    private com.toedter.calendar.JDateChooser jDateChooserPIDataDueDate;
    private javax.swing.JLabel jLabelPIDataAlertDate;
    private javax.swing.JLabel jLabelPIDataBalance;
    private javax.swing.JLabel jLabelPIDataDate;
    private javax.swing.JLabel jLabelPIDataDiscount;
    private javax.swing.JLabel jLabelPIDataInvNo;
    private javax.swing.JLabel jLabelPIDataNetTot;
    private javax.swing.JLabel jLabelPIDataPaid;
    private javax.swing.JLabel jLabelPIDataPaymentDueDate;
    private javax.swing.JLabel jLabelPIDataSupplier;
    private javax.swing.JLabel jLabelPIDataSysInvNo;
    private javax.swing.JLabel jLabelPIDataTotal;
    private javax.swing.JTextField jTextFieldInvBal;
    private javax.swing.JTextField jTextFieldInvDis;
    private javax.swing.JTextField jTextFieldInvNo;
    private javax.swing.JTextField jTextFieldInvPaid;
    private javax.swing.JTextField jTextFieldInvSysInvNo;
    private javax.swing.JTextField jTextFieldInvTotal;
    private javax.swing.JTextField jTextFieldInvnetTot;
    // End of variables declaration//GEN-END:variables
}
