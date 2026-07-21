
package easyPOS.stock;

import appDataModels.BarcodeLableItemDataModel;
import control.ApplicationDataManager;
import control.EasyPosLogger;
import control.RuntimeDataManager;
import control.ZebraStickerPrinter;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import tableModels.BarcodeLabletemTbl;

/**
 *
 * @author malit
 */
public class BarcodeLablePrintPanel extends javax.swing.JPanel {

    private ArrayList<BarcodeLableItemDataModel> barcodeLableItems;
    
    public BarcodeLablePrintPanel() {
        this.barcodeLableItems = new ArrayList<>();
        initComponents();
    }
    
    public void loadData(){
        loadBarcodeItemsTable();
    }
    
    private void loadBarcodeItemsTable() {
        BarcodeLabletemTbl stoLTbl = new BarcodeLabletemTbl(barcodeLableItems);
        jTableBarcodeLablePrintItems.setModel(stoLTbl);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        jTableBarcodeLablePrintItems.getColumnModel().getColumn(1).setMinWidth(20);// itemcode
        jTableBarcodeLablePrintItems.getColumnModel().getColumn(2).setMinWidth(150);//barcode
        jTableBarcodeLablePrintItems.getColumnModel().getColumn(3).setMinWidth(300);//item name
        jTableBarcodeLablePrintItems.getColumnModel().getColumn(4).setMinWidth(300);//item name
        jTableBarcodeLablePrintItems.getColumnModel().getColumn(5).setMinWidth(300);//item name
        jTableBarcodeLablePrintItems.getColumnModel().getColumn(6).setMinWidth(150);// Price
        jTableBarcodeLablePrintItems.getColumnModel().getColumn(7).setMinWidth(150);// Lable Count
        
        try {
            // 1. Define the custom font
            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
        
            // 2. Create the custom renderer
            DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {

                    // Get the standard component properties first
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Apply your custom font to this component
                    c.setFont(customFont1); 
                    return c;
                }
            };

            // 3. Target the specific column index (e.g., Column 0 for "Name")
            TableColumn nameColumn = jTableBarcodeLablePrintItems.getColumnModel().getColumn(4);
            // 4. Bind the renderer to the column
            nameColumn.setCellRenderer(customRenderer);
        } catch (FontFormatException | IOException ex) {
            EasyPosLogger.getInstance().error("Error", ex);
        }
                
        jTableBarcodeLablePrintItems.setRowHeight(35);
        
        if(!barcodeLableItems.isEmpty())
            jTableBarcodeLablePrintItems.setRowSelectionInterval(0, 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jTableBarcodeLablePrintItems = new javax.swing.JTable();
        jButtonPrintAList = new javax.swing.JButton();
        jButtonStockFullPrint = new javax.swing.JButton();

        jScrollPane5.setAutoscrolls(true);

        jTableBarcodeLablePrintItems.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableBarcodeLablePrintItems.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableBarcodeLablePrintItems.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableBarcodeLablePrintItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBarcodeLablePrintItemsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableBarcodeLablePrintItems);

        jButtonPrintAList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonPrintAList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/stock/Bundle"); // NOI18N
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonStockFullPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPrintAList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonStockFullPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPrintAList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableBarcodeLablePrintItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBarcodeLablePrintItemsMouseClicked

    }//GEN-LAST:event_jTableBarcodeLablePrintItemsMouseClicked

    private void jButtonPrintAListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintAListActionPerformed
        // Print All
        ZebraStickerPrinter zebraPrinter = new ZebraStickerPrinter();
        zebraPrinter.print(barcodeLableItems, 
                RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute().getBusinessName());
    }//GEN-LAST:event_jButtonPrintAListActionPerformed

    private void jButtonStockFullPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockFullPrintActionPerformed
        int rowNum = jTableBarcodeLablePrintItems.getSelectedRow();
        
        if (rowNum >= 0 && rowNum < barcodeLableItems.size()) {
            barcodeLableItems.remove(rowNum);
            loadBarcodeItemsTable();
        }
        
    }//GEN-LAST:event_jButtonStockFullPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPrintAList;
    private javax.swing.JButton jButtonStockFullPrint;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableBarcodeLablePrintItems;
    // End of variables declaration//GEN-END:variables

    void addItem(BarcodeLableItemDataModel dataModel) {        
        barcodeLableItems.add(dataModel);
    }
}
