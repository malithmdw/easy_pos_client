package easyPOS.billpayment;

import control.OnBillPaymentCategorySelectListener;
import control.RuntimeDataManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import serverDataModels.BillPaymentCategoryModel;

/**
 * Horizontal card strip for bill-payment categories.
 *
 * Categories are loaded from RuntimeData (populated by BillPaymentServerAPIConnection
 * after a successful login + get-category call). Images are fetched from the EasyReload CDN.
 */
public class BillPaymentCategoryCardList extends javax.swing.JPanel {

    private OnBillPaymentCategorySelectListener onBillPaymentCategorySelectListener;
    private List<BillPaymentCategoryModel> billPaymentCategoryList;
    private JScrollPane parentScrollPane;

    public BillPaymentCategoryCardList() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemGridPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout itemGridPanelLayout = new javax.swing.GroupLayout(itemGridPanel);
        itemGridPanel.setLayout(itemGridPanelLayout);
        itemGridPanelLayout.setHorizontalGroup(
            itemGridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );
        itemGridPanelLayout.setVerticalGroup(
            itemGridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(itemGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(itemGridPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loadItemGrid() {
        if (billPaymentCategoryList == null || billPaymentCategoryList.isEmpty()) return;

        itemGridPanel.removeAll();
        itemGridPanel.setLayout(new BoxLayout(itemGridPanel, BoxLayout.X_AXIS));

        if (parentScrollPane != null) {
            prepareTouchScrolling(parentScrollPane);
        }

        for (BillPaymentCategoryModel category : billPaymentCategoryList) {
            final BillPaymentCategoryItemCard card = new BillPaymentCategoryItemCard();
            String displayName = category.getLocalizedName();
            String imageUrl = (category.image != null && !category.image.isEmpty())
                    ? (new webService.WebService(true).getServerImageFolderPath()) + "/" + category.image
                    : "";
            card.setDataFromUrl(imageUrl, displayName);
            itemGridPanel.add(card);
            itemGridPanel.add(Box.createRigidArea(new Dimension(10, 0)));

            if (onBillPaymentCategorySelectListener != null) {
                if (parentScrollPane != null) {
                    installDragToScroll(card, category, parentScrollPane);
                } else {
                    installClickOnly(card, category);
                }
            }
        }

        itemGridPanel.setPreferredSize(new Dimension(billPaymentCategoryList.size() * 168, 85));
        itemGridPanel.revalidate();
        itemGridPanel.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel itemGridPanel;
    // End of variables declaration//GEN-END:variables

    void loadAllCards() {
        billPaymentCategoryList = RuntimeDataManager.getInstance()
                .getRuntimeData().getBillPaymentCategories();
        loadItemGrid();
    }

    void setOnBillPaymentCategorySelectListener(OnBillPaymentCategorySelectListener listener) {
        this.onBillPaymentCategorySelectListener = listener;
    }

    void setParentScrollPane(JScrollPane parentScrollPane) {
        this.parentScrollPane = parentScrollPane;
    }

    // -------------------------------------------------------------------------
    // Touch / drag-to-scroll support
    // -------------------------------------------------------------------------

    private void prepareTouchScrolling(final JScrollPane scrollPane) {
        scrollPane.addMouseWheelListener(e -> {
            JScrollBar h = scrollPane.getHorizontalScrollBar();
            h.setValue(h.getValue() + e.getWheelRotation() * h.getUnitIncrement() * 5);
        });
    }

    /** Full drag-to-scroll with click detection (used when a parent scroll pane exists). */
    private void installDragToScroll(final Component comp,
                                     final BillPaymentCategoryModel category,
                                     final JScrollPane scrollPane) {
        MouseAdapter ma = new MouseAdapter() {
            int startXOnScreen;
            int startScroll;
            boolean dragging;
            static final int DRAG_THRESHOLD = 8;

            @Override
            public void mousePressed(MouseEvent e) {
                startXOnScreen = e.getXOnScreen();
                startScroll = scrollPane.getHorizontalScrollBar().getValue();
                dragging = false;
                comp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getXOnScreen() - startXOnScreen;
                JScrollBar h = scrollPane.getHorizontalScrollBar();
                int newVal = Math.max(h.getMinimum(),
                        Math.min(startScroll - deltaX, h.getMaximum() - h.getVisibleAmount()));
                h.setValue(newVal);
                if (Math.abs(deltaX) > DRAG_THRESHOLD) dragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                comp.setCursor(Cursor.getDefaultCursor());
                if (!dragging) {
                    onBillPaymentCategorySelectListener.onBillPaymentCategorySelect(category);
                }
            }
        };
        addMouseListenersRecursively(comp, ma);
    }

    /** Simple click-only listener (used when there is no parent scroll pane). */
    private void installClickOnly(final Component comp, final BillPaymentCategoryModel category) {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onBillPaymentCategorySelectListener.onBillPaymentCategorySelect(category);
            }
        };
        addMouseListenersRecursively(comp, ma);
    }

    private void addMouseListenersRecursively(Component c, MouseAdapter ma) {
        if (c == null) return;
        if (c instanceof AbstractButton) return;
        c.addMouseListener(ma);
        c.addMouseMotionListener(ma);
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                addMouseListenersRecursively(child, ma);
            }
        }
    }
}
