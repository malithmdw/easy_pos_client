
package garbage;

import java.awt.event.KeyEvent;

/**
 *
 * @author malit
 */
public class InventoryInvoiceForm extends javax.swing.JPanel {

    /**
     * Creates new form StockItemForm
     */
    public InventoryInvoiceForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel42 = new javax.swing.JLabel();
        jTextFieldSuppDate = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jTextFieldSuppInvNo = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jTextFieldSuppTot = new javax.swing.JTextField();
        jTextFieldSuppDiss = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jTextFieldSuppNetTot = new javax.swing.JTextField();
        jCheckBoxSuppAlert = new javax.swing.JCheckBox();
        jTextFieldSuppPaid = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jTextFieldSuppBal = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jTextFieldSuppDueToPay = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jTextFieldSuppAlertDate = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButtonEditSupply2 = new javax.swing.JButton();
        jButtonDeleteSupply2 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jComboBoxStokeAdd = new javax.swing.JComboBox();

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Date:");

        jTextFieldSuppDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppDateKeyPressed(evt);
            }
        });

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel68.setText("Invoice No:");

        jTextFieldSuppInvNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppInvNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppInvNoKeyPressed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Supplier:");

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel71.setText("Total:");

        jTextFieldSuppTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldSuppTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppTotKeyPressed(evt);
            }
        });

        jTextFieldSuppDiss.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppDiss.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldSuppDiss.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppDissKeyPressed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel69.setText("Discount:");

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel70.setText("Net Total:");

        jTextFieldSuppNetTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppNetTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldSuppNetTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppNetTotKeyPressed(evt);
            }
        });

        jCheckBoxSuppAlert.setText("Set Alert");
        jCheckBoxSuppAlert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSuppAlertActionPerformed(evt);
            }
        });
        jCheckBoxSuppAlert.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBoxSuppAlertKeyPressed(evt);
            }
        });

        jTextFieldSuppPaid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldSuppPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSuppPaidActionPerformed(evt);
            }
        });
        jTextFieldSuppPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppPaidKeyPressed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel72.setText("Paid:");

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel73.setText("Balance:");

        jTextFieldSuppBal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppBal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldSuppBal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppBalKeyPressed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel74.setText("Date -due to pay:");

        jTextFieldSuppDueToPay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppDueToPay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppDueToPayKeyPressed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel75.setText("Alert Date:");

        jTextFieldSuppAlertDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldSuppAlertDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppAlertDateKeyPressed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton15.setText("Submit");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButtonEditSupply2.setBackground(new java.awt.Color(255, 153, 102));
        jButtonEditSupply2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonEditSupply2.setText("Save Changes");
        jButtonEditSupply2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditSupply2ActionPerformed(evt);
            }
        });

        jButtonDeleteSupply2.setBackground(new java.awt.Color(255, 0, 0));
        jButtonDeleteSupply2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDeleteSupply2.setText("Delete");
        jButtonDeleteSupply2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteSupply2ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton16.setText("Cancel");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jComboBoxStokeAdd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxStokeAdd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));
        jComboBoxStokeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStokeAddActionPerformed(evt);
            }
        });
        jComboBoxStokeAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxStokeAddKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldSuppDate)
                            .addComponent(jTextFieldSuppTot, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldSuppPaid, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBoxSuppAlert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .addComponent(jTextFieldSuppNetTot, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldSuppDiss)
                            .addComponent(jTextFieldSuppAlertDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldSuppDueToPay)
                            .addComponent(jTextFieldSuppBal)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBoxStokeAdd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldSuppInvNo, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(177, 177, 177))))
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEditSupply2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDeleteSupply2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppInvNo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxStokeAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppTot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppDiss, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppNetTot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxSuppAlert)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSuppPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppBal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSuppDueToPay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSuppAlertDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton15)
                .addGap(4, 4, 4)
                .addComponent(jButtonEditSupply2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDeleteSupply2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16)
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSuppDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppDateKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppInvNo.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppInvNo.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppDateKeyPressed

    private void jTextFieldSuppInvNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppInvNoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppDate.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
//            jTextFieldSuppCode.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            jTextFieldSuppCode.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppInvNoKeyPressed

    private void jTextFieldSuppTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppTotKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
