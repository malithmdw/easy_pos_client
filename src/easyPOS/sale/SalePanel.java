package easyPOS.sale;

import appDataModels.CategoryModel;
import appDataModels.ItemCardDataModel;
import appDataModels.ItemModel;
import appDataModels.ItemStockModel;
import control.ApplicationDataManager;
import control.EventManager;
import control.MenuItemChangeEvent;
import control.SalesMenuItemClickListener;
import dataModels.Language;
import dataModels.MenuItemType;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import localDatabase.DatabaseManager;
import util.GeneralUtil;

/**
 *
 * @author malit
 */
public class SalePanel extends javax.swing.JPanel {
    /**
     * Creates new form SalePanel
     */
    public SalePanel() {
        initComponents();
        
        // Add our "finger drag = scroll" listener
        addHorizontalTouchScrollSupport(jScrollPaneCategoryList);
        addVerticalTouchScrollSupport(jScrollPaneItemCard);
        
        // Load all categories
        itemSelection2.setOnGridItemSelectListener((ItemCardDataModel itemCardDataModel) -> {
            if (ItemCardDataModel.CardType.MAIN_ITEM.equals(itemCardDataModel.getCardType())) {
                List<ItemCardDataModel> itemCardDataModels = new ArrayList<>();
                
                for (ItemStockModel itemStock : itemCardDataModel.getItemModel().getStock()) {
                    ItemCardDataModel icdm = new ItemCardDataModel();
                    icdm.setCardType(ItemCardDataModel.CardType.STOCK_ITEM);
                    icdm.setImageName(itemCardDataModel.getImageName());
                    icdm.setItemModel(itemCardDataModel.getItemModel());
                    icdm.setItemNameText(itemCardDataModel.getItemNameText());
                    icdm.setItemPriceText(GeneralUtil.getCurrencyString(itemStock.getSellingPrice()));
                    icdm.setItemStockModel(itemStock);
                    
                    itemCardDataModels.add(icdm);
                }
                loadItemCardData(itemCardDataModels);
            }
            else{
                saleInvoiceJPanel.loadItemToAddToBill(itemCardDataModel.getItemModel(), itemCardDataModel.getItemStockModel());
            }
        });
        
        categoryCardList1.setParentScrollPane(jScrollPaneCategoryList);
        categoryCardList1.loadAllCards();
        categoryCardList1.setOnCategorySelectListener((CategoryModel categoryModel) -> {
            // Change the items on category select
            List<ItemModel> items = DatabaseManager.getInstance().getItemsByCategoryId(categoryModel.getCategoryId());
            List<ItemCardDataModel> itemCardDataModels = new ArrayList();
            
            for (ItemModel item : items) {
                
                ItemCardDataModel itemCardDataModel = new ItemCardDataModel();
                
                if (item.getStock().isEmpty()) {
                    continue;
                }else if (item.getStock().size() == 1) {
                    itemCardDataModel.setCardType(ItemCardDataModel.CardType.STOCK_ITEM);
                }else{
                    itemCardDataModel.setCardType(ItemCardDataModel.CardType.MAIN_ITEM);
                }
                
                itemCardDataModel.setImageName(item.getImageName());
                itemCardDataModel.setItemModel(item);
                itemCardDataModel.setItemStockModel(item.getStock().get(0));
                itemCardDataModel.setItemNameText(Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())?
                            item.getItemNameSin():item.getItemName());
                itemCardDataModels.add(itemCardDataModel);
            }
            
            itemSelection2.loadItemGrid(itemCardDataModels);
        });
        
        EventManager.getInstance().addMenuItemChangeEvent(new MenuItemChangeEvent() {
            @Override
            public void onMenuItemSelected(MenuItemType menuItemType) {
                if (MenuItemType.ICON_CLICK.equals(menuItemType)) {
                                        
                }
            }
        });
        
        EventManager.getInstance().addSalesMenuItemClickEvent((SalesMenuItemClickListener.SalesMenuItem menuItem) -> {
            switch (menuItem) {
                case SEARCH_STOCK:
                    itemSelection2.loadStockSearchPanel();
                    break;
                case NUMBER_PAD:
                    itemSelection2.loadNumberPad();
                    break;
                default:
            }
        });  

