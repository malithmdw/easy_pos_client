package easyPOS.settings;

import appDataModels.UserAccountModel;
import control.ApplicationDataManager;
import dataModels.Language;
import dbOperations.LoginDBOperation;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import easyPOS.localization.ApplicationMessages;
import javax.swing.JOptionPane;
import uiUtil.EasyPOSMessageDialog;
import localDatabase.DatabaseManager;

/**
 *
 * @author malit
 */
public class SettingsPanel extends javax.swing.JPanel implements control.LanguageChangeListener {


    private LoginDBOperation logScr=new LoginDBOperation();
    private String currentUser="";

    /**
     * Creates new form SettingsPanel
     */
    public SettingsPanel() {
        initComponents();
        switchLanguage();
        loadInitialData();
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    
    @SuppressWarnings("unchecked")
    private void loadInitialData() {
        UserAccountModel userAccount = ApplicationDataManager.getInstance().getLoggedInUser();
        if (userAccount != null) {
            jTextFieldUserName.setText(userAccount.getUserName());
            jTextFieldUserCode.setText(userAccount.getUserCode());
            jTextFieldUserEmail.setText(userAccount.getEmail());
            jTextFieldUserPhone.setText(userAccount.getPassword());
        }

        Language[] languages = Language.values();
        jComboBox1.setModel(new DefaultComboBoxModel(languages));
        jComboBox2.setModel(new DefaultComboBoxModel(languages));
        jComboBox3.setModel(new DefaultComboBoxModel(languages));
        jComboBox4.setModel(new DefaultComboBoxModel(languages));
        jComboBox5.setModel(new DefaultComboBoxModel(languages));

        jComboBox1.setSelectedItem(ApplicationDataManager.getInstance().getApplicationLanguage());
        jComboBox2.setSelectedItem(ApplicationDataManager.getInstance().getReceiptBusinessDataLanguage());
        jComboBox3.setSelectedItem(ApplicationDataManager.getInstance().getReceiptContentLanguage());
        jComboBox4.setSelectedItem(ApplicationDataManager.getInstance().getReceiptItemLanguage());
        jComboBox5.setSelectedItem(ApplicationDataManager.getInstance().getReceiptFooterLanguage());

        jTextField18.setText(ApplicationDataManager.getInstance().getReceiptPrinterName());
        jTextField19.setText(ApplicationDataManager.getInstance().getLabelPrinterName());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jTextFieldUserCode = new javax.swing.JTextField();
        jPanelSettingsPwdChangeDelete = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jPasswordFieldChangePwd = new javax.swing.JPasswordField();
        jPasswordFieldChangePwd2 = new javax.swing.JPasswordField();
        jLabel66 = new javax.swing.JLabel();
        jPasswordFieldCurPwd = new javax.swing.JPasswordField();
        jButton41 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jTextFieldUserEmail = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jTextFieldUserPhone = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel18.setPreferredSize(new java.awt.Dimension(1034, 550));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("User Name :");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("Last Name:");

        jTextFieldUserName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldUserName.setEnabled(false);

        jTextFieldUserCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldUserCode.setEnabled(false);

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel62.setText("New Password:");

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel63.setText("User Name:");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel61.setText("Type password Again:");

        jTextField25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPasswordFieldChangePwd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPasswordFieldChangePwd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel66.setText("Current Password:");

        jPasswordFieldCurPwd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton41.setText("Cancel");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSettingsPwdChangeDeleteLayout = new javax.swing.GroupLayout(jPanelSettingsPwdChangeDelete);
        jPanelSettingsPwdChangeDelete.setLayout(jPanelSettingsPwdChangeDeleteLayout);
        jPanelSettingsPwdChangeDeleteLayout.setHorizontalGroup(
            jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsPwdChangeDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSettingsPwdChangeDeleteLayout.createSequentialGroup()
                        .addComponent(jButton41)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldCurPwd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldChangePwd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldChangePwd2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelSettingsPwdChangeDeleteLayout.setVerticalGroup(
            jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsPwdChangeDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldCurPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(jPasswordFieldChangePwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsPwdChangeDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jPasswordFieldChangePwd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel64.setText("User Name:");

        jTextFieldUserEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldUserEmail.setEnabled(false);

        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel65.setText("User Name:");

        jTextFieldUserPhone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldUserPhone.setEnabled(false);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextFieldUserEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel85))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldUserPhone))
                        .addComponent(jPanelSettingsPwdChangeDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(519, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(jTextFieldUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jTextFieldUserPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addComponent(jPanelSettingsPwdChangeDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("User Accounts", jPanel18);

        jLabel40.setText("Receipt Printer Name");

        jLabel42.setText("Label Printer Name");

        jLabel43.setText("Application Language");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel44.setText("Receipt Business Data Language");

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel45.setText("Receipt Content Language");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel46.setText("Receipt Item Language");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel47.setText("Receipt Footer Language");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel21Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(598, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(21, 21, 21)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(237, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Advanced Settings", jPanel21);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 935, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 524, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createUser(){
//        jPanelSettingsPwdChangeDelete.setVisible(false);
//
//        if(jTextFieldUserNme.getText().equals("")){
//            JOptionPane.showMessageDialog(this, "Fill the required fields !");
//        }else{
//            if(jCheckBoxCreateAdminAcc.isSelected()){//create administrator account
//                if(accountType==1){//if loged in admin account?
//                    if(jPasswordFieldCreatePwd.getText().equals(jPasswordFieldCreatePwd2.getText())){
//                        //create admin account
//                        UserDataModel usrv=new UserDataModel();
//
//                        usrv.setAccountType(1);
//                        usrv.setUserName(jTextFieldUserNme.getText());
//                        usrv.setPassword(jPasswordFieldCreatePwd.getText());
//                        usrv.setPwdHint(jTextFieldSecAns.getText());
//                        usrv.setHintType(jComboBoxCreateAcc.getSelectedIndex() + 1);
//                        usrv.setStocks(1);
//                        usrv.setEditStocks(1);
//                        usrv.setAddStocks(1);
//                        usrv.setDelStocks(1);
//                        usrv.setSupplies(1);
//                        usrv.setAddSupplies(1);
//                        usrv.setEditSupplies(1);
//                        usrv.setDelSupplies(1);
//                        usrv.setReports(1);
//
//                        boolean res=logScr.addNewUser(usrv);
//                        if(res==true){
//                            JOptionPane.showMessageDialog(this, "Your account is successfully created !");
//                            //clear fields
//                            clearFieldsAddNewUser();
//                        }else{
//                            JOptionPane.showMessageDialog(this, "Can not do your process !");
//                        }
//
//                    }else{//pwd mismatch
//                        JOptionPane.showMessageDialog(this, "passwords do not match !");
//                        jPasswordFieldCreatePwd.setText("");
//                        jPasswordFieldCreatePwd.setText("");
//                    }
//                    //create account.....
//                }else{
//                    JOptionPane.showMessageDialog(this, "You should loggin with administrater account !");
//                }
//
//            }else{//normal account
//                //show access levels
//                jPanelAccessLevels1.setVisible(true);
//
//            }
//        }
    }
    
    private void changePassword(){
        if(jPasswordFieldChangePwd.getText().equals(jPasswordFieldChangePwd2.getText())){
            int res=logScr.checkPassWord(currentUser, jPasswordFieldCurPwd.getText());
            if(res==0){
                //change account password
                boolean res2=logScr.changeUserPwd(currentUser, jPasswordFieldChangePwd.getText());
                if(res2==true){
                    EasyPOSMessageDialog.showLocalizedInfo(this, ApplicationMessages.INFO_PASSWORD_CHANGED);
                    //clear fields
                    jTextField25.setText("");
                    jPasswordFieldCurPwd.setText("");
                    jPasswordFieldChangePwd.setText("");
                    jPasswordFieldChangePwd2.setText("");
                }else{
                    EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PROCESS_FAILED);
                }

            }else{
                EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_PASSWORD_CURRENT_MISMATCH);
            }
        }else{
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_PASSWORD_NEW_MISMATCH);
        }
    }
    
    private void deleteUser(){
        if(jPasswordFieldChangePwd.getText().equals(jPasswordFieldChangePwd2.getText())){
            int res=logScr.checkPassWord(currentUser, jPasswordFieldCurPwd.getText());
            if(res==0){
                //delete account
                boolean res2=logScr.deleteUserAccount(currentUser);
                if(res2==true){
                    EasyPOSMessageDialog.showLocalizedInfo(this, ApplicationMessages.INFO_ACCOUNT_DELETED);
                    //clear fields
                    jTextField25.setText("");
                    jPasswordFieldCurPwd.setText("");
                    jPasswordFieldChangePwd.setText("");
                    jPasswordFieldChangePwd2.setText("");
                }else{
                    EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PROCESS_FAILED);
                }

            }else{
                EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_PASSWORD_CURRENT_MISMATCH);
            }
        }else{
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_PASSWORD_NEW_MISMATCH);
        }
    }
    