//            jTextFieldSuppName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppDiss.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppDiss.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppTotKeyPressed

    private void jTextFieldSuppDissKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppDissKeyPressed
        try{
            double total=Double.parseDouble(jTextFieldSuppTot.getText());
            double dis=Double.parseDouble(jTextFieldSuppDiss.getText());
            double netTotal=total-dis;

            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                jTextFieldSuppNetTot.setText(new Double(netTotal).toString());
                jTextFieldSuppNetTot.requestFocus();
            }
        }catch(Exception e){

        }

        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppTot.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppNetTot.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppDissKeyPressed

    private void jTextFieldSuppNetTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppNetTotKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppDiss.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jCheckBoxSuppAlert.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jCheckBoxSuppAlert.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppNetTotKeyPressed

    private void jCheckBoxSuppAlertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSuppAlertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxSuppAlertActionPerformed

    private void jCheckBoxSuppAlertKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBoxSuppAlertKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppNetTot.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppPaid.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jCheckBoxSuppAlert.setSelected(true);
            jTextFieldSuppPaid.requestFocus();
        }
    }//GEN-LAST:event_jCheckBoxSuppAlertKeyPressed

    private void jTextFieldSuppPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSuppPaidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSuppPaidActionPerformed

    private void jTextFieldSuppPaidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppPaidKeyPressed
        try{
            double netTotal=Double.parseDouble(jTextFieldSuppNetTot.getText());
            double paid=Double.parseDouble(jTextFieldSuppPaid.getText());
            double balance=netTotal-paid;
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                jTextFieldSuppBal.setText(new Double(balance).toString());
                jTextFieldSuppBal.requestFocus();
            }
        }catch(Exception e){

        }
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jCheckBoxSuppAlert.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppBal.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppPaidKeyPressed

    private void jTextFieldSuppBalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppBalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppPaid.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppDueToPay.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppDueToPay.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppBalKeyPressed

    private void jTextFieldSuppDueToPayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppDueToPayKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppBal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppAlertDate.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppAlertDate.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppDueToPayKeyPressed

    private void jTextFieldSuppAlertDateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppAlertDateKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppDueToPay.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldSuppAlertDateKeyPressed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        boolean permission=true;
