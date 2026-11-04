
package easyPOS.billpayment;

import control.ApplicationDataManager;
import control.BillPaymentReceiptPrint;
import control.EasyPosLogger;
import control.RuntimeDataManager;
import appDataModels.InstituteModel;
import control.EventManager;
import control.NumberPadKeyPressListener;
import dataModels.Language;
import dataModels.ReceiptCommonData;
import easyPOS.NumberPanel;
import easyPOS.localization.ApplicationMessages;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import serverDataModels.BillPaymentProviderModel;
import serverResponseDataModel.BillPaymentHistoryItem;
import serverResponseDataModel.BillPaymentHistoryResponse;
import serverResponseDataModel.BillPaymentResult;
import serverResponseDataModel.CommonResponse;
import tableModels.BillPaymentHistoryTableModel;
import uiUtil.EasyPOSMessageDialog;
import webService.BillPaymentServerAPIConnection;

/**
 *
 * @author MalithWanniarachchi
 */
public final class BillPaymentPanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    private javax.swing.JPanel billersInnerPanel;
    private BillPaymentProviderModel selectedProvider;
    private ResourceBundle bundle;
    private BillPaymentHistoryTableModel historyTableModel;

    /**
     * Creates new form BillPaymentPanel
     */
    public BillPaymentPanel() {
        initComponents();
        switchLanguage();
        setupBillersPanel();
        setupHistoryTable();

        numberPanel1.setButtonInvisible(NumberPadKeyPressListener.NumberPadButton.BUTTON_OK);

        billPaymentCategoryCardList.setOnBillPaymentCategorySelectListener(category ->
                loadBillerCards(category.id));

        loadInitialDataToUI(true);

        if (RuntimeDataManager.getInstance().getRuntimeData().getBillPaymentJwt() != null) {
            loadPaymentHistory();
        }

        EventManager.getInstance().addLanguageChangeListener(this);
        EventManager.getInstance().addNumberPadKeyEventListener(new NumberPadKeyPressListener() {
            @Override
            public void onKeyPressed(NumberPadKeyPressListener.NumberPadButton pressed) {
                if (!BillPaymentPanel.this.isShowing()) {
                    return;
                }
                
                if (jTextFieldAccNumber.isFocusOwner()) {
                    jTextFieldAccNumber.setText(NumberPanel.getNum(jTextFieldAccNumber.getText(), pressed));
                    jTextFieldAccNumber.setRequestFocusEnabled(true);
                }else if (jTextFieldAmount.isFocusOwner()) {
                    jTextFieldAmount.setText(NumberPanel.getNum(jTextFieldAmount.getText(), pressed));
                    jTextFieldAmount.setRequestFocusEnabled(true);
                }
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        bundle = ResourceBundle.getBundle("easyPOS/billpayment/Bundle", locale);
        if (appLang == Language.SINHALA) {
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                        ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(14f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                jLabel1.setFont(customFont);
                jLabel2.setFont(customFont);
                jButtonPayBill.setFont(customFont);
                jLabelSelectedBillerName.setFont(customFont);
            } catch (IOException | FontFormatException e) {
                EasyPosLogger.getInstance().error("BillPayment font load error", e);
            }
        }
        jLabel1.setText(bundle.getString("BillPaymentPanel.jLabel1.text"));
        jLabel2.setText(bundle.getString("BillPaymentPanel.jLabel2.text"));
        jButtonPayBill.setText(bundle.getString("BillPaymentPanel.jButtonPayBill.text"));
    }

    public void loadInitialDataToUI(boolean isApplicationLoading){
        if (RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentCategories() == null || 
            RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentCategories().isEmpty() || 
            RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentProviders() == null || 
            RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentProviders().isEmpty() 
                ) {
            String user = ApplicationDataManager.getInstance().getBillPaymentUser();
            String pwd  = ApplicationDataManager.getInstance().getBillPaymentPassword();
            if (user != null && !user.isEmpty() && pwd != null && !pwd.isEmpty()) {
                initializeBillPaymentData();
            }else{
                if (!isApplicationLoading) {
                    EasyPOSMessageDialog.showLocalizedError(this, ApplicationMessages.ERROR_BILLPAYMENT_CREDENTIALS_NOT_FOUND);
                }                
            }
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
                        loadPaymentHistory();
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
        jLabelSelectedBillerName.setText("");

        for (BillPaymentProviderModel provider : providers) {
            if (categoryId != null && !categoryId.equals(provider.categoryId)) continue;

            BillerCard card = new BillerCard();
            String displayName = provider.getLocalizedName();
            String imageUrl = (provider.image != null && !provider.image.isEmpty())
                    ? (new webService.WebService(true).getServerImageFolderPath()) + "/" + provider.image : "";
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
        
        if (ApplicationDataManager.getInstance().getApplicationLanguage().equals(Language.SINHALA)) {
            jLabelSelectedBillerName.setText(provider.si);
        }else{
            jLabelSelectedBillerName.setText(provider.name);
        }

        if (provider.image == null || provider.image.isEmpty()) {
            jLabelSelectedBillerLogo.setIcon(null);
            return;
        }

        String imageUrl = (new webService.WebService(true).getServerImageFolderPath()) + "/" + provider.image;

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

    private void printBillPaymentReceipt(BillPaymentResult result, BillPaymentProviderModel provider) {
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
            JOptionPane.showMessageDialog(this, bundle.getString("BillPaymentPanel.msg.printerNotFound") + " " + printerName);
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

            job.setPrintable(new BillPaymentReceiptPrint(result, provider, buildReceiptData()), pf);
            job.print();
        } catch (PrinterException ex) {
            EasyPosLogger.getInstance().error("BillPayment receipt print failed", ex);
            JOptionPane.showMessageDialog(this, bundle.getString("BillPaymentPanel.msg.printFailed"));
        }
    }

    private void setupHistoryTable() {
        historyTableModel = new BillPaymentHistoryTableModel(new ArrayList<>());
        jTableBillPaymentHistory.setModel(historyTableModel);
        jTableBillPaymentHistory.setRowHeight(28);
        jTableBillPaymentHistory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTableBillPaymentHistory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTableBillPaymentHistory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_ROW_NUM).setMaxWidth(35);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_DATETIME).setPreferredWidth(145);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_CATEGORY).setPreferredWidth(80);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_PROVIDER).setPreferredWidth(100);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_ACCOUNT).setPreferredWidth(120);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_AMOUNT).setPreferredWidth(80);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_STATUS).setPreferredWidth(80);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_PRINT).setPreferredWidth(70);
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_PRINT).setMaxWidth(70);

        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_STATUS)
                .setCellRenderer(new StatusCellRenderer());

        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_PRINT)
                .setCellRenderer(new PrintButtonRenderer());
        jTableBillPaymentHistory.getColumnModel().getColumn(BillPaymentHistoryTableModel.COL_PRINT)
                .setCellEditor(new PrintButtonEditor());
    }

    private void loadPaymentHistory() {
        new javax.swing.SwingWorker<CommonResponse, Void>() {
            @Override
            protected CommonResponse doInBackground() {
                LocalDate today = LocalDate.now();
                String toDate   = today.format(DateTimeFormatter.ISO_LOCAL_DATE);
                String fromDate = today.minusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE);
                return BillPaymentServerAPIConnection.getInstance().getHistory(fromDate, toDate);
            }

            @Override
            protected void done() {
                try {
                    CommonResponse response = get();
                    if (response.getAPIResponse().isSuccess()) {
                        BillPaymentHistoryResponse historyResponse = (BillPaymentHistoryResponse) response.getData();
                        if (historyResponse != null && historyResponse.items != null) {
                            historyTableModel.setItems(historyResponse.items);
                        }
                    } else {
                        EasyPosLogger.getInstance().log(EasyPosLogger.LogLevel.ERROR,
                                "BillPayment history load failed: " + response.getAPIResponse().getMessage());
                    }
                } catch (Exception ex) {
                    EasyPosLogger.getInstance().error("BillPayment history load error", ex);
                }
            }
        }.execute();
    }

    private void printHistoryItemAtRow(int row) {
        BillPaymentHistoryItem item = historyTableModel.getItem(row);

        BillPaymentResult result = new BillPaymentResult();
        result.clientRef         = item.clientRef;
        result.categoryId        = item.categoryId;
        result.serviceProviderId = item.serviceProviderId;
        result.accountNumber     = item.accountNumber;
        result.amount            = item.amount;
        result.packageId         = item.packageId;
        result.dateTime          = item.dateTime;
        result.status            = item.status;

        BillPaymentProviderModel provider = null;
        List<BillPaymentProviderModel> providers = RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentProviders();
        if (providers != null && item.serviceProviderId != null) {
            for (BillPaymentProviderModel p : providers) {
                if (item.serviceProviderId.equals(p.id)) {
                    provider = p;
                    break;
                }
            }
        }

        printBillPaymentReceipt(result, provider);
    }

    // ---- Inner cell renderer/editor classes ----

    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        private static final Color COLOR_SUCCESS = new Color(144, 238, 144);
        private static final Color COLOR_PENDING = new Color(255, 193,   7);
        private static final Color COLOR_FAILED  = new Color(255, 182, 193);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(CENTER);
            if (!isSelected) {
                String status = value != null ? value.toString() : "";
                switch (status) {
                    case "Success": setBackground(COLOR_SUCCESS); break;
                    case "Pending": setBackground(COLOR_PENDING); break;
                    case "Failed":  setBackground(COLOR_FAILED);  break;
                    default:        setBackground(table.getBackground()); break;
                }
            }
            setForeground(Color.BLACK);
            return this;
        }
    }

    private static class PrintButtonRenderer extends JButton implements TableCellRenderer {
        public PrintButtonRenderer() {
            setOpaque(true);
            setText("Print");
            setFont(new Font("Segoe UI", Font.PLAIN, 11));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class PrintButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private int currentRow;

        public PrintButtonEditor() {
            super(new JCheckBox());
            setClickCountToStart(1);
            button = new JButton("Print");
            button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            button.addActionListener(e -> {
                fireEditingStopped();
                printHistoryItemAtRow(currentRow);
            });
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            if (e instanceof MouseEvent) {
                return ((MouseEvent) e).getClickCount() >= 1;
            }
            return super.isCellEditable(e);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = table.convertRowIndexToModel(row);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Print";
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
        jLabelSelectedBillerName = new javax.swing.JLabel();
        numberPanel1 = new easyPOS.NumberPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBillPaymentHistory = new javax.swing.JTable();

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Account / Mobile Number :");

        jTextFieldAccNumber.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Amount :");

        jTextFieldAmount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jButtonPayBill.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
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
                    .addComponent(jButtonPayBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelSelectedBillerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelSelectedBillerLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSelectedBillerLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSelectedBillerName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
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

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTableBillPaymentHistory.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableBillPaymentHistory);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numberPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPayBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPayBillActionPerformed
        // ---- Validation ----
        if (selectedProvider == null) {
            JOptionPane.showMessageDialog(this, bundle.getString("BillPaymentPanel.msg.selectBillerFirst"));
            return;
        }

        String accNumber = jTextFieldAccNumber.getText().trim();
        if (accNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, bundle.getString("BillPaymentPanel.msg.enterAccNumber"));
            return;
        }

        String amountStr = jTextFieldAmount.getText().trim();
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, bundle.getString("BillPaymentPanel.msg.enterValidAmount"));
            return;
        }
        if (amount <= 0 || amount >= 10000) {
            JOptionPane.showMessageDialog(this, bundle.getString("BillPaymentPanel.msg.amountRange"));
            return;
        }

        // ---- Prevent double-submission ----
        jButtonPayBill.setEnabled(false);

        final String finalAccNumber = accNumber;
        final double finalAmount    = amount;
        final BillPaymentProviderModel finalProvider = selectedProvider;
        final String clientRef = ApplicationDataManager.getInstance().getNextTransactionId();
        ApplicationDataManager.getInstance().updateInvoiceNumberUtilized();

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
                        printBillPaymentReceipt(result, finalProvider);
                        jTextFieldAccNumber.setText("");
                        jTextFieldAmount.setText("");
                        loadPaymentHistory();
                    } else {
                        EasyPOSMessageDialog.showErrorMessageDialog(
                                BillPaymentPanel.this, response.getAPIResponse());
                    }
                } catch (InterruptedException | ExecutionException ex) {
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
    private javax.swing.JLabel jLabelSelectedBillerName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBillPaymentBillers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBillPaymentHistory;
    private javax.swing.JTextField jTextFieldAccNumber;
    private javax.swing.JTextField jTextFieldAmount;
    private easyPOS.NumberPanel numberPanel1;
    // End of variables declaration//GEN-END:variables
}
