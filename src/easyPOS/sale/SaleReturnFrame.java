package easyPOS.sale;

import appDataModels.APIHeaderData;
import appDataModels.InstituteModel;
import appDataModels.ItemModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import control.SaleReturnVoucherPrint;
import dataModels.ReceiptCommonData;
import dataModels.SaleReturnInvoiceItemDataModel;
import dataModels.SaleReturnLineDataModel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
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
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import localDatabase.DatabaseManager;
import serverDataModels.SaleInvoice;
import serverDataModels.SaleItem;
import serverDataModels.SaleReturn;
import serverDataModels.SaleReturnItem;
import serverResponseDataModel.CommonResponse;
import tableModels.SaleReturnInvoiceItemsTableModel;
import tableModels.SaleReturnItemsTableModel;
import uiUtil.EasyPOSMessageDialog;
import uiUtil.LoadingGlassPane;
import webService.ServerAPIConnection;
import easyPOS.localization.ApplicationMessages;
import serverDataModels.Voucher;

/**
 *
 * @author MalithWanniarachchi
 */
public class SaleReturnFrame extends javax.swing.JFrame implements control.LanguageChangeListener {

    private SaleInvoiceJPanel parentPanel;
    private SaleInvoice currentInvoice;
    private SaleReturnInvoiceItemsTableModel invoiceItemsTableModel;
    private SaleReturnItemsTableModel returnItemsTableModel;