    private void savePermissions(){
        //create normal account
//        String controlSet="";
//
//        if(jTextFieldUserNme.getText().equals("")){
//            JOptionPane.showMessageDialog(this, "Fill the required fields !");
//        }else{
//            if(logScr.userType(currentUser)==true){//if loged in admin account?
//                if(jPasswordFieldCreatePwd.getText().equals(jPasswordFieldCreatePwd2.getText())){
//
//                    UserDataModel usrv=new UserDataModel();
//
//                    usrv.setAccountType(0);
//                    usrv.setUserName(jTextFieldUserNme.getText());
//                    usrv.setPassword(jPasswordFieldCreatePwd.getText());
//                    usrv.setPwdHint(jTextFieldSecAns.getText());
//                    usrv.setHintType(jComboBoxCreateAcc.getSelectedIndex() + 1);
//                    if(jCheckBoxStock.isSelected()){
//                        usrv.setStocks(1);
//                    }else{
//                        usrv.setStocks(0);
//                    }
//                    if(jCheckBox3.isSelected()){
//                        usrv.setEditStocks(1);
//                    }else{
//                        usrv.setEditStocks(0);
//                    }
//                    if(jCheckBox14.isSelected()){
//                        usrv.setAddStocks(1);
//                    }else{
//                        usrv.setAddStocks(0);
//                    }
//                    if(jCheckBox13.isSelected()){
//                        usrv.setDelStocks(1);
//                    }else{
//                        usrv.setDelStocks(0);
//                    }
//
//                    if(jCheckBox15.isSelected()){
//                        usrv.setSupplies(1);
//                    }else{
//                        usrv.setSupplies(0);
//                    }
//                    if(jCheckBox16.isSelected()){
//                        usrv.setAddSupplies(1);
//                    }else{
//                        usrv.setAddSupplies(0);
//                    }
//                    if(jCheckBox17.isSelected()){
//                        usrv.setEditSupplies(1);
//                    }else{
//                        usrv.setEditSupplies(0);
//                    }
//                    if(jCheckBox18.isSelected()){
//                        usrv.setDelSupplies(1);
//                    }else{
//                        usrv.setDelSupplies(0);
//                    }
//                    if(jCheckBox19.isSelected()){
//                        usrv.setReports(1);
//                    }else{
//                        usrv.setReports(0);
//                    }
//
//                    boolean res=logScr.addNewUser(usrv);
//                    if(res==true){
//                        JOptionPane.showMessageDialog(this, "Your account is successfully created !");
//                        //clear fields
//                        clearFieldsAddNewUser();
//                        //hide panel
//                        jPanelAccessLevels1.setVisible(false);
//                    }else{
//                        JOptionPane.showMessageDialog(this, "Can not do your process !");
//                    }
//
//                }else{//pwd mismatch
//                    JOptionPane.showMessageDialog(this, "passwords do not match !");
//                    jPasswordFieldCreatePwd.setText("");
//                    jPasswordFieldCreatePwd.setText("");
//                }
//
//            }else{
//                JOptionPane.showMessageDialog(this, "You should loggin with administrater account !");
//            }
//
//        }

        /*
        if(DB result==ok){
            JOptionPane.showMessageDialog(this, "Normal use account is successfully created.");
            jPanelAccessLevels1.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(this, "Can not do your process.");
        }*/
    }
    
    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        jPanelSettingsPwdChangeDelete.setVisible(false);
        jTextField25.setText("");
        jPasswordFieldChangePwd.setText("");
        jPasswordFieldChangePwd2.setText("");
        jPasswordFieldCurPwd.setText("");
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ApplicationDataManager.getInstance().setReceiptPrinterName(jTextField18.getText());
        ApplicationDataManager.getInstance().setLabelPrinterName(jTextField19.getText());
        
