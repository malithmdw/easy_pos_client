package easyPOS.sale;

import appDataModels.APIHeaderData;
import appDataModels.InstituteModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import control.ServerDataSubmissionQueue;
import control.SimpleReceiptPrint;
import dataModels.BillDataModel;
import dataModels.BillItemDataModel;
import dataModels.ReceiptCommonData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.print.PrintService;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import localDatabase.DatabaseManager;
import serverDataModels.Customer;
import serverDataModels.DiscountRule;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverDataModels.Voucher;
import serverResponseDataModel.CommonResponse;
import tableModels.SalesVoucherRedeemTbl;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;
import easyPOS.localization.ApplicationMessages;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturnFrame extends javax.swing.JFrame implements control.LanguageChangeListener {

    private SaleInvoiceJPanel parentPanel;
    private BillDataModel billDataModel;
    private Customer selectedCustomer;
    private SalesVoucherRedeemTbl voucherTableModel;

    /**
     * Creates new form SalePaymentFrame
     * @param parentPanel
     */
    public SaleReturnFrame() {
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

    private void loadSaleInvoiceItemsTable() {
        voucherTableModel = new SalesVoucherRedeemTbl();
        saleInvoiceItemsTable.setModel(voucherTableModel);

        saleInvoiceItemsTable.getColumnModel().getColumn(2).setCellRenderer(
            (javax.swing.table.TableCellRenderer) (table, value, isSelected, hasFocus, row, column) -> {
                javax.swing.JButton btn = new javax.swing.JButton("Remove");
                btn.setFont(new java.awt.Font("Segoe UI", 0, 11));
                return btn;
            }
        );

        saleInvoiceItemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = saleInvoiceItemsTable.rowAtPoint(e.getPoint());
                int col = saleInvoiceItemsTable.columnAtPoint(e.getPoint());
                if (col == 2 && row >= 0) {
                    voucherTableModel.removeRow(row);
                }
            }
        });
    }

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/sale/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge2.registerFont(customFont2);

            jLabel1.setFont(customFont);
            jLabel8.setFont(customFont2);
            salereturnSaleInvoiceInputLabel.setFont(customFont);
            submitSalereturnButton.setFont(customFont);
            jButton2.setFont(customFont);

        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        jLabel1.setText(resourceBundle.getString("SalePaymentFrame.jLabel1.text"));
        jLabel8.setText(resourceBundle.getString("SalePaymentFrame.jLabel8.text"));
        salereturnSaleInvoiceInputLabel.setText(resourceBundle.getString("SalePaymentFrame.paymentMethodInputLabel.text"));
        submitSalereturnButton.setText(resourceBundle.getString("SalePaymentFrame.jButton1.text"));
        jButton2.setText(resourceBundle.getString("SalePaymentFrame.jButton2.text"));
    }

    private boolean printSaleReturnVoucher(Voucher voucher)
    {
        PrinterJob job = PrinterJob.getPrinterJob();

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService selectedPrinter = null;

        String printerName = ApplicationDataManager.getInstance().getReceiptPrinterName(); // your printer name

        InstituteModel instituteModel = RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute();
        ReceiptCommonData receiptCommonData = new ReceiptCommonData();
        receiptCommonData.setBusinessName(instituteModel.getPrintBusinessName());
        receiptCommonData.setBusinessSubName(instituteModel.getPrintBusinessSubName());
        receiptCommonData.setAddressLine1(instituteModel.getPrintBusinessAddressLine1());
        receiptCommonData.setAddressLine2(instituteModel.getPrintBusinessAddressLine2());
        receiptCommonData.setAddressLine3(instituteModel.getPrintBusinessAddressLine3());
        receiptCommonData.setContactDataLine(instituteModel.getPrintBusinessContact());
        receiptCommonData.setFooterLine1(instituteModel.getPrintFooter1());
        receiptCommonData.setFooterLine2(instituteModel.getPrintFooter2());
        receiptCommonData.setServiceProviderLine(instituteModel.getServiceProviderPrintLine());

        for (PrintService service : printServices) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                selectedPrinter = service;
                break;
            }
        }

        if (selectedPrinter != null) {
            try {

                job.setPrintService(selectedPrinter);

//                PageFormat pf = new PageFormat();
//                Paper paper = new Paper();
//
//                paper.setSize(760, 800);
//                paper.setImageableArea(5, 2, 450, 420);
//
//                pf.setPaper(paper);
//                pf.setOrientation(PageFormat.PORTRAIT);
//
//                job.setPrintable(new SimpleReceiptPrint(billDataModel, receiptCommonData), pf);
                PageFormat pf = new PageFormat();
                Paper paper = new Paper();

                paper.setSize(576, 2000); // width, height
                paper.setImageableArea(0, 0, 576, 2000);

                pf.setPaper(paper);
                pf.setOrientation(PageFormat.PORTRAIT);

                job.setPrintable(new SimpleReceiptPrint(billDataModel, receiptCommonData), pf);

                job.print(); // 🔹 No dialog

                return true;

            } catch (PrinterException e) {
                EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINTER_FAILED_TASK);
            }
        } else {
            EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_PRINTER_NOT_FOUND);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardTypeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        salepanelNetAmtTextField = new javax.swing.JTextField();
        jScrollPaneVoucherTbl = new javax.swing.JScrollPane();
        saleInvoiceItemsTable = new javax.swing.JTable();
        salereturnSaleInvoiceInputLabel = new javax.swing.JLabel();
        salereturnSaleInvoiceNoTextField = new javax.swing.JTextField();
        saleInvoiceSearchButton = new javax.swing.JButton();
        addItemToReturnsButton = new javax.swing.JButton();
        saleReturnItmQtyTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPaneVoucherTbl1 = new javax.swing.JScrollPane();
        vouchersTable1 = new javax.swing.JTable();
        submitSalereturnButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        saleReturnAmtTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sale Invoice"));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("BILL NET AMOUNT :");

        salepanelNetAmtTextField.setEditable(false);
        salepanelNetAmtTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salepanelNetAmtTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        saleInvoiceItemsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPaneVoucherTbl.setViewportView(saleInvoiceItemsTable);

        salereturnSaleInvoiceInputLabel.setText("Sale Invoice Number");

        salereturnSaleInvoiceNoTextField.setAutoscrolls(false);

        saleInvoiceSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        saleInvoiceSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saleInvoiceSearchButtonActionPerformed(evt);
            }
        });

        addItemToReturnsButton.setText("Add to Return");
        addItemToReturnsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemToReturnsButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Quantity");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneVoucherTbl)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salereturnSaleInvoiceInputLabel)
                .addGap(9, 9, 9)
                .addComponent(salereturnSaleInvoiceNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saleInvoiceSearchButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saleReturnItmQtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addItemToReturnsButton)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(salepanelNetAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(salereturnSaleInvoiceInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(saleInvoiceSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(salereturnSaleInvoiceNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneVoucherTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(salepanelNetAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemToReturnsButton)
                    .addComponent(saleReturnItmQtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Return Items"));

        vouchersTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPaneVoucherTbl1.setViewportView(vouchersTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneVoucherTbl1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneVoucherTbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        submitSalereturnButton.setText("Submit Return");
        submitSalereturnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitSalereturnButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("RETURN AMOUNT :");

        saleReturnAmtTextField.setEditable(false);
        saleReturnAmtTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        saleReturnAmtTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submitSalereturnButton))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saleReturnAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(saleReturnAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitSalereturnButton)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitSalereturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitSalereturnButtonActionPerformed
        
    }//GEN-LAST:event_submitSalereturnButtonActionPerformed

    private void saleInvoiceSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saleInvoiceSearchButtonActionPerformed

    }//GEN-LAST:event_saleInvoiceSearchButtonActionPerformed

    private void addItemToReturnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemToReturnsButtonActionPerformed

    }//GEN-LAST:event_addItemToReturnsButtonActionPerformed


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
            java.util.logging.Logger.getLogger(SaleReturnFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaleReturnFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaleReturnFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaleReturnFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SalePaymentFrame().setVisible(true);
//            }
//        });
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SalePaymentFrame().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemToReturnsButton;
    private javax.swing.ButtonGroup cardTypeButtonGroup;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPaneVoucherTbl;
    private javax.swing.JScrollPane jScrollPaneVoucherTbl1;
    private javax.swing.JTable saleInvoiceItemsTable;
    private javax.swing.JButton saleInvoiceSearchButton;
    private javax.swing.JTextField saleReturnAmtTextField;
    private javax.swing.JTextField saleReturnItmQtyTextField;
    private javax.swing.JTextField salepanelNetAmtTextField;
    private javax.swing.JLabel salereturnSaleInvoiceInputLabel;
    private javax.swing.JTextField salereturnSaleInvoiceNoTextField;
    private javax.swing.JButton submitSalereturnButton;
    private javax.swing.JTable vouchersTable1;
    // End of variables declaration//GEN-END:variables
}