//        if(jTextFieldSuppNetTot.getText().equals("") || jTextFieldSuppCode.getText().equals("") || jTextFieldSuppInvNo.getText().equals("")){
//            permission=false;
//            JOptionPane.showMessageDialog(this, "Fill the required fields ! !");
//        }else{
//        }
//        boolean result1=supp.checkSupplierExist(jTextFieldSuppCode.getText());
//        if(jCheckBoxSuppAlert.isSelected()){
//            if(jTextFieldSuppAlertDate.getText().equals("")){
//                permission=false;
//                JOptionPane.showMessageDialog(rootPane, "Set the Alert date !");
//            }
//        }else{
//        }
//        if(result1==false ){
//            permission=false;
//            JOptionPane.showMessageDialog(rootPane, "Supplieer code does not exist !");
//        }else{
//        }
//
//        if(permission==true ){
//            SupplyVariables suppVar=new SupplyVariables();
//            try{
//                suppVar.setDate(dateFormatForDB(jTextFieldSuppDate.getText()));
//                suppVar.setSupplier_code(jTextFieldSuppCode.getText());
//
//                suppVar.setInvoice_no(jTextFieldSuppInvNo.getText());
//                suppVar.setTotal(Double.parseDouble(jTextFieldSuppTot.getText()));
//                suppVar.setDiscount(Double.parseDouble(jTextFieldSuppDiss.getText()));
//                suppVar.setNet_total(Double.parseDouble(jTextFieldSuppNetTot.getText()));
//
//                if(jCheckBoxSuppAlert.isSelected()){
//                    suppVar.setSet_alert(1);
//                }else{
//                    suppVar.setSet_alert(0);
//                }
//                if(jTextFieldSuppPaid.getText().equals("")){
//                    suppVar.setPaid(Double.parseDouble(jTextFieldSuppNetTot.getText()));
//                }else{
//                    suppVar.setPaid(Double.parseDouble(jTextFieldSuppPaid.getText()));
//                }
//                if(jTextFieldSuppBal.getText().equals("")){
//                    suppVar.setBalance(0.00);
//                }else{
//                    suppVar.setBalance(Double.parseDouble(jTextFieldSuppBal.getText()));
//                }
//                suppVar.setDue_to_pay(jTextFieldSuppDueToPay.getText());
//                suppVar.setAlert_date(jTextFieldSuppAlertDate.getText());
//
//                suppVar.setEditBy(currentUser);
//                suppVar.setInputMethod("manual");
//
//                boolean result2=supp.addSupply(suppVar);
//                if(result2==true){
//                    JOptionPane.showMessageDialog(rootPane, "Successfully inserted !");
//                    clearFieldsSupply();
//                    jButtonEditSupply2.setVisible(false);
//                    jButtonDeleteSupply2.setVisible(false);
//                    jButton15.setVisible(true);
//                    jTextFieldSuppDate.setText(jTextFieldDate1.getText());
//                    jTextFieldSuppInvNo.setEditable(true);
//
//                    suppliesLoadTableForSuppliers(0,"","","");
//                }else{
//                    JOptionPane.showMessageDialog(rootPane, "Error occur while storing data !");
//                }
//
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(rootPane, "Invalid input !");
//            }
//
//        }

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButtonEditSupply2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditSupply2ActionPerformed
//        boolean permission=true;
//        if(jTextFieldSuppNetTot.getText().equals("") || jTextFieldSuppCode.getText().equals("") || jTextFieldSuppInvNo.getText().equals("")){
//            permission=false;
//            JOptionPane.showMessageDialog(rootPane, "Fill the required fields ! !");
//        }else{
//        }
//        boolean result1=supp.checkSupplierExist(jTextFieldSuppCode.getText());
//        if(jCheckBoxSuppAlert.isSelected()){
//            if(jTextFieldSuppAlertDate.getText().equals("")){
//                permission=false;
//                JOptionPane.showMessageDialog(rootPane, "Set the Alert date !");
//            }
//        }else{
//        }
//        if(result1==false ){
//            permission=false;
//            JOptionPane.showMessageDialog(rootPane, "Supplieer code does not exist !");
//        }else{
//        }
//
//        if(permission==true ){
//            SupplyVariables suppVar=new SupplyVariables();
//            try{
//                suppVar.setDate(dateFormatForDB(jTextFieldSuppDate.getText()));
//                suppVar.setSupplier_code(jTextFieldSuppCode.getText());
//
//                suppVar.setInvoice_no(jTextFieldSuppInvNo.getText());
//                suppVar.setTotal(Double.parseDouble(jTextFieldSuppTot.getText()));
//                suppVar.setDiscount(Double.parseDouble(jTextFieldSuppDiss.getText()));
//                suppVar.setNet_total(Double.parseDouble(jTextFieldSuppNetTot.getText()));
//                if(jCheckBoxSuppAlert.isSelected()){
//                    suppVar.setSet_alert(1);
//                }else{
//                    suppVar.setSet_alert(0);
//                }
//                suppVar.setPaid(Double.parseDouble(jTextFieldSuppPaid.getText()));
//                if(jTextFieldSuppPaid.getText().equals("")){
//                    suppVar.setPaid(Double.parseDouble(jTextFieldSuppNetTot.getText()));
//                }
//                suppVar.setBalance(Double.parseDouble(jTextFieldSuppBal.getText()));
//                if(jTextFieldSuppBal.getText().equals("")){
//                    suppVar.setBalance(0.00);
//                }
//                suppVar.setDue_to_pay(jTextFieldSuppDueToPay.getText());
//                suppVar.setAlert_date(jTextFieldSuppAlertDate.getText());
//                suppVar.setEditBy(currentUser);
//                suppVar.setInputMethod("Edited");
//
//                boolean result2=supp.editSupply(suppVar);
//                if(result2==true){
//                    JOptionPane.showMessageDialog(rootPane, "Successfully updated !");
//                    clearFieldsSupply();
//                    jButtonEditSupply2.setVisible(false);
//                    jButtonDeleteSupply2.setVisible(false);
//                    jButton15.setVisible(true);
//                    jTextFieldSuppDate.setText(jTextFieldDate1.getText());
//                    jTextFieldSuppInvNo.setEditable(true);
//
//                    suppliesLoadTableForSuppliers(0,"","","");
//                }else{
//                    JOptionPane.showMessageDialog(rootPane, "Error occur while updating data !");
//                }
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(rootPane, "Invalid input !");
//            }
//        }
    }//GEN-LAST:event_jButtonEditSupply2ActionPerformed

    private void jButtonDeleteSupply2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteSupply2ActionPerformed
