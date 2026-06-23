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
        numberPadButton = new javax.swing.JButton();

        searchStockButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        searchStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStockButtonActionPerformed(evt);
            }
        });

        rePrintButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        rePrintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rePrintButtonActionPerformed(evt);
            }
        });

        saveBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/id_card.png"))); // NOI18N

        savedBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/id_card.png"))); // NOI18N
        savedBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savedBillButtonActionPerformed(evt);
            }
        });

        calButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cal.png"))); // NOI18N

        noteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N

        numberPadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cal.png"))); // NOI18N
        numberPadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberPadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numberPadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchStockButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rePrintButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveBillButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(savedBillButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(calButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(noteButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rePrintButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBillButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(savedBillButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numberPadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(searchStockButton)
                    .addComponent(noteButton)
                    .addComponent(calButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStockButtonActionPerformed
        EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.SEARCH_STOCK);
    }//GEN-LAST:event_searchStockButtonActionPerformed

    private void rePrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rePrintButtonActionPerformed
        EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.RE_PRINT);
    }//GEN-LAST:event_rePrintButtonActionPerformed

    private void numberPadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberPadButtonActionPerformed
        EventManager.getInstance().notifySalesMenuItemClicked(SalesMenuItemClickListener.SalesMenuItem.NUMBER_PAD);
    }//GEN-LAST:event_numberPadButtonActionPerformed

    private void savedBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savedBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_savedBillButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calButton;
    private javax.swing.JButton noteButton;
    private javax.swing.JButton numberPadButton;
    private javax.swing.JButton rePrintButton;
    private javax.swing.JButton saveBillButton;
    private javax.swing.JButton savedBillButton;
    private javax.swing.JButton searchStockButton;
    // End of variables declaration//GEN-END:variables
}
