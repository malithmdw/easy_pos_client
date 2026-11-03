/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package easyPOS.billpayment;

import control.ApplicationDataManager;
import control.BillPaymentReceiptPrint;
import control.EasyPosLogger;
import control.RuntimeDataManager;
import appDataModels.InstituteModel;
import dataModels.ReceiptCommonData;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import serverDataModels.BillPaymentProviderModel;
import serverResponseDataModel.BillPaymentResult;
import serverResponseDataModel.CommonResponse;
import uiUtil.EasyPOSMessageDialog;
import webService.BillPaymentServerAPIConnection;
import webService.BillPaymentWebService;

/**
 *
 * @author MalithWanniarachchi
 */
public class BillPaymentPanel extends javax.swing.JPanel {

    private javax.swing.JPanel billersInnerPanel;
    private BillPaymentProviderModel selectedProvider;

    /**
     * Creates new form BillPaymentPanel
     */
    public BillPaymentPanel() {
        initComponents();
        setupBillersPanel();

        billPaymentCategoryCardList.setOnBillPaymentCategorySelectListener(category ->
                loadBillerCards(category.id));

        String user = ApplicationDataManager.getInstance().getBillPaymentUser();
        String pwd  = ApplicationDataManager.getInstance().getBillPaymentPassword();
        if (user != null && !user.isEmpty() && pwd != null && !pwd.isEmpty()) {
            initializeBillPaymentData();
        }
    }