//        int ans=JOptionPane.showConfirmDialog(rootPane, "Are you sure?\n Do you want to permanently delete this all data ?");
//        if(ans==JOptionPane.YES_OPTION){
//            boolean res=supp.deleteSupply(jTextFieldSuppInvNo.getText());
//            if(res==true){
//                JOptionPane.showMessageDialog(rootPane, "Successfully deleted !");
//                suppliesLoadTableForSuppliers(0,"","","");
//                clearFieldsSupply();
//                jButtonEditSupply2.setVisible(false);
//                jButtonDeleteSupply2.setVisible(false);
//                jButton15.setVisible(true);
//                jTextFieldSuppDate.setText(jTextFieldDate1.getText());
//                jTextFieldSuppInvNo.setEditable(true);
//            }else{
//                JOptionPane.showMessageDialog(rootPane, "Error occur while deleting !");
//            }
//        }else{
//            clearFieldsSupply();
//            jButtonEditSupply2.setVisible(false);
//            jButtonDeleteSupply2.setVisible(false);
//            jButton15.setVisible(true);
//            jTextFieldSuppDate.setText(jTextFieldDate1.getText());
//            jTextFieldSuppInvNo.setEditable(true);
//        }
    }//GEN-LAST:event_jButtonDeleteSupply2ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
