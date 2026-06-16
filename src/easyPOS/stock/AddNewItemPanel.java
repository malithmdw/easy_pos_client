
package easyPOS.stock;

import appDataModels.APIHeaderData;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import serverDataModels.Item;
import serverResponseDataModel.CommonResponse;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;

/**
 *
 * @author MalithWanniarachchi
 */
public class AddNewItemPanel extends javax.swing.JPanel {

    /**
     * Creates new form AddNewStockPanel
     */
    public AddNewItemPanel() {
        initComponents();
        switchLanguage();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stockItemForm1 = new easyPOS.stock.StockItemForm();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(stockItemForm1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stockItemForm1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Item item = stockItemForm1.validateAndGetUIData();
        if (item != null) {
            // Valid data >> send to server
            addNewItem(item);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        stockItemForm1.clearFieldsAll();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void addNewItem(Item item) {
                
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

                return ServerAPIConnection.getInstance(aPIHeaderData).addItem(item);
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
                        JOptionPane.showMessageDialog(AddNewItemPanel.this.getRootPane(), label, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AddNewItemPanel.this.getRootPane(), label, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(AddNewItemPanel.this.getRootPane(), "Unexpected error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    

    private void switchLanguage() {
        Locale locale = new Locale("si", "LK");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/stock/Bundle", locale);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            jButton1.setFont(customFont);
            jButton2.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        jButton1.setText(resourceBundle.getString("AddNewItemPanel.jButton1.text"));
        jButton2.setText(resourceBundle.getString("AddNewItemPanel.jButton2.text"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private easyPOS.stock.StockItemForm stockItemForm1;
    // End of variables declaration//GEN-END:variables

    void loadData() {
        stockItemForm1.loadData();
    }
}
