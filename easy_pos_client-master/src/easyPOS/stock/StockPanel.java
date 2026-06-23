package easyPOS.stock;

import dbOperations.StockDBOperation;
import dbOperations.SuppliesDBOperation;
import java.awt.HeadlessException;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;

/**
 *
 * @author malit
 */
public class StockPanel extends javax.swing.JPanel {
    
    SuppliesDBOperation supp=new SuppliesDBOperation();
    StockDBOperation stockRepository = new StockDBOperation();
    
    /**
     * Creates new form StockContentPanel
     */
    public StockPanel() {
        initComponents();
    }
    
    
    public void loadInitialDataToUI() {
        stockItemListPanel.loadInitialDataToUI(this);
//        stockItemListPanel1.loadInitialDataToUI();
//        stockItemListPanel1.loadInitialDataToUI();
    }
    
    public void changeStockPanelToItemForm() {
        stockItemForm1.setInitDataToUI(this);
        jPanelStock.removeAll();
        jPanelStock.repaint();
        jPanelStock.revalidate();
        jPanelStock.add(jScrollPaneStockItemForm);
        jPanelStock.repaint();
        jPanelStock.revalidate();
    }
    
    public void changeItemFormToStockPanel() {
        jPanelStock.removeAll();
        jPanelStock.repaint();
        jPanelStock.revalidate();
        jPanelStock.add(jScrollPaneStockPanel);
        jPanelStock.repaint();
        jPanelStock.revalidate();
    }
    
