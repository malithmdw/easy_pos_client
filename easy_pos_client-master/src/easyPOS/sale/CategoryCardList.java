package easyPOS.sale;

import appDataModels.CategoryModel;
import control.ApplicationDataManager;
import control.OnCategorySelectListener;
import dataModels.Language;
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
import localDatabase.DatabaseManager;

/**
 *
 * @author malit
 */
public class CategoryCardList extends javax.swing.JPanel {

    private OnCategorySelectListener onCategorySelectListener;
    private List<CategoryModel> itemCategoryList;
    private JScrollPane parentScrollPane;
    /**
     * Creates new form CategoryCardList
     */
    public CategoryCardList() {
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


//    private void loadItemGrid()
//    {
//        if (itemCategoryList == null || itemCategoryList.isEmpty()) {
//            return;
//        }
//        
//        int noOfItems = itemCategoryList.size();
//        
//        int columns = noOfItems;// no of categories
//        int ROWS = 1;
//        
//        
//        //JPanel itemGridPanel = new JPanel(new GridLayout(rows, COLUMNS, 10, 10)); 
//        itemGridPanel.setLayout(new GridLayout(ROWS, columns, 20, 20));
//        
//        for (ItemCategory category : itemCategoryList) {
//            CategoryItemCard card = new CategoryItemCard();
//            card.setData("1.png", category.getCategoryName());
//            itemGridPanel.add(card);
//            card.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    onCategorySelectListener.onCategorySelect(category);
//                    super.mouseClicked(e);
//                }
//            });
//        }
//    }
    
    private void loadItemGrid() {
        if (itemCategoryList == null || itemCategoryList.isEmpty()) return;

        itemGridPanel.removeAll();
        itemGridPanel.setLayout(new BoxLayout(itemGridPanel, BoxLayout.X_AXIS));

        // assume scrollPane is already created and contains itemGridPanel
        prepareTouchScrolling(this.parentScrollPane);

        for (CategoryModel category : itemCategoryList) {
            final CategoryItemCard card = new CategoryItemCard();
            card.setData(category.getImageName(), 
                    Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())?
                            category.getCatNameSin(): category.getCatName());
            itemGridPanel.add(card);
            itemGridPanel.add(Box.createRigidArea(new Dimension(20, 0)));

            // install drag + click behavior on card subtree
            installDragToScroll(card, category, this.parentScrollPane);
        }

        // ensure content is wider than viewport so scrollbar is available
        itemGridPanel.setPreferredSize(new Dimension(itemCategoryList.size() * 180, 85));
        itemGridPanel.revalidate();
        itemGridPanel.repaint();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel itemGridPanel;
    // End of variables declaration//GEN-END:variables

    void loadAllCards() {
        //get all categories
        itemCategoryList = DatabaseManager.getInstance().getCategories();
        
        // Load cards
        loadItemGrid();
    }

    void setOnCategorySelectListener(OnCategorySelectListener onCategorySelectListener) {
        this.onCategorySelectListener = onCategorySelectListener;
    }

    void setParentScrollPane(JScrollPane parentScrollPane) {
        this.parentScrollPane = parentScrollPane;
    }
    
    
    
    
    
    
    
    
    
    // call once after you create the scrollPane and itemGridPanel
    private void prepareTouchScrolling(final JScrollPane scrollPane) {
        // Optional: allow mouse wheel to scroll horizontally
        scrollPane.addMouseWheelListener(e -> {
            JScrollBar h = scrollPane.getHorizontalScrollBar();
            int rotation = e.getWheelRotation();
            int amount = h.getUnitIncrement() * 5;
            h.setValue(h.getValue() + rotation * amount);
        });
    }

    // install recursive listener on a component (card) and all its children
    private void installDragToScroll(final Component comp, final CategoryModel category, final JScrollPane scrollPane) {
        MouseAdapter ma = new MouseAdapter() {
            int startXOnScreen;
            int startScroll;
            boolean dragging;
            final int DRAG_THRESHOLD = 8; // px, tune if necessary

            @Override
            public void mousePressed(MouseEvent e) {
                startXOnScreen = e.getXOnScreen();
                startScroll = scrollPane.getHorizontalScrollBar().getValue();
                dragging = false;
                // optional: change cursor for feedback
                comp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getXOnScreen() - startXOnScreen;
                JScrollBar h = scrollPane.getHorizontalScrollBar();
                int newVal = startScroll - deltaX; // negative delta => scroll right
                newVal = Math.max(h.getMinimum(), Math.min(newVal, h.getMaximum() - h.getVisibleAmount()));
                h.setValue(newVal);

                if (Math.abs(deltaX) > DRAG_THRESHOLD) {
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                comp.setCursor(Cursor.getDefaultCursor());
                if (!dragging) {
                    // treat as a tap/click
                    onCategorySelectListener.onCategorySelect(category);
                }
            }
        };

        // recursively add listener to component and its children
        addMouseListenersRecursively(comp, ma);
    }

    // helper to attach both mouse and motion listeners to component subtree
    private void addMouseListenersRecursively(Component c, MouseAdapter ma) {
        if (c!=null) {     
            // Skip install on interactive buttons if you want native button behavior:
            if (c instanceof AbstractButton) {
                // if you want drag-to-scroll even on buttons, remove this condition
                return;
            }

            c.addMouseListener(ma);
            c.addMouseMotionListener(ma);

            if (c instanceof Container) {
                for (Component child : ((Container) c).getComponents()) {
                    addMouseListenersRecursively(child, ma);
                }
            }   
        }
    }
}
