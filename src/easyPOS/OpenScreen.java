package easyPOS;

import appDataModels.UserAccountModel;
import control.MenuItemChangeEvent;
import control.LoginEvent;
import control.EventManager;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import control.ApplicationDataManager;
import control.EasyPosLogger;
import dataModels.MenuItemType;
import static dataModels.MenuItemType.CUSTOMER;
import static dataModels.MenuItemType.HOME;
import static dataModels.MenuItemType.INVOICE;
import static dataModels.MenuItemType.LOGOUT;
import static dataModels.MenuItemType.REPORT;
import static dataModels.MenuItemType.SALES_MOOD;
import static dataModels.MenuItemType.SETTINGS;
import static dataModels.MenuItemType.STOCK;
import static dataModels.MenuItemType.SUPPLIER;
import appDataModels.Permission;
import java.awt.Toolkit;
import uiUtil.EasyPOSMessageDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class OpenScreen extends javax.swing.JFrame{

    private easyPOS.customerdisplay.CustomerScreenFrame customerScreenFrame;
    
    public OpenScreen() {
        initComponents();

        //insert logo to the top left of the frame
        setIcon();

        openCustomerDisplay();
        
        EventManager.getInstance().addLoginEvent(new LoginEvent() {
                        @Override
                        public void onLoginSuccess(UserAccountModel user) {
                            jPanelBase.removeAll();   //change j Panel
                            jPanelBase.repaint();
                            jPanelBase.revalidate();
                            jPanelBase.add(jPanelMainMenu);
                            jPanelBase.repaint();
                            jPanelBase.revalidate();
                        }

                        @Override
                        public void onForgotPassword(String UserName) {
                            
                        }
                    });
        EventManager.getInstance().addMenuItemChangeEvent(new MenuItemChangeEvent(){
            @Override
            public void onMenuItemSelected(MenuItemType menuItemType) {
                changePanelOnClickMenuItem(menuItemType);
            }
        });
        
        // Set language & change bundle property accordingly
        
        
    }
        
    void changePanelOnClickMenuItem(MenuItemType menuItemType){
        
        if (!checkMenuPermission(menuItemType)) {
            EasyPOSMessageDialog.showRawError(this, ApplicationDataManager.getInstance().getResourceBundle().getString("Error.AccessDenied"));
            return;
        }
        switch (menuItemType) {
            case HOME:
            {
                loadPage(jPanelMainMenu);
                break;
            }
            case SALES_MOOD:
            {
                loadPage(jPanelDailyTrans);
                salePanel.setNextInvoiceNumber();
                break;
            }
            case STOCK:
            {
                loadPage(jPanelStock);
                stockPanel1.loadInitialDataToUI();                
                break;
            }
            case CUSTOMER:
            {
                loadPage(jPanelCustomer);
                break;
            }
            case SUPPLIER:
            {
                loadPage(jPanelSuppliers);
                break;
            }
            case INVOICE:
            {
                loadPage(jPanelInventory);
                inventoryPanel1.showPanel();
                break;
            }
            case REPORT:
            {
                loadPage(jPanelReports);
                break;
            }
            case SETTINGS:
            {
                loadPage(jPanelSettings);
                break;
            }
            case LOGOUT:
            {
                logOut();
                break;
            }
            default:
                throw new AssertionError();
        }
    }
    
    private boolean checkMenuPermission(MenuItemType menuItemType)
    {
        Permission permissionName = null;
        switch (menuItemType) {
            case HOME:
            {
                return true;
            }
            case SALES_MOOD:
            {                
                permissionName = Permission.P_SALE_PANEL;
                break;
            }
            case STOCK:
            {
                permissionName = Permission.P_STOCK_PANEL;
                break;
            }
            case CUSTOMER:
            {
                permissionName = Permission.P_CUSTOMER;
                break;
            }
            case SUPPLIER:
            {
                permissionName = Permission.P_SUPPLIER_PANEL;
                break;
            }
            case INVOICE:
            {
                permissionName = Permission.P_INVOICE_PANEL;                
                break;
            }
            case REPORT:
            {
                permissionName = Permission.P_REPORT_PANEL;
                break;
            }
            case SETTINGS:
            {
                permissionName = Permission.P_SETTINGS_PANEL;
                break;
            }
            case LOGOUT:
            {
                return true;
            }
            default:
                EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, permissionName + " - Permission Not Defined");
        }
        
        return (ApplicationDataManager.getInstance().getPermissionsOfLoggedInUser().contains(permissionName));
    }
    
    void loadPage(JPanel nextPanel)
    {
        jPanelBase.removeAll();   //change j Panel
        jPanelBase.repaint();
        jPanelBase.revalidate();
        jPanelBase.add(nextPanel);
        jPanelBase.repaint();
        jPanelBase.revalidate();
    }
    
    private void setIcon(){
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logoes/logo.png")));
    }
    
    void logOut(){
        loadPage(jPanelLogScreen);
//        clearFields();
//        clearFieldsSupplierAddToBill();
//        clearFieldsStokeAdd();
//        clearFieldsAddNewUser();
//        clearFieldsSupply();
    }
    
   
    
    private void openCustomerDisplay() {
        customerScreenFrame = new easyPOS.customerdisplay.CustomerScreenFrame();
        customerScreenFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        java.awt.GraphicsDevice[] screens = java.awt.GraphicsEnvironment
                .getLocalGraphicsEnvironment().getScreenDevices();

        if (screens.length > 1) {
            java.awt.Rectangle bounds = screens[1].getDefaultConfiguration().getBounds();
            customerScreenFrame.setBounds(bounds);
            customerScreenFrame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        }

        customerScreenFrame.setVisible(true);
        salePanel.setCustomerScreenFrame(customerScreenFrame);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel23 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jTextFieldFPwd = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jComboBoxFPwd = new javax.swing.JComboBox();
        jTextFieldFPwdAns = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanelBase = new javax.swing.JPanel();
        jPanelLogScreen = new javax.swing.JPanel();
        loginPanel1 = new easyPOS.login.LoginPanel();
        jPanelMainMenu = new javax.swing.JPanel();
        mainMenuPanel1 = new easyPOS.MainMenuPanel();
        jPanelDailyTrans = new javax.swing.JPanel();
        salePanel = new easyPOS.sale.SalePanel();
        jPanelStock = new javax.swing.JPanel();
        headerPanel2 = new easyPOS.HeaderPanel();
        stockPanel1 = new easyPOS.stock.StockPanel();
        jPanelSuppliers = new javax.swing.JPanel();
        headerPanel1 = new easyPOS.HeaderPanel();
        supplierPanel1 = new easyPOS.supplier.SupplierPanel();
        jPanelInventory = new javax.swing.JPanel();
        headerPanel3 = new easyPOS.HeaderPanel();
        inventoryPanel1 = new easyPOS.invoice.PurchasingInvoiceBasePanel();
        jPanelReports = new javax.swing.JPanel();
        headerPanel4 = new easyPOS.HeaderPanel();
        reportPanel1 = new easyPOS.reports.ReportPanel();
        jPanelSettings = new javax.swing.JPanel();
        headerPanel5 = new easyPOS.HeaderPanel();
        settingsPanel1 = new easyPOS.settings.SettingsPanel();
        jPanelLogScreen1 = new javax.swing.JPanel();
        jPanelCustomer = new javax.swing.JPanel();
        customer1 = new easyPOS.customers.Customer();
        headerPanel6 = new easyPOS.HeaderPanel();

        jLabel50.setText("User Name:");

        jLabel51.setText("Answer:");

        jLabel53.setText("Hint:");

        jComboBoxFPwd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Your Favourite Movie?", "Your mother's maiden name", "Your favourite place?", "Your place of birth?" }));

        jButton10.setText("OK");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton10)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldFPwd)
                            .addComponent(jTextFieldFPwdAns)
                            .addComponent(jComboBoxFPwd, 0, 250, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jTextFieldFPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jComboBoxFPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jTextFieldFPwdAns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Easy POS");

        jPanelBase.setPreferredSize(new java.awt.Dimension(1036, 550));
        jPanelBase.setLayout(new java.awt.CardLayout());

        jPanelLogScreen.setBackground(new java.awt.Color(204, 204, 204));
        jPanelLogScreen.setPreferredSize(new java.awt.Dimension(1034, 619));

        javax.swing.GroupLayout jPanelLogScreenLayout = new javax.swing.GroupLayout(jPanelLogScreen);
        jPanelLogScreen.setLayout(jPanelLogScreenLayout);
        jPanelLogScreenLayout.setHorizontalGroup(
            jPanelLogScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE)
        );
        jPanelLogScreenLayout.setVerticalGroup(
            jPanelLogScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        jPanelBase.add(jPanelLogScreen, "card2");

        jPanelMainMenu.setBackground(new java.awt.Color(204, 204, 204));
        jPanelMainMenu.setForeground(new java.awt.Color(204, 204, 204));
        jPanelMainMenu.setPreferredSize(new java.awt.Dimension(1034, 619));

        javax.swing.GroupLayout jPanelMainMenuLayout = new javax.swing.GroupLayout(jPanelMainMenu);
        jPanelMainMenu.setLayout(jPanelMainMenuLayout);
        jPanelMainMenuLayout.setHorizontalGroup(
            jPanelMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainMenuPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMainMenuLayout.setVerticalGroup(
            jPanelMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainMenuLayout.createSequentialGroup()
                .addComponent(mainMenuPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanelBase.add(jPanelMainMenu, "card3");

        jPanelDailyTrans.setBackground(new java.awt.Color(153, 153, 153));
        jPanelDailyTrans.setPreferredSize(new java.awt.Dimension(1034, 619));
        jPanelDailyTrans.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanelDailyTransKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDailyTransLayout = new javax.swing.GroupLayout(jPanelDailyTrans);
        jPanelDailyTrans.setLayout(jPanelDailyTransLayout);
        jPanelDailyTransLayout.setHorizontalGroup(
            jPanelDailyTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelDailyTransLayout.setVerticalGroup(
            jPanelDailyTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelBase.add(jPanelDailyTrans, "card4");

        javax.swing.GroupLayout jPanelStockLayout = new javax.swing.GroupLayout(jPanelStock);
        jPanelStock.setLayout(jPanelStockLayout);
        jPanelStockLayout.setHorizontalGroup(
            jPanelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(headerPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelStockLayout.setVerticalGroup(
            jPanelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStockLayout.createSequentialGroup()
                .addComponent(headerPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelBase.add(jPanelStock, "card5");

        jPanelSuppliers.setPreferredSize(new java.awt.Dimension(1034, 619));

        javax.swing.GroupLayout jPanelSuppliersLayout = new javax.swing.GroupLayout(jPanelSuppliers);
        jPanelSuppliers.setLayout(jPanelSuppliersLayout);
        jPanelSuppliersLayout.setHorizontalGroup(
            jPanelSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(supplierPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(headerPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
        );
        jPanelSuppliersLayout.setVerticalGroup(
            jPanelSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSuppliersLayout.createSequentialGroup()
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplierPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelBase.add(jPanelSuppliers, "card7");

        javax.swing.GroupLayout jPanelInventoryLayout = new javax.swing.GroupLayout(jPanelInventory);
        jPanelInventory.setLayout(jPanelInventoryLayout);
        jPanelInventoryLayout.setHorizontalGroup(
            jPanelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(inventoryPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE)
        );
        jPanelInventoryLayout.setVerticalGroup(
            jPanelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInventoryLayout.createSequentialGroup()
                .addComponent(headerPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inventoryPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
        );

        jPanelBase.add(jPanelInventory, "card10");

        jPanelReports.setPreferredSize(new java.awt.Dimension(1034, 619));

        javax.swing.GroupLayout jPanelReportsLayout = new javax.swing.GroupLayout(jPanelReports);
        jPanelReports.setLayout(jPanelReportsLayout);
        jPanelReportsLayout.setHorizontalGroup(
            jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(headerPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
        );
        jPanelReportsLayout.setVerticalGroup(
            jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportsLayout.createSequentialGroup()
                .addComponent(headerPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reportPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
        );

        jPanelBase.add(jPanelReports, "card8");

        jPanelSettings.setPreferredSize(new java.awt.Dimension(1034, 619));

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(settingsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(headerPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addComponent(headerPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelBase.add(jPanelSettings, "card6");

        jPanelLogScreen1.setBackground(new java.awt.Color(0, 204, 255));
        jPanelLogScreen1.setPreferredSize(new java.awt.Dimension(1034, 619));

        javax.swing.GroupLayout jPanelLogScreen1Layout = new javax.swing.GroupLayout(jPanelLogScreen1);
        jPanelLogScreen1.setLayout(jPanelLogScreen1Layout);
        jPanelLogScreen1Layout.setHorizontalGroup(
            jPanelLogScreen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1034, Short.MAX_VALUE)
        );
        jPanelLogScreen1Layout.setVerticalGroup(
            jPanelLogScreen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );

        jPanelBase.add(jPanelLogScreen1, "card2");

        javax.swing.GroupLayout jPanelCustomerLayout = new javax.swing.GroupLayout(jPanelCustomer);
        jPanelCustomer.setLayout(jPanelCustomerLayout);
        jPanelCustomerLayout.setHorizontalGroup(
            jPanelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(customer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(headerPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
        );
        jPanelCustomerLayout.setVerticalGroup(
            jPanelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomerLayout.createSequentialGroup()
                .addComponent(headerPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customer1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelBase.add(jPanelCustomer, "card11");

        jScrollPane11.setViewportView(jPanelBase);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //>> for change j panel from main menu(end)
    
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jPanelDailyTransKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanelDailyTransKeyPressed
        
    }//GEN-LAST:event_jPanelDailyTransKeyPressed
    
    
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
                try{
                    UIManager.setLookAndFeel(new AluminiumLookAndFeel());
                }catch(UnsupportedLookAndFeelException e){}
                new OpenScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private easyPOS.customers.Customer customer1;
    private easyPOS.HeaderPanel headerPanel1;
    private easyPOS.HeaderPanel headerPanel2;
    private easyPOS.HeaderPanel headerPanel3;
    private easyPOS.HeaderPanel headerPanel4;
    private easyPOS.HeaderPanel headerPanel5;
    private easyPOS.HeaderPanel headerPanel6;
    private easyPOS.invoice.PurchasingInvoiceBasePanel inventoryPanel1;
    private javax.swing.JButton jButton10;
    private javax.swing.JComboBox jComboBoxFPwd;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanelBase;
    private javax.swing.JPanel jPanelCustomer;
    private javax.swing.JPanel jPanelDailyTrans;
    private javax.swing.JPanel jPanelInventory;
    private javax.swing.JPanel jPanelLogScreen;
    private javax.swing.JPanel jPanelLogScreen1;
    private javax.swing.JPanel jPanelMainMenu;
    private javax.swing.JPanel jPanelReports;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JPanel jPanelStock;
    private javax.swing.JPanel jPanelSuppliers;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JTextField jTextFieldFPwd;
    private javax.swing.JTextField jTextFieldFPwdAns;
    private easyPOS.login.LoginPanel loginPanel1;
    private easyPOS.MainMenuPanel mainMenuPanel1;
    private easyPOS.reports.ReportPanel reportPanel1;
    private easyPOS.sale.SalePanel salePanel;
    private easyPOS.settings.SettingsPanel settingsPanel1;
    private easyPOS.stock.StockPanel stockPanel1;
    private easyPOS.supplier.SupplierPanel supplierPanel1;
    // End of variables declaration//GEN-END:variables
}