        ApplicationDataManager.getInstance().setApplicationLanguage((Language) jComboBox1.getSelectedItem());        
        
        ApplicationDataManager.getInstance().setReceiptBusinessDataLanguage((Language) jComboBox2.getSelectedItem());
        ApplicationDataManager.getInstance().setReceiptContentLanguage((Language) jComboBox3.getSelectedItem());
        ApplicationDataManager.getInstance().setReceiptItemLanguage((Language) jComboBox4.getSelectedItem());
        ApplicationDataManager.getInstance().setReceiptFooterLanguage((Language) jComboBox5.getSelectedItem());
        
        // Update DB
        DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.APP_DATA_TABLE);
        for (Map.Entry<String, String> entry : ApplicationDataManager.getInstance().getAppData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            DatabaseManager.getInstance().insertAppDataRecord(key, value);
        }

        // Broadcast language change to all registered UI panels
        control.EventManager.getInstance().notifyLanguageChanged();
    }//GEN-LAST:event_jButton1ActionPerformed

    void clearFieldsAddNewUser(){
        jTextFieldUserName.setText("");
        jTextFieldUserCode.setText("");
        jTextFieldUserEmail.setText("");
//        jTextFieldSecAns.setText("");
//        jPasswordFieldCreatePwd.setText("");
//        jPasswordFieldCreatePwd2.setText("");
    }

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/settings/Bundle", locale);
        if (appLang == Language.SINHALA) {
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                        ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(12f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                jLabel35.setFont(customFont); jLabel36.setFont(customFont);
                jLabel61.setFont(customFont); jLabel62.setFont(customFont);
                jLabel63.setFont(customFont); jLabel64.setFont(customFont);
                jLabel65.setFont(customFont); jLabel66.setFont(customFont);
                jLabel40.setFont(customFont); jLabel42.setFont(customFont);
                jLabel43.setFont(customFont); jLabel44.setFont(customFont);
                jLabel45.setFont(customFont); jLabel46.setFont(customFont);
                jLabel47.setFont(customFont); jButton1.setFont(customFont);
                jButton41.setFont(customFont);
            } catch (IOException | FontFormatException e) {
                System.err.println(e);
            }
        }
        jTabbedPane3.setTitleAt(0, resourceBundle.getString("SettingsPanel.tab.userAccounts"));
        jTabbedPane3.setTitleAt(1, resourceBundle.getString("SettingsPanel.tab.advancedSettings"));
        jLabel35.setText(resourceBundle.getString("SettingsPanel.jLabel35.text"));
        jLabel36.setText(resourceBundle.getString("SettingsPanel.jLabel36.text"));
        jLabel61.setText(resourceBundle.getString("SettingsPanel.jLabel61.text"));
        jLabel62.setText(resourceBundle.getString("SettingsPanel.jLabel62.text"));
        jLabel63.setText(resourceBundle.getString("SettingsPanel.jLabel63.text"));
        jLabel64.setText(resourceBundle.getString("SettingsPanel.jLabel64.text"));
        jLabel65.setText(resourceBundle.getString("SettingsPanel.jLabel65.text"));
        jLabel66.setText(resourceBundle.getString("SettingsPanel.jLabel66.text"));
        jButton41.setText(resourceBundle.getString("SettingsPanel.jButton41.text"));
        jLabel40.setText(resourceBundle.getString("SettingsPanel.jLabel40.text"));
        jLabel42.setText(resourceBundle.getString("SettingsPanel.jLabel42.text"));
        jLabel43.setText(resourceBundle.getString("SettingsPanel.jLabel43.text"));
        jLabel44.setText(resourceBundle.getString("SettingsPanel.jLabel44.text"));
        jLabel45.setText(resourceBundle.getString("SettingsPanel.jLabel45.text"));
        jLabel46.setText(resourceBundle.getString("SettingsPanel.jLabel46.text"));
        jLabel47.setText(resourceBundle.getString("SettingsPanel.jLabel47.text"));
        jButton1.setText(resourceBundle.getString("SettingsPanel.jButton1.text"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton41;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanelSettingsPwdChangeDelete;
    private javax.swing.JPasswordField jPasswordFieldChangePwd;
    private javax.swing.JPasswordField jPasswordFieldChangePwd2;
    private javax.swing.JPasswordField jPasswordFieldCurPwd;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextFieldUserCode;
    private javax.swing.JTextField jTextFieldUserEmail;
    private javax.swing.JTextField jTextFieldUserName;
    private javax.swing.JTextField jTextFieldUserPhone;
    // End of variables declaration//GEN-END:variables
}
