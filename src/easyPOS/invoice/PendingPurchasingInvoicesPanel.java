
package easyPOS.invoice;

import appDataModels.APIHeaderData;
import appDataModels.PurchaseInvoiceModel;
import control.RuntimeDataManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import serverResponseDataModel.CommonResponse;
import tableModels.PendingInvoicesTbl;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class PendingPurchasingInvoicesPanel extends javax.swing.JPanel {

    private List<PurchaseInvoiceModel> pendingInvoices;
    private JPanel thisPanel;
    private InvoicePanelActions invoicePanelActions;
    private boolean isNewInvoice;
    /**
     * Creates new form PendingInvoicesPanel
     */
    public PendingPurchasingInvoicesPanel() {
        initComponents();
        
        this.thisPanel = this;
    }
    
    public void showPanel(InvoicePanelActions invoicePanelActions){
        this.invoicePanelActions = invoicePanelActions;
        loadPendingInvoicesAction();
        this.isNewInvoice = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePendingInvoices = new javax.swing.JTable();
        jButtonAddNewPurchaseInvoice = new javax.swing.JButton();

        jTablePendingInvoices.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTablePendingInvoices.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTablePendingInvoices);

        jButtonAddNewPurchaseInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus_icon.png"))); // NOI18N
        jButtonAddNewPurchaseInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNewPurchaseInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAddNewPurchaseInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAddNewPurchaseInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddNewPurchaseInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNewPurchaseInvoiceActionPerformed
        // Add new invoice
        this.isNewInvoice = true;
        invoicePanelActions.addNewActionPerformed();
    }//GEN-LAST:event_jButtonAddNewPurchaseInvoiceActionPerformed

    private void loadPendingInvoicesAction() {
        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);
        
        pendingInvoices = new ArrayList<>();

        SwingWorker<CommonResponse, Void> worker;
        worker = new SwingWorker<CommonResponse, Void>() {
            
            
            @Override
            protected CommonResponse doInBackground() {
                loader.start(); // show loader
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).getPendingPurchasingInvoices();
                
                if (commonResponse.getAPIResponse().isSuccess()) {
                    List<serverDataModels.PurchaseInvoice> purchasingInvs = new ArrayList<>((ArrayList<serverDataModels.PurchaseInvoice>) commonResponse.getData());

                    for (serverDataModels.PurchaseInvoice purInv : purchasingInvs) {
                        pendingInvoices.add(new PurchaseInvoiceModel(purInv));
                    }
                }
                return commonResponse;
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                
                //reset  table
                loadPendingInvicesTable(pendingInvoices);
                
                CommonResponse commonResponse;
                try {
                    commonResponse = get();
                    if (!commonResponse.getAPIResponse().isSuccess()) {
                        uiUtil.EasyPOSMessageDialog.showErrorMessageDialog(thisPanel, commonResponse.getAPIResponse());
                        invoicePanelActions.dataloadingFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PendingPurchasingInvoicesPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(PendingPurchasingInvoicesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    private void loadPendingInvicesTable(List<PurchaseInvoiceModel> list){
        PendingInvoicesTbl ctt = new PendingInvoicesTbl(list);
        jTablePendingInvoices.setModel(ctt);        
        jTablePendingInvoices.setRowHeight(35);
    }
    
    public PurchaseInvoiceModel getSelectedRecord()
    {
        int selectedRowId = jTablePendingInvoices.getSelectedRow();
        
        if (pendingInvoices != null 
                && !pendingInvoices.isEmpty() 
                && selectedRowId >= 0 
                && pendingInvoices.size() > selectedRowId 
                && !this.isNewInvoice) {
            return pendingInvoices.get(selectedRowId);
        }
        return null;
    }    
    
    boolean isNewInvoiceRequest() {
        return this.isNewInvoice;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddNewPurchaseInvoice;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePendingInvoices;
    // End of variables declaration//GEN-END:variables

}
