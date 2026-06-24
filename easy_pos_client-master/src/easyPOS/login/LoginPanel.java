package easyPOS.login;

import control.ApplicationDataManager;
import control.EventManager;
import dbOperations.LoginDBOperation;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;

import uiUtil.LoadingGlassPane;
import serverResponseDataModel.CommonResponse;
import appDataModels.APIHeaderData;
import appDataModels.InstituteModel;
import appDataModels.LoginResponseModel;
import appDataModels.UserAccountModel;
import control.RuntimeDataManager;
import appDataModels.Permission;
import control.EasyPosLogger;
import control.ServerDataSubmissionQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dataModels.Language;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import localDatabase.DatabaseManager;
import serverDataModels.Category;
import serverDataModels.DiscountRule;
import serverDataModels.Institute;
import serverDataModels.Item;
import serverDataModels.ItemStock;
import serverDataModels.MeasureUnit;
import serverResponseDataModel.LoginResponse;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class LoginPanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    LoginDBOperation userRepository = new LoginDBOperation();
    
    /**
     * Creates new form LoginPanel
     */
    public LoginPanel() {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLogin = new javax.swing.JPanel();
        jPanelLoginComponents = new javax.swing.JPanel();
        jLabelLoginPwd = new javax.swing.JLabel();
        jPasswordFieldLoginPwd = new javax.swing.JPasswordField();
        jButtonPwdFogot = new javax.swing.JButton();
        jTextFieldLoginUserName = new javax.swing.JTextField();
        jButtonLogin = new javax.swing.JButton();
        jLabelLoginUserName = new javax.swing.JLabel();

        jPanelLogin.setBackground(new java.awt.Color(204, 204, 204));

        jPanelLoginComponents.setBackground(new java.awt.Color(204, 204, 204));

        jLabelLoginPwd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLoginPwd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/login/Bundle"); // NOI18N
        jLabelLoginPwd.setText(bundle.getString("LoginPanel.jLabelLoginPwd.text")); // NOI18N

        jPasswordFieldLoginPwd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPasswordFieldLoginPwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordFieldLoginPwdKeyPressed(evt);
            }
        });

        jButtonPwdFogot.setBackground(new java.awt.Color(204, 204, 204));
        jButtonPwdFogot.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonPwdFogot.setText(bundle.getString("LoginPanel.jButtonPwdFogot.text")); // NOI18N
        jButtonPwdFogot.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonPwdFogot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPwdFogotActionPerformed(evt);
            }
        });

        jTextFieldLoginUserName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldLoginUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldLoginUserNameKeyPressed(evt);
            }
        });

        jButtonLogin.setBackground(new java.awt.Color(153, 153, 153));
        jButtonLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonLogin.setText(bundle.getString("LoginPanel.jButtonLogin.text")); // NOI18N
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });
        jButtonLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonLoginKeyPressed(evt);
            }
        });

        jLabelLoginUserName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLoginUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLoginUserName.setText(bundle.getString("LoginPanel.jLabelLoginUserName.text")); // NOI18N

        javax.swing.GroupLayout jPanelLoginComponentsLayout = new javax.swing.GroupLayout(jPanelLoginComponents);
        jPanelLoginComponents.setLayout(jPanelLoginComponentsLayout);
        jPanelLoginComponentsLayout.setHorizontalGroup(
            jPanelLoginComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginComponentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLoginComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLoginUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelLoginPwd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLoginComponentsLayout.createSequentialGroup()
                        .addGroup(jPanelLoginComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLoginComponentsLayout.createSequentialGroup()
                                .addComponent(jButtonPwdFogot)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPasswordFieldLoginPwd)
                            .addComponent(jTextFieldLoginUserName))
                        .addContainerGap())))
        );
        jPanelLoginComponentsLayout.setVerticalGroup(
            jPanelLoginComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginComponentsLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabelLoginUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldLoginUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLoginPwd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordFieldLoginPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginComponentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPwdFogot, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelLoginComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLoginLayout.createSequentialGroup()
                .addContainerGap(179, Short.MAX_VALUE)
                .addComponent(jPanelLoginComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldLoginPwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldLoginPwdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (util.NetworkUtil.isInternetAvailable()) {
                // Online login
                onlineLoginAction(jTextFieldLoginUserName.getText(), new String(jPasswordFieldLoginPwd.getPassword()));
            }
            else{
                // Access local DB
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldLoginUserName.requestFocus();
        }
    }//GEN-LAST:event_jPasswordFieldLoginPwdKeyPressed

    private void jButtonPwdFogotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPwdFogotActionPerformed
        forgotPasswordAction(jTextFieldLoginUserName.getText());
    }//GEN-LAST:event_jButtonPwdFogotActionPerformed

    private void jTextFieldLoginUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLoginUserNameKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_DOWN){
            jPasswordFieldLoginPwd.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldLoginUserNameKeyPressed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        loginAction();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonLoginKeyPressed
        loginAction();
    }//GEN-LAST:event_jButtonLoginKeyPressed

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/login/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            jLabelLoginUserName.setFont(customFont);
            jLabelLoginPwd.setFont(customFont);
            jButtonLogin.setFont(customFont);
            jButtonPwdFogot.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }
        jLabelLoginUserName.setText(resourceBundle.getString("LoginPanel.jLabelLoginUserName.text"));
        jLabelLoginPwd.setText(resourceBundle.getString("LoginPanel.jLabelLoginPwd.text"));
        jButtonLogin.setText(resourceBundle.getString("LoginPanel.jButtonLogin.text"));
        jButtonPwdFogot.setText(resourceBundle.getString("LoginPanel.jButtonPwdFogot.text"));
    }

    private void loginAction(){
        if (util.NetworkUtil.isInternetAvailable()) {
            // Online login
            onlineLoginAction(jTextFieldLoginUserName.getText(), new String(jPasswordFieldLoginPwd.getPassword()));
        }
        else{
            // Access local DB
            offlineLoginAction(jTextFieldLoginUserName.getText(), new String(jPasswordFieldLoginPwd.getPassword()));
        }
    }
    
    private void offlineLoginAction(String userName, String password){
        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "User offline login");
        
        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);

        SwingWorker<LoginResponseModel, Void> worker;
        worker = new SwingWorker<LoginResponseModel, Void>() {
            @Override
            protected LoginResponseModel doInBackground() {
                loader.start(); // show loader
                
                LoginResponseModel loginResponseModel = null; 
                loginResponseModel = DatabaseManager.getInstance().login(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId(), 
                        userName, password);
                
                if (loginResponseModel != null) {
                    
                    UserAccountModel userAccountModel = loginResponseModel.getUser();
                    List<Permission> permissions = loginResponseModel.getPermissions();
                    
                    /** Set run-time data **/ 
                    RuntimeDataManager.getInstance().getRuntimeData().setUser(loginResponseModel.getUser());
                    
                    for (Institute institute : DatabaseManager.getInstance().getInstitutes()){
                        if (institute.institute_id == loginResponseModel.getUser().getInstituteId()) {
                            RuntimeDataManager.getInstance().getRuntimeData().setSelectedInstitute(new InstituteModel(institute));
                        }
                    }
                    
                    // Receipt logo
                    String fileName = ApplicationDataManager.getInstance().getReceiptLogoName(Integer.parseInt(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId()));
                    File localFile = new File(ApplicationDataManager.RECEIPT_LOGO_LOCAL_FOLDER_PATH + fileName);

                    if (localFile.exists()) {
                        ApplicationDataManager.getInstance().setReceiptLogo(localFile);
                    }
                                        
                    ApplicationDataManager.getInstance().setLoggedInUser(userAccountModel);
                    ApplicationDataManager.getInstance().setPermissionsOfLoggedInUser(permissions);
                                        
                    // Load app data
                    HashMap<String, String> dbAppData = DatabaseManager.getInstance().getAppData();
                    ApplicationDataManager.getInstance().setAppData(dbAppData);
                }
                return loginResponseModel;
            }

            @Override
            protected void done() {
                loader.stop(); // hide loader
                try {
                    LoginResponseModel loginResponseModel = get();

                    JLabel label = new JLabel("Local data error - Please connect to valid internet connection");
                    try {                        
                        Font customFont = Font.createFont(
                                Font.TRUETYPE_FONT,
                                ApplicationDataManager.getInstance().getSinhalaFontFile()
                        ).deriveFont(12f);
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        ge.registerFont(customFont);
                        label.setFont(customFont);
                    } catch (IOException | FontFormatException ignored) {}

                    if (loginResponseModel != null) {
                        
                        //Fire login event
                        UserAccountModel userAccountModel = loginResponseModel.getUser();
                        userAccountModel.setPassword(password);
                        
                        jTextFieldLoginUserName.setText("");
                        jPasswordFieldLoginPwd.setText("");
                        
                        EventManager.getInstance().notifyLoginSuccess(userAccountModel);
                    } else {
                        JOptionPane.showMessageDialog(jPanelLogin.getRootPane(), label, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(jPanelLogin.getRootPane(), "Unexpected error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    private void onlineLoginAction(String userName, String password) {
        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "User online login");
        
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

                CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).login(userName, password);
                
                if (commonResponse.getAPIResponse().isSuccess()) {
                    LoginResponseModel loginResponseModel = new LoginResponseModel((LoginResponse) commonResponse.getData());                    
                    RuntimeDataManager.getInstance().getRuntimeData().setUser(loginResponseModel.getUser());
                    
                    UserAccountModel userAccountModel = loginResponseModel.getUser();
                    List<Permission> permissions = loginResponseModel.getPermissions();
                    
                    /** Set run-time data **/ 
                    ApplicationDataManager.getInstance().setLoggedInUser(userAccountModel);
                    ApplicationDataManager.getInstance().setPermissionsOfLoggedInUser(permissions);

                    // Create and or load data to local database
                    DatabaseManager.createTablesIfNotExist();

                    /** Delete Existing data **/
                    // Delete User data
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.USER_TABLE);
                    // Delete User permission data
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.USER_PERMISSION_TABLE);
                    // Delete Institute
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.INSTITUTE_TABLE);
                    // Delete Category
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.CATEGORY_TABLE);
                    // Delete Measure unit
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.MEASURE_UNIT_TABLE);
                    // Delete Item stock
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.ITEM_TABLE);
                    // Delete Item
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.ITEM_STOCK_TABLE);
                    // Delete Discount Rules
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.DISCOUNT_RULES_TABLE);

                    /** Insert new data **/
                    // Insert new user data
                    userAccountModel.setPassword(password);
                    DatabaseManager.getInstance().insertUser(userAccountModel.newUserAccountDTO());                    
                    // Insert new user permission data
                    for (Permission permission : permissions) {
                        DatabaseManager.getInstance().insertPermission(userAccountModel.getInstituteId(), userAccountModel.getUserName(), permission);
                    }
                    
                    // Insert Institute
                    CommonResponse cr;
                    cr = ServerAPIConnection.getInstance(aPIHeaderData).getInstitute();
                    if (cr.getAPIResponse().isSuccess()) {
                        DatabaseManager.getInstance().insertInstitute((Institute) cr.getData());
                        // load Runtime data
                        RuntimeDataManager.getInstance().getRuntimeData().setSelectedInstitute(new InstituteModel((Institute) cr.getData()));
                    }
                    
                    // Insert Categories
                    cr = ServerAPIConnection.getInstance(aPIHeaderData).getCategories();
                    if (cr.getAPIResponse().isSuccess()) {
                        List<Category> categories = (ArrayList<Category>) cr.getData();
                        for (Category category : categories) {
                            DatabaseManager.getInstance().insertCategory(category);
                        }                        
                    }
                    
                    // Insert Measure units
                    cr = ServerAPIConnection.getInstance(aPIHeaderData).getMeasureUnitsData();
                    if (cr.getAPIResponse().isSuccess()) {
                        List<MeasureUnit> measureUnits = (ArrayList<MeasureUnit>) cr.getData();
                        for (MeasureUnit measureUnit : measureUnits) {
                            DatabaseManager.getInstance().insertMeasureUnit(measureUnit);
                        }                        
                    }
                    
                    // Insert Item & Item stock
                    cr = ServerAPIConnection.getInstance(aPIHeaderData).getAllItemStock();
                    if (cr.getAPIResponse().isSuccess()) {
                        List<Item> itemDataList = (ArrayList<Item>) cr.getData();
                        for (Item item : itemDataList) {
                            DatabaseManager.getInstance().insertItem(item);
                            for (ItemStock itemStock : item.stock) {
                                DatabaseManager.getInstance().insertItemStock(itemStock);
                            }
                        }
                    }
                    
                    // Insert Discount Rules
                    cr = ServerAPIConnection.getInstance(aPIHeaderData).getDiscountRules();
                    if (cr.getAPIResponse().isSuccess()) {
                        List<DiscountRule> discountRules = (ArrayList<DiscountRule>) cr.getData();
                        for (DiscountRule discountRule : discountRules) {
                            DatabaseManager.getInstance().insertDiscountRule(discountRule);
                        }                        
                    }
                    
                    // Load app data
                    HashMap<String, String> dbAppData = DatabaseManager.getInstance().getAppData();
                    ApplicationDataManager.getInstance().setAppData(dbAppData);
                    
                    // Insert app data if data not exist - fresh installation                    
                    DatabaseManager.getInstance().deleteAllData(DatabaseManager.TableName.APP_DATA_TABLE);
                    for (Map.Entry<String, String> entry : ApplicationDataManager.getInstance().getAppData().entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        DatabaseManager.getInstance().insertAppDataRecord(key, value);
                    }
                }
                return commonResponse;
            }

            @Override
            protected void done() {
                loader.stop(); // hide loader
                try {
                    CommonResponse response = get();
                    
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.INFO, "User Login Action Status : " + response.getAPIResponse().getResponseCode() + " - " + response.getAPIResponse().getMessage());

                    JLabel label = new JLabel(response.getAPIResponse().getMessageWithErrorCodeSinhala());
                    try {                        
                        Font customFont = Font.createFont(
                                Font.TRUETYPE_FONT,
                                ApplicationDataManager.getInstance().getSinhalaFontFile()
                        ).deriveFont(12f);
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        ge.registerFont(customFont);
                        label.setFont(customFont);
                    } catch (IOException | FontFormatException ignored) {}

                    if (response.getAPIResponse().isSuccess()) {
                        
                        //Fire login event
                        LoginResponseModel loginResponseModel = new LoginResponseModel((LoginResponse) response.getData());
                        UserAccountModel userAccountModel = loginResponseModel.getUser();
                        userAccountModel.setPassword(password);
                        
                        jTextFieldLoginUserName.setText("");
                        jPasswordFieldLoginPwd.setText("");
                        
                        EventManager.getInstance().notifyLoginSuccess(userAccountModel);
                        
                        ServerDataSubmissionQueue.getInstance().notifyAction(() -> ServerDataSubmissionQueue.getInstance().doImageSync());
                        ServerDataSubmissionQueue.getInstance().notifyAction(() -> ServerDataSubmissionQueue.getInstance().doChangeLogSaleDataSync());
                    } else {
                        JOptionPane.showMessageDialog(jPanelLogin.getRootPane(), label, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(jPanelLogin.getRootPane(), "Unexpected error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, ex.toString());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }

    private void forgotPasswordAction(String userName){
        
        int x = userRepository.checkUserName(userName);
        
        switch (x) {
            case 0:
                // Fire forgot pwd event
                EventManager.getInstance().notifyForgotPassword(userName);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, ApplicationDataManager.getInstance().getResourceBundle().getString("Error.DataConnectionError"));
                break;
            default:
                JOptionPane.showMessageDialog(this, "Can not find your account!");
                jTextFieldLoginUserName.setText("");
                jPasswordFieldLoginPwd.setText("");
                break;
        }
    }
    
    
//    private void dataSyncAction(){
//
//        SwingWorker<CommonResponse, Void> worker = new SwingWorker<CommonResponse, Void>() {
//            @Override
//            protected CommonResponse doInBackground() {
//                uploadLocalDataChangesToServer();
//                loadDataToLocatDatabase();
//            }
//
//            @Override
//            protected void done() {
//                try {
//                    CommonResponse response = get();
//
//                    JLabel label = new JLabel(response.getAPIResponse().getMessageWithErrorCodeSinhala());
//                    try {
//                        
//                        Font customFont = Font.createFont(
//                                Font.TRUETYPE_FONT,
//                                ApplicationDataManager.getInstance().getSinhalaFontFile()
//                        ).deriveFont(12f);
//                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                        ge.registerFont(customFont);
//                        label.setFont(customFont);
//                    } catch (IOException | FontFormatException ignored) {}
//
//                    if (response.getAPIResponse().isSuccess()) {
//                        //Fire login event
//                        UserDataModel user = userRepository.checkUserLogin(userName, password);
//                        EventManager.getInstance().notifyLoginSuccess(user);
//                        ApplicationDataManager.getInstance().setLoggedInUser(user);
//
//                        // Load user permissions
//                        ArrayList<Permissions> permissions = userRepository.getPermissionListOfUser(userName);
//
//                        if (permissions != null) {
//                            ApplicationDataManager.getInstance().setPermissionsOfLoggedInUser(permissions);
//                        }
//                        
//                        // Create and or load data to local database
//                        DBInit.createTables();
//                        uploadLocalDataChangesToServer();
//                        loadDataToLocatDatabase();
//                        
//
//                        jTextFieldLoginUserName.setText("");
//                        jPasswordFieldLoginPwd.setText("");
//                    } else {
//                        JOptionPane.showMessageDialog(jPanelLogin.getRootPane(), label, "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//
//                } catch (InterruptedException | ExecutionException ex) {
//                    JOptionPane.showMessageDialog(jPanelLogin.getRootPane(), "Unexpected error: " + ex.getMessage(),
//                            "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        };
//
//        worker.execute();
//    }
    
//    private void uploadLocalDataChangesToServer(){
//        APIHeaderData aPIHeaderData = new APIHeaderData();
//        aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
//        aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());
//
//        CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).login(userName, password);
//
//        if (commonResponse.getAPIResponse().isSuccess()) {
//            LoginResponseModel loginResponseModel = new LoginResponseModel((LoginResponse) commonResponse.getData());                    
//            RuntimeDataManager.getInstance().getRuntimeData().setUser(loginResponseModel.getUser());
//        }
//    }
    
//    private void loadDataToLocatDatabase()
//    {
//        APIHeaderData aPIHeaderData = new APIHeaderData();
//        aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
//        aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());
//
//        CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).login(userName, password);
//
//        if (commonResponse.getAPIResponse().isSuccess()) {
//            LoginResponseModel loginResponseModel = new LoginResponseModel((LoginResponse) commonResponse.getData());                    
//            RuntimeDataManager.getInstance().getRuntimeData().setUser(loginResponseModel.getUser());
//        }
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonPwdFogot;
    private javax.swing.JLabel jLabelLoginPwd;
    private javax.swing.JLabel jLabelLoginUserName;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelLoginComponents;
    private javax.swing.JPasswordField jPasswordFieldLoginPwd;
    private javax.swing.JTextField jTextFieldLoginUserName;
    // End of variables declaration//GEN-END:variables
}
