
package easyPOS.invoice;

import appDataModels.APIHeaderData;
import appDataModels.ItemModel;
import appDataModels.ItemStockModel;
import appDataModels.PurchaseInvoiceModel;
import appDataModels.PurchaseItemModel;
import appDataModels.PurchasingInvoiceItemUIDataModel;
import appDataModels.SupplierModel;
import control.ApplicationDataManager;
import control.EasyPosLogger;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import localDatabase.DatabaseManager;
import serverDataModels.Item;
import serverResponseDataModel.CommonResponse;
import tableModels.PurchasingInvoiceItemsTbl;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class PurchasingInvoiceItemsPanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    private List<PurchaseInvoiceModel> pendingInvoices;
    private JPanel thisPanel;
    private PurchaseInvoiceModel selectedPurchaseInvoiceModel;
    private InvoicePanelActions invoicePanelActions;
    private List<PurchasingInvoiceItemUIDataModel> purchaseItems;
    private ItemModel lastSelectedItem;
    /**
     * Creates new form PendingInvoicesPanel
     */
    public PurchasingInvoiceItemsPanel() {
        initComponents();
        switchLanguage();
        this.thisPanel = this;
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    public void showPanel(PurchaseInvoiceModel pim, InvoicePanelActions invoicePanelActions){
        this.selectedPurchaseInvoiceModel = pim;
        this.invoicePanelActions = invoicePanelActions;
        
        this.purchaseItems = new ArrayList<>();
        for (PurchaseItemModel purchaseItem : pim.getPurchaseItems()) {            
            ItemModel itemModel = DatabaseManager.getInstance().getItemByItemId(purchaseItem.getItemId());
            purchaseItems.add(new PurchasingInvoiceItemUIDataModel(itemModel, purchaseItem));
        }
        
        loadInvoiceItemsTable(purchaseItems);
    }
    
    private void loadInvoiceItemsTable(List<PurchasingInvoiceItemUIDataModel> list){
        PurchasingInvoiceItemsTbl ctt = new PurchasingInvoiceItemsTbl(list);
        jTablePendingInvoices.setModel(ctt);        
        jTablePendingInvoices.setRowHeight(35);
    }

    private void fetchItemLocalOrServer()
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
            JOptionPane.showMessageDialog(null, "Item not found in Local Machine!");
            loadNewItemFromServer(jTextFieldItmCode.getText());
            // Find in server
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
    
    private void loadNewItemFromServer(String barcode) {
                
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

                return ServerAPIConnection.getInstance(aPIHeaderData).getItem(barcode);
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                                
                // load data
                try {
                    CommonResponse response = get();

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
                        
                        lastSelectedItem = new ItemModel((Item) response.getData());
            
                        // Display details
                        List<String> dropdownItems = new ArrayList<>();
                        for (ItemStockModel itemStockModel : lastSelectedItem.getStock()) {
                            dropdownItems.add(Integer.toString((int) itemStockModel.getBatchId()));
                        }
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropdownItems.toArray(new String[0]));
                        jComboBoxPIItemBatches.setModel(model);

                        if (lastSelectedItem.getStock().size() == 1) {                                
                            setItemStockDataToUI(lastSelectedItem, 0);
                        }
                        
                        jTextFieldInvItemItmName.setText(lastSelectedItem.getItemName());
                        jTextFieldInvItmItmName2.setText(lastSelectedItem.getItemNameSin());
                    } else {
                        JOptionPane.showMessageDialog(PurchasingInvoiceItemsPanel.this.getRootPane(), label, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(PurchasingInvoiceItemsPanel.this.getRootPane(), "Unexpected error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    private void setItemStockDataToUI(ItemModel itemModel, int index)
    {
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelPIItemBarcode = new javax.swing.JLabel();
        jTextFieldItmCode = new javax.swing.JTextField();
        jLabelPIItemName = new javax.swing.JLabel();
        jTextFieldInvItemItmName = new javax.swing.JTextField();
        jLabelPIItemSaleUnitPrice = new javax.swing.JLabel();
        jTextFieldInvItmUnitPrice = new javax.swing.JTextField();
        jLabelPIItemDiscount = new javax.swing.JLabel();
        jTextFieldInvItmItmDis = new javax.swing.JTextField();
        jLabelPIItemWholeSalePrice = new javax.swing.JLabel();
        jTextFieldInvItmWholesalePrice = new javax.swing.JTextField();
        jLabelPIItemQtyAvail = new javax.swing.JLabel();
        jTextFieldInvItmAvailQty = new javax.swing.JTextField();
        jLabelPIItemPurchasingUnitPrice = new javax.swing.JLabel();
        jTextFieldInvItmPurchAmount = new javax.swing.JTextField();
        jTextFieldInvItmItmName2 = new javax.swing.JTextField();
        jLabelPIItemBatch = new javax.swing.JLabel();
        jComboBoxPIItemBatches = new javax.swing.JComboBox<>();
        jLabelPIItemExpDate = new javax.swing.JLabel();
        jTextFieldInvItmExp = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButtonInvItemSearch = new javax.swing.JButton();
        jLabelPIItemLabelUnitPrice = new javax.swing.JLabel();
        jTextFieldPIItmLablePrice = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabelPIItemNewPurchasingUnitPrice = new javax.swing.JLabel();
        jLabelPIItemNewDiscount = new javax.swing.JLabel();
        jLabelPIItemNewWholeSalePrice = new javax.swing.JLabel();
        jLabelPIItemNewQty = new javax.swing.JLabel();
        jTextFieldPIItmPurchasePrice = new javax.swing.JTextField();
        jTextFieldPIItmDis = new javax.swing.JTextField();
        jTextFieldPIItmWholeSalePrice = new javax.swing.JTextField();
        jTextFieldPIItmQty = new javax.swing.JTextField();
        jButtonPIItemAddToStock = new javax.swing.JButton();
        jLabelPIItemNewExpDate = new javax.swing.JLabel();
        jLabelPIItemNewLabelUnitPrice = new javax.swing.JLabel();
        jTextFieldPIItmLabelSellPrice = new javax.swing.JTextField();
        jLabelPIItemNewSalelUnitPrice = new javax.swing.JLabel();
        jTextFieldPIItmRetailSellPrice = new javax.swing.JTextField();
        jDateChooserPIItmExpDate = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePendingInvoices = new javax.swing.JTable();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/invoice/Bundle"); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PurchasingInvoiceItemPanel.ItemDetails"))); // NOI18N

        jLabelPIItemBarcode.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemBarcode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemBarcode.setText(bundle.getString("PurchasingInvoiceItemPanel.Barcode")); // NOI18N

        jTextFieldItmCode.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldItmCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldItmCodeKeyPressed(evt);
            }
        });

        jLabelPIItemName.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemName.setText(bundle.getString("PurchasingInvoiceItemPanel.ItemName")); // NOI18N

        jTextFieldInvItemItmName.setEditable(false);
        jTextFieldInvItemItmName.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItemItmName.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabelPIItemSaleUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemSaleUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemSaleUnitPrice.setText(bundle.getString("PurchasingInvoiceItemPanel.SellingUnitPrice")); // NOI18N

        jTextFieldInvItmUnitPrice.setEditable(false);
        jTextFieldInvItmUnitPrice.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmUnitPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelPIItemDiscount.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemDiscount.setText(bundle.getString("PurchasingInvoiceItemPanel.Discount")); // NOI18N

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

        jLabelPIItemWholeSalePrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemWholeSalePrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemWholeSalePrice.setText(bundle.getString("PurchasingInvoiceItemPanel.WholeSalePrice")); // NOI18N

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
        jLabelPIItemQtyAvail.setText(bundle.getString("PurchasingInvoiceItemPanel.QtyAvailable")); // NOI18N

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
        jLabelPIItemPurchasingUnitPrice.setText(bundle.getString("PurchasingInvoiceItemPanel.PurchasingUnitPrice")); // NOI18N

        jTextFieldInvItmPurchAmount.setEditable(false);
        jTextFieldInvItmPurchAmount.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmPurchAmount.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmPurchAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextFieldInvItmItmName2.setEditable(false);
        jTextFieldInvItmItmName2.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmItmName2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabelPIItemBatch.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemBatch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemBatch.setText(bundle.getString("PurchasingInvoiceItemPanel.Batch")); // NOI18N

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

        jLabelPIItemExpDate.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemExpDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemExpDate.setText(bundle.getString("PurchasingInvoiceItemPanel.ExpiryDate")); // NOI18N

        jTextFieldInvItmExp.setEditable(false);
        jTextFieldInvItmExp.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldInvItmExp.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldInvItmExp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus_icon.png"))); // NOI18N

        jButtonInvItemSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        jButtonInvItemSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInvItemSearchActionPerformed(evt);
            }
        });

        jLabelPIItemLabelUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemLabelUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemLabelUnitPrice.setText(bundle.getString("PurchasingInvoiceItemPanel.LabelPrice")); // NOI18N

        jTextFieldPIItmLablePrice.setEditable(false);
        jTextFieldPIItmLablePrice.setBackground(new java.awt.Color(235, 235, 235));
        jTextFieldPIItmLablePrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmLablePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabelPIItemPurchasingUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemWholeSalePrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemBatch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemBarcode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemSaleUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemQtyAvail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemExpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemLabelUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPIItemDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldInvItmItmName2)
                                .addComponent(jTextFieldInvItemItmName)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextFieldItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonInvItemSearch)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jComboBoxPIItemBatches, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldInvItmAvailQty)
                                .addComponent(jTextFieldInvItmWholesalePrice)
                                .addComponent(jTextFieldInvItmUnitPrice)))
                        .addComponent(jTextFieldPIItmLablePrice, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldInvItmItmDis, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldInvItmExp, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jTextFieldInvItmPurchAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPIItemBarcode))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButtonInvItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxPIItemBatches, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemBatch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldInvItemItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldInvItmItmName2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPIItemLabelUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPIItmLablePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmItmDis, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemSaleUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemWholeSalePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmAvailQty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemQtyAvail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmPurchAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemPurchasingUnitPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInvItmExp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemExpDate))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PurchasingInvoiceItemPanel.NewDetails"))); // NOI18N

        jLabelPIItemNewPurchasingUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewPurchasingUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewPurchasingUnitPrice.setText(bundle.getString("PurchasingInvoiceItemPanel.PurchasingUnitPrice")); // NOI18N

        jLabelPIItemNewDiscount.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewDiscount.setText(bundle.getString("PurchasingInvoiceItemPanel.Discount")); // NOI18N

        jLabelPIItemNewWholeSalePrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewWholeSalePrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewWholeSalePrice.setText(bundle.getString("PurchasingInvoiceItemPanel.WholeSalePrice")); // NOI18N

        jLabelPIItemNewQty.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewQty.setText(bundle.getString("PurchasingInvoiceItemPanel.Qty")); // NOI18N

        jTextFieldPIItmPurchasePrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmPurchasePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextFieldPIItmDis.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPIItmDis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPIItmDisActionPerformed(evt);
            }
        });
        jTextFieldPIItmDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmDisKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmDisKeyReleased(evt);
            }
        });

        jTextFieldPIItmWholeSalePrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmWholeSalePrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPIItmWholeSalePrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmWholeSalePriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmWholeSalePriceKeyReleased(evt);
            }
        });

        jTextFieldPIItmQty.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPIItmQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmQtyKeyReleased(evt);
            }
        });

        jButtonPIItemAddToStock.setText(bundle.getString("PurchasingInvoiceItemPanel.AddToStock")); // NOI18N
        jButtonPIItemAddToStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPIItemAddToStockActionPerformed(evt);
            }
        });

        jLabelPIItemNewExpDate.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewExpDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewExpDate.setText(bundle.getString("PurchasingInvoiceItemPanel.ExpiryDate")); // NOI18N

        jLabelPIItemNewLabelUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewLabelUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewLabelUnitPrice.setText(bundle.getString("PurchasingInvoiceItemPanel.LabelPrice")); // NOI18N

        jTextFieldPIItmLabelSellPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmLabelSellPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelPIItemNewSalelUnitPrice.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabelPIItemNewSalelUnitPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPIItemNewSalelUnitPrice.setText(bundle.getString("PurchasingInvoiceItemPanel.SellingUnitPrice")); // NOI18N

        jTextFieldPIItmRetailSellPrice.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldPIItmRetailSellPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPIItmRetailSellPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPIItmRetailSellPriceActionPerformed(evt);
            }
        });
        jTextFieldPIItmRetailSellPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmRetailSellPriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPIItmRetailSellPriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonPIItemAddToStock)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelPIItemNewQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPIItemNewWholeSalePrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPIItemNewDiscount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPIItemNewLabelUnitPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPIItemNewPurchasingUnitPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPIItemNewSalelUnitPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPIItemNewExpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldPIItmQty, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jTextFieldPIItmWholeSalePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jTextFieldPIItmLabelSellPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jTextFieldPIItmPurchasePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jTextFieldPIItmDis, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jTextFieldPIItmRetailSellPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jDateChooserPIItmExpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPIItemNewPurchasingUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPIItmPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPIItemNewLabelUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPIItmLabelSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPIItmDis, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemNewDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPIItmRetailSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemNewSalelUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPIItmWholeSalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemNewWholeSalePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPIItmQty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPIItemNewQty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPIItemNewExpDate)
                    .addComponent(jDateChooserPIItmExpDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPIItemAddToStock)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("PurchasingInvoiceItemPanel.InvoiceItems"))); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/news.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldItmCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldItmCodeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && !jTextFieldItmCode.getText().isEmpty())
        {
            fetchItemLocalOrServer();
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

    private void jTextFieldPIItmDisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPIItmDisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmDisActionPerformed

    private void jTextFieldPIItmDisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmDisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmDisKeyPressed

    private void jTextFieldPIItmDisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmDisKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmDisKeyReleased

    private void jTextFieldPIItmWholeSalePriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmWholeSalePriceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmWholeSalePriceKeyPressed

    private void jTextFieldPIItmWholeSalePriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmWholeSalePriceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmWholeSalePriceKeyReleased

    private void jTextFieldPIItmQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmQtyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmQtyKeyPressed

    private void jTextFieldPIItmQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmQtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmQtyKeyReleased

    private void jButtonInvItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInvItemSearchActionPerformed
        fetchItemLocalOrServer();
    }//GEN-LAST:event_jButtonInvItemSearchActionPerformed

    private void jButtonPIItemAddToStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPIItemAddToStockActionPerformed
        if (validateInputs()) {
            addToTempItemTable();
        }        
    }//GEN-LAST:event_jButtonPIItemAddToStockActionPerformed

    private void jTextFieldPIItmRetailSellPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPIItmRetailSellPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmRetailSellPriceActionPerformed

    private void jTextFieldPIItmRetailSellPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmRetailSellPriceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmRetailSellPriceKeyPressed

    private void jTextFieldPIItmRetailSellPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPIItmRetailSellPriceKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPIItmRetailSellPriceKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Save to server
        PurchaseInvoiceModel newCopyPIM = new PurchaseInvoiceModel(selectedPurchaseInvoiceModel);
        List<PurchaseItemModel> pItms = new ArrayList<>();
        for (PurchasingInvoiceItemUIDataModel purchaseItemUIData : purchaseItems) {
            pItms.add(purchaseItemUIData.getPurchaseItemModel());
        }
        newCopyPIM.setPurchaseItems(pItms);
        
        addOrUpdateInvoiceAction(newCopyPIM);
    }//GEN-LAST:event_jButton5ActionPerformed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedRowIndex = jTablePendingInvoices.getSelectedRow();
        
        if (!purchaseItems.isEmpty() && purchaseItems.size() > selectedRowIndex) {
            purchaseItems.remove(selectedRowIndex);
            loadInvoiceItemsTable(purchaseItems);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private boolean validateInputs(){
        String errorMsg = "";
        boolean isValid = true;
        
        double purchasePrice = util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmPurchasePrice.getText());            
        double labelPrice = util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmLabelSellPrice.getText());
        double discount = util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmDis.getText());
        double salePrice = util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmRetailSellPrice.getText());
        double wholeSalePrice = util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmWholeSalePrice.getText());            
        double qty = Double.parseDouble(jTextFieldPIItmQty.getText());
        
        if (util.DateTimeUtil.getDBFormatStringFromDate(jDateChooserPIItmExpDate.getDate()) == null) {
            errorMsg = "Expire Date Mandatory";
            isValid = false;
        }
        
        if (purchasePrice <= 0) {
            errorMsg = "Invalid Purchasing Price";
            isValid = false;
        }
        
        if (labelPrice <= 0) {
            errorMsg = "Invalid Label Price";
            isValid = false;
        }
        
        if (salePrice <= 0) {
            errorMsg = "Invalid Sale Price";
            isValid = false;
        }
        
        if (wholeSalePrice <= 0) {
            errorMsg = "Invalid Whole Sale Price";
            isValid = false;
        }
        
        if (qty <= 0) {
            errorMsg = "Invalid Qty";
            isValid = false;
        }
        
        if ((labelPrice - discount) != salePrice) {
            errorMsg = "Invalid Prices. Should be (Label Price - Discount = Sale Price)";
            isValid = false;
        }
        
        if (wholeSalePrice > salePrice) {
            errorMsg = "Invalid Prices. Wholesale Price > Retail Price";
            isValid = false;
        }
                
        if (!isValid) {
            JOptionPane.showMessageDialog(this, errorMsg);
        }
        return isValid;
    }
    
    private void addToTempItemTable() {
        if (lastSelectedItem == null) {
            JOptionPane.showMessageDialog(this, "Select the item to proceed");
        }
        else{
            purchaseItems.add(new PurchasingInvoiceItemUIDataModel (lastSelectedItem, getDataFromUI()));
            loadInvoiceItemsTable(purchaseItems);
        }
    }
    
    private PurchaseItemModel getDataFromUI(){
        
        if (lastSelectedItem == null) {
            return null;
        }
        try {
            PurchaseItemModel pim = new PurchaseItemModel();
            
            pim.setItemId(lastSelectedItem.getItemId());
            
            pim.setAddedBy(ApplicationDataManager.getInstance().getLoggedInUser().getUserName());
            pim.setAddedDate(util.DateTimeUtil.getCurrentDateTimeDBFormat());
            pim.setBatchNum(""); // Not used because not practical to enter the batch number of each product
            pim.setBatchId(0); // Set batch number 0 here and will be taken at server side
            pim.setExpDate(util.DateTimeUtil.getDBFormatStringFromDate(jDateChooserPIItmExpDate.getDate()));
            pim.setInsertDateTime(util.DateTimeUtil.getCurrentDateTimeDBFormat());            
            pim.setPurchaseUnitPrice(util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmPurchasePrice.getText()));            
            pim.setLabelPrice(util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmLabelSellPrice.getText()));
            pim.setDiscount(util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmDis.getText()));
            pim.setSellingUnitPrice(util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmRetailSellPrice.getText()));
            pim.setWholesalePrice(util.GeneralUtil.currencyStringToDouble(jTextFieldPIItmWholeSalePrice.getText()));            
            pim.setQty(Double.parseDouble(jTextFieldPIItmQty.getText()));
            pim.setUpdatedBy(ApplicationDataManager.getInstance().getLoggedInUser().getUserName());
            pim.setUpdatedDate(util.DateTimeUtil.getCurrentDateTimeDBFormat());
                        
            return pim;
            
        } catch (NumberFormatException e) {
            EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, e.toString());
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
        
        
        return null;
   }
    
    private void addOrUpdateInvoiceAction(PurchaseInvoiceModel pim)
    {
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

                return ServerAPIConnection.getInstance(aPIHeaderData).addUpdatePurchasingInvoice(pim.newPurchaseInvoiceDTO());
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop();
                
                
                CommonResponse commonResponse;
                try {
                    commonResponse = get();
                    if (commonResponse.getAPIResponse().isSuccess()) {
                        clearAllFields();
                        invoicePanelActions.successfullyUpdated();
                    }
                    else{
                        EasyPOSMessageDialog.showErrorMessageDialog(PurchasingInvoiceItemsPanel.this, commonResponse.getAPIResponse());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(PendingPurchasingInvoicesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
            }

        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    
    private void clearAllFields() {
        jTextFieldPIItmPurchasePrice.setText("");
    }
            
    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/invoice/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            //create the font to use. Specify the size!

            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);

            jTextFieldInvItmItmName2.setFont(customFont1);

            jLabelPIItemBarcode.setFont(customFont1);
            jLabelPIItemBatch.setFont(customFont1);
            jLabelPIItemName.setFont(customFont1);
            jLabelPIItemLabelUnitPrice.setFont(customFont1);
            jLabelPIItemDiscount.setFont(customFont1);
            jLabelPIItemSaleUnitPrice.setFont(customFont1);
            jLabelPIItemWholeSalePrice.setFont(customFont1);
            jLabelPIItemQtyAvail.setFont(customFont1);
            jLabelPIItemPurchasingUnitPrice.setFont(customFont1);
            jLabelPIItemExpDate.setFont(customFont1);

            jLabelPIItemNewPurchasingUnitPrice.setFont(customFont1);
            jLabelPIItemNewLabelUnitPrice.setFont(customFont1);
            jLabelPIItemNewDiscount.setFont(customFont1);
            jLabelPIItemNewSalelUnitPrice.setFont(customFont1);
            jLabelPIItemNewWholeSalePrice.setFont(customFont1);
            jLabelPIItemNewQty.setFont(customFont1);
            jLabelPIItemNewExpDate.setFont(customFont1);

            jButtonPIItemAddToStock.setFont(customFont1);

        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        //use the font
        
        jLabelPIItemBarcode.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.Barcode"));
        jLabelPIItemBatch.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.Batch"));
        jLabelPIItemName.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.ItemName"));
        jLabelPIItemLabelUnitPrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.LabelPrice"));
        jLabelPIItemDiscount.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.Discount"));
        jLabelPIItemSaleUnitPrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.SellingUnitPrice"));
        jLabelPIItemWholeSalePrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.WholeSalePrice"));
        jLabelPIItemQtyAvail.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.QtyAvailable"));
        jLabelPIItemPurchasingUnitPrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.PurchasingUnitPrice")); 
        jLabelPIItemExpDate.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.ExpiryDate")); 
        
        jLabelPIItemNewPurchasingUnitPrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.PurchasingUnitPrice"));
        jLabelPIItemNewLabelUnitPrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.LabelPrice"));
        jLabelPIItemNewDiscount.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.Discount"));
        jLabelPIItemNewSalelUnitPrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.SellingUnitPrice"));
        jLabelPIItemNewWholeSalePrice.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.WholeSalePrice"));
        jLabelPIItemNewQty.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.Qty")); 
        jLabelPIItemNewExpDate.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.ExpiryDate")); 
        
        jButtonPIItemAddToStock.setText(resourceBundle.getString("PurchasingInvoiceItemPanel.AddToStock")); 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonInvItemSearch;
    private javax.swing.JButton jButtonPIItemAddToStock;
    private javax.swing.JComboBox<String> jComboBoxPIItemBatches;
    private com.toedter.calendar.JDateChooser jDateChooserPIItmExpDate;
    private javax.swing.JLabel jLabelPIItemBarcode;
    private javax.swing.JLabel jLabelPIItemBatch;
    private javax.swing.JLabel jLabelPIItemDiscount;
    private javax.swing.JLabel jLabelPIItemExpDate;
    private javax.swing.JLabel jLabelPIItemLabelUnitPrice;
    private javax.swing.JLabel jLabelPIItemName;
    private javax.swing.JLabel jLabelPIItemNewDiscount;
    private javax.swing.JLabel jLabelPIItemNewExpDate;
    private javax.swing.JLabel jLabelPIItemNewLabelUnitPrice;
    private javax.swing.JLabel jLabelPIItemNewPurchasingUnitPrice;
    private javax.swing.JLabel jLabelPIItemNewQty;
    private javax.swing.JLabel jLabelPIItemNewSalelUnitPrice;
    private javax.swing.JLabel jLabelPIItemNewWholeSalePrice;
    private javax.swing.JLabel jLabelPIItemPurchasingUnitPrice;
    private javax.swing.JLabel jLabelPIItemQtyAvail;
    private javax.swing.JLabel jLabelPIItemSaleUnitPrice;
    private javax.swing.JLabel jLabelPIItemWholeSalePrice;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePendingInvoices;
    private javax.swing.JTextField jTextFieldInvItemItmName;
    private javax.swing.JTextField jTextFieldInvItmAvailQty;
    private javax.swing.JTextField jTextFieldInvItmExp;
    private javax.swing.JTextField jTextFieldInvItmItmDis;
    private javax.swing.JTextField jTextFieldInvItmItmName2;
    private javax.swing.JTextField jTextFieldInvItmPurchAmount;
    private javax.swing.JTextField jTextFieldInvItmUnitPrice;
    private javax.swing.JTextField jTextFieldInvItmWholesalePrice;
    private javax.swing.JTextField jTextFieldItmCode;
    private javax.swing.JTextField jTextFieldPIItmDis;
    private javax.swing.JTextField jTextFieldPIItmLabelSellPrice;
    private javax.swing.JTextField jTextFieldPIItmLablePrice;
    private javax.swing.JTextField jTextFieldPIItmPurchasePrice;
    private javax.swing.JTextField jTextFieldPIItmQty;
    private javax.swing.JTextField jTextFieldPIItmRetailSellPrice;
    private javax.swing.JTextField jTextFieldPIItmWholeSalePrice;
    // End of variables declaration//GEN-END:variables

}