    /**
     * Creates new form SalePaymentFrame
     */
    public SaleReturnFrame() {
        initComponents();
        switchLanguage();
        setupInvoiceItemsTable();
        setupReturnItemsTable();
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    private void setupInvoiceItemsTable() {
        invoiceItemsTableModel = new SaleReturnInvoiceItemsTableModel(new ArrayList<>());
        saleInvoiceItemsTable.setModel(invoiceItemsTableModel);
    }

    private void setupReturnItemsTable() {
        returnItemsTableModel = new SaleReturnItemsTableModel();
        vouchersTable1.setModel(returnItemsTableModel);

        vouchersTable1.getColumnModel().getColumn(5).setCellRenderer(
            (javax.swing.table.TableCellRenderer) (table, value, isSelected, hasFocus, row, column) -> {
                javax.swing.JButton btn = new javax.swing.JButton("Remove");
                btn.setFont(new java.awt.Font("Segoe UI", 0, 11));
                return btn;
            }
        );

        vouchersTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = vouchersTable1.rowAtPoint(e.getPoint());
                int col = vouchersTable1.columnAtPoint(e.getPoint());
                if (col == 5 && row >= 0) {
                    returnItemsTableModel.removeRow(row);
                    updateReturnAmount();
                }
            }
        });
    }

    private void loadInvoiceIntoTable(SaleInvoice invoice) {
        currentInvoice = invoice;

        List<SaleReturnInvoiceItemDataModel> rows = new ArrayList<>();
        if (invoice.sale_items != null) {
            for (SaleItem saleItem : invoice.sale_items) {
                ItemModel itemModel = DatabaseManager.getInstance().getItemByItemId(saleItem.item_id);

                SaleReturnInvoiceItemDataModel row = new SaleReturnInvoiceItemDataModel();
                row.setItemId(saleItem.item_id);
                row.setBatchId(saleItem.batch_id);
                row.setItemCode(itemModel != null ? itemModel.getBarcode() : "");
                row.setItemName(itemModel != null ? itemModel.getItemName() : "");
                row.setQtySold(saleItem.qty);
                row.setUnitPrice(saleItem.selling_price);
                row.setDiscount(saleItem.discount);
                row.setLineTotal(saleItem.net_amount);
                rows.add(row);
            }
        }

        invoiceItemsTableModel = new SaleReturnInvoiceItemsTableModel(rows);
        saleInvoiceItemsTable.setModel(invoiceItemsTableModel);

        salepanelNetAmtTextField.setText(util.GeneralUtil.getCurrencyString(invoice.net_total));

        returnItemsTableModel.clearAll();
        updateReturnAmount();
    }

    private void updateReturnAmount() {
        saleReturnAmtTextField.setText(util.GeneralUtil.getCurrencyString(returnItemsTableModel.getTotalReturnAmount()));
    }

    private void resetReturnForm() {
        currentInvoice = null;
        salereturnSaleInvoiceNoTextField.setText("");
        saleReturnItmQtyTextField.setText("");
        salepanelNetAmtTextField.setText("");
        invoiceItemsTableModel = new SaleReturnInvoiceItemsTableModel(new ArrayList<>());
        saleInvoiceItemsTable.setModel(invoiceItemsTableModel);
        returnItemsTableModel.clearAll();
        updateReturnAmount();
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
            jLabel9.setFont(customFont2);
            salereturnSaleInvoiceInputLabel.setFont(customFont);
            submitSalereturnButton.setFont(customFont);
            jButton2.setFont(customFont);
            addItemToReturnsButton.setFont(customFont);
            jLabel10.setFont(customFont);

        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        jLabel1.setText(resourceBundle.getString("SaleReturnFrame.jLabel1.text"));
        jLabel8.setText(resourceBundle.getString("SaleReturnFrame.jLabel8.text"));
        jLabel9.setText(resourceBundle.getString("SaleReturnFrame.jLabel9.text"));
        salereturnSaleInvoiceInputLabel.setText(resourceBundle.getString("SaleReturnFrame.salereturnSaleInvoiceInputLabel.text"));
        submitSalereturnButton.setText(resourceBundle.getString("SaleReturnFrame.submitSalereturnButton.text"));
        jButton2.setText(resourceBundle.getString("SaleReturnFrame.jButton2.text"));
        addItemToReturnsButton.setText(resourceBundle.getString("SaleReturnFrame.addItemToReturnsButton.text"));
        jLabel10.setText(resourceBundle.getString("SaleReturnFrame.jLabel10.text"));

        if (jPanel2.getBorder() instanceof javax.swing.border.TitledBorder) {
            ((javax.swing.border.TitledBorder) jPanel2.getBorder()).setTitle(resourceBundle.getString("SaleReturnFrame.jPanel2.border.title"));
        }
        if (jPanel3.getBorder() instanceof javax.swing.border.TitledBorder) {
            ((javax.swing.border.TitledBorder) jPanel3.getBorder()).setTitle(resourceBundle.getString("SaleReturnFrame.jPanel3.border.title"));
        }
    }

    private boolean printSaleReturnVoucher(String voucherId, double voucherAmount, String voucherDateTime)
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

        String cashierName = ApplicationDataManager.getInstance().getLoggedInUser() != null
                ? ApplicationDataManager.getInstance().getLoggedInUser().getUserName() : "";

        for (PrintService service : printServices) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                selectedPrinter = service;
                break;
            }
        }

        if (selectedPrinter != null) {
            try {

                job.setPrintService(selectedPrinter);

                PageFormat pf = new PageFormat();
                Paper paper = new Paper();

                paper.setSize(576, 2000); // width, height
                paper.setImageableArea(0, 0, 576, 2000);

                pf.setPaper(paper);
                pf.setOrientation(PageFormat.PORTRAIT);

                job.setPrintable(new SaleReturnVoucherPrint(voucherId, voucherAmount, voucherDateTime, receiptCommonData, cashierName), pf);

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
        returnVoucherNumberTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sale Invoice"));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saleReturnItmQtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addItemToReturnsButton)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salepanelNetAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPaneVoucherTbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("RETURN AMOUNT :");

        saleReturnAmtTextField.setEditable(false);
        saleReturnAmtTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        saleReturnAmtTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        returnVoucherNumberTextField.setEditable(false);
        returnVoucherNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        returnVoucherNumberTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Return Voucher Number");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submitSalereturnButton))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saleReturnAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(returnVoucherNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(saleReturnAmtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitSalereturnButton)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnVoucherNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitSalereturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitSalereturnButtonActionPerformed
        if (currentInvoice == null) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_SELECT_OR_INSERT_INVOICE);
            return;
        }

        if (returnItemsTableModel.getRowCount() == 0) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_INVOICE_EMPTY_ITEMS);
            return;
        }

        String requestNo = ApplicationDataManager.getInstance().getNextTransactionId();
        String requestDateTime = util.DateTimeUtil.getTodayDateDBFormat() + " " + util.DateTimeUtil.getCurrentTimeHHmmss();
        double totalAmount = returnItemsTableModel.getTotalReturnAmount();

        SaleReturn saleReturn = new SaleReturn();
        saleReturn.request_no = requestNo;
        saleReturn.sale_invoice_no = currentInvoice.invoice_no;
        saleReturn.customer_id = currentInvoice.customer_id;
        saleReturn.total_amount = totalAmount;
        saleReturn.request_date_time = requestDateTime;
        saleReturn.request_by = (ApplicationDataManager.getInstance().getLoggedInUser() != null)
                ? ApplicationDataManager.getInstance().getLoggedInUser().getUserName() : "";

        List<SaleReturnItem> returnItems = new ArrayList<>();
        for (SaleReturnLineDataModel line : returnItemsTableModel.getReturnLines()) {
            SaleReturnItem sri = new SaleReturnItem();
            sri.item_id = line.getItemId();
            sri.qty = line.getReturnQty();
            sri.selling_price = line.getUnitPrice();
            returnItems.add(sri);
        }
        saleReturn.return_items = returnItems;

        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);

        SwingWorker<CommonResponse, Void> worker = new SwingWorker<CommonResponse, Void>() {
            @Override
            protected CommonResponse doInBackground() {
                loader.start();
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                return ServerAPIConnection.getInstance(aPIHeaderData).saleReturn(saleReturn);
            }

            @Override
            protected void done() {
                loader.stop();
                try {
                    CommonResponse response = get();

                    if (response.getAPIResponse().isSuccess()) {
                        EasyPOSMessageDialog.showLocalizedInfo(getRootPane(), ApplicationMessages.INFO_SALE_RETURN_SUCCESS);
                        Voucher returnVoucher = (Voucher) response.getData();
                        String voucherIDStr = String.format("%06d", returnVoucher.voucher_id); 
                        printSaleReturnVoucher(voucherIDStr, totalAmount, requestDateTime);
                        resetReturnForm();
                        returnVoucherNumberTextField.setText(voucherIDStr);
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(getRootPane(), response.getAPIResponse());
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(getRootPane(), ex.getMessage());
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_submitSalereturnButtonActionPerformed

    private void saleInvoiceSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saleInvoiceSearchButtonActionPerformed
        String invoiceNo = salereturnSaleInvoiceNoTextField.getText().trim();

        if (invoiceNo.isEmpty()) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_FILL_REQUIRED_FIELDS);
            return;
        }

        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);

        SwingWorker<CommonResponse, Void> worker = new SwingWorker<CommonResponse, Void>() {
            @Override
            protected CommonResponse doInBackground() {
                loader.start();
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                return ServerAPIConnection.getInstance(aPIHeaderData).getSaleInvoiceData(invoiceNo);
            }

            @Override
            protected void done() {
                loader.stop();
                try {
                    CommonResponse response = get();
                    String responseCode = (response.getAPIResponse() != null) ? response.getAPIResponse().getResponseCode() : "99";

                    SaleInvoice invoice = null;

                    if (response.getAPIResponse() != null && response.getAPIResponse().isSuccess()) {
                        invoice = (SaleInvoice) response.getData();
                    } else if ("96".equals(responseCode) || "97".equals(responseCode) || "98".equals(responseCode)) {
                        // API unavailable / network unavailable / timeout - fall back to local database
                        invoice = DatabaseManager.getInstance().getSaleInvoice(invoiceNo);
                        if (invoice != null) {
                            invoice.sale_items = DatabaseManager.getInstance().getSaleItems((int) invoice.rec_id);
                        }
                    } else if ("07".equals(responseCode) || "20".equals(responseCode)) {
                        EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_NO_DATA_FOUND);
                        return;
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(getRootPane(), response.getAPIResponse());
                        return;
                    }

                    if (invoice == null) {
                        EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_NO_DATA_FOUND);
                        return;
                    }

                    loadInvoiceIntoTable(invoice);

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(getRootPane(), ex.getMessage());
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_saleInvoiceSearchButtonActionPerformed

    private void addItemToReturnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemToReturnsButtonActionPerformed
        int selectedRow = saleInvoiceItemsTable.getSelectedRow();

        if (selectedRow < 0) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_SELECT_ROW);
            return;
        }

        SaleReturnInvoiceItemDataModel selectedItem = invoiceItemsTableModel.getRow(selectedRow);

        double qty;
        try {
            qty = Double.parseDouble(saleReturnItmQtyTextField.getText().trim());
        } catch (NumberFormatException ex) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_RETURN_QTY_INVALID);
            return;
        }

        if (qty <= 0) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_RETURN_QTY_POSITIVE);
            return;
        }

        if (qty > selectedItem.getQtySold()) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_RETURN_QTY_EXCEEDS_SOLD);
            return;
        }

        double alreadyReturned = returnItemsTableModel.getReturnedQtyForItem(selectedItem.getItemId());
        double remainingReturnable = selectedItem.getQtySold() - alreadyReturned;

        if (qty > remainingReturnable) {
            EasyPOSMessageDialog.showLocalizedWarning(getRootPane(), ApplicationMessages.VALIDATION_RETURN_QTY_EXCEEDS_REMAINING);
            return;
        }

        SaleReturnLineDataModel line = new SaleReturnLineDataModel();
        line.setItemId(selectedItem.getItemId());
        line.setBatchId(selectedItem.getBatchId());
        line.setItemCode(selectedItem.getItemCode());
        line.setItemName(selectedItem.getItemName());
        line.setUnitPrice(selectedItem.getUnitPrice());
        line.setReturnQty(qty);

        returnItemsTableModel.addReturnLine(line);
        saleReturnItmQtyTextField.setText("");
        updateReturnAmount();
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPaneVoucherTbl;
    private javax.swing.JScrollPane jScrollPaneVoucherTbl1;
    private javax.swing.JTextField returnVoucherNumberTextField;
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
