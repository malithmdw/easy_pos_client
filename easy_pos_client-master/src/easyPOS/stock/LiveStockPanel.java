package easyPOS.stock;

import appDataModels.APIHeaderData;
import appDataModels.BarcodeLableItemDataModel;
import appDataModels.CategoryModel;
import control.ApplicationDataManager;
import control.EasyPosLogger;
import control.RuntimeDataManager;
import dataModels.SupplierDataModel;
import dbOperations.StockDBOperation;
import dbOperations.SupplierDBOperation;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import localDatabase.DatabaseManager;
import serverDataModels.Category;
import serverDataModels.Item;
import serverDataModels.ItemStock;
import serverResponseDataModel.CommonResponse;
import tableModels.LiveStockBatchTbl;
import tableModels.LiveStockItemTbl;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class LiveStockPanel extends javax.swing.JPanel {

    SupplierDBOperation supplierDBOperation = new SupplierDBOperation();
    
    private StockPanel parent;
    private List<Item> itemDataList;
    private ArrayList<SupplierDataModel> allActiveSuppliers;
    
    /**
     * Creates new form StockItemListPanel
     */
    public LiveStockPanel() {
        initComponents();
    }
    
    public void loadInitialDataToUI(StockPanel parent)
    {
        this.parent = parent;
        getAndLoadAllLiveStock();
    }
    
    private void getAndLoadAllLiveStock() {        
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

                return ServerAPIConnection.getInstance(aPIHeaderData).getAllItemStock();
            }

            @Override
            protected void done() {
                loader.stop(); // hide loader
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
                        // Load table 
                        itemDataList = (ArrayList<Item>) response.getData();
                        List<CategoryModel> categorys = DatabaseManager.getInstance().getCategories();
                        
                        for (Item item : itemDataList) {
                            for (CategoryModel category : categorys) {
                                if (category.getCategoryId() == item.category_id) {
                                    item.category = category.newCategoryDTO();
                                    break;
                                }
                            }
                        }
                        loadStockTable();
                        
                    } else {
                        JOptionPane.showMessageDialog(LiveStockPanel.this.getRootPane(), label, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(LiveStockPanel.this.getRootPane(), "Unexpected error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR, ex.toString());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }

    private void loadStockTable() {
        LiveStockItemTbl stoLTbl = new LiveStockItemTbl(itemDataList);
        jTableCurStockItems.setModel(stoLTbl);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        jTableCurStockItems.getColumnModel().getColumn(1).setMinWidth(20);// itemcode
        jTableCurStockItems.getColumnModel().getColumn(2).setMinWidth(150);//barcode
        jTableCurStockItems.getColumnModel().getColumn(3).setMinWidth(300);//item name
        jTableCurStockItems.getColumnModel().getColumn(4).setMinWidth(300);//item name
        jTableCurStockItems.getColumnModel().getColumn(5).setMinWidth(300);//item name
        jTableCurStockItems.getColumnModel().getColumn(6).setMinWidth(150);// category
        jTableCurStockItems.getColumnModel().getColumn(7).setMinWidth(150);// measure unit
                
        jTableCurStockItems.setRowHeight(35);
        
        if(!itemDataList.isEmpty())
            jTableCurStockItems.setRowSelectionInterval(0, 0);
    }
    
    private void loadStockBatchTable(Item item) {
        LiveStockBatchTbl stoLTbl = new LiveStockBatchTbl(item.stock);
        jTableLiveStockItemBatches.setModel(stoLTbl);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        
        jTableLiveStockItemBatches.getColumnModel().getColumn(1).setMinWidth(50);// Batch ID
        jTableLiveStockItemBatches.getColumnModel().getColumn(2).setMinWidth(100);//QTY Available
        jTableLiveStockItemBatches.getColumnModel().getColumn(3).setMinWidth(150);//Label Price
        jTableLiveStockItemBatches.getColumnModel().getColumn(4).setMinWidth(150);//Discount per 1
        jTableLiveStockItemBatches.getColumnModel().getColumn(5).setMinWidth(150);//Retail Price
        jTableLiveStockItemBatches.getColumnModel().getColumn(6).setMinWidth(150);// Whole Sale Price
        jTableLiveStockItemBatches.getColumnModel().getColumn(7).setMinWidth(150);// Purchasing Unit Price
        jTableLiveStockItemBatches.getColumnModel().getColumn(8).setMinWidth(200);// Expiry Date
        
        for (int i = 2; i < 9; i++) {
            jTableLiveStockItemBatches.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
        jTableLiveStockItemBatches.setRowHeight(35);
    }
        
    private void loadSuppliersToDropdown() {
        allActiveSuppliers = supplierDBOperation.getAllSuppliers(ApplicationDataManager.getInstance().getLoggedInUser().getInstituteId());
        String[] supplierDropdownItems = new String[allActiveSuppliers.size() + 1]; 
        
        supplierDropdownItems[0] = "-- ALL --";
        
        for (int i = 0; i < allActiveSuppliers.size(); i++) {
            SupplierDataModel supplier = allActiveSuppliers.get(i);
            supplierDropdownItems[i+1] = supplier.getSupplierName();
        }
        
        jComboBoxSearchItem.setModel(new DefaultComboBoxModel<>(supplierDropdownItems));
    }
    
    void loadStokeTbleFull(int situation,String searchInput1,String searchInput2){
//        stList=stock.getStocks(situation,searchInput1,searchInput2);
//        StockLoadTblFull stoLTblFull=new StockLoadTblFull(stList);
//        jTableCurStoke.setModel(stoLTblFull);
//                DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
//                rightRenderer.setHorizontalAlignment(jLabel1.RIGHT);
//                jTableCurStoke.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//                jTableCurStoke.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
//                jTableCurStoke.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
//                jTableCurStoke.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
//                jTableCurStoke.getColumnModel().getColumn(12).setCellRenderer(rightRenderer); 
//                
//                jTableCurStoke.getColumnModel().getColumn(2).setMinWidth(150);
//                jTableCurStoke.getColumnModel().getColumn(3).setMinWidth(200);
//                jTableCurStoke.getColumnModel().getColumn(9).setMinWidth(100);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jTextFieldStokeSearch = new javax.swing.JTextField();
        jComboBoxSearchItem = new javax.swing.JComboBox();
        jButtonStokeSearch = new javax.swing.JButton();
        jPanelStockEditDelete = new javax.swing.JPanel();
        jButtonPrintAList = new javax.swing.JButton();
        jButtonStockFullPrint = new javax.swing.JButton();
        jButtonStockBarcodePrint = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableCurStockItems = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLiveStockItemBatches = new javax.swing.JTable();

        jPanel7.setPreferredSize(new java.awt.Dimension(1009, 501));

        jTextFieldStokeSearch.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextFieldStokeSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldStokeSearchKeyPressed(evt);
            }
        });

        jComboBoxSearchItem.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBoxSearchItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--All--", "by Code", "by Name", "by Sub Name", "by Supplier Code" }));
        jComboBoxSearchItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxSearchItemItemStateChanged(evt);
            }
        });

        jButtonStokeSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonStokeSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/stock/Bundle"); // NOI18N
        jButtonStokeSearch.setText(bundle.getString("LiveStockPanel.jButtonStokeSearch.text")); // NOI18N
        jButtonStokeSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStokeSearchActionPerformed(evt);
            }
        });

        jButtonPrintAList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonPrintAList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        jButtonPrintAList.setText(bundle.getString("LiveStockPanel.jButtonPrintAList.text")); // NOI18N
        jButtonPrintAList.setMaximumSize(new java.awt.Dimension(173, 41));
        jButtonPrintAList.setMinimumSize(new java.awt.Dimension(173, 41));
        jButtonPrintAList.setPreferredSize(new java.awt.Dimension(175, 41));
        jButtonPrintAList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintAListActionPerformed(evt);
            }
        });

        jButtonStockFullPrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonStockFullPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        jButtonStockFullPrint.setText(bundle.getString("LiveStockPanel.jButtonStockFullPrint.text")); // NOI18N
        jButtonStockFullPrint.setMaximumSize(new java.awt.Dimension(173, 41));
        jButtonStockFullPrint.setMinimumSize(new java.awt.Dimension(173, 41));
        jButtonStockFullPrint.setPreferredSize(new java.awt.Dimension(175, 41));
        jButtonStockFullPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockFullPrintActionPerformed(evt);
            }
        });

        jButtonStockBarcodePrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonStockBarcodePrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        jButtonStockBarcodePrint.setText(bundle.getString("LiveStockPanel.jButtonStockBarcodePrint.text")); // NOI18N
        jButtonStockBarcodePrint.setMaximumSize(new java.awt.Dimension(173, 41));
        jButtonStockBarcodePrint.setMinimumSize(new java.awt.Dimension(173, 41));
        jButtonStockBarcodePrint.setPreferredSize(new java.awt.Dimension(175, 41));
        jButtonStockBarcodePrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockBarcodePrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelStockEditDeleteLayout = new javax.swing.GroupLayout(jPanelStockEditDelete);
        jPanelStockEditDelete.setLayout(jPanelStockEditDeleteLayout);
        jPanelStockEditDeleteLayout.setHorizontalGroup(
            jPanelStockEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStockEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonPrintAList, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonStockFullPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonStockBarcodePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelStockEditDeleteLayout.setVerticalGroup(
            jPanelStockEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStockEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStockEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPrintAList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonStockFullPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonStockBarcodePrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LiveStockPanel.jPanel1.border.title"))); // NOI18N

        jScrollPane5.setAutoscrolls(true);

        jTableCurStockItems.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableCurStockItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCurStockItems.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableCurStockItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCurStockItemsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableCurStockItems);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LiveStockPanel.jPanel2.border.title"))); // NOI18N

        jTableLiveStockItemBatches.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableLiveStockItemBatches.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableLiveStockItemBatches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLiveStockItemBatchesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableLiveStockItemBatches);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelStockEditDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextFieldStokeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxSearchItem, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonStokeSearch)))
                        .addGap(0, 337, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonStokeSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldStokeSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSearchItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelStockEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 923, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldStokeSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldStokeSearchKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            int searchByIndex=jComboBoxSearchItem.getSelectedIndex();
            loadStokeTbleFull(searchByIndex,jTextFieldStokeSearch.getText(),"");
        }
    }//GEN-LAST:event_jTextFieldStokeSearchKeyPressed

    private void jButtonStokeSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStokeSearchActionPerformed
        int searchByIndex=jComboBoxSearchItem.getSelectedIndex();
        loadStokeTbleFull(searchByIndex,jTextFieldStokeSearch.getText(),"");
    }//GEN-LAST:event_jButtonStokeSearchActionPerformed

    private void jButtonPrintAListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintAListActionPerformed
        //        Notes n=new Notes();
        //
        //        String printStoke=""
        //        + "              RxD  PHARMACY \n"
        //        + " NO:123,Main Road,Telijjawila    \n"
        //        + "               Matara          \n"
        //        + "             Tel- 041 2517772 \n"
        //        + "          ANNUAL STOCK CENSUS           \n"
        //        + "-------------------------------------------------------\n"
        //        + ""+jTextFieldDate.getText()+" "+jTextFieldTime.getText()+"\n"
        //        + "User Name: "+currentUser+"\n"
        //        + "\n"
        //        + "-------------------------------------------------------\n"
        //        + ""+stock.getStockForPrint()+""
        //        + "-------------------------------------------------------\n"
        //        + "              software by:\n"
        //        + "       easy Solution Softwares     \n"
        //        + "                 (eSS)     \n"
        //        + "             Tel-0779 158823              ";
        //
        //        n.setVisible(true);
        //        n.setDefaultCloseOperation(WIDTH);
        //        n.setPrintPreview(printStoke);
    }//GEN-LAST:event_jButtonPrintAListActionPerformed

    private void jButtonStockFullPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockFullPrintActionPerformed
        try{
            boolean result=jTableCurStockItems.print();
            if(result){
            }else{
                JOptionPane.showMessageDialog(this, "Printer has stopped working !");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error occur..\nCan not do your printing process !");
        }
    }//GEN-LAST:event_jButtonStockFullPrintActionPerformed

    private void jComboBoxSearchItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxSearchItemItemStateChanged
//        int index = jComboBoxSearchItem.getSelectedIndex();
//        
//        if (index != 0) {
//            stList = stockRepository.getItemListBySupplierCode(allActiveSuppliers.get(index - 1).getSupplierCode());
//            loadStockTable();
//        }
//        else
//        {
//            //load all
//            stList = stockRepository.getAllItems();
//            loadStockTable();            
//        }
//        loadStockTable();
    }//GEN-LAST:event_jComboBoxSearchItemItemStateChanged

    private void jTableCurStockItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCurStockItemsMouseClicked
        int selectedRowIndex = jTableCurStockItems.getSelectedRow();
        if (itemDataList != null && itemDataList.size() > selectedRowIndex) {
            loadStockBatchTable(itemDataList.get(selectedRowIndex));
        }
    }//GEN-LAST:event_jTableCurStockItemsMouseClicked

    private void jTableLiveStockItemBatchesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLiveStockItemBatchesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableLiveStockItemBatchesMouseClicked

    private void jButtonStockBarcodePrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockBarcodePrintActionPerformed
        if (jTableLiveStockItemBatches.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Please select the batch");
        }
        else if (jTableCurStockItems.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(parent, "Please select the Item");
        }
        else{
            Item selectedItem = itemDataList.get(jTableCurStockItems.getSelectedRow());
            ItemStock selectedItemStock = selectedItem.stock.get(jTableLiveStockItemBatches.getSelectedRow());
            
            BarcodeLableItemDataModel dm =new BarcodeLableItemDataModel();
            dm.setBarcode(selectedItem.barcode);
            dm.setItemId(selectedItem.item_id);
            dm.setItemName(selectedItem.item_name);
            dm.setItemNameSin(selectedItem.item_name_sin);
            dm.setItemNameTam(selectedItem.item_name_tam);
            dm.setPrice(selectedItemStock.selling_price);
            
            BarcodeStickerCountWindow barcodeStickerCountWindow = new BarcodeStickerCountWindow();
            barcodeStickerCountWindow.setData(dm, parent.getBarcodeLablePrintPanel());
            barcodeStickerCountWindow.show();
        }
    }//GEN-LAST:event_jButtonStockBarcodePrintActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPrintAList;
    private javax.swing.JButton jButtonStockBarcodePrint;
    private javax.swing.JButton jButtonStockFullPrint;
    private javax.swing.JButton jButtonStokeSearch;
    private javax.swing.JComboBox jComboBoxSearchItem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelStockEditDelete;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableCurStockItems;
    private javax.swing.JTable jTableLiveStockItemBatches;
    private javax.swing.JTextField jTextFieldStokeSearch;
    // End of variables declaration//GEN-END:variables

}