    public BarcodeLablePrintPanel getBarcodeLablePrintPanel(){
        return barcodeLablePrintPanel1;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneListForOrder = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanelStock = new javax.swing.JPanel();
        jScrollPaneStockPanel = new javax.swing.JScrollPane();
        stockItemListPanel = new easyPOS.stock.LiveStockPanel();
        jScrollPaneStockItemForm = new javax.swing.JScrollPane();
        stockItemForm1 = new easyPOS.stock.StockItemForm();
        jPanel1 = new javax.swing.JPanel();
        changePricePanel1 = new easyPOS.stock.ChangePricePanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jButtonPrintExpSt = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableExpStocks = new javax.swing.JTable();
        jComboBoxSupplier2 = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableToOrder = new javax.swing.JTable();
        jLabel81 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jComboBoxSupplier = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        addNewItemPanel1 = new easyPOS.stock.AddNewItemPanel();
        barcodeLablePrintPanel1 = new easyPOS.stock.BarcodeLablePrintPanel();

        jTabbedPaneListForOrder.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTabbedPaneListForOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneListForOrderMouseClicked(evt);
            }
        });

        jPanel7.setPreferredSize(new java.awt.Dimension(1009, 501));

        jPanelStock.setLayout(new java.awt.CardLayout());

        jScrollPaneStockPanel.setViewportView(stockItemListPanel);

        jPanelStock.add(jScrollPaneStockPanel, "card4");

        jScrollPaneStockItemForm.setViewportView(stockItemForm1);

        jPanelStock.add(jScrollPaneStockItemForm, "card3");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelStock, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE)
        );

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/Bundle"); // NOI18N
        jTabbedPaneListForOrder.addTab(bundle.getString("StockPanel.jPanel7.TabConstraints.tabTitle"), jPanel7); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(changePricePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 203, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(changePricePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 470, Short.MAX_VALUE))
        );

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("easyPOS/stock/Bundle"); // NOI18N
        jTabbedPaneListForOrder.addTab(bundle1.getString("StockPanel.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel86.setText(bundle.getString("StockPanel.jLabel86.text")); // NOI18N

        jButtonPrintExpSt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonPrintExpSt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        jButtonPrintExpSt.setText(bundle.getString("StockPanel.jButtonPrintExpSt.text")); // NOI18N
        jButtonPrintExpSt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintExpStActionPerformed(evt);
            }
        });

        jTableExpStocks.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableExpStocks);

        jComboBoxSupplier2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxSupplier2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSupplier2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPrintExpSt, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPrintExpSt)
                    .addComponent(jComboBoxSupplier2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneListForOrder.addTab(bundle.getString("StockPanel.jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        jTableToOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(jTableToOrder);

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel81.setText(bundle.getString("StockPanel.jLabel81.text")); // NOI18N

        jButton24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        jButton24.setText(bundle.getString("StockPanel.jButton24.text")); // NOI18N
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jComboBoxSupplier.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneListForOrder.addTab(bundle.getString("StockPanel.jPanel12.TabConstraints.tabTitle"), jPanel12); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(addNewItemPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 211, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(addNewItemPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 297, Short.MAX_VALUE))
        );

        jTabbedPaneListForOrder.addTab(bundle1.getString("StockPanel.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N
        jTabbedPaneListForOrder.addTab(bundle1.getString("StockPanel.barcodeLablePrintPanel1.TabConstraints.tabTitle"), barcodeLablePrintPanel1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneListForOrder)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneListForOrder)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        int res=JOptionPane.showConfirmDialog(this, "Do you want to print this table ?");
        if(res==JOptionPane.YES_OPTION){
            try{
                boolean result=jTableToOrder.print();
                if(result){
                }else{
                    JOptionPane.showMessageDialog(this, "Printer has stopped working !");
                }
            }catch(HeadlessException | PrinterException e){
                JOptionPane.showMessageDialog(this, "Error occur..\nCan not do your printing process !");
            }
        }

    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButtonPrintExpStActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintExpStActionPerformed
        int res=JOptionPane.showConfirmDialog(this, "Do you want to print this table ?");
        if(res==JOptionPane.YES_OPTION){
            try{
                boolean result=jTableExpStocks.print();
                if(result){
                }else{
                    JOptionPane.showMessageDialog(this, "Printer has stopped working !");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error occur..\nCan not do your printing process !");
            }
        }
    }//GEN-LAST:event_jButtonPrintExpStActionPerformed

    private void jTabbedPaneListForOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneListForOrderMouseClicked
        switch (jTabbedPaneListForOrder.getSelectedIndex()) {
            case 0:
                loadStokeTbleFull(0, "", "");
                break;
            case 1:
                loadToParchaseTbl(11, "", "");
                break;
            case 2:
                loadExpStockTbl(13, util.DateTimeUtil.getTodayDateDBFormat(),"");
                break;
            case 3:
                loadExpStockTbl(13, util.DateTimeUtil.getTodayDateDBFormat(),"");
                break;
            case 4:
                addNewItemPanel1.loadData();
            case 5:
                barcodeLablePrintPanel1.loadData();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jTabbedPaneListForOrderMouseClicked


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
    void loadToParchaseTbl(int situation,String searchInput1,String searchInput2){
//        ItemMovement imove=new ItemMovement();
//        stList2=imove.getStockAndItemMovement(situation,searchInput1,searchInput2);
//        ListForOrder lfo=new ListForOrder(stList2);
//        jTableToOrder.setModel(lfo);
//                DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
//                rightRenderer.setHorizontalAlignment(jLabel1.RIGHT);
//                jTableToOrder.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//                jTableToOrder.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
//                jTableToOrder.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
//                jTableToOrder.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
    }
    void loadExpStockTbl(int situation,String searchInput1,String searchInput2){
//        stList3=stock.getStocks(situation,searchInput1,searchInput2);
//        ListOfExp loex=new ListOfExp(stList3);
//        jTableExpStocks.setModel(loex);
//                DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
//                rightRenderer.setHorizontalAlignment(jLabel1.RIGHT);
//                jTableExpStocks.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//                jTableExpStocks.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
//                jTableExpStocks.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
//                jTableExpStocks.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
//        
    }
    
    
    void loadStokeTble(int situation,String searchInput1,String searchInput2){
//        stList=stock.getStocks(situation,searchInput1,searchInput2);
//        StockLoadTbl stoLTbl=new StockLoadTbl(stList);
//        jTableSaleSearch.setModel(stoLTbl);
//                DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
//                rightRenderer.setHorizontalAlignment(jLabel1.RIGHT);
//                jTableSaleSearch.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
//                jTableSaleSearch.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//                jTableSaleSearch.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
//                
//                jTableSaleSearch.getColumnModel().getColumn(0).setMinWidth(20);
//                jTableSaleSearch.getColumnModel().getColumn(1).setMinWidth(150);
//                jTableSaleSearch.getColumnModel().getColumn(2).setMinWidth(150);
    }
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private easyPOS.stock.AddNewItemPanel addNewItemPanel1;
    private easyPOS.stock.BarcodeLablePrintPanel barcodeLablePrintPanel1;
    private easyPOS.stock.ChangePricePanel changePricePanel1;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButtonPrintExpSt;
    private javax.swing.JComboBox jComboBoxSupplier;
    private javax.swing.JComboBox jComboBoxSupplier2;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelStock;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneStockItemForm;
    private javax.swing.JScrollPane jScrollPaneStockPanel;
    private javax.swing.JTabbedPane jTabbedPaneListForOrder;
    private javax.swing.JTable jTableExpStocks;
    private javax.swing.JTable jTableToOrder;
    private easyPOS.stock.StockItemForm stockItemForm1;
    private easyPOS.stock.LiveStockPanel stockItemListPanel;
    // End of variables declaration//GEN-END:variables

}