//        clearFieldsSupply();
//        jButtonEditSupply2.setVisible(false);
//        jButtonDeleteSupply2.setVisible(false);
//        jButton15.setVisible(true);
//        jTextFieldSuppDate.setText(jTextFieldDate1.getText());
//        jTextFieldSuppInvNo.setEditable(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jComboBoxStokeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStokeAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAddActionPerformed

    private void jComboBoxStokeAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxStokeAddKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_LEFT){
//            jTextFieldAddToStSubName.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
//            //            jTextFieldAddToStTotPaid.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            //            jTextFieldAddToStTotPaid.requestFocus();
//        }
    }//GEN-LAST:event_jComboBoxStokeAddKeyPressed

    void addNewStock(){
//        if(jTextFieldAddToStItCode.getText().equals("")||
//            jTextFieldAddToStQty.getText().equals("") || jTextFieldAddToStPurPrice.getText().equals("") ||
//            jTextFieldAddToStSellrPrice.getText().equals("")){
//            JOptionPane.showMessageDialog(null, "Fill the required fields !\n ");
//
//        }else{
//            StockItemDataModel sv=new StockItemDataModel();
//            try{
//                sv.setItem_code(jTextFieldAddToStItCode.getText());
//                sv.setItem_name(jTextFieldAddToStName.getText());
//                sv.setSub_name(jTextFieldAddToStSubName.getText());
//                sv.setCategory(jComboBoxStokeAdd.getSelectedItem().toString());
//                sv.setQty(Integer.parseInt(jTextFieldAddToStQty.getText()));
//                sv.setPurchase_price(Double.parseDouble(jTextFieldAddToStPurPrice.getText()));
//                sv.setSelling_price(Double.parseDouble(jTextFieldAddToStSellrPrice.getText()));
//                sv.setDiscount(Double.parseDouble(jTextFieldAddToStDis.getText()));
//                sv.setSupp_code1(jTextFieldAddToStSupCode.getText());
//                sv.setSupp_code2(jTextFieldAddToStSupCode2.getText());
//                sv.setComment(jTextFieldAddToStComent.getText());
//                sv.setLast_edit_date(todayDATE);
//                sv.setLast_editor(currentUser);
//                sv.setExp_date(dateFormatForDB(jTextFieldExpDte.getText()));
//
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(null, "Illegal input.\n check and try again !\n ");
//            }
//
//            boolean checkItmCode=stock.checkItemCodeExist(jTextFieldAddToStItCode.getText());
//            if(checkItmCode==true){
//                boolean resu=stock.addStoke(sv,jTextFieldAddToStItCode.getText());
//                if(resu==true){
//                    if(startAutoCreateInv==1){
//                        int qt=Integer.parseInt(jTextFieldAddToStQty.getText());
//                        double unitPerPri=Double.parseDouble(jTextFieldAddToStPurPrice.getText());
//                        boolean res=supp.editTemp(qt*unitPerPri, "0.00", "0.00", currentUser);
//                        if(res==false){
//                            JOptionPane.showMessageDialog(null, "Auto create invoice process is not working!\n ");
//                        }
//                    }                    
//                    clearFieldsStokeAdd();
//                    jTextFieldAddToStItCode.requestFocus();
//                    loadStokeTbleFull(0,"","");//Here should load table
//                }else{
//                    JOptionPane.showMessageDialog(null, "Can not do your process !\n ");
//                }
//            }else{
//                JOptionPane.showMessageDialog(null, "Item code does not exist in stock.\n Enter as a new item ?\n ");
//            }
//        }
    }
    void addNewItemToStock(){
//        if(jTextFieldAddToStItCode.getText().equals("")|| jTextFieldAddToStName.getText().equals("")||
//            jTextFieldAddToStQty.getText().equals("") || jTextFieldAddToStPurPrice.getText().equals("") ||
//            jTextFieldAddToStSellrPrice.getText().equals("")){
//            JOptionPane.showMessageDialog(null, "Fill the required fields !\n ");
//
//        }else{
//            StockItemDataModel sv=new StockItemDataModel();
//            try{
//                sv.setItem_code(jTextFieldAddToStItCode.getText());
//                sv.setItem_name(jTextFieldAddToStName.getText());
//                sv.setSub_name(jTextFieldAddToStSubName.getText());
//                sv.setCategory(jComboBoxStokeAdd.getSelectedItem().toString());
//                sv.setQty(Integer.parseInt(jTextFieldAddToStQty.getText()));
//                sv.setPurchase_price(Double.parseDouble(jTextFieldAddToStPurPrice.getText()));
//                sv.setSelling_price(Double.parseDouble(jTextFieldAddToStSellrPrice.getText()));
//                sv.setDiscount(Double.parseDouble(jTextFieldAddToStDis.getText()));
//                sv.setSupp_code1(jTextFieldAddToStSupCode.getText());
//                sv.setSupp_code2(jTextFieldAddToStSupCode2.getText());
//                sv.setComment(jTextFieldAddToStComent.getText());
//                sv.setMinimumQtyForAlert(Integer.parseInt(jTextFieldMinimumQtyForAlert.getText()));
//                sv.setLast_edit_date(jTextFieldDate2.getText());
//                sv.setLast_editor(currentUser);
//                sv.setExp_date(dateFormatForDB(jTextFieldExpDte.getText()));
//                
//                boolean checkItmCode=stock.checkItemCodeExist(jTextFieldAddToStItCode.getText());
//                if(checkItmCode==true){
//                    JOptionPane.showMessageDialog(null, "Item code already exist.\n change the Item Code !\n ");
//                }else{
//                    boolean resu=stock.addNewItem(sv);
//                    if(resu==true){
//                        im.addMovementItem(jTextFieldAddToStItCode.getText(),dateFormatForDB(jTextFieldDate2.getText()));//add this new item to movement table
//                        if(startAutoCreateInv==1){
//                            int qt=Integer.parseInt(jTextFieldAddToStQty.getText());
//                            double unitPerPri=Double.parseDouble(jTextFieldAddToStPurPrice.getText());
//                            boolean res=supp.editTemp(qt*unitPerPri, "0.00", "0.00", currentUser);
//                            if(res==false){
//                                JOptionPane.showMessageDialog(null, "Auto create invoice process is not working!\n ");
//                            }
//                        }
//                        clearFieldsStokeAdd();//clear fields
//                        jTextFieldAddToStItCode.requestFocus();//move curser
//                        loadStokeTbleFull(0,"","");//Here should load table
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Can't do your process !\n ");
//                    }
//                }
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(null, "Illegal input.\n check and try again !\n ");
//            }            
//        }
    }
     
    void clearFieldsStokeAdd(){
//        jTextFieldAddToStItCode.setText("");
//        jTextFieldAddToStName.setText("");
//        jTextFieldAddToStSubName.setText("");
//        jTextFieldAddToStQty.setText("");
////        jTextFieldAddToStPurPrice.setText("");
//        jTextFieldAddToStSellrPrice.setText("");
//        jTextFieldAddToStDis.setText("0");
////        jTextFieldAddToStSupCode.setText("");
////        jTextFieldAddToStSupCode2.setText("");
//        jTextFieldAddToStComent.setText("");
//        jTextFieldMinimumQtyForAlert.setText("0");
//        jTextFieldExpDte.setText("");
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButtonDeleteSupply2;
    private javax.swing.JButton jButtonEditSupply2;
    private javax.swing.JCheckBox jCheckBoxSuppAlert;
    private javax.swing.JComboBox jComboBoxStokeAdd;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JTextField jTextFieldSuppAlertDate;
    private javax.swing.JTextField jTextFieldSuppBal;
    private javax.swing.JTextField jTextFieldSuppDate;
    private javax.swing.JTextField jTextFieldSuppDiss;
    private javax.swing.JTextField jTextFieldSuppDueToPay;
    private javax.swing.JTextField jTextFieldSuppInvNo;
    private javax.swing.JTextField jTextFieldSuppNetTot;
    private javax.swing.JTextField jTextFieldSuppPaid;
    private javax.swing.JTextField jTextFieldSuppTot;
    // End of variables declaration//GEN-END:variables
}
