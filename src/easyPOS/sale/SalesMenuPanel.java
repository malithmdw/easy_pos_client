package easyPOS.sale;

import control.EventManager;
import control.SalesMenuItemClickListener;

/**
 *
 * @author malit
 */
public class SalesMenuPanel extends javax.swing.JPanel {

    /**
     * Creates new form SalesMenuPanel
     */
    public SalesMenuPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchStockButton = new javax.swing.JButton();
        rePrintButton = new javax.swing.JButton();
        saveBillButton = new javax.swing.JButton();
        savedBillButton = new javax.swing.JButton();
        calButton = new javax.swing.JButton();
        noteButton = new javax.swing.JButton();
        saleReturnButton = new javax.swing.JButton();

        searchStockButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        searchStockButton.setToolTipText("Search Stock");
        searchStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStockButtonActionPerformed(evt);
            }
        });

        rePrintButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        rePrintButton.setToolTipText("Re Print Last Bill");
        rePrintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rePrintButtonActionPerformed(evt);
            }
        });

        saveBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/id_card.png"))); // NOI18N
        saveBillButton.setToolTipText("Save Bill");
        saveBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBillButtonActionPerformed(evt);
            }
        });

        savedBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/id_card.png"))); // NOI18N
        savedBillButton.setToolTipText("Saved Bills");
        savedBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savedBillButtonActionPerformed(evt);
            }
        });

        calButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cal.png"))); // NOI18N
        calButton.setToolTipText("Calculator");

        noteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/note.png"))); // NOI18N
        noteButton.setToolTipText("Notes");
        noteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteButtonActionPerformed(evt);
            }
        });

        saleReturnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        saleReturnButton.setToolTipText("Sale Return");
        saleReturnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saleReturnButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saleReturnButton)
                .addGap(13, 13, 13)
                .addComponent(searchStockButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(rePrintButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(saveBillButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(savedBillButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(calButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(noteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rePrintButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveBillButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(savedBillButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saleReturnButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchStockButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStockButtonActionPerformed
        EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.SEARCH_STOCK);
    }//GEN-LAST:event_searchStockButtonActionPerformed

    private void rePrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rePrintButtonActionPerformed
        EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.RE_PRINT);
    }//GEN-LAST:event_rePrintButtonActionPerformed

    private void saleReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saleReturnButtonActionPerformed
        EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.SALE_RETURN);
    }//GEN-LAST:event_saleReturnButtonActionPerformed

    private void savedBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savedBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_savedBillButtonActionPerformed

    private void saveBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveBillButtonActionPerformed

    private void noteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calButton;
    private javax.swing.JButton noteButton;
    private javax.swing.JButton rePrintButton;
    private javax.swing.JButton saleReturnButton;
    private javax.swing.JButton saveBillButton;
    private javax.swing.JButton savedBillButton;
    private javax.swing.JButton searchStockButton;
    // End of variables declaration//GEN-END:variables
}