        saleInvoiceJPanel.setParentSalePanel(this);
    }
    
    /**
     * Adds touch-style drag scrolling to a JScrollPane.
     */
    private static void addVerticalTouchScrollSupport(JScrollPane scrollPane) {
        final JScrollBar vbar = scrollPane.getVerticalScrollBar();

        MouseAdapter adapter = new MouseAdapter() {
            private int lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int delta = lastY - e.getYOnScreen();
                vbar.setValue(vbar.getValue() + delta);
                lastY = e.getYOnScreen();
            }
        };

        // Add to the viewport's view
        scrollPane.getViewport().addMouseListener(adapter);
        scrollPane.getViewport().addMouseMotionListener(adapter);
    }
    
    private static void addHorizontalTouchScrollSupport(JScrollPane scrollPane) {
        final JScrollBar hbar = scrollPane.getHorizontalScrollBar();

        MouseAdapter adapter = new MouseAdapter() {
            private int lastX;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = lastX - e.getXOnScreen();
                hbar.setValue(hbar.getValue() + deltaX);
                lastX = e.getXOnScreen();
            }
        };

        Component view = scrollPane.getViewport().getView();
        view.addMouseListener(adapter);
        view.addMouseMotionListener(adapter);
    }

    private static void addTouchScrollSupport(JScrollPane scrollPane) {
        final JScrollBar vbar = scrollPane.getVerticalScrollBar();
        final JScrollBar hbar = scrollPane.getHorizontalScrollBar();

        MouseAdapter adapter = new MouseAdapter() {
            private int lastX;
            private int lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaY = lastY - e.getYOnScreen();
                int deltaX = lastX - e.getXOnScreen();

                // Scroll vertically
                vbar.setValue(vbar.getValue() + deltaY);
                // Scroll horizontally
                hbar.setValue(hbar.getValue() + deltaX);

                lastY = e.getYOnScreen();
                lastX = e.getXOnScreen();
            }
        };

        // Attach to the viewport’s view
        Component view = scrollPane.getViewport();
        view.addMouseListener(adapter);
        view.addMouseMotionListener(adapter);
    }

    public void setNextInvoiceNumber() {
        saleInvoiceJPanel.setNextInvoiceNumber();
    }
    
    public void loadItemCardData(List<ItemCardDataModel> items)
    {
        itemSelection2.loadItemGrid(items);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel1 = new easyPOS.HeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPaneCategoryList = new javax.swing.JScrollPane();
        categoryCardList1 = new easyPOS.sale.CategoryCardList();
        jScrollPaneItemCard = new javax.swing.JScrollPane();
        itemSelection2 = new easyPOS.sale.ItemSelection();
        saleInvoiceJPanel = new easyPOS.sale.SaleInvoiceJPanel();
        salesMenuPanel1 = new easyPOS.sale.SalesMenuPanel();

        setBackground(new java.awt.Color(204, 204, 204));

        jScrollPaneCategoryList.setViewportView(categoryCardList1);

        jScrollPaneItemCard.setViewportView(itemSelection2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneItemCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPaneCategoryList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPaneCategoryList, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneItemCard)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saleInvoiceJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(salesMenuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(headerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(saleInvoiceJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salesMenuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

//Notes n=new Notes();
//        n.setCurUser(currentUser);
//        n.setVisible(true);
//        n.setNote(currentUser);
//        n.setDefaultCloseOperation(WIDTH);
    
    
//     //reset for new customer
//            clearFields();
//            jTextFieldGrossAmount.setText("");
//            jTextFieldTotalDis.setText("");
//            jTextFieldSalesNoOfItms.setText("");
//            jTextFieldNetTot.setText("");
//            jTextFieldEditableDis.setText("0");
//            jTextFieldWithoutDis.setText("");
//            clearSaleTbl(); 
//            loadStokeTble(0,"","");//load table
//            //availabe for new bill
//            nextRowNumber=0;
//            setNewInvoiceNo();//set new invoice no 
//            jTextFieldItmCode.requestFocus();//move cursor
//            billTotalBeforeDiscountAdd=0;
    
//    Calculator cal=new Calculator();
//        cal.setVisible(true);
//        cal.setDefaultCloseOperation(WIDTH);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private easyPOS.sale.CategoryCardList categoryCardList1;
    private easyPOS.HeaderPanel headerPanel1;
    private easyPOS.sale.ItemSelection itemSelection2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPaneCategoryList;
    private javax.swing.JScrollPane jScrollPaneItemCard;
    private easyPOS.sale.SaleInvoiceJPanel saleInvoiceJPanel;
    private easyPOS.sale.SalesMenuPanel salesMenuPanel1;
    // End of variables declaration//GEN-END:variables

}
