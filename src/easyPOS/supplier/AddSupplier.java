package easyPOS.supplier;

import control.ApplicationDataManager;
import control.EasyPosLogger;
import dataModels.SupplierDataModel;
import dbOperations.SuppliesDBOperation;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import easyPOS.localization.ApplicationMessages;
import javax.swing.JOptionPane;
import uiUtil.EasyPOSMessageDialog;

public class AddSupplier extends javax.swing.JFrame implements control.LanguageChangeListener {

    public AddSupplier() {
        initComponents();
        switchLanguage();
        setIcon();
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    void setIcon(){
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logoes/logo.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldSuppCode = new javax.swing.JTextField();
        jTextFieldSuppAddress = new javax.swing.JTextField();
        jTextFieldSuppAcc = new javax.swing.JTextField();
        jTextFieldSuppName = new javax.swing.JTextField();
        jTextFieldSuppComp = new javax.swing.JTextField();
        jTextFieldSuppBank = new javax.swing.JTextField();
        jTextFieldSuppTP = new javax.swing.JTextField();
        jButtonAddSupp = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldNIC = new javax.swing.JTextField();
        jButtonClear = new javax.swing.JButton();
        jButtonCancel1 = new javax.swing.JButton();
        jButtonSaveChange = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jTextFieldSupplierTP1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supplier");
        setResizable(false);

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Supplier Code");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Supplier Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Address");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Account No");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Company");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Bank");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Phone No");

        jTextFieldSuppCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppCodeKeyPressed(evt);
            }
        });

        jTextFieldSuppAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppAddressKeyPressed(evt);
            }
        });

        jTextFieldSuppAcc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppAcc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppAccKeyPressed(evt);
            }
        });

        jTextFieldSuppName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppNameKeyPressed(evt);
            }
        });

        jTextFieldSuppComp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppCompKeyPressed(evt);
            }
        });

        jTextFieldSuppBank.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppBank.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppBankKeyPressed(evt);
            }
        });

        jTextFieldSuppTP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSuppTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSuppTPKeyPressed(evt);
            }
        });

        jButtonAddSupp.setText("Submit");
        jButtonAddSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddSuppActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("NIC No");

        jTextFieldNIC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldNIC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldNICKeyPressed(evt);
            }
        });

        jButtonClear.setText("Clear fields");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonCancel1.setText("Cancel");
        jButtonCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancel1ActionPerformed(evt);
            }
        });

        jButtonSaveChange.setText("Save Changes");
        jButtonSaveChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveChangeActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jTextFieldSupplierTP1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldSupplierTP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSupplierTP1KeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Phone No");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldSuppCode, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldSupplierTP1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                    .addComponent(jTextFieldSuppAddress)
                                    .addComponent(jTextFieldSuppComp)
                                    .addComponent(jTextFieldNIC)
                                    .addComponent(jTextFieldSuppAcc)
                                    .addComponent(jTextFieldSuppBank)
                                    .addComponent(jTextFieldSuppName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldSuppTP)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonSaveChange, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(jButtonCancel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAddSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldSuppCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSuppName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSuppAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSupplierTP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldNIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppAcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSuppTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClear)
                    .addComponent(jButtonCancel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSaveChange)
                    .addComponent(jButtonAddSupp)
                    .addComponent(jButtonDelete))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    SupplierDataModel supVar=new SupplierDataModel();
    SuppliesDBOperation s=new SuppliesDBOperation();
    
    SupplierPanel os=new SupplierPanel();
    
    private void clearFields() {
        jTextFieldSuppCode.setText("");
        jTextFieldSuppName.setText("");
        jTextFieldSuppAddress.setText("");
        jTextFieldSuppComp.setText("");
        jTextFieldNIC.setText("");
        jTextFieldSuppAcc.setText("");
        jTextFieldSuppBank.setText("");
        jTextFieldSuppTP.setText("");
        jTextFieldSupplierTP1.setText("");
    }
    
    
    private void jButtonAddSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddSuppActionPerformed
        //add data to DB
        if(jTextFieldSuppCode.getText().equals("") || jTextFieldSuppName.getText().equals("")){
            EasyPOSMessageDialog.showLocalizedWarning(rootPane, ApplicationMessages.VALIDATION_FILL_REQUIRED_FIELDS);
        }else{
            if(s.checkSupplierExist(jTextFieldSuppCode.getText())==true){
               EasyPOSMessageDialog.showLocalizedWarning(rootPane, ApplicationMessages.VALIDATION_SUPPLIER_DUPLICATE);
            }else{
                supVar.setSupplierCode(Integer.parseInt(jTextFieldSuppCode.getText()));
                supVar.setSupplierName(jTextFieldSuppName.getText());
                supVar.setSupplierAddress(jTextFieldSuppAddress.getText());
                supVar.setCompany(jTextFieldSuppComp.getText());
                supVar.setNIC(jTextFieldNIC.getText());
                supVar.setAccNo(jTextFieldSuppAcc.getText());
                supVar.setBank(jTextFieldSuppBank.getText());
                supVar.setPhoneNo(jTextFieldSuppTP.getText());
                supVar.setPhone_no1(jTextFieldSupplierTP1.getText());

                boolean result=s.addSupplier(supVar);
                if(result==true){
                    EasyPOSMessageDialog.showLocalizedInfo(rootPane, ApplicationMessages.INFO_INSERT_SUCCESS);
                    clearFields();
                    os.loadSSupplierTble(0,"","");  
                }else{
                     EasyPOSMessageDialog.showLocalizedError(rootPane, ApplicationMessages.ERROR_INSERT_FAILED);
                }   
            }
        }
    }//GEN-LAST:event_jButtonAddSuppActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
        
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancel1ActionPerformed
        clearFields();
        this.dispose();
    }//GEN-LAST:event_jButtonCancel1ActionPerformed

    private void jButtonSaveChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveChangeActionPerformed
        //change data in DB
        if(jTextFieldSuppCode.getText().equals("") || jTextFieldSuppName.getText().equals("")){
            EasyPOSMessageDialog.showLocalizedWarning(rootPane, ApplicationMessages.VALIDATION_FILL_REQUIRED_FIELDS);
        }else{         
            supVar.setSupplierCode(Integer.parseInt(jTextFieldSuppCode.getText()));
            supVar.setSupplierName(jTextFieldSuppName.getText());
            supVar.setSupplierAddress(jTextFieldSuppAddress.getText());
            supVar.setCompany(jTextFieldSuppComp.getText());
            supVar.setNIC(jTextFieldNIC.getText());
            supVar.setAccNo(jTextFieldSuppAcc.getText());
            supVar.setBank(jTextFieldSuppBank.getText());
            supVar.setPhoneNo(jTextFieldSuppTP.getText());
            supVar.setPhone_no1(jTextFieldSupplierTP1.getText());
            
            
            boolean result=s.editSupplier(supVar,jTextFieldSuppCode.getText());
            if(result==true){
                EasyPOSMessageDialog.showLocalizedInfo(rootPane, ApplicationMessages.INFO_UPDATE_SUCCESS);
                clearFields();
                os.loadSSupplierTble(0,"",""); 
                this.dispose();
            }else{
                 EasyPOSMessageDialog.showLocalizedError(rootPane, ApplicationMessages.ERROR_UPDATE_FAILED);
            }
        }
        
        
    }//GEN-LAST:event_jButtonSaveChangeActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // delete data in DB
        int ans=JOptionPane.showConfirmDialog(rootPane, "Are you sure?\n Do you want to permanently delete this supplier's data ?");
        if(ans==JOptionPane.YES_OPTION){
            boolean result=s.deleteSupplier(jTextFieldSuppCode.getText());
            if(result==true){
                EasyPOSMessageDialog.showLocalizedInfo(rootPane, ApplicationMessages.INFO_DELETE_SUCCESS);
                os.loadSSupplierTble(0,"","");
                clearFields();
                this.dispose();
            }else{
                EasyPOSMessageDialog.showLocalizedError(rootPane, ApplicationMessages.ERROR_DELETE_FAILED);
            }
        }
        
        
    }//GEN-LAST:event_jButtonDeleteActionPerformed

 
    
    
        //****************key pressed actions(for UP,DOWVN and ENTER)**************
    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL && evt.getKeyCode()==KeyEvent.VK_S){
            //System.out.println("ok");            
        }
    }//GEN-LAST:event_jPanel1KeyPressed

    private void jTextFieldSuppCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppCodeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppName.requestFocus();
        }
        if(jTextFieldSuppCode.getText().length()>4){
            jLabel9.setText("max length of supplier code is 4");
            jLabel9.setForeground(Color.RED);

        }else{
            jLabel9.setText("");
        }
    }//GEN-LAST:event_jTextFieldSuppCodeKeyPressed

    private void jTextFieldSuppNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppNameKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppCode.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppAddress.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppAddress.requestFocus();
        }
        
        if(jTextFieldSuppName.getText().length()>32){
            jLabel9.setText("max length of supplier name is 32");
            jLabel9.setForeground(Color.RED);

        }else{
            jLabel9.setText("");
        }
    }//GEN-LAST:event_jTextFieldSuppNameKeyPressed

    private void jTextFieldSuppAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppAddressKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppComp.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppComp.requestFocus();
        }
        if(jTextFieldSuppAddress.getText().length()>50){
            jLabel9.setText("max length of supplier address is 50");
            jLabel9.setForeground(Color.RED);

        }else{
            jLabel9.setText("");
        }
    }//GEN-LAST:event_jTextFieldSuppAddressKeyPressed

    private void jTextFieldSuppCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppCompKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppAddress.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSupplierTP1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSupplierTP1.requestFocus();
        }else{
            if(jTextFieldSuppComp.getText().length()>20){
                jLabel9.setText("max length of company name is 20");
                jLabel9.setForeground(Color.RED);

            }else{
                jLabel9.setText("");
            }
        }
    }//GEN-LAST:event_jTextFieldSuppCompKeyPressed

    private void jTextFieldNICKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNICKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSupplierTP1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppAcc.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppAcc.requestFocus();
        }else{
            if(jTextFieldNIC.getText().length()>10){
                jLabel9.setText("error of NIC number");
                jLabel9.setForeground(Color.RED);

            }else{
                jLabel9.setText("");
            }
        }
    }//GEN-LAST:event_jTextFieldNICKeyPressed

    private void jTextFieldSuppAccKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppAccKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldNIC.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppBank.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppBank.requestFocus();
        }
        if(jTextFieldSuppAcc.getText().length()>20){
            jLabel9.setText("max length of account no is 20");
            jLabel9.setForeground(Color.RED);

        }else{
            jLabel9.setText("");
        }
    }//GEN-LAST:event_jTextFieldSuppAccKeyPressed

    private void jTextFieldSuppBankKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppBankKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppAcc.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldSuppTP.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldSuppTP.requestFocus();
        }
        if(jTextFieldSuppBank.getText().length()>20){
            jLabel9.setText("max length of bank name is 20");
            jLabel9.setForeground(Color.RED);

        }else{
            jLabel9.setText("");
        }
    }//GEN-LAST:event_jTextFieldSuppBankKeyPressed

    private void jTextFieldSuppTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSuppTPKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppBank.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        }else{
            if(jTextFieldSuppTP.getText().length()>=11){
                jLabel9.setText("this is not a phone number");
                jLabel9.setForeground(Color.RED);

            }else{
                jLabel9.setText("");
            }
        }
        
    }//GEN-LAST:event_jTextFieldSuppTPKeyPressed

    private void jTextFieldSupplierTP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSupplierTP1KeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldSuppComp.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldNIC.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldNIC.requestFocus();
        }else{
            if(jTextFieldSupplierTP1.getText().length()>11){
                jLabel9.setText("this is not a phone number");
                jLabel9.setForeground(Color.RED);

            }else{
                jLabel9.setText("");
            }
        }         
    }//GEN-LAST:event_jTextFieldSupplierTP1KeyPressed
    
    
    
    //methods for multi purpos.    (execute when the window set visible..)
    public void setFieldsForAddNewSupplier(SupplierDataModel suprv) {
        this.setTitle("View Supplier");
        jTextFieldSuppCode.setText(Integer.toString(suprv.getSupplierCode()));
        jTextFieldSuppName.setText(suprv.getSupplierName());
        jTextFieldSuppAddress.setText(suprv.getSupplierAddress());
        jTextFieldSuppComp.setText(suprv.getCompany());
        jTextFieldNIC.setText(suprv.getNIC());
        jTextFieldSuppAcc.setText(suprv.getAccNo());
        jTextFieldSuppBank.setText(suprv.getBank());
        jTextFieldSuppTP.setText(suprv.getPhoneNo());
        jTextFieldSupplierTP1.setText(suprv.getPhoneNo1());
        
        jTextFieldSuppCode.setEditable(false);
        jTextFieldSuppName.setEditable(false);
        jTextFieldSuppAddress.setEditable(false);
        jTextFieldSuppComp.setEditable(false);
        jTextFieldNIC.setEditable(false);
        jTextFieldSuppAcc.setEditable(false);
        jTextFieldSuppBank.setEditable(false);
        jTextFieldSuppTP.setEditable(false);
        jTextFieldSupplierTP1.setEditable(false);
        
        jButtonAddSupp.setVisible(false);
        jButtonClear.setVisible(false);
        jButtonSaveChange.setVisible(false);
        jButtonDelete.setVisible(false);
        jButtonCancel1.setVisible(true);
    }
    public void addSupplier() {
        this.setTitle("Add New Supplier");
        jButtonSaveChange.setVisible(false);
        jButtonDelete.setVisible(false); 
        jButtonAddSupp.setVisible(true);
        jButtonClear.setVisible(true);
        jButtonCancel1.setVisible(true);
    }
    public void editSupplier(SupplierDataModel suprv){
        this.setTitle("Edit Supplier");
        jButtonAddSupp.setVisible(false);
        jButtonClear.setVisible(false);
        jButtonDelete.setVisible(false);
        jButtonSaveChange.setVisible(true);
        jButtonCancel1.setVisible(true);
        
        jTextFieldSuppCode.setText(Integer.toString(suprv.getSupplierCode()));
        jTextFieldSuppName.setText(suprv.getSupplierName());
        jTextFieldSuppAddress.setText(suprv.getSupplierAddress());
        jTextFieldSuppComp.setText(suprv.getCompany());
        jTextFieldNIC.setText(suprv.getNIC());
        jTextFieldSuppAcc.setText(suprv.getAccNo());
        jTextFieldSuppBank.setText(suprv.getBank());
        jTextFieldSuppTP.setText(suprv.getPhoneNo());
        jTextFieldSupplierTP1.setText(suprv.getPhoneNo1());
        
        jTextFieldSuppCode.setEditable(false);
        
        
    }
    public void deleteSupplier(SupplierDataModel suprv){
        this.setTitle("Delete Supplier");
        jButtonAddSupp.setVisible(false);
        jButtonClear.setVisible(false);
        jButtonSaveChange.setVisible(false);
        jButtonDelete.setVisible(true);
        jButtonCancel1.setVisible(true);
        
        jTextFieldSuppCode.setText(Integer.toString(suprv.getSupplierCode()));
        jTextFieldSuppName.setText(suprv.getSupplierName());
        jTextFieldSuppAddress.setText(suprv.getSupplierAddress());
        jTextFieldSuppComp.setText(suprv.getCompany());
        jTextFieldNIC.setText(suprv.getNIC());
        jTextFieldSuppAcc.setText(suprv.getAccNo());
        jTextFieldSuppBank.setText(suprv.getBank());
        jTextFieldSuppTP.setText(suprv.getPhoneNo());
        jTextFieldSupplierTP1.setText(suprv.getPhoneNo1());
        
        jTextFieldSuppCode.setEditable(false);
        jTextFieldSuppName.setEditable(false);
        jTextFieldSuppAddress.setEditable(false);
        jTextFieldSuppComp.setEditable(false);
        jTextFieldNIC.setEditable(false);
        jTextFieldSuppAcc.setEditable(false);
        jTextFieldSuppBank.setEditable(false);
        jTextFieldSuppTP.setEditable(false);
        jTextFieldSupplierTP1.setEnabled(false);
        
        
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
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
            EasyPosLogger.getInstance().error("", ex);
        } catch (InstantiationException ex) {
            EasyPosLogger.getInstance().error("", ex);
        } catch (IllegalAccessException ex) {
            EasyPosLogger.getInstance().error("", ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            EasyPosLogger.getInstance().error("", ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddSupplier().setVisible(true);
            }
        });
    }

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/supplier/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            jLabel1.setFont(customFont);
            jLabel2.setFont(customFont);
            jLabel3.setFont(customFont);
            jLabel4.setFont(customFont);
            jLabel5.setFont(customFont);
            jLabel6.setFont(customFont);
            jLabel7.setFont(customFont);
            jLabel10.setFont(customFont);
            jLabel8.setFont(customFont);
            jButtonAddSupp.setFont(customFont);
            jButtonClear.setFont(customFont);
            jButtonCancel1.setFont(customFont);
            jButtonSaveChange.setFont(customFont);
            jButtonDelete.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }
        jLabel1.setText(resourceBundle.getString("AddSupplier.jLabel1.text"));
        jLabel2.setText(resourceBundle.getString("AddSupplier.jLabel2.text"));
        jLabel3.setText(resourceBundle.getString("AddSupplier.jLabel3.text"));
        jLabel4.setText(resourceBundle.getString("AddSupplier.jLabel4.text"));
        jLabel5.setText(resourceBundle.getString("AddSupplier.jLabel5.text"));
        jLabel6.setText(resourceBundle.getString("AddSupplier.jLabel6.text"));
        jLabel7.setText(resourceBundle.getString("AddSupplier.jLabel7.text"));
        jLabel10.setText(resourceBundle.getString("AddSupplier.jLabel10.text"));
        jLabel8.setText(resourceBundle.getString("AddSupplier.jLabel8.text"));
        jButtonAddSupp.setText(resourceBundle.getString("AddSupplier.jButtonAddSupp.text"));
        jButtonClear.setText(resourceBundle.getString("AddSupplier.jButtonClear.text"));
        jButtonCancel1.setText(resourceBundle.getString("AddSupplier.jButtonCancel1.text"));
        jButtonSaveChange.setText(resourceBundle.getString("AddSupplier.jButtonSaveChange.text"));
        jButtonDelete.setText(resourceBundle.getString("AddSupplier.jButtonDelete.text"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddSupp;
    private javax.swing.JButton jButtonCancel1;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonSaveChange;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldNIC;
    private javax.swing.JTextField jTextFieldSuppAcc;
    private javax.swing.JTextField jTextFieldSuppAddress;
    private javax.swing.JTextField jTextFieldSuppBank;
    private javax.swing.JTextField jTextFieldSuppCode;
    private javax.swing.JTextField jTextFieldSuppComp;
    private javax.swing.JTextField jTextFieldSuppName;
    private javax.swing.JTextField jTextFieldSuppTP;
    private javax.swing.JTextField jTextFieldSupplierTP1;
    // End of variables declaration//GEN-END:variables

    

    



   
}
