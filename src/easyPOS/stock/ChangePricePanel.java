package easyPOS.stock;

import appDataModels.APIHeaderData;
import appDataModels.ItemModel;
import appDataModels.ItemStockModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import localDatabase.DatabaseManager;
import serverResponseDataModel.CommonResponse;
import easyPOS.localization.ApplicationMessages;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author MalithWanniarachchi
 */
public class ChangePricePanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    private ItemModel lastSelectedItem;
    private ItemStockModel lastSelectedItemBatch;
    
    public ChangePricePanel() {
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

    private void fetchItem()
    {
        lastSelectedItem = null;
        ItemModel itemModel = DatabaseManager.getInstance().getItemByBarcode(jTextFieldItmCode.getText());
        
        if (itemModel == null) {
            long itemId;
            try {
                itemId = Long.parseLong(jTextFieldItmCode.getText());
                itemModel = DatabaseManager.getInstance().getItemByItemId(itemId);
            } catch (NumberFormatException e) {
            }            
        }
        
        if (itemModel == null) {
            EasyPOSMessageDialog.showLocalizedError(null, ApplicationMessages.ERROR_ITEM_NOT_FOUND);
        }else {
            lastSelectedItem = itemModel;
            
            // Display details
            List<String> dropdownItems = new ArrayList<>();
            for (ItemStockModel itemStockModel : itemModel.getStock()) {
                dropdownItems.add(Integer.toString((int) itemStockModel.getBatchId()));
            }
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropdownItems.toArray(new String[0]));
            jComboBoxPIItemBatches.setModel(model);
            
            if (itemModel.getStock().size() == 1) {                                
                setItemStockDataToUI(itemModel, 0);
            }
        }
    }
    
    private void setItemStockDataToUI(ItemModel itemModel, int index)
    {
        lastSelectedItemBatch = itemModel.getStock().get(index);
        
        jTextFieldInvItemItmName.setText(itemModel.getItemName());
        jTextFieldInvItmItmName2.setText(itemModel.getItemNameSin());
        jTextFieldPIItmLablePrice.setText(util.GeneralUtil.getCurrencyString(itemModel.getStock().get(index).getLabelPrice()));
        jTextFieldInvItmItmDis.setText(util.GeneralUtil.getCurrencyString(itemModel.getStock().get(index).getDiscount()));
        jTextFieldInvItmUnitPrice.setText(util.GeneralUtil.getCurrencyString(itemModel.getStock().get(index).getSellingPrice()));
        jTextFieldInvItmWholesalePrice.setText(util.GeneralUtil.getCurrencyString(itemModel.getStock().get(index).getWholeSalePrice()));
        jTextFieldInvItmAvailQty.setText(Double.toString(itemModel.getStock().get(index).getQuantityAvailable()));
        jTextFieldInvItmPurchAmount.setText(util.GeneralUtil.getCurrencyString(itemModel.getStock().get(index).getPurchasingPrice()));
        jTextFieldInvItmExp.setText(itemModel.getStock().get(index).getExpiryDate());
    }
    
    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/stock/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            //create the font to use. Specify the size!

            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);

            jLabelPIItemBarcode.setFont(customFont1);
            jLabelPIItemBatch.setFont(customFont1);
            jLabelPIItemName.setFont(customFont1);
            jLabelPIItemDiscount.setFont(customFont1);
            jLabelPIItemExpDate.setFont(customFont1);
            jLabelPIItemLabelUnitPrice.setFont(customFont1);
            jLabelPIItemPurchasingUnitPrice.setFont(customFont1);
            jLabelPIItemQtyAvail.setFont(customFont1);
            jLabelPIItemSaleUnitPrice.setFont(customFont1);
            jLabelPIItemWholeSalePrice.setFont(customFont1);

            jLabelChangePriceNewlabelPrice.setFont(customFont1);
            jLabelChangePriceNewDis.setFont(customFont1);
            jLabelChangePriceNewSellPrice.setFont(customFont1);
            jLabelChangePriceNewWSPrice.setFont(customFont1);

        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        //use the font
        jLabelPIItemBarcode.setText(resourceBundle.getString("ChangePricePanel.Barcode"));
        jLabelPIItemBatch.setText(resourceBundle.getString("ChangePricePanel.Batch"));
        jLabelPIItemName.setText(resourceBundle.getString("ChangePricePanel.ItemName"));
        jLabelPIItemDiscount.setText(resourceBundle.getString("ChangePricePanel.Discount"));
        jLabelPIItemExpDate.setText(resourceBundle.getString("ChangePricePanel.ExpiryDate"));
        jLabelPIItemLabelUnitPrice.setText(resourceBundle.getString("ChangePricePanel.LabelPrice"));
        jLabelPIItemPurchasingUnitPrice.setText(resourceBundle.getString("ChangePricePanel.PurchasingUnitPrice"));
        jLabelPIItemQtyAvail.setText(resourceBundle.getString("ChangePricePanel.QtyAvailable"));
        jLabelPIItemSaleUnitPrice.setText(resourceBundle.getString("ChangePricePanel.SellingUnitPrice"));
        jLabelPIItemWholeSalePrice.setText(resourceBundle.getString("ChangePricePanel.WholeSalePrice"));

        jLabelChangePriceNewlabelPrice.setText(resourceBundle.getString("ChangePricePanel.LabelPrice"));
        jLabelChangePriceNewDis.setText(resourceBundle.getString("ChangePricePanel.Discount"));
        jLabelChangePriceNewSellPrice.setText(resourceBundle.getString("ChangePricePanel.SellingUnitPrice"));
        jLabelChangePriceNewWSPrice.setText(resourceBundle.getString("ChangePricePanel.WholeSalePrice"));        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jTextFieldAddToStQty1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldAddToStSellrPrice = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldAddToStDis = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabelChangePriceNewWSPrice = new javax.swing.JLabel();
        jTextFieldNewWholeSalePrice = new javax.swing.JTextField();
        jLabelChangePriceNewSellPrice = new javax.swing.JLabel();
        jTextFieldNewSellingPrice = new javax.swing.JTextField();
        jLabelChangePriceNewDis = new javax.swing.JLabel();
        jTextFieldNewDiscount = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabelChangePriceNewlabelPrice = new javax.swing.JLabel();
        jTextFieldNewLabelPrice = new javax.swing.JTextField();
        jLabelPIItemBarcode = new javax.swing.JLabel();
        jLabelPIItemBatch = new javax.swing.JLabel();
        jLabelPIItemName = new javax.swing.JLabel();
        jTextFieldItmCode = new javax.swing.JTextField();
        jButtonInvItemSearch = new javax.swing.JButton();
        jComboBoxPIItemBatches = new javax.swing.JComboBox<>();
        jTextFieldInvItemItmName = new javax.swing.JTextField();
        jTextFieldInvItmItmName2 = new javax.swing.JTextField();
        jTextFieldPIItmLablePrice = new javax.swing.JTextField();
        jLabelPIItemLabelUnitPrice = new javax.swing.JLabel();
        jLabelPIItemDiscount = new javax.swing.JLabel();
        jTextFieldInvItmItmDis = new javax.swing.JTextField();
        jLabelPIItemSaleUnitPrice = new javax.swing.JLabel();
        jTextFieldInvItmUnitPrice = new javax.swing.JTextField();
        jLabelPIItemWholeSalePrice = new javax.swing.JLabel();
        jTextFieldInvItmWholesalePrice = new javax.swing.JTextField();
        jLabelPIItemQtyAvail = new javax.swing.JLabel();
        jTextFieldInvItmAvailQty = new javax.swing.JTextField();
        jLabelPIItemPurchasingUnitPrice = new javax.swing.JLabel();
        jTextFieldInvItmPurchAmount = new javax.swing.JTextField();
        jLabelPIItemExpDate = new javax.swing.JLabel();
        jTextFieldInvItmExp = new javax.swing.JTextField();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/stock/Bundle"); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("StockItemForm.jPanel2.border.title"))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText(bundle.getString("StockItemForm.jLabel33.text")); // NOI18N

        jTextFieldAddToStQty1.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStQty1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStQty1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStQty1KeyPressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText(bundle.getString("StockItemForm.jLabel28.text")); // NOI18N

        jTextFieldAddToStSellrPrice.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStSellrPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStSellrPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAddToStSellrPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStSellrPriceActionPerformed(evt);
            }
        });
        jTextFieldAddToStSellrPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStSellrPriceKeyPressed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText(bundle.getString("StockItemForm.jLabel46.text")); // NOI18N

        jTextFieldAddToStDis.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStDis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAddToStDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStDisKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAddToStQty1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jTextFieldAddToStDis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAddToStSellrPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStQty1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldAddToStSellrPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldAddToStDis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("StockItemForm.jPanel2.border.title"))); // NOI18N

        jLabelChangePriceNewWSPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelChangePriceNewWSPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelChangePriceNewWSPrice.setText(bundle.getString("ChangePricePanel.jLabel33.text")); // NOI18N

        jTextFieldNewWholeSalePrice.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldNewWholeSalePrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldNewWholeSalePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldNewWholeSalePrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldNewWholeSalePriceKeyPressed(evt);
            }
        });

        jLabelChangePriceNewSellPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelChangePriceNewSellPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelChangePriceNewSellPrice.setText(bundle.getString("ChangePricePanel.jLabel28.text")); // NOI18N

        jTextFieldNewSellingPrice.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldNewSellingPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldNewSellingPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldNewSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNewSellingPriceActionPerformed(evt);
            }
        });
        jTextFieldNewSellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldNewSellingPriceKeyPressed(evt);
            }
        });

        jLabelChangePriceNewDis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelChangePriceNewDis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelChangePriceNewDis.setText(bundle.getString("ChangePricePanel.jLabel46.text")); // NOI18N

        jTextFieldNewDiscount.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldNewDiscount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldNewDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldNewDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldNewDiscountKeyPressed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelChangePriceNewlabelPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelChangePriceNewlabelPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelChangePriceNewlabelPrice.setText(bundle.getString("ChangePricePanel.jLabel33.text")); // NOI18N

        jTextFieldNewLabelPrice.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldNewLabelPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldNewLabelPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldNewLabelPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldNewLabelPriceKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabelChangePriceNewDis, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNewDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabelChangePriceNewSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNewSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelChangePriceNewWSPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNewWholeSalePrice))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelChangePriceNewlabelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNewLabelPrice)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelChangePriceNewlabelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNewLabelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelChangePriceNewDis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNewDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelChangePriceNewSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNewSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelChangePriceNewWSPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNewWholeSalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelPIItemBarcode.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemBarcode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("easyPOS/invoice/Bundle"); // NOI18N
        jLabelPIItemBarcode.setText(bundle1.getString("ChangePricePanel.Barcode")); // NOI18N

        jLabelPIItemBatch.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemBatch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemBatch.setText(bundle1.getString("ChangePricePanel.Batch")); // NOI18N

        jLabelPIItemName.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemName.setText(bundle1.getString("ChangePricePanel.ItemName")); // NOI18N

        jTextFieldItmCode.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldItmCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldItmCodeKeyPressed(evt);
            }
        });

        jButtonInvItemSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        jButtonInvItemSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInvItemSearchActionPerformed(evt);
            }
        });

        jComboBoxPIItemBatches.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxPIItemBatches.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxPIItemBatchesItemStateChanged(evt);
            }
        });
        jComboBoxPIItemBatches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPIItemBatchesActionPerformed(evt);
            }
        });

        jTextFieldInvItemItmName.setEditable(false);
        jTextFieldInvItemItmName.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItemItmName.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jTextFieldInvItmItmName2.setEditable(false);
        jTextFieldInvItmItmName2.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmItmName2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jTextFieldPIItmLablePrice.setEditable(false);
        jTextFieldPIItmLablePrice.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldPIItmLablePrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmLablePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelPIItemLabelUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemLabelUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemLabelUnitPrice.setText(bundle1.getString("ChangePricePanel.LabelPrice")); // NOI18N

        jLabelPIItemDiscount.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemDiscount.setText(bundle1.getString("ChangePricePanel.Discount")); // NOI18N

        jTextFieldInvItmItmDis.setEditable(false);
        jTextFieldInvItmItmDis.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmItmDis.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmItmDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvItmItmDis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldInvItmItmDisActionPerformed(evt);
            }
        });
        jTextFieldInvItmItmDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldInvItmItmDisKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldInvItmItmDisKeyReleased(evt);
            }
        });

        jLabelPIItemSaleUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemSaleUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemSaleUnitPrice.setText(bundle1.getString("ChangePricePanel.SellingUnitPrice")); // NOI18N

        jTextFieldInvItmUnitPrice.setEditable(false);
        jTextFieldInvItmUnitPrice.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmUnitPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelPIItemWholeSalePrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemWholeSalePrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemWholeSalePrice.setText(bundle1.getString("ChangePricePanel.WholeSalePrice")); // NOI18N

        jTextFieldInvItmWholesalePrice.setEditable(false);
        jTextFieldInvItmWholesalePrice.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmWholesalePrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmWholesalePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvItmWholesalePrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldInvItmWholesalePriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldInvItmWholesalePriceKeyReleased(evt);
            }
        });

        jLabelPIItemQtyAvail.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemQtyAvail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemQtyAvail.setText(bundle1.getString("ChangePricePanel.QtyAvailable")); // NOI18N

        jTextFieldInvItmAvailQty.setEditable(false);
        jTextFieldInvItmAvailQty.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmAvailQty.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmAvailQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInvItmAvailQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldInvItmAvailQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldInvItmAvailQtyKeyReleased(evt);
            }
        });

        jLabelPIItemPurchasingUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemPurchasingUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemPurchasingUnitPrice.setText(bundle1.getString("ChangePricePanel.PurchasingUnitPrice")); // NOI18N

        jTextFieldInvItmPurchAmount.setEditable(false);
        jTextFieldInvItmPurchAmount.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmPurchAmount.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmPurchAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelPIItemExpDate.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemExpDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemExpDate.setText(bundle1.getString("ChangePricePanel.ExpiryDate")); // NOI18N

        jTextFieldInvItmExp.setEditable(false);
        jTextFieldInvItmExp.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmExp.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmExp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabelPIItemPurchasingUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemWholeSalePrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemBatch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemBarcode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemSaleUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemQtyAvail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemExpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemLabelUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldInvItmItmName2)
                                .addComponent(jTextFieldInvItemItmName)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextFieldItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonInvItemSearch))
                                .addComponent(jComboBoxPIItemBatches, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldInvItmAvailQty)
                                .addComponent(jTextFieldInvItmWholesalePrice)
                                .addComponent(jTextFieldInvItmUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jTextFieldPIItmLablePrice, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldInvItmItmDis, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldInvItmExp, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jTextFieldInvItmPurchAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelPIItemBarcode))
                            .addComponent(jButtonInvItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxPIItemBatches, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPIItemBatch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInvItemItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPIItemName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldInvItmItmName2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPIItemLabelUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPIItmLablePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldInvItmItmDis, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPIItemDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemSaleUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemWholeSalePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmAvailQty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemQtyAvail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmPurchAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemPurchasingUnitPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmExp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemExpDate))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldAddToStQty1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStQty1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStQty1KeyPressed

    private void jTextFieldAddToStSellrPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSellrPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStSellrPriceActionPerformed

    private void jTextFieldAddToStSellrPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSellrPriceKeyPressed

    }//GEN-LAST:event_jTextFieldAddToStSellrPriceKeyPressed

    private void jTextFieldAddToStDisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStDisKeyPressed

    }//GEN-LAST:event_jTextFieldAddToStDisKeyPressed

    private void jTextFieldNewWholeSalePriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNewWholeSalePriceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNewWholeSalePriceKeyPressed

    private void jTextFieldNewSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNewSellingPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNewSellingPriceActionPerformed

    private void jTextFieldNewSellingPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNewSellingPriceKeyPressed

    }//GEN-LAST:event_jTextFieldNewSellingPriceKeyPressed

    private void jTextFieldNewDiscountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNewDiscountKeyPressed

    }//GEN-LAST:event_jTextFieldNewDiscountKeyPressed

    private void jTextFieldItmCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldItmCodeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && !jTextFieldItmCode.getText().isEmpty())
        {
            fetchItem();
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }
    }//GEN-LAST:event_jTextFieldItmCodeKeyPressed

    private void jButtonInvItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInvItemSearchActionPerformed
        fetchItem();
    }//GEN-LAST:event_jButtonInvItemSearchActionPerformed

    private void jComboBoxPIItemBatchesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxPIItemBatchesItemStateChanged
        int selectedIndex = (int) jComboBoxPIItemBatches.getSelectedIndex();
        if (lastSelectedItem != null) {
            setItemStockDataToUI(lastSelectedItem, selectedIndex);
        }
    }//GEN-LAST:event_jComboBoxPIItemBatchesItemStateChanged

    private void jComboBoxPIItemBatchesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPIItemBatchesActionPerformed
        int selectedIndex = (int) jComboBoxPIItemBatches.getSelectedIndex();
        if (lastSelectedItem != null) {
            setItemStockDataToUI(lastSelectedItem, selectedIndex);
        }
    }//GEN-LAST:event_jComboBoxPIItemBatchesActionPerformed

    private void jTextFieldInvItmItmDisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldInvItmItmDisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldInvItmItmDisActionPerformed

    private void jTextFieldInvItmItmDisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInvItmItmDisKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
            jTextFieldItmCode.requestFocus();
            break;
            case KeyEvent.VK_RIGHT:
            jTextFieldItmCode.requestFocus();//move cursor
            break;
            case KeyEvent.VK_DOWN:
            jTextFieldItmCode.requestFocus();//move cursor
            break;
            case KeyEvent.VK_LEFT:
            jTextFieldInvItmUnitPrice.requestFocus();//move cursor
            break;
            case KeyEvent.VK_UP:
            jTextFieldInvItmAvailQty.requestFocus();//move cursor
            break;
            default:
            break;
        }
    }//GEN-LAST:event_jTextFieldInvItmItmDisKeyPressed

    private void jTextFieldInvItmItmDisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInvItmItmDisKeyReleased
        //calculateAmountOnTypeInput();
    }//GEN-LAST:event_jTextFieldInvItmItmDisKeyReleased

    private void jTextFieldInvItmWholesalePriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInvItmWholesalePriceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldInvItmWholesalePriceKeyPressed

    private void jTextFieldInvItmWholesalePriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInvItmWholesalePriceKeyReleased
        //calculateAmountOnTypeInput();
    }//GEN-LAST:event_jTextFieldInvItmWholesalePriceKeyReleased

    private void jTextFieldInvItmAvailQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInvItmAvailQtyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && !jTextFieldItmCode.getText().isEmpty())
        {
            //validateAndAddToBill();
        }else if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldInvItemItmName.requestFocus();//move cursor
        }
    }//GEN-LAST:event_jTextFieldInvItmAvailQtyKeyPressed

    private void jTextFieldInvItmAvailQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInvItmAvailQtyKeyReleased
        //calculateAmountOnTypeInput();
    }//GEN-LAST:event_jTextFieldInvItmAvailQtyKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(validateNewInputs()){
            double newLabelPrice = Double.parseDouble(jTextFieldNewLabelPrice.getText());
            double newDiscount = Double.parseDouble(jTextFieldNewDiscount.getText());
            double newSellingPrice = Double.parseDouble(jTextFieldNewSellingPrice.getText());
            double newWholeSalePrice = Double.parseDouble(jTextFieldNewWholeSalePrice.getText());
            
            updateNewSellingPrices(lastSelectedItemBatch.getBatchId(), newLabelPrice, newDiscount, newSellingPrice, newWholeSalePrice);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextFieldNewLabelPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNewLabelPriceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNewLabelPriceKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonInvItemSearch;
    private javax.swing.JComboBox<String> jComboBoxPIItemBatches;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabelChangePriceNewDis;
    private javax.swing.JLabel jLabelChangePriceNewSellPrice;
    private javax.swing.JLabel jLabelChangePriceNewWSPrice;
    private javax.swing.JLabel jLabelChangePriceNewlabelPrice;
    private javax.swing.JLabel jLabelPIItemBarcode;
    private javax.swing.JLabel jLabelPIItemBatch;
    private javax.swing.JLabel jLabelPIItemDiscount;
    private javax.swing.JLabel jLabelPIItemExpDate;
    private javax.swing.JLabel jLabelPIItemLabelUnitPrice;
    private javax.swing.JLabel jLabelPIItemName;
    private javax.swing.JLabel jLabelPIItemPurchasingUnitPrice;
    private javax.swing.JLabel jLabelPIItemQtyAvail;
    private javax.swing.JLabel jLabelPIItemSaleUnitPrice;
    private javax.swing.JLabel jLabelPIItemWholeSalePrice;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldAddToStDis;
    private javax.swing.JTextField jTextFieldAddToStQty1;
    private javax.swing.JTextField jTextFieldAddToStSellrPrice;
    private javax.swing.JTextField jTextFieldInvItemItmName;
    private javax.swing.JTextField jTextFieldInvItmAvailQty;
    private javax.swing.JTextField jTextFieldInvItmExp;
    private javax.swing.JTextField jTextFieldInvItmItmDis;
    private javax.swing.JTextField jTextFieldInvItmItmName2;
    private javax.swing.JTextField jTextFieldInvItmPurchAmount;
    private javax.swing.JTextField jTextFieldInvItmUnitPrice;
    private javax.swing.JTextField jTextFieldInvItmWholesalePrice;
    private javax.swing.JTextField jTextFieldItmCode;
    private javax.swing.JTextField jTextFieldNewDiscount;
    private javax.swing.JTextField jTextFieldNewLabelPrice;
    private javax.swing.JTextField jTextFieldNewSellingPrice;
    private javax.swing.JTextField jTextFieldNewWholeSalePrice;
    private javax.swing.JTextField jTextFieldPIItmLablePrice;
    // End of variables declaration//GEN-END:variables

    private boolean validateNewInputs() {
        double newLabelPrice = 0;
        double newDiscount = 0;
        double newSellingPrice = 0;
        double newWholeSalePrice = 0;
        
        String errorMessage = null;
                
        // Check input formats
        try {
            newLabelPrice = Double.parseDouble(jTextFieldNewLabelPrice.getText());
        } catch (NumberFormatException e) {
            errorMessage = "Invalid vaue for Label Price";
        }
        
        try {
            newDiscount = Double.parseDouble(jTextFieldNewDiscount.getText());
        } catch (NumberFormatException e) {
            errorMessage = "Invalid vaue for Discount";
        }
        
        try {
            newSellingPrice = Double.parseDouble(jTextFieldNewSellingPrice.getText());
        } catch (NumberFormatException e) {
            errorMessage = "Invalid vaue for Selling Price";
        }
        
        try {
            newWholeSalePrice = Double.parseDouble(jTextFieldNewWholeSalePrice.getText());
        } catch (NumberFormatException e) {
            errorMessage = "Invalid vaue for Whole Sale Price";
        }
        // Check calculations
        if (newLabelPrice < newDiscount) {
            errorMessage = "Error Values - Label Price should be higher than Discount";
        }
        if (newLabelPrice != (newDiscount + newSellingPrice)) {
            errorMessage = "Error Values - Label Price, Discount & Selling Price";
        }
        if (newWholeSalePrice > newSellingPrice) {
            errorMessage = "Error Values - Whole Sale Price";
        }
        
        // Check batch selected        
        if (lastSelectedItemBatch == null) {
            errorMessage = "Please select the Batch";
        }
        
        // Result
        if (errorMessage != null) {
            JOptionPane.showConfirmDialog(this, errorMessage);
            return false;
        }else{
            return true;
        }
    }

    private void updateNewSellingPrices(long batchId, 
            double newLabelPrice, double newDiscount, 
            double newSellingPrice, double newWholeSalePrice) {
        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);

        SwingWorker<CommonResponse, Void> worker = new SwingWorker<CommonResponse, Void>() {
            @Override
            protected CommonResponse doInBackground() {
                loader.start(); // show loader
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                return ServerAPIConnection.getInstance(aPIHeaderData).batchPriceUpdate(batchId, newLabelPrice, newDiscount, newSellingPrice, newWholeSalePrice);
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                                
                // load data
                try {
                    CommonResponse response = get();

                    EasyPOSMessageDialog.showApiResponseDialog(ChangePricePanel.this.getRootPane(), response.getAPIResponse());

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(ChangePricePanel.this.getRootPane(), ex.getMessage());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
}
