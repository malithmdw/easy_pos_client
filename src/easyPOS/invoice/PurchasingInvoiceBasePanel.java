
package easyPOS.invoice;

import appDataModels.APIHeaderData;
import appDataModels.SupplierModel;
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
import control.EasyPosLogger;
import easyPOS.localization.ApplicationMessages;
import javax.swing.JOptionPane;
import uiUtil.EasyPOSMessageDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import serverResponseDataModel.CommonResponse;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class PurchasingInvoiceBasePanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    private InvoiceWizardStage currentWizardStage;
    private List<SupplierModel> suppliers;
    /**
     * Creates new form InventoryPanel
     */
    public PurchasingInvoiceBasePanel() {
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

    public void showPanel() {
        changePanel(InvoiceWizardStage.PENDING_INVOICES);
        loadAllSuppliersAction();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelInvoiceBase = new javax.swing.JPanel();
        pendingInvoicesPanel = new easyPOS.invoice.PendingPurchasingInvoicesPanel();
        invoiceDataPanel = new easyPOS.invoice.PurchasingInvoiceDataPanel();
        invoiceItemsPanel = new easyPOS.invoice.PurchasingInvoiceItemsPanel();
        invoiceOverviewPanel = new easyPOS.invoice.PurchasingInvoiceOverviewPanel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButtonPendingInvoices = new javax.swing.JRadioButton();
        jRadioButtonInvoiceOverview = new javax.swing.JRadioButton();
        jRadioButtonInvoiceItem = new javax.swing.JRadioButton();
        jRadioButtonInvoiceComplete = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jButtonInvoiceBack = new javax.swing.JButton();
        jButtonInvoiceNext = new javax.swing.JButton();

        jPanelInvoiceBase.setLayout(new java.awt.CardLayout());

        pendingInvoicesPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanelInvoiceBase.add(pendingInvoicesPanel, "card2");
        jPanelInvoiceBase.add(invoiceDataPanel, "card3");
        jPanelInvoiceBase.add(invoiceItemsPanel, "card4");
        jPanelInvoiceBase.add(invoiceOverviewPanel, "card5");

        jScrollPane1.setViewportView(jPanelInvoiceBase);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jRadioButtonPendingInvoices.setForeground(new java.awt.Color(255, 255, 255));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/invoice/Bundle"); // NOI18N
        jRadioButtonPendingInvoices.setText(bundle.getString("PurchasingInvoiceBasePanel.PendingInvoices")); // NOI18N
        jRadioButtonPendingInvoices.setEnabled(false);
        jRadioButtonPendingInvoices.setFocusable(false);

        jRadioButtonInvoiceOverview.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonInvoiceOverview.setText(bundle.getString("PurchasingInvoiceBasePanel.InvoiceDetails")); // NOI18N
        jRadioButtonInvoiceOverview.setEnabled(false);
        jRadioButtonInvoiceOverview.setFocusable(false);

        jRadioButtonInvoiceItem.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonInvoiceItem.setText(bundle.getString("PurchasingInvoiceBasePanel.ItemDetails")); // NOI18N
        jRadioButtonInvoiceItem.setEnabled(false);
        jRadioButtonInvoiceItem.setFocusable(false);

        jRadioButtonInvoiceComplete.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonInvoiceComplete.setText(bundle.getString("PurchasingInvoiceBasePanel.Complete")); // NOI18N
        jRadioButtonInvoiceComplete.setEnabled(false);
        jRadioButtonInvoiceComplete.setFocusable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/invoice_wizard_header.png"))); // NOI18N

        jButtonInvoiceBack.setText("Back");
        jButtonInvoiceBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInvoiceBackActionPerformed(evt);
            }
        });

        jButtonInvoiceNext.setText("Next");
        jButtonInvoiceNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInvoiceNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonPendingInvoices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonInvoiceOverview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonInvoiceItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonInvoiceComplete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonInvoiceBack, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonInvoiceNext, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButtonInvoiceNext, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(jButtonInvoiceBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButtonPendingInvoices)
                            .addComponent(jRadioButtonInvoiceOverview)
                            .addComponent(jRadioButtonInvoiceItem)
                            .addComponent(jRadioButtonInvoiceComplete))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInvoiceNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInvoiceNextActionPerformed
        switch (currentWizardStage) {
            case PENDING_INVOICES:
                changePanel(InvoiceWizardStage.INVOICE_DATA);
                break;
            case INVOICE_DATA:
                changePanel(InvoiceWizardStage.ITEM_DATA);
                break;
            case ITEM_DATA:
                changePanel(InvoiceWizardStage.COMPLETE);
                break;
            case COMPLETE:
                break;
            default:
                changePanel(InvoiceWizardStage.PENDING_INVOICES);
        }
    }//GEN-LAST:event_jButtonInvoiceNextActionPerformed

    private void jButtonInvoiceBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInvoiceBackActionPerformed
        switch (currentWizardStage) {
            case PENDING_INVOICES:
                break;
            case INVOICE_DATA:
                changePanel(InvoiceWizardStage.PENDING_INVOICES);
                break;
            case ITEM_DATA:
                changePanel(InvoiceWizardStage.INVOICE_DATA);
                break;
            case COMPLETE:
                changePanel(InvoiceWizardStage.ITEM_DATA);
                break;
            default:
                changePanel(InvoiceWizardStage.PENDING_INVOICES);
        }
    }//GEN-LAST:event_jButtonInvoiceBackActionPerformed


    private void loadPage(JPanel nextPanel)
    {
        jPanelInvoiceBase.removeAll();   //change j Panel
        jPanelInvoiceBase.repaint();
        jPanelInvoiceBase.revalidate();
        jPanelInvoiceBase.add(nextPanel);
        jPanelInvoiceBase.repaint();
        jPanelInvoiceBase.revalidate();
    }
    
    private void changePanel(InvoiceWizardStage nextStage)
    {
        switch (nextStage) {
            case PENDING_INVOICES:
            {
                jRadioButtonPendingInvoices.setSelected(true);
                jRadioButtonInvoiceOverview.setSelected(false);
                jRadioButtonInvoiceItem.setSelected(false);
                jRadioButtonInvoiceComplete.setSelected(false);
                
                jButtonInvoiceBack.setVisible(false);
                jButtonInvoiceNext.setVisible(true);
                
                loadPage(pendingInvoicesPanel);
                pendingInvoicesPanel.showPanel(new InvoicePanelActions() {
                    @Override
                    public void dataloadingFailed() {
                        EasyPOSMessageDialog.showLocalizedError(PurchasingInvoiceBasePanel.this, ApplicationMessages.ERROR_LOAD_FAILED);
                    }

                    @Override
                    public void addNewActionPerformed() {
                        changePanel(InvoiceWizardStage.INVOICE_DATA);
                    }

                    @Override
                    public void saveChangesActionPerformed() {
                        
                    }

                    @Override
                    public void successfullyUpdated() {
                        
                    }

                    @Override
                    public void actionCancelled() {
                        
                    }
                });
                currentWizardStage = InvoiceWizardStage.PENDING_INVOICES;
                break;
            }
            case INVOICE_DATA:
            {
                if (pendingInvoicesPanel.getSelectedRecord() == null
                        && !pendingInvoicesPanel.isNewInvoiceRequest()) {
                    EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_SELECT_INVOICE_RECORD);
                    return;
                }
                jRadioButtonPendingInvoices.setSelected(false);
                jRadioButtonInvoiceOverview.setSelected(true);
                jRadioButtonInvoiceItem.setSelected(false);
                jRadioButtonInvoiceComplete.setSelected(false);
                
                jButtonInvoiceBack.setVisible(true);
                jButtonInvoiceNext.setVisible(true);
                
                loadPage(invoiceDataPanel);
                invoiceDataPanel.showPanel(pendingInvoicesPanel.getSelectedRecord(), 
                        suppliers, 
                        new InvoicePanelActions() {
                    @Override
                    public void dataloadingFailed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void addNewActionPerformed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void saveChangesActionPerformed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void successfullyUpdated() {
                        changePanel(InvoiceWizardStage.PENDING_INVOICES);
                    }

                    @Override
                    public void actionCancelled() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                });
                currentWizardStage = InvoiceWizardStage.INVOICE_DATA;
                break;
            }
            case ITEM_DATA:
            {
                if (invoiceDataPanel.getInsertedPurchaseInvoiceModel() == null) {
                    EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_SELECT_OR_INSERT_INVOICE);
                    return;
                }
                jRadioButtonPendingInvoices.setSelected(false);
                jRadioButtonInvoiceOverview.setSelected(false);
                jRadioButtonInvoiceItem.setSelected(true);
                jRadioButtonInvoiceComplete.setSelected(false);
                
                jButtonInvoiceBack.setVisible(true);
                jButtonInvoiceNext.setVisible(true);
                
                loadPage(invoiceItemsPanel);
                invoiceItemsPanel.showPanel(pendingInvoicesPanel.getSelectedRecord(), new InvoicePanelActions() {
                    @Override
                    public void dataloadingFailed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void addNewActionPerformed() {
                        changePanel(InvoiceWizardStage.INVOICE_DATA);
                    }

                    @Override
                    public void saveChangesActionPerformed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void successfullyUpdated() {
                        changePanel(InvoiceWizardStage.PENDING_INVOICES);
                    }

                    @Override
                    public void actionCancelled() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                });
                currentWizardStage = InvoiceWizardStage.ITEM_DATA;
                break;
            }
            case COMPLETE:
            {
                jRadioButtonPendingInvoices.setSelected(false);
                jRadioButtonInvoiceOverview.setSelected(false);
                jRadioButtonInvoiceItem.setSelected(false);
                jRadioButtonInvoiceComplete.setSelected(true);
                
                jButtonInvoiceBack.setVisible(true);
                jButtonInvoiceNext.setVisible(false);
                loadPage(invoiceOverviewPanel);
                invoiceOverviewPanel.showPanel(pendingInvoicesPanel.getSelectedRecord(), new InvoicePanelActions() {
                    @Override
                    public void dataloadingFailed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void addNewActionPerformed() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void saveChangesActionPerformed() {
                        // Not Implemented
                    }

                    @Override
                    public void successfullyUpdated() {
                        changePanel(InvoiceWizardStage.PENDING_INVOICES);
                    }

                    @Override
                    public void actionCancelled() {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                });
                currentWizardStage = InvoiceWizardStage.COMPLETE;
                break;
            }
            default:
                throw new AssertionError();
        }
    }
    
    private void loadAllSuppliersAction() {
        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);
        
        suppliers = new ArrayList<>();

        SwingWorker<CommonResponse, Void> worker;
        worker = new SwingWorker<CommonResponse, Void>() {
            
            
            @Override
            protected CommonResponse doInBackground() {
                loader.start(); // show loader
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).getAllSuppliers();
                
                if (commonResponse.getAPIResponse().isSuccess()) {
                    List<serverDataModels.Supplier> suppliersList = new ArrayList<>((ArrayList<serverDataModels.Supplier>) commonResponse.getData());

                    for (serverDataModels.Supplier sup : suppliersList) {
                        suppliers.add(new SupplierModel(sup));
                    }
                }
                return commonResponse;
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                
                
                CommonResponse commonResponse;
                try {
                    commonResponse = get();
                    if (!commonResponse.getAPIResponse().isSuccess()) {
                        uiUtil.EasyPOSMessageDialog.showErrorMessageDialog(PurchasingInvoiceBasePanel.this, commonResponse.getAPIResponse());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    EasyPosLogger.getInstance().error("", ex);
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
            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);
            jRadioButtonPendingInvoices.setFont(customFont1);
            jRadioButtonInvoiceOverview.setFont(customFont1);
            jRadioButtonInvoiceItem.setFont(customFont1);
            jRadioButtonInvoiceComplete.setFont(customFont1);

            ge.registerFont(customFont2);
            jRadioButtonPendingInvoices.setFont(customFont2);
            jRadioButtonInvoiceOverview.setFont(customFont2);
            jRadioButtonInvoiceItem.setFont(customFont2);
            jRadioButtonInvoiceComplete.setFont(customFont2);


        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        //use the font
        jRadioButtonPendingInvoices.setText(resourceBundle.getString("PurchasingInvoiceBasePanel.PendingInvoices"));
        jRadioButtonInvoiceOverview.setText(resourceBundle.getString("PurchasingInvoiceBasePanel.InvoiceDetails"));
        jRadioButtonInvoiceItem.setText(resourceBundle.getString("PurchasingInvoiceBasePanel.ItemDetails"));
        jRadioButtonInvoiceComplete.setText(resourceBundle.getString("PurchasingInvoiceBasePanel.Complete"));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private easyPOS.invoice.PurchasingInvoiceDataPanel invoiceDataPanel;
    private easyPOS.invoice.PurchasingInvoiceItemsPanel invoiceItemsPanel;
    private easyPOS.invoice.PurchasingInvoiceOverviewPanel invoiceOverviewPanel;
    private javax.swing.JButton jButtonInvoiceBack;
    private javax.swing.JButton jButtonInvoiceNext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelInvoiceBase;
    private javax.swing.JRadioButton jRadioButtonInvoiceComplete;
    private javax.swing.JRadioButton jRadioButtonInvoiceItem;
    private javax.swing.JRadioButton jRadioButtonInvoiceOverview;
    private javax.swing.JRadioButton jRadioButtonPendingInvoices;
    private javax.swing.JScrollPane jScrollPane1;
    private easyPOS.invoice.PendingPurchasingInvoicesPanel pendingInvoicesPanel;
    // End of variables declaration//GEN-END:variables

}