    /** Wraps jPanelBillPaymentBillers in a scroll pane with a FlowLayout inner panel. */
    private void setupBillersPanel() {
        billersInnerPanel = new javax.swing.JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 5));
        javax.swing.JScrollPane sp = new javax.swing.JScrollPane(billersInnerPanel);
        sp.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        jPanelBillPaymentBillers.setLayout(new java.awt.BorderLayout());
        jPanelBillPaymentBillers.add(sp, java.awt.BorderLayout.CENTER);
    }

    /**
     * Logs in to the EasyReload API on a background thread, then fetches categories
     * and providers. On success the category strip and biller grid are populated on the EDT.
     */
    private void initializeBillPaymentData() {
        new javax.swing.SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                BillPaymentServerAPIConnection api = BillPaymentServerAPIConnection.getInstance();

                String user = ApplicationDataManager.getInstance().getBillPaymentUser();
                String pwd  = ApplicationDataManager.getInstance().getBillPaymentPassword();

                CommonResponse loginResp = api.login(user, pwd);
                if (!loginResp.getAPIResponse().isSuccess()) {
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR,
                            "BillPayment login failed: " + loginResp.getAPIResponse().getMessage());
                    return false;
                }

                CommonResponse catResp = api.getBillerCategory();
                if (!catResp.getAPIResponse().isSuccess()) {
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR,
                            "BillPayment get-category failed: " + catResp.getAPIResponse().getMessage());
                }

                CommonResponse provResp = api.getBillers();
                if (!provResp.getAPIResponse().isSuccess()) {
                    EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR,
                            "BillPayment get-providers failed: " + provResp.getAPIResponse().getMessage());
                }

                return catResp.getAPIResponse().isSuccess();
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        billPaymentCategoryCardList.loadAllCards();
                        loadBillerCards(null);
                    }
                } catch (Exception ex) {
                    EasyPosLogger.getInstance().error("BillPayment UI init error", ex);
                }
            }
        }.execute();
    }

    /**
     * Populates the billers grid filtered by categoryId (null = show all).
     * Each card fires selectBiller() on click.
     */
    private void loadBillerCards(String categoryId) {
        List<BillPaymentProviderModel> providers = RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentProviders();

        billersInnerPanel.removeAll();
        selectedProvider = null;
        jLabelSelectedBillerLogo.setIcon(null);

        for (BillPaymentProviderModel provider : providers) {
            if (categoryId != null && !categoryId.equals(provider.categoryId)) continue;

            BillerCard card = new BillerCard();
            String displayName = provider.getLocalizedName();
            String imageUrl = (provider.image != null && !provider.image.isEmpty())
                    ? BillPaymentWebService.IMAGE_FOLDER_PATH + "/" + provider.image : "";
            card.setDataFromUrl(imageUrl, displayName);

            final BillPaymentProviderModel p = provider;
            card.addClickListenerRecursively(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectBiller(p);
                }
            });

            billersInnerPanel.add(card);
        }

        billersInnerPanel.revalidate();
        billersInnerPanel.repaint();
    }

    /** Shows the selected biller's logo in jLabelSelectedBillerLogo. */
    private void selectBiller(BillPaymentProviderModel provider) {
        selectedProvider = provider;

        if (provider.image == null || provider.image.isEmpty()) {
            jLabelSelectedBillerLogo.setIcon(null);
            return;
        }

        String imageUrl = BillPaymentWebService.IMAGE_FOLDER_PATH + "/" + provider.image;

        new Thread(() -> {
            try {
                String fileName  = provider.image;
                File localFile = new File(ApplicationDataManager.BILLERS_LOCAL_FOLDER_PATH + fileName);
                BufferedImage img = localFile.exists()
                        ? ImageIO.read(localFile)
                        : ImageIO.read(new URL(imageUrl));

                if (img != null) {
                    Image scaled = img.getScaledInstance(79, 71, Image.SCALE_SMOOTH);
                    SwingUtilities.invokeLater(() ->
                            jLabelSelectedBillerLogo.setIcon(new ImageIcon(scaled)));
                }
            } catch (IOException ex) {
                EasyPosLogger.getInstance().error("BillPayment biller logo load error", ex);
            }
        }).start();
    }

    /** Builds ReceiptCommonData from the selected institute (null-safe). */
    private ReceiptCommonData buildReceiptData() {
        ReceiptCommonData data = new ReceiptCommonData();
        InstituteModel inst = RuntimeDataManager.getInstance().getRuntimeData().getSelectedInstitute();
        if (inst != null) {
            data.setBusinessName(inst.getPrintBusinessName());
            data.setBusinessSubName(inst.getPrintBusinessSubName());
            data.setAddressLine1(inst.getPrintBusinessAddressLine1());
            data.setAddressLine2(inst.getPrintBusinessAddressLine2());
            data.setAddressLine3(inst.getPrintBusinessAddressLine3());
            data.setContactDataLine(inst.getPrintBusinessContact());
            data.setFooterLine1(inst.getPrintFooter1());
            data.setFooterLine2(inst.getPrintFooter2());
            data.setServiceProviderLine(inst.getServiceProviderPrintLine());
        }
        return data;
    }

    private void printBillPaymentReceipt(BillPaymentResult result) {
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrinterJob.lookupPrintServices();
        String printerName = ApplicationDataManager.getInstance().getReceiptPrinterName();

        PrintService target = null;
        for (PrintService svc : services) {
            if (svc.getName().equalsIgnoreCase(printerName)) {
                target = svc;
                break;
            }
        }

        if (target == null) {
            JOptionPane.showMessageDialog(this, "Printer not found: " + printerName);
            return;
        }

        try {
            job.setPrintService(target);

            PageFormat pf = new PageFormat();
            Paper paper = new Paper();
            paper.setSize(576, 2000);
            paper.setImageableArea(0, 0, 576, 2000);
            pf.setPaper(paper);
            pf.setOrientation(PageFormat.PORTRAIT);

            job.setPrintable(new BillPaymentReceiptPrint(result, selectedProvider, buildReceiptData()), pf);
            job.print();
        } catch (PrinterException ex) {
            EasyPosLogger.getInstance().error("BillPayment receipt print failed", ex);
            JOptionPane.showMessageDialog(this, "Receipt printing failed.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        billPaymentCategoryCardList = new easyPOS.billpayment.BillPaymentCategoryCardList();
        jPanelBillPaymentBillers = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabelSelectedBillerLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldAccNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldAmount = new javax.swing.JTextField();
        jButtonPayBill = new javax.swing.JButton();
        numberPanel1 = new easyPOS.NumberPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanelBillPaymentBillersLayout = new javax.swing.GroupLayout(jPanelBillPaymentBillers);
        jPanelBillPaymentBillers.setLayout(jPanelBillPaymentBillersLayout);
        jPanelBillPaymentBillersLayout.setHorizontalGroup(
            jPanelBillPaymentBillersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelBillPaymentBillersLayout.setVerticalGroup(
            jPanelBillPaymentBillersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(billPaymentCategoryCardList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelBillPaymentBillers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(billPaymentCategoryCardList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBillPaymentBillers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Account / Mobile Number :");

        jTextFieldAccNumber.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel2.setText("Amount :");

        jTextFieldAmount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jButtonPayBill.setText("Pay");
        jButtonPayBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPayBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldAccNumber)
                    .addComponent(jTextFieldAmount)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSelectedBillerLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jButtonPayBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSelectedBillerLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAccNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonPayBill)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(numberPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPayBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPayBillActionPerformed
        // ---- Validation ----
        if (selectedProvider == null) {
            JOptionPane.showMessageDialog(this, "Please select a biller first.");
            return;
        }

        String accNumber = jTextFieldAccNumber.getText().trim();
        if (accNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter account / mobile number.");
            return;
        }

        String amountStr = jTextFieldAmount.getText().trim();
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            return;
        }
        if (amount <= 0 || amount >= 10000) {
            JOptionPane.showMessageDialog(this, "Amount must be greater than 0 and less than 10,000.");
            return;
        }

        // ---- Prevent double-submission ----
        jButtonPayBill.setEnabled(false);

        final String finalAccNumber = accNumber;
        final double finalAmount    = amount;
        final BillPaymentProviderModel finalProvider = selectedProvider;
        final String clientRef = UUID.randomUUID().toString();

        new javax.swing.SwingWorker<CommonResponse, Void>() {
            @Override
            protected CommonResponse doInBackground() {
                return BillPaymentServerAPIConnection.getInstance().payBill(
                        finalProvider.categoryId,
                        finalProvider.id,
                        finalAccNumber,
                        finalAmount,
                        null,
                        clientRef);
            }

            @Override
            protected void done() {
                jButtonPayBill.setEnabled(true);
                try {
                    CommonResponse response = get();
                    if (response.getAPIResponse().isSuccess()) {
                        BillPaymentResult result = (BillPaymentResult) response.getData();
                        printBillPaymentReceipt(result);
                        // Clear fields after successful payment
                        jTextFieldAccNumber.setText("");
                        jTextFieldAmount.setText("");
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(
                                BillPaymentPanel.this, response.getAPIResponse());
                    }
                } catch (Exception ex) {
                    EasyPosLogger.getInstance().error("BillPayment pay action error", ex);
                }
            }
        }.execute();
    }//GEN-LAST:event_jButtonPayBillActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private easyPOS.billpayment.BillPaymentCategoryCardList billPaymentCategoryCardList;
    private javax.swing.JButton jButtonPayBill;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelSelectedBillerLogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBillPaymentBillers;
    private javax.swing.JTextField jTextFieldAccNumber;
    private javax.swing.JTextField jTextFieldAmount;
    private easyPOS.NumberPanel numberPanel1;
    // End of variables declaration//GEN-END:variables
}
