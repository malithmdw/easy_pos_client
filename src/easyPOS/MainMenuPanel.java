
package easyPOS;

import appDataModels.UserAccountModel;
import control.ApplicationDataManager;
import control.EventManager;
import control.LoginEvent;
import control.RuntimeDataManager;
import dataModels.MenuItemType;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author malit
 */
public class MainMenuPanel extends javax.swing.JPanel implements control.LanguageChangeListener, LoginEvent {

    /**
     * Creates new form MainMenuPanel
     */
    public MainMenuPanel() {
        initComponents();
        switchLanguage();
        control.EventManager.getInstance().addLanguageChangeListener(this);
        EventManager.getInstance().addLoginEvent(this);
    }
    
    public void LoadUIData() {
        if(RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute() != null)
        {
            jLabelMainMenuBusinessName.setText(RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute().getBusinessName());
        }
        else{
            // Fresh run. Will be loaded next time
            jLabelMainMenuBusinessName.setText("Loading...");
        }
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }
    
    @Override
    public void onLoginSuccess(UserAccountModel user) {
        LoadUIData();
    }

    @Override
    public void onForgotPassword(String UserName) {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel106 = new javax.swing.JLabel();
        jLabelMainMenuBusinessName = new javax.swing.JLabel();
        jButtonDailyTrans = new javax.swing.JButton();
        jButtonStoke = new javax.swing.JButton();
        jButtonReports = new javax.swing.JButton();
        jButtonSettings = new javax.swing.JButton();
        jButtonLogOut1 = new javax.swing.JButton();
        jButtonSupp1 = new javax.swing.JButton();
        jButtonSettings2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel106.setFont(new java.awt.Font("Buxton Sketch", 0, 42)); // NOI18N
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logoes/pagelogo.png"))); // NOI18N

        jLabelMainMenuBusinessName.setBackground(new java.awt.Color(0, 153, 204));
        jLabelMainMenuBusinessName.setFont(new java.awt.Font("Tekton Pro", 3, 36)); // NOI18N
        jLabelMainMenuBusinessName.setForeground(new java.awt.Color(102, 102, 102));
        jLabelMainMenuBusinessName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jButtonDailyTrans.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonDailyTrans.setForeground(new java.awt.Color(51, 51, 51));
        jButtonDailyTrans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sale.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/Bundle"); // NOI18N
        jButtonDailyTrans.setText(bundle.getString("MainMenuPanel.jButtonDailyTrans.text")); // NOI18N
        jButtonDailyTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDailyTransActionPerformed(evt);
            }
        });

        jButtonStoke.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonStoke.setForeground(new java.awt.Color(51, 51, 51));
        jButtonStoke.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stock.png"))); // NOI18N
        jButtonStoke.setText(bundle.getString("MainMenuPanel.jButtonStoke.text")); // NOI18N
        jButtonStoke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStokeActionPerformed(evt);
            }
        });

        jButtonReports.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonReports.setForeground(new java.awt.Color(51, 51, 51));
        jButtonReports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reports.png"))); // NOI18N
        jButtonReports.setText(bundle.getString("MainMenuPanel.jButtonReports.text")); // NOI18N
        jButtonReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportsActionPerformed(evt);
            }
        });

        jButtonSettings.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonSettings.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings.png"))); // NOI18N
        jButtonSettings.setText(bundle.getString("MainMenuPanel.jButtonSettings.text")); // NOI18N
        jButtonSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettingsActionPerformed(evt);
            }
        });

        jButtonLogOut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jButtonLogOut1.setText(bundle.getString("MainMenuPanel.jButtonLogOut1.text")); // NOI18N
        jButtonLogOut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogOut1ActionPerformed(evt);
            }
        });

        jButtonSupp1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonSupp1.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSupp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/invoice.png"))); // NOI18N
        jButtonSupp1.setText(bundle.getString("MainMenuPanel.jButtonSupp1.text")); // NOI18N
        jButtonSupp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupp1ActionPerformed(evt);
            }
        });

        jButtonSettings2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonSettings2.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSettings2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/customer.png"))); // NOI18N
        jButtonSettings2.setText(bundle.getString("MainMenuPanel.jButtonSettings2.text")); // NOI18N
        jButtonSettings2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettings2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelMainMenuBusinessName, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLogOut1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButtonSettings2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                    .addComponent(jButtonSupp1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonStoke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonDailyTrans, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonReports, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(46, 46, 46)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMainMenuBusinessName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDailyTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReports, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonStoke, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSettings2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSupp1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(jButtonLogOut1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDailyTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDailyTransActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.SALES_MOOD);
    }//GEN-LAST:event_jButtonDailyTransActionPerformed

    private void jButtonStokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStokeActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.STOCK);
    }//GEN-LAST:event_jButtonStokeActionPerformed

    private void jButtonReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportsActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.REPORT);
    }//GEN-LAST:event_jButtonReportsActionPerformed

    private void jButtonSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.SETTINGS);
    }//GEN-LAST:event_jButtonSettingsActionPerformed

    private void jButtonLogOut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogOut1ActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.LOGOUT);
    }//GEN-LAST:event_jButtonLogOut1ActionPerformed

    private void jButtonSupp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupp1ActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.INVOICE);
    }//GEN-LAST:event_jButtonSupp1ActionPerformed

    private void jButtonSettings2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettings2ActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.CUSTOMER);
    }//GEN-LAST:event_jButtonSettings2ActionPerformed

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            jLabelMainMenuBusinessName.setFont(customFont);
            jButtonDailyTrans.setFont(customFont);
            jButtonStoke.setFont(customFont);
//            jButtonSupp.setFont(customFont);
            jButtonReports.setFont(customFont);
            jButtonSettings.setFont(customFont);
            jButtonLogOut1.setFont(customFont);
            jButtonSupp1.setFont(customFont);
//            jButtonSettings1.setFont(customFont);
            jButtonSettings2.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }
        jButtonDailyTrans.setText(resourceBundle.getString("MainMenuPanel.jButtonDailyTrans.text"));
        jButtonStoke.setText(resourceBundle.getString("MainMenuPanel.jButtonStoke.text"));
//        jButtonSupp.setText(resourceBundle.getString("MainMenuPanel.jButtonSupp.text"));
        jButtonReports.setText(resourceBundle.getString("MainMenuPanel.jButtonReports.text"));
        jButtonSettings.setText(resourceBundle.getString("MainMenuPanel.jButtonSettings.text"));
        jButtonLogOut1.setText(resourceBundle.getString("MainMenuPanel.jButtonLogOut1.text"));
        jButtonSupp1.setText(resourceBundle.getString("MainMenuPanel.jButtonSupp1.text"));
//        jButtonSettings1.setText(resourceBundle.getString("MainMenuPanel.jButtonSettings1.text"));
        jButtonSettings2.setText(resourceBundle.getString("MainMenuPanel.jButtonSettings2.text"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDailyTrans;
    private javax.swing.JButton jButtonLogOut1;
    private javax.swing.JButton jButtonReports;
    private javax.swing.JButton jButtonSettings;
    private javax.swing.JButton jButtonSettings2;
    private javax.swing.JButton jButtonStoke;
    private javax.swing.JButton jButtonSupp1;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabelMainMenuBusinessName;
    // End of variables declaration//GEN-END:variables
}
