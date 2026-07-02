package easyPOS.customers;

import appDataModels.APIHeaderData;
import appDataModels.BarcodeLableItemDataModel;
import appDataModels.CustomerLedgerModel;
import appDataModels.CustomerModel;
import appDataModels.InstituteModel;
import control.ApplicationDataManager;
import control.RuntimeDataManager;
import control.SimpleCustomerStatementPrint;
import control.ZebraStickerPrinter;
import dataModels.ReceiptCommonData;
import easyPOS.stock.StockPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.io.IOException;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import control.EasyPosLogger;
import javax.print.PrintService;
import easyPOS.localization.ApplicationMessages;
import javax.swing.JOptionPane;
import uiUtil.EasyPOSMessageDialog;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import serverDataModels.CustomerLedger;
import serverResponseDataModel.CommonResponse;
import tableModels.CustomerTransactionsTbl;
import uiUtil.LoadingGlassPane;
import util.DateTimeUtil;
import util.GeneralUtil;
import webService.ServerAPIConnection;

/**
 *
 * @author malit
 */
public class Customer extends javax.swing.JPanel implements control.LanguageChangeListener {

    private StockPanel parent;
    private CustomerModel selectedCustomer;
    List<CustomerLedgerModel> ustomerLedgers = new ArrayList<>();
    /**
     * Creates new form StockItemForm
     */
    public Customer() {
        initComponents();
        switchLanguage();
        customerDetailsPanel.setVisible(false);
        customerTransactionsPanel.setVisible(false);
        jPanelCustPayment.setVisible(false);
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    public void setInitDataToUI(StockPanel parent)
    {
        this.parent = parent;
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCustomer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldCustomerContact = new javax.swing.JTextField();
        jTextFieldCustomerSearch = new javax.swing.JButton();
        customerTransactionsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTransactionsTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jButtonAddNewCustomer = new javax.swing.JButton();
        jButtonEditCustomer = new javax.swing.JButton();
        jButtonPrintSummary = new javax.swing.JButton();
        jButtonPrintSticker = new javax.swing.JButton();
        customerDetailsPanel = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldCustomerName = new javax.swing.JTextField();
        jTextFieldCustomerEmail = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldCustomerAccBalance = new javax.swing.JTextField();
        jPanelCustPayment = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldCustomerPayAmount = new javax.swing.JTextField();
        jTextFieldCustomerSearch1 = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/customers/Bundle"); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("Customer.jPanel1.border.title"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText(bundle.getString("Customer.jLabel18.text")); // NOI18N

        jTextFieldCustomerContact.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldCustomerContact.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCustomerContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomerContactActionPerformed(evt);
            }
        });
        jTextFieldCustomerContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerContactKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerContactKeyTyped(evt);
            }
        });

        jTextFieldCustomerSearch.setText(bundle.getString("Customer.jTextFieldCustomerSearch.text")); // NOI18N
        jTextFieldCustomerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomerSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCustomerContact, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCustomerContact, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextFieldCustomerSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        customerTransactionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("Customer.customerTransactionsPanel.border.title"))); // NOI18N

        customerTransactionsTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerTransactionsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(customerTransactionsTable);

        javax.swing.GroupLayout customerTransactionsPanelLayout = new javax.swing.GroupLayout(customerTransactionsPanel);
        customerTransactionsPanel.setLayout(customerTransactionsPanelLayout);
        customerTransactionsPanelLayout.setHorizontalGroup(
            customerTransactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerTransactionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        customerTransactionsPanelLayout.setVerticalGroup(
            customerTransactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerTransactionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonAddNewCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonAddNewCustomer.setText(bundle.getString("Customer.jButtonAddNewCustomer.text")); // NOI18N
        jButtonAddNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNewCustomerActionPerformed(evt);
            }
        });

        jButtonEditCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonEditCustomer.setText(bundle.getString("Customer.jButtonEditCustomer.text")); // NOI18N
        jButtonEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditCustomerActionPerformed(evt);
            }
        });

        jButtonPrintSummary.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonPrintSummary.setText(bundle.getString("Customer.jButtonPrintSummary.text")); // NOI18N
        jButtonPrintSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintSummaryActionPerformed(evt);
            }
        });

        jButtonPrintSticker.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonPrintSticker.setText(bundle.getString("Customer.jButtonPrintSticker.text")); // NOI18N
        jButtonPrintSticker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintStickerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jButtonEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addComponent(jButtonAddNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(jButtonPrintSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addComponent(jButtonPrintSticker, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAddNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPrintSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPrintSticker, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customerDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("Customer.customerDetailsPanel.border.title"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText(bundle.getString("Customer.jLabel25.text")); // NOI18N

        jTextFieldCustomerName.setEditable(false);
        jTextFieldCustomerName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomerNameActionPerformed(evt);
            }
        });
        jTextFieldCustomerName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerNameKeyTyped(evt);
            }
        });

        jTextFieldCustomerEmail.setEditable(false);
        jTextFieldCustomerEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCustomerEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerEmailKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerEmailKeyTyped(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText(bundle.getString("Customer.jLabel26.text")); // NOI18N

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText(bundle.getString("Customer.jLabel31.text")); // NOI18N

        jTextFieldCustomerAccBalance.setEditable(false);
        jTextFieldCustomerAccBalance.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCustomerAccBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomerAccBalanceActionPerformed(evt);
            }
        });
        jTextFieldCustomerAccBalance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerAccBalanceKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerAccBalanceKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout customerDetailsPanelLayout = new javax.swing.GroupLayout(customerDetailsPanel);
        customerDetailsPanel.setLayout(customerDetailsPanelLayout);
        customerDetailsPanelLayout.setHorizontalGroup(
            customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                        .addComponent(jTextFieldCustomerAccBalance, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jTextFieldCustomerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(482, Short.MAX_VALUE))
        );
        customerDetailsPanelLayout.setVerticalGroup(
            customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customerDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomerAccBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelCustPayment.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("Customer.jPanelCustPayment.border.title"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText(bundle.getString("Customer.jLabel19.text")); // NOI18N

        jTextFieldCustomerPayAmount.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldCustomerPayAmount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCustomerPayAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomerPayAmountActionPerformed(evt);
            }
        });
        jTextFieldCustomerPayAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerPayAmountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCustomerPayAmountKeyTyped(evt);
            }
        });

        jTextFieldCustomerSearch1.setText(bundle.getString("Customer.jTextFieldCustomerSearch1.text")); // NOI18N
        jTextFieldCustomerSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomerSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCustPaymentLayout = new javax.swing.GroupLayout(jPanelCustPayment);
        jPanelCustPayment.setLayout(jPanelCustPaymentLayout);
        jPanelCustPaymentLayout.setHorizontalGroup(
            jPanelCustPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustPaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCustomerPayAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCustomerSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCustPaymentLayout.setVerticalGroup(
            jPanelCustPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustPaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCustPaymentLayout.createSequentialGroup()
                        .addGroup(jPanelCustPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCustomerPayAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextFieldCustomerSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelCustomerLayout = new javax.swing.GroupLayout(jPanelCustomer);
        jPanelCustomer.setLayout(jPanelCustomerLayout);
        jPanelCustomerLayout.setHorizontalGroup(
            jPanelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customerTransactionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customerDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelCustomerLayout.setVerticalGroup(
            jPanelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelCustomerLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelCustPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerTransactionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCustomerContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomerContactActionPerformed
        
    }//GEN-LAST:event_jTextFieldCustomerContactActionPerformed

    private void jTextFieldCustomerContactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerContactKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && !jTextFieldCustomerContact.getText().isEmpty())
        {
            loadCustomerDataAction(jTextFieldCustomerContact.getText());
        }
    }//GEN-LAST:event_jTextFieldCustomerContactKeyPressed

    private void jTextFieldCustomerContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerContactKeyTyped

    }//GEN-LAST:event_jTextFieldCustomerContactKeyTyped

    private void jButtonAddNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNewCustomerActionPerformed
        AddEditCustomer addEditCustomer = new AddEditCustomer(AddEditCustomer.MODE.ADD_CUSTOMER, null);
        addEditCustomer.setLocationRelativeTo(this); // center to first frame
        addEditCustomer.setVisible(true);
    }//GEN-LAST:event_jButtonAddNewCustomerActionPerformed

    private void jTextFieldCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerNameActionPerformed

    private void jTextFieldCustomerNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerNameKeyPressed

    private void jTextFieldCustomerNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerNameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerNameKeyTyped

    private void jTextFieldCustomerEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerEmailKeyPressed

    private void jTextFieldCustomerEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerEmailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerEmailKeyTyped

    private void jTextFieldCustomerAccBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomerAccBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerAccBalanceActionPerformed

    private void jTextFieldCustomerAccBalanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerAccBalanceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerAccBalanceKeyPressed

    private void jTextFieldCustomerAccBalanceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerAccBalanceKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerAccBalanceKeyTyped

    private void jTextFieldCustomerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomerSearchActionPerformed
        loadCustomerDataAction(jTextFieldCustomerContact.getText());
    }//GEN-LAST:event_jTextFieldCustomerSearchActionPerformed

    private void jButtonEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditCustomerActionPerformed
        AddEditCustomer addEditCustomer = new AddEditCustomer(AddEditCustomer.MODE.UPDATE_CUSTOMER, selectedCustomer);
        addEditCustomer.setLocationRelativeTo(this); // center to first frame
        addEditCustomer.setVisible(true);
    }//GEN-LAST:event_jButtonEditCustomerActionPerformed

    private void jButtonPrintSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintSummaryActionPerformed
        printBill();
    }//GEN-LAST:event_jButtonPrintSummaryActionPerformed

    private void jTextFieldCustomerPayAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomerPayAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerPayAmountActionPerformed

    private void jTextFieldCustomerPayAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerPayAmountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerPayAmountKeyPressed

    private void jTextFieldCustomerPayAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomerPayAmountKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomerPayAmountKeyTyped

    private void jTextFieldCustomerSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomerSearch1ActionPerformed
        JOptionPane.showConfirmDialog(this, "Confirm Payment", "Payment Confirmation", 1);
        
        double amount;
        
        try {
            amount = Double.parseDouble(jTextFieldCustomerPayAmount.getText());
            
            if (amount < 0) {
                amount = amount * (-1);
            }
            
            if (amount == 0) {
                EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_PAYMENT_AMOUNT_INCORRECT);
                return;
            }
        } catch (NumberFormatException e) {
            EasyPOSMessageDialog.showLocalizedWarning(this, ApplicationMessages.VALIDATION_PAYMENT_AMOUNT_INCORRECT);
            return;
        }
        
        CustomerLedger customerLedger = new CustomerLedger();
        customerLedger.tran_id = ApplicationDataManager.getInstance().getNextTransactionId();
        customerLedger.transaction_date = DateTimeUtil.getTodayDateDBFormat() + " " + DateTimeUtil.getCurrentTimeHHmmss();
        customerLedger.transaction_amount = amount;
        customerLedger.ledger_type = CustomerLedger.LEDGER_TYPE.CREDIT;
        customerLedger.customer_id = selectedCustomer.getCustomerId();
        customerLedger.accept_by = ApplicationDataManager.getInstance().getLoggedInUser().getUserName();
        customerLedger.description = "Re-Payment";
        
        customerPayAction(selectedCustomer.getContactNumber(), customerLedger);
    }//GEN-LAST:event_jTextFieldCustomerSearch1ActionPerformed

    private void jButtonPrintStickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintStickerActionPerformed
        if (selectedCustomer == null) {
            EasyPOSMessageDialog.showLocalizedWarning(parent, ApplicationMessages.VALIDATION_SELECT_CUSTOMER);
            return;            
        }
        // Print All 
        ArrayList<BarcodeLableItemDataModel> barcodeLableItems = new ArrayList<>();
        BarcodeLableItemDataModel barcodeLabel = new BarcodeLableItemDataModel();
        barcodeLabel.setBarcode(selectedCustomer.getContactNumber());
        barcodeLabel.setItemId(Integer.parseInt(selectedCustomer.getContactNumber().replaceAll(" ", "")));
        barcodeLabel.setItemName(selectedCustomer.getCustomerName());
        barcodeLabel.setItemNameSin("");
        barcodeLabel.setItemNameTam("");
        barcodeLabel.setPrice(0);
        barcodeLabel.setStickerCount(3); // Add 3 labels to fill the row
        
        barcodeLableItems.add(barcodeLabel);
        
        ZebraStickerPrinter zebraPrinter = new ZebraStickerPrinter();
        zebraPrinter.print(barcodeLableItems, 
                RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute().getBusinessName());
    }//GEN-LAST:event_jButtonPrintStickerActionPerformed
    
    private void customerPayAction(String customerContactno, CustomerLedger customerLedger) {
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

                return ServerAPIConnection.getInstance(aPIHeaderData).addCustomerTransaction(customerContactno, customerLedger);
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop(); 
                
                try {
                    CommonResponse commonResponse = get();
                    
                    if (commonResponse.getAPIResponse().isSuccess()) {
                        ApplicationDataManager.getInstance().updateInvoiceNumberUtilized();
                        loadCustomerDataAction(customerContactno);
                        jTextFieldCustomerPayAmount.setText("");
                    }
                    else
                    {
                        uiUtil.EasyPOSMessageDialog.showErrorMessageDialog(parent, commonResponse.getAPIResponse());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    EasyPosLogger.getInstance().error("", ex);
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    private boolean printBill()
    {
        if (selectedCustomer == null) {
            return false;
        }
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
        
        List<CustomerLedgerModel> customerLedgerModelsToPrint = ustomerLedgers.subList(0, Math.min(10, ustomerLedgers.size()));
        
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

                job.setPrintable(new SimpleCustomerStatementPrint(
                            selectedCustomer,
                            customerLedgerModelsToPrint, 
                            receiptCommonData, 
                            ApplicationDataManager.getInstance().getLoggedInUser().getUserName()), 
                        pf);

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
    
    private void clearFormFields()
    {
        jTextFieldCustomerName.setText("");
        jTextFieldCustomerEmail.setText("");
        jTextFieldCustomerAccBalance.setText("");
        jTextFieldCustomerPayAmount.setText("");
        jPanelCustPayment.setVisible(false);
    }
    
    private void loadCustomerDataAction(String customerPhoneNo) {
        JRootPane root = getRootPane();
        LoadingGlassPane loader = new LoadingGlassPane();
        root.setGlassPane(loader);
        
        clearFormFields();
        selectedCustomer = null;
        ustomerLedgers = new ArrayList<>();

        SwingWorker<CommonResponse, Void> worker = new SwingWorker<CommonResponse, Void>() {
            
            
            @Override
            protected CommonResponse doInBackground() {
                loader.start(); // show loader
                APIHeaderData aPIHeaderData = new APIHeaderData();
                aPIHeaderData.setInstituteId(RuntimeDataManager.getInstance().getRuntimeData().getInstituteId());
                aPIHeaderData.setTerminalId(RuntimeDataManager.getInstance().getRuntimeData().getTerminalId());

                CommonResponse commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).getCustomer(customerPhoneNo);
                
                if (commonResponse.getAPIResponse().isSuccess()) {
                    selectedCustomer = new CustomerModel((serverDataModels.Customer) commonResponse.getData());
                }
                
                if (selectedCustomer != null) {
                    commonResponse = ServerAPIConnection.getInstance(aPIHeaderData).getCustomerTransactionHistory(selectedCustomer.getContactNumber(), DateTimeUtil.getMinusDaysFromTodayDBFormat(90), DateTimeUtil.getTodayDateDBFormat());
                
                    if (commonResponse.getAPIResponse().isSuccess()) {
                        List<serverDataModels.CustomerLedger> customerLedgers = new ArrayList<>((ArrayList<serverDataModels.CustomerLedger>) commonResponse.getData());

                        for (serverDataModels.CustomerLedger cusLeg : customerLedgers) {
                            ustomerLedgers.add(new CustomerLedgerModel(cusLeg));
                        }
                    }
                }
                return commonResponse;
            }

            @Override
            protected void done() {
                // hide loader
                loader.stop(); 
                
                // Reset UI
                clearFormFields();
                jTextFieldCustomerAccBalance.setForeground(Color.black);
                jButtonEditCustomer.setVisible(false);
                    
                //reset transactions table
                loadCustomerTransactionTable(new ArrayList<>());
                
                // load data
                try {
                    
                    if (selectedCustomer != null) {
                        
                        customerDetailsPanel.setVisible(true);
                        customerTransactionsPanel.setVisible(true);
                        
                        //load customer
                        jTextFieldCustomerName.setText(selectedCustomer.getCustomerName());
                        jTextFieldCustomerEmail.setText(selectedCustomer.getEmail());
                        jTextFieldCustomerAccBalance.setText(GeneralUtil.getCurrencyString(selectedCustomer.getAccountBalance()));
                        
                        if (selectedCustomer.getAccountBalance() < 0) {
                            jTextFieldCustomerAccBalance.setForeground(Color.red);
                        }
                        
                        jButtonEditCustomer.setVisible(true);
                        jPanelCustPayment.setVisible(true);
                        
                    }
                    CommonResponse response = get();

                    if (response.getAPIResponse().isSuccess()) {
                        //load transactions
                        loadCustomerTransactionTable(ustomerLedgers);
                    } else {
                        uiUtil.EasyPOSMessageDialog.showErrorMessageDialog(parent, response.getAPIResponse());
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    EasyPOSMessageDialog.showUnexpectedError(jPanelCustomer.getRootPane(), ex.getMessage());
                }
            }
        };

        loader.start(); // show before execution
        worker.execute();
    }
    
    private void loadCustomerTransactionTable(List<CustomerLedgerModel> customerLedgers){
        CustomerTransactionsTbl ctt = new CustomerTransactionsTbl(customerLedgers);
        customerTransactionsTable.setModel(ctt);
        
        customerTransactionsTable.setRowHeight(35);
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

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/customers/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            jLabel18.setFont(customFont);
            jLabel25.setFont(customFont);
            jLabel26.setFont(customFont);
            jLabel31.setFont(customFont);
            jLabel19.setFont(customFont);
            jButtonAddNewCustomer.setFont(customFont);
            jButtonEditCustomer.setFont(customFont);
            jButtonPrintSummary.setFont(customFont);
            jTextFieldCustomerSearch.setFont(customFont);
            jTextFieldCustomerSearch1.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }
        jLabel18.setText(resourceBundle.getString("Customer.jLabel18.text"));
        jLabel25.setText(resourceBundle.getString("Customer.jLabel25.text"));
        jLabel26.setText(resourceBundle.getString("Customer.jLabel26.text"));
        jLabel31.setText(resourceBundle.getString("Customer.jLabel31.text"));
        jLabel19.setText(resourceBundle.getString("Customer.jLabel19.text"));
        jButtonAddNewCustomer.setText(resourceBundle.getString("Customer.jButtonAddNewCustomer.text"));
        jButtonEditCustomer.setText(resourceBundle.getString("Customer.jButtonEditCustomer.text"));
        jButtonPrintSummary.setText(resourceBundle.getString("Customer.jButtonPrintSummary.text"));
        jTextFieldCustomerSearch.setText(resourceBundle.getString("Customer.jTextFieldCustomerSearch.text"));
        jTextFieldCustomerSearch1.setText(resourceBundle.getString("Customer.jTextFieldCustomerSearch1.text"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel customerDetailsPanel;
    private javax.swing.JPanel customerTransactionsPanel;
    private javax.swing.JTable customerTransactionsTable;
    private javax.swing.JButton jButtonAddNewCustomer;
    private javax.swing.JButton jButtonEditCustomer;
    private javax.swing.JButton jButtonPrintSticker;
    private javax.swing.JButton jButtonPrintSummary;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelCustPayment;
    private javax.swing.JPanel jPanelCustomer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldCustomerAccBalance;
    private javax.swing.JTextField jTextFieldCustomerContact;
    private javax.swing.JTextField jTextFieldCustomerEmail;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldCustomerPayAmount;
    private javax.swing.JButton jTextFieldCustomerSearch;
    private javax.swing.JButton jTextFieldCustomerSearch1;
    // End of variables declaration//GEN-END:variables
}
