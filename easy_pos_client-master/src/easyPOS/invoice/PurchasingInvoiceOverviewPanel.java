
package easyPOS.invoice;

import appDataModels.APIHeaderData;
import appDataModels.ItemModel;
import appDataModels.PurchaseInvoiceModel;
import appDataModels.PurchaseItemModel;
import appDataModels.PurchasingInvoiceItemUIDataModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import localDatabase.DatabaseManager;
import serverResponseDataModel.CommonResponse;
import tableModels.PurchasingInvoiceItemsTbl;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class PurchasingInvoiceOverviewPanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    /**
     * Creates new form InvoiceOverviewPanel
     */
    private PurchaseInvoiceModel purchaseInvoiceModel;
    private InvoicePanelActions invoicePanelActions;
    private List<PurchasingInvoiceItemUIDataModel> purchaseItems;
    
    public PurchasingInvoiceOverviewPanel() {
        initComponents();
        switchLanguage();
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    public void showPanel(PurchaseInvoiceModel purchaseInvoiceModel, InvoicePanelActions invoicePanelActions)
    {
        this.invoicePanelActions = invoicePanelActions;
        this.purchaseInvoiceModel = purchaseInvoiceModel;
        
        if (purchaseInvoiceModel != null) {
            this.purchaseItems = new ArrayList<>();
            if (purchaseInvoiceModel.getPurchaseItems() != null) {
                for (PurchaseItemModel purchaseItem : purchaseInvoiceModel.getPurchaseItems()) {            
                    ItemModel itemModel = DatabaseManager.getInstance().getItemByItemId(purchaseItem.getItemId());
                    purchaseItems.add(new PurchasingInvoiceItemUIDataModel(itemModel, purchaseItem));
                }
                loadInviceItemsTable(purchaseItems);
            }
            
            setDataToUI(purchaseInvoiceModel);
        }
        
                
    }
    
    private void setDataToUI(PurchaseInvoiceModel pi){
        jLabelInvOvDate.setText(pi.getDate());
        jLabelInvOvSupplier.setText(pi.getSupplierCode());
        jLabelInvOvInvNo.setText(pi.getInvoiceNo());
        jLabelInvOvSysInvNo.setText(pi.getSystemInvoiceNumber());
        jLabelInvOvTotal.setText(util.GeneralUtil.getCurrencyString(pi.getTotal()));
        jLabelInvOvDis.setText(util.GeneralUtil.getCurrencyString(pi.getDiscount()));
        jLabelInvOvNetTot.setText(util.GeneralUtil.getCurrencyString(pi.getNetTotal()));
        jLabelInvOvPaid.setText(util.GeneralUtil.getCurrencyString(pi.getPaid()));
        jLabelInvOvBalance.setText(util.GeneralUtil.getCurrencyString(pi.getBalance()));
        jLabelInvOvPaymentDue.setText(pi.getDueToPay());
        jLabelInvOvAlertdate.setText(pi.getAlertDate());
    }
    
    private void loadInviceItemsTable(List<PurchasingInvoiceItemUIDataModel> list){
        PurchasingInvoiceItemsTbl ctt = new PurchasingInvoiceItemsTbl(list);
        jTableInvoiceOverviewItems.setModel(ctt);        
        jTableInvoiceOverviewItems.setRowHeight(35);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPIOverviewDate = new javax.swing.JLabel();
        jLabelPIOverviewDiscount = new javax.swing.JLabel();
        jLabelPIOverviewNetTot = new javax.swing.JLabel();
        jLabelPIOverviewSupplier = new javax.swing.JLabel();
        jLabelPIOverviewTotal = new javax.swing.JLabel();
        jLabelPIOverviewAlertDate = new javax.swing.JLabel();
        jLabelPIOverviewPaid = new javax.swing.JLabel();
        jLabelPIOverviewPaymentDueDate = new javax.swing.JLabel();
        jLabelPIOverviewBalance = new javax.swing.JLabel();
        jLabelPIOverviewInvNo = new javax.swing.JLabel();
        jButtonPIOverviewComplete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInvoiceOverviewItems = new javax.swing.JTable();
        jLabelPIOverviewSysInvNo = new javax.swing.JLabel();
        jLabelInvOvSupplier = new javax.swing.JLabel();
        jLabelInvOvInvNo = new javax.swing.JLabel();
        jLabelInvOvDate = new javax.swing.JLabel();
        jLabelInvOvSysInvNo = new javax.swing.JLabel();
        jLabelInvOvTotal = new javax.swing.JLabel();
        jLabelInvOvDis = new javax.swing.JLabel();
        jLabelInvOvNetTot = new javax.swing.JLabel();
        jLabelInvOvPaymentDue = new javax.swing.JLabel();
        jLabelInvOvPaid = new javax.swing.JLabel();
        jLabelInvOvAlertdate = new javax.swing.JLabel();
        jLabelInvOvBalance = new javax.swing.JLabel();

        jLabelPIOverviewDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/invoice/Bundle"); // NOI18N
        jLabelPIOverviewDate.setText(bundle.getString("PurchasingInvoiceDataPanel.Date")); // NOI18N

        jLabelPIOverviewDiscount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewDiscount.setText(bundle.getString("PurchasingInvoiceDataPanel.Discount")); // NOI18N

        jLabelPIOverviewNetTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewNetTot.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewNetTot.setText(bundle.getString("PurchasingInvoiceDataPanel.NetTotal")); // NOI18N

        jLabelPIOverviewSupplier.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewSupplier.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewSupplier.setText(bundle.getString("PurchasingInvoiceDataPanel.Supplier")); // NOI18N

        jLabelPIOverviewTotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewTotal.setText(bundle.getString("PurchasingInvoiceDataPanel.Total")); // NOI18N

        jLabelPIOverviewAlertDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewAlertDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewAlertDate.setText(bundle.getString("PurchasingInvoiceDataPanel.AlertDate")); // NOI18N

        jLabelPIOverviewPaid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewPaid.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewPaid.setText(bundle.getString("PurchasingInvoiceDataPanel.Paid")); // NOI18N

        jLabelPIOverviewPaymentDueDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewPaymentDueDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewPaymentDueDate.setText(bundle.getString("PurchasingInvoiceBasePanel.PaymentDueDate")); // NOI18N

        jLabelPIOverviewBalance.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewBalance.setText(bundle.getString("PurchasingInvoiceDataPanel.Balance")); // NOI18N

        jLabelPIOverviewInvNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewInvNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewInvNo.setText(bundle.getString("PurchasingInvoiceDataPanel.InvoiceNo")); // NOI18N

        jButtonPIOverviewComplete.setText(bundle.getString("PurchasingInvoiceOverviewPanel.Complete")); // NOI18N
        jButtonPIOverviewComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPIOverviewCompleteActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PurchasingInvoiceItemPanel.ItemDetails"))); // NOI18N

        jTableInvoiceOverviewItems.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableInvoiceOverviewItems);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelPIOverviewSysInvNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPIOverviewSysInvNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIOverviewSysInvNo.setText(bundle.getString("PurchasingInvoiceDataPanel.SystemInvNo")); // NOI18N

        jLabelInvOvSupplier.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvSupplier.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvSupplier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvInvNo.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvInvNo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvInvNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvDate.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvSysInvNo.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvSysInvNo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvSysInvNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvTotal.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelInvOvTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvDis.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvDis.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvDis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelInvOvDis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvNetTot.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvNetTot.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvNetTot.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelInvOvNetTot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvPaymentDue.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvPaymentDue.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvPaymentDue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvPaid.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvPaid.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvPaid.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelInvOvPaid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvAlertdate.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvAlertdate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvAlertdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelInvOvBalance.setBackground(new java.awt.Color(204, 204, 204));
        jLabelInvOvBalance.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabelInvOvBalance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelInvOvBalance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelPIOverviewInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jLabelPIOverviewSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelInvOvSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(jLabelInvOvInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelPIOverviewSysInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelPIOverviewDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelInvOvDate, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(jLabelInvOvSysInvNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelPIOverviewPaid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelPIOverviewNetTot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelPIOverviewDiscount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelPIOverviewTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelPIOverviewBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelInvOvBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelInvOvTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelInvOvDis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelInvOvNetTot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelInvOvPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelPIOverviewAlertDate, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelPIOverviewPaymentDueDate))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabelInvOvAlertdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabelInvOvPaymentDue, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jButtonPIOverviewComplete))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabelPIOverviewDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelInvOvSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIOverviewSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelInvOvDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelInvOvInvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInvOvSysInvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIOverviewSysInvNo)
                    .addComponent(jLabelPIOverviewInvNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabelInvOvTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelInvOvPaymentDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIOverviewTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIOverviewPaymentDueDate, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelInvOvAlertdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelInvOvDis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIOverviewDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIOverviewAlertDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelPIOverviewNetTot, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelInvOvNetTot, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelInvOvPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelPIOverviewPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelPIOverviewBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelInvOvBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonPIOverviewComplete))
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPIOverviewCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPIOverviewCompleteActionPerformed
        if (purchaseInvoiceModel.getPurchaseItems() != null 
                && !purchaseInvoiceModel.getPurchaseItems().isEmpty()) {
            processCompleteInvoiceAction(purchaseInvoiceModel.getSystemInvoiceNumber());
        }
        else{
            JOptionPane.showMessageDialog(this, "Incomplete Invoice - Empty Items");
        }
    }//GEN-LAST:event_jButtonPIOverviewCompleteActionPerformed

    private void processCompleteInvoiceAction(String systemInvNo)
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

                return ServerAPIConnection.getInstance(aPIHeaderData).processCompletePurchasingInvoice(systemInvNo);
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                
                
                CommonResponse commonResponse;
                try {
                    commonResponse = get();
                    if (commonResponse.getAPIResponse().isSuccess()) {
                        invoicePanelActions.successfullyUpdated();
                    }
                    else{
                        EasyPOSMessageDialog.showErrorMessageDialog(PurchasingInvoiceOverviewPanel.this, commonResponse.getAPIResponse());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(PendingPurchasingInvoicesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
            }

        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/invoice/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            //create the font to use. Specify the size!

            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);
            jLabelPIOverviewSupplier.setFont(customFont1);
            jLabelPIOverviewDate.setFont(customFont1);
            jLabelPIOverviewInvNo.setFont(customFont1);
            jLabelPIOverviewSysInvNo.setFont(customFont1);
            jLabelPIOverviewTotal.setFont(customFont1);
            jLabelPIOverviewDiscount.setFont(customFont1);
            jLabelPIOverviewNetTot.setFont(customFont1);
            jLabelPIOverviewPaid.setFont(customFont1);
            jLabelPIOverviewBalance.setFont(customFont1);
            jLabelPIOverviewPaymentDueDate.setFont(customFont1);
            jLabelPIOverviewAlertDate.setFont(customFont1);

            jButtonPIOverviewComplete.setFont(customFont1);

        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        //use the font
        jLabelPIOverviewSupplier.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Supplier"));
        jLabelPIOverviewDate.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Date"));
        jLabelPIOverviewInvNo.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.InvoiceNo"));
        jLabelPIOverviewSysInvNo.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.SystemInvNo"));
        jLabelPIOverviewTotal.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Total"));
        jLabelPIOverviewDiscount.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Discount"));
        jLabelPIOverviewNetTot.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.NetTotal"));
        jLabelPIOverviewPaid.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Paid"));
        jLabelPIOverviewBalance.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.Balance"));
        jLabelPIOverviewPaymentDueDate.setText(resourceBundle.getString("PurchasingInvoiceBasePanel.PaymentDueDate"));
        jLabelPIOverviewAlertDate.setText(resourceBundle.getString("PurchasingInvoiceDataPanel.AlertDate"));

        jButtonPIOverviewComplete.setText(resourceBundle.getString("PurchasingInvoiceOverviewPanel.Complete"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPIOverviewComplete;
    private javax.swing.JLabel jLabelInvOvAlertdate;
    private javax.swing.JLabel jLabelInvOvBalance;
    private javax.swing.JLabel jLabelInvOvDate;
    private javax.swing.JLabel jLabelInvOvDis;
    private javax.swing.JLabel jLabelInvOvInvNo;
    private javax.swing.JLabel jLabelInvOvNetTot;
    private javax.swing.JLabel jLabelInvOvPaid;
    private javax.swing.JLabel jLabelInvOvPaymentDue;
    private javax.swing.JLabel jLabelInvOvSupplier;
    private javax.swing.JLabel jLabelInvOvSysInvNo;
    private javax.swing.JLabel jLabelInvOvTotal;
    private javax.swing.JLabel jLabelPIOverviewAlertDate;
    private javax.swing.JLabel jLabelPIOverviewBalance;
    private javax.swing.JLabel jLabelPIOverviewDate;
    private javax.swing.JLabel jLabelPIOverviewDiscount;
    private javax.swing.JLabel jLabelPIOverviewInvNo;
    private javax.swing.JLabel jLabelPIOverviewNetTot;
    private javax.swing.JLabel jLabelPIOverviewPaid;
    private javax.swing.JLabel jLabelPIOverviewPaymentDueDate;
    private javax.swing.JLabel jLabelPIOverviewSupplier;
    private javax.swing.JLabel jLabelPIOverviewSysInvNo;
    private javax.swing.JLabel jLabelPIOverviewTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableInvoiceOverviewItems;
    // End of variables declaration//GEN-END:variables

}
