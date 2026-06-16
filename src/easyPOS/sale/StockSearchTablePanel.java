
package easyPOS.sale;

import dataModels.StockItemDataModel;
import dbOperations.StockDBOperation;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import tableModels.StockLoadTbl;

/**
 *
 * @author malit
 */
public class StockSearchTablePanel extends javax.swing.JPanel {

    List<StockItemDataModel> stList;
    StockDBOperation stockDBOperation;
    /**
     * Creates new form StockSearchTablePanel
     */
    public StockSearchTablePanel() {
        initComponents();
        stockDBOperation = new StockDBOperation();
        stList = new ArrayList<>();
        
        loadTableData(null);
        
        
    }
    
    private void loadTableData(String searchInput)
    {
        if (searchInput == null || searchInput.isEmpty()) {
            stList=stockDBOperation.getAllItems();
        }
        else{
            stList=stockDBOperation.getItemsByInput(searchInput);
        }
        
        StockLoadTbl stoLTblFull=new StockLoadTbl(stList);
        searchStockTable.setModel(stoLTblFull);
        
        searchStockTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        searchStockTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        searchStockTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        searchStockTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        searchStockTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        searchStockTable.getColumnModel().getColumn(12).setCellRenderer(rightRenderer); 

        searchStockTable.getColumnModel().getColumn(2).setMinWidth(150);
        searchStockTable.getColumnModel().getColumn(3).setMinWidth(200);
        searchStockTable.getColumnModel().getColumn(9).setMinWidth(100);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stockItemSearchTextField = new javax.swing.JTextField();
        stockTableScrollPane = new javax.swing.JScrollPane();
        searchStockTable = new javax.swing.JTable();

        stockItemSearchTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        stockItemSearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stockItemSearchTextFieldKeyPressed(evt);
            }
        });

        searchStockTable.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        searchStockTable.setModel(new javax.swing.table.DefaultTableModel(
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
        searchStockTable.setRowHeight(35);
        stockTableScrollPane.setViewportView(searchStockTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stockTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(stockItemSearchTextField))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stockItemSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void stockItemSearchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stockItemSearchTextFieldKeyPressed
        loadTableData(stockItemSearchTextField.getText().trim());
    }//GEN-LAST:event_stockItemSearchTextFieldKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable searchStockTable;
    private javax.swing.JTextField stockItemSearchTextField;
    private javax.swing.JScrollPane stockTableScrollPane;
    // End of variables declaration//GEN-END:variables
}
