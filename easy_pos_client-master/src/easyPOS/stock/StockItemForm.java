package easyPOS.stock;

import appDataModels.CategoryModel;
import control.ApplicationDataManager;
import dataModels.Language;
import easyPOS.sale.ItemCard;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import localDatabase.DatabaseManager;
import serverDataModels.Item;
import serverDataModels.MeasureUnit;
import util.DateTimeUtil;

/**
 *
 * @author malit
 */
public class StockItemForm extends javax.swing.JPanel {

    private StockPanel parent;
    
    /**
     * Creates new form StockItemForm
     */
    public StockItemForm() {
        initComponents();
        
        try {
            Font customFontSin_20 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(20f);
            
            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())) {
                jTextFieldAddToStName1.setFont(customFontSin_20);
                jComboBoxAddItemCategory.setFont(customFontSin_20);
                jComboBoxAddItemMesUnit.setFont(customFontSin_20);
            }
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(ItemCard.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        clearFieldsAll();
    }
    
    public void setInitDataToUI(StockPanel parent)
    {
        this.parent = parent;
        
    }
    
    
    void loadData() {
        loadComboValues();
        switchLanguage();
    }
    
    private void loadComboValues(){
        List<CategoryModel> categoryModels = DatabaseManager.getInstance().getCategories();
        List<MeasureUnit> measureUnits = DatabaseManager.getInstance().getMeasureUnits();
        
        // Cat combo
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (CategoryModel categoryModel : categoryModels) {
            comboBoxModel.addElement(categoryModel.getCatName() + " - " + categoryModel.getCatNameSin());
        }

        jComboBoxAddItemCategory.setModel(comboBoxModel);
        
        // Measure Unit combo
        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();
        for (MeasureUnit measureUnit : measureUnits) {
            comboBoxModel2.addElement(measureUnit.unit_name_eng + " - " + measureUnit.unit_name_sin);
        }

        jComboBoxAddItemMesUnit.setModel(comboBoxModel2);
        
    }
    
    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/stock/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            //create the font to use. Specify the size!

            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont1);
            jLabelNewItemItemCode.setFont(customFont1);
            jLabelNewItemBarcode.setFont(customFont1);
            jLabelNewItemName.setFont(customFont1);
            jLabelNewItemSubName.setFont(customFont1);
            jLabelNewItemNameSin.setFont(customFont1);
            jLabelNewItemNameTam.setFont(customFont1);
            jLabelNewItemCat.setFont(customFont1);
            jLabelNewItemMUnit.setFont(customFont1);
            jLabelNewItemComment.setFont(customFont1);


        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }

        //use the font
        jLabelNewItemItemCode.setText(resourceBundle.getString("StockItemForm.jLabelNewItemItemCode.text"));
        jLabelNewItemBarcode.setText(resourceBundle.getString("StockItemForm.jLabelNewItemBarcode.text"));
        jLabelNewItemName.setText(resourceBundle.getString("StockItemForm.jLabelNewItemName.text"));
        jLabelNewItemSubName.setText(resourceBundle.getString("StockItemForm.jLabelNewItemSubName.text"));
        jLabelNewItemNameSin.setText(resourceBundle.getString("StockItemForm.jLabelNewItemNameSin.text"));
        jLabelNewItemNameTam.setText(resourceBundle.getString("StockItemForm.jLabelNewItemNameTam.text"));
        jLabelNewItemCat.setText(resourceBundle.getString("StockItemForm.jLabelNewItemCat.text"));
        jLabelNewItemMUnit.setText(resourceBundle.getString("StockItemForm.jLabelNewItemMUnit.text"));
        jLabelNewItemComment.setText(resourceBundle.getString("StockItemForm.jLabelNewItemComment.text"));
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel14 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelNewItemItemCode = new javax.swing.JLabel();
        jTextFieldAddToStItCode = new javax.swing.JTextField();
        jTextFieldAddToStItCode1 = new javax.swing.JTextField();
        jLabelNewItemBarcode = new javax.swing.JLabel();
        jLabelNewItemName = new javax.swing.JLabel();
        jTextFieldAddToStName = new javax.swing.JTextField();
        jTextFieldAddToStSubName = new javax.swing.JTextField();
        jLabelNewItemSubName = new javax.swing.JLabel();
        jLabelNewItemNameSin = new javax.swing.JLabel();
        jTextFieldAddToStName1 = new javax.swing.JTextField();
        jLabelNewItemNameTam = new javax.swing.JLabel();
        jTextFieldAddToStName2 = new javax.swing.JTextField();
        jLabelNewItemCat = new javax.swing.JLabel();
        jComboBoxAddItemCategory = new javax.swing.JComboBox();
        jLabelNewItemMUnit = new javax.swing.JLabel();
        jComboBoxAddItemMesUnit = new javax.swing.JComboBox();
        jLabelNewItemComment = new javax.swing.JLabel();
        jTextFieldAddToStComent = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jTextFieldMinimumQtyForAlert = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextFieldMinimumQtyForAlert1 = new javax.swing.JTextField();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/stock/Bundle"); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("StockItemForm.jPanel1.border.title"))); // NOI18N

        jLabelNewItemItemCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemItemCode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemItemCode.setText(bundle.getString("StockItemForm.jLabelNewItemItemCode.text")); // NOI18N

        jTextFieldAddToStItCode.setEditable(false);
        jTextFieldAddToStItCode.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStItCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStItCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStItCodeActionPerformed(evt);
            }
        });
        jTextFieldAddToStItCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStItCodeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStItCodeKeyTyped(evt);
            }
        });

        jTextFieldAddToStItCode1.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStItCode1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStItCode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStItCode1ActionPerformed(evt);
            }
        });
        jTextFieldAddToStItCode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStItCode1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStItCode1KeyTyped(evt);
            }
        });

        jLabelNewItemBarcode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemBarcode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemBarcode.setText(bundle.getString("StockItemForm.jLabelNewItemBarcode.text")); // NOI18N

        jLabelNewItemName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemName.setText(bundle.getString("StockItemForm.jLabelNewItemName.text")); // NOI18N

        jTextFieldAddToStName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStNameActionPerformed(evt);
            }
        });
        jTextFieldAddToStName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStNameKeyTyped(evt);
            }
        });

        jTextFieldAddToStSubName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStSubName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStSubNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStSubNameKeyTyped(evt);
            }
        });

        jLabelNewItemSubName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemSubName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemSubName.setText(bundle.getString("StockItemForm.jLabelNewItemSubName.text")); // NOI18N

        jLabelNewItemNameSin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemNameSin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemNameSin.setText(bundle.getString("StockItemForm.jLabelNewItemNameSin.text")); // NOI18N

        jTextFieldAddToStName1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStName1ActionPerformed(evt);
            }
        });
        jTextFieldAddToStName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStName1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStName1KeyTyped(evt);
            }
        });

        jLabelNewItemNameTam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemNameTam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemNameTam.setText(bundle.getString("StockItemForm.jLabelNewItemNameTam.text")); // NOI18N

        jTextFieldAddToStName2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStName2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStName2ActionPerformed(evt);
            }
        });
        jTextFieldAddToStName2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStName2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStName2KeyTyped(evt);
            }
        });

        jLabelNewItemCat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemCat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemCat.setText(bundle.getString("StockItemForm.jLabelNewItemCat.text")); // NOI18N

        jComboBoxAddItemCategory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxAddItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAddItemCategoryActionPerformed(evt);
            }
        });
        jComboBoxAddItemCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxAddItemCategoryKeyPressed(evt);
            }
        });

        jLabelNewItemMUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemMUnit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemMUnit.setText(bundle.getString("StockItemForm.jLabelNewItemMUnit.text")); // NOI18N

        jComboBoxAddItemMesUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxAddItemMesUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAddItemMesUnitActionPerformed(evt);
            }
        });
        jComboBoxAddItemMesUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxAddItemMesUnitKeyPressed(evt);
            }
        });

        jLabelNewItemComment.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewItemComment.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNewItemComment.setText(bundle.getString("StockItemForm.jLabelNewItemComment.text")); // NOI18N

        jTextFieldAddToStComent.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStComent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStComent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStComentKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelNewItemComment, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelNewItemCat, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelNewItemNameSin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(jLabelNewItemName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNewItemBarcode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNewItemItemCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldAddToStName1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAddToStName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAddToStItCode1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxAddItemCategory, 0, 250, Short.MAX_VALUE)
                            .addComponent(jTextFieldAddToStItCode, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelNewItemNameTam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(jLabelNewItemMUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNewItemSubName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAddToStName2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAddToStSubName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxAddItemMesUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextFieldAddToStComent)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddToStItCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNewItemItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddToStItCode1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNewItemBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNewItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNewItemSubName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStSubName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNewItemNameSin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStName1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStName2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNewItemNameTam, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNewItemCat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxAddItemCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNewItemMUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxAddItemMesUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddToStComent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNewItemComment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("StockItemForm.jPanel5.border.title"))); // NOI18N

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel78.setText(bundle.getString("StockItemForm.jLabel78.text")); // NOI18N

        jTextFieldMinimumQtyForAlert.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldMinimumQtyForAlert.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMinimumQtyForAlertKeyPressed(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel80.setText(bundle.getString("StockItemForm.jLabel80.text")); // NOI18N

        jTextFieldMinimumQtyForAlert1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldMinimumQtyForAlert1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMinimumQtyForAlert1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMinimumQtyForAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMinimumQtyForAlert1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMinimumQtyForAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMinimumQtyForAlert1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldAddToStItCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStItCodeActionPerformed

    private void jTextFieldAddToStItCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCodeKeyPressed
        
    }//GEN-LAST:event_jTextFieldAddToStItCodeKeyPressed

    private void jTextFieldAddToStItCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCodeKeyTyped
//        loadStokeTbleFull(5, jTextFieldAddToStItCode.getText(),"");////should load the table search ///////
        if(jTextFieldAddToStItCode.getText().length()>8){
            jLabelNewItemItemCode.setText("maximum length=8");
            jLabelNewItemItemCode.setForeground(Color.RED);

        }else{
            jLabelNewItemItemCode.setText("Item Code:");
            jLabelNewItemItemCode.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldAddToStItCodeKeyTyped

    private void jTextFieldAddToStNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStNameActionPerformed

    private void jTextFieldAddToStNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStNameKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldAddToStItCode.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldAddToStSubName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldAddToStSubName.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldAddToStNameKeyPressed

    private void jTextFieldAddToStNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStNameKeyTyped
//        loadStokeTbleFull(6, jTextFieldAddToStName.getText(),"");
        if(jTextFieldAddToStName.getText().length()>32){
            jLabelNewItemName.setText("max length=32");
            jLabelNewItemName.setForeground(Color.RED);

        }else{
            jLabelNewItemName.setText("Item Name:");
            jLabelNewItemName.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldAddToStNameKeyTyped

    private void jTextFieldAddToStSubNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSubNameKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldAddToStName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jComboBoxAddItemCategory.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jComboBoxAddItemCategory.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldAddToStSubNameKeyPressed

    private void jTextFieldAddToStSubNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSubNameKeyTyped

        if(jTextFieldAddToStSubName.getText().length()>32){
            jLabelNewItemSubName.setText("max length=32");
            jLabelNewItemSubName.setForeground(Color.RED);

        }else{
            jLabelNewItemSubName.setText("Sub Name:");
            jLabelNewItemSubName.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldAddToStSubNameKeyTyped

    private void jComboBoxAddItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAddItemCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAddItemCategoryActionPerformed

    private void jComboBoxAddItemCategoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxAddItemCategoryKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldAddToStSubName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
//            jTextFieldAddToStTotPaid.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            jTextFieldAddToStTotPaid.requestFocus();
        }
    }//GEN-LAST:event_jComboBoxAddItemCategoryKeyPressed

    private void jTextFieldAddToStComentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStComentKeyPressed

    }//GEN-LAST:event_jTextFieldAddToStComentKeyPressed

    private void jTextFieldMinimumQtyForAlertKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMinimumQtyForAlertKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
//            jTextFieldAddToStSupCode2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
//            jTextFieldExpDte.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            jTextFieldExpDte.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldMinimumQtyForAlertKeyPressed

    private void jTextFieldAddToStItCode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStItCode1ActionPerformed

    private void jTextFieldAddToStItCode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStItCode1KeyPressed

    private void jTextFieldAddToStItCode1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCode1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStItCode1KeyTyped

    private void jTextFieldAddToStName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStName1ActionPerformed

    private void jTextFieldAddToStName1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStName1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStName1KeyPressed

    private void jTextFieldAddToStName1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStName1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStName1KeyTyped

    private void jTextFieldAddToStName2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStName2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStName2ActionPerformed

    private void jTextFieldAddToStName2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStName2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStName2KeyPressed

    private void jTextFieldAddToStName2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStName2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStName2KeyTyped

    private void jComboBoxAddItemMesUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAddItemMesUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAddItemMesUnitActionPerformed

    private void jComboBoxAddItemMesUnitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxAddItemMesUnitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAddItemMesUnitKeyPressed

    private void jTextFieldMinimumQtyForAlert1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMinimumQtyForAlert1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMinimumQtyForAlert1KeyPressed

    public Item validateAndGetUIData(){
        Item item = getItemModelFromUI();
        if (validateUIData(item)) {
            return item;
        }
        return null;
    }
    
    private Item getItemModelFromUI(){
        
        Item item = new Item();
        
        try {
            item.item_id = 0; 
            item.institute_id = ApplicationDataManager.getInstance().getLoggedInUser().getInstituteId();  
            item.barcode = jTextFieldAddToStItCode1.getText(); 
            item.item_name = jTextFieldAddToStName.getText(); 
            item.item_name_sin = jTextFieldAddToStName1.getText(); 
            item.item_name_tam = jTextFieldAddToStName2.getText(); 
            item.image_name = ""; 
            item.minimum_stock = Double.parseDouble(jTextFieldMinimumQtyForAlert.getText()); 
            item.added_by = ApplicationDataManager.getInstance().getLoggedInUser().getUserName(); 
            item.added_date_time = DateTimeUtil.getCurrentDateTimeDBFormat(); 
            item.modified_by = ApplicationDataManager.getInstance().getLoggedInUser().getUserName(); 
            item.modified_date_time = DateTimeUtil.getCurrentDateTimeDBFormat(); 
            item.is_active = 1;

            List<CategoryModel> allCategories = DatabaseManager.getInstance().getCategories();        
            List<MeasureUnit> allMeasureUnits = DatabaseManager.getInstance().getMeasureUnits();
            item.measure_unit_id = allMeasureUnits.get(jComboBoxAddItemMesUnit.getSelectedIndex()).measure_unit_id;
            item.category_id = allCategories.get(jComboBoxAddItemCategory.getSelectedIndex()).getCategoryId();
            
        } catch (Exception e) {
        }
                
        return item;
    }
    
    private boolean validateUIData(Item item){
        if (item.barcode == null || item.barcode.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Barcode required");
            return false;
        }
        if (item.item_name == null || item.item_name.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Item Name required");
            return false;
        }
        if (item.item_name_sin == null || item.item_name_sin.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Item Name Sinhala required");
            return false;
        }
        
        if (item.category_id == 0) {
            JOptionPane.showMessageDialog(parent, "Incorrect Category");
            return false;
        }
        if (item.measure_unit_id == 0) {
            JOptionPane.showMessageDialog(parent, "Incorrcet Measure Unit");
            return false;
        }
        
        return true;
    }
    
    final void clearFieldsAll(){
        jTextFieldAddToStComent.setText("");
        jTextFieldAddToStItCode.setText("");
        jTextFieldAddToStItCode1.setText("");
        jTextFieldAddToStName.setText("");
        jTextFieldAddToStName1.setText("");
        jTextFieldAddToStName2.setText("");
        jTextFieldAddToStSubName.setText("");
        jTextFieldMinimumQtyForAlert.setText("0");
        jTextFieldMinimumQtyForAlert1.setText("0");
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBoxAddItemCategory;
    private javax.swing.JComboBox jComboBoxAddItemMesUnit;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabelNewItemBarcode;
    private javax.swing.JLabel jLabelNewItemCat;
    private javax.swing.JLabel jLabelNewItemComment;
    private javax.swing.JLabel jLabelNewItemItemCode;
    private javax.swing.JLabel jLabelNewItemMUnit;
    private javax.swing.JLabel jLabelNewItemName;
    private javax.swing.JLabel jLabelNewItemNameSin;
    private javax.swing.JLabel jLabelNewItemNameTam;
    private javax.swing.JLabel jLabelNewItemSubName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextFieldAddToStComent;
    private javax.swing.JTextField jTextFieldAddToStItCode;
    private javax.swing.JTextField jTextFieldAddToStItCode1;
    private javax.swing.JTextField jTextFieldAddToStName;
    private javax.swing.JTextField jTextFieldAddToStName1;
    private javax.swing.JTextField jTextFieldAddToStName2;
    private javax.swing.JTextField jTextFieldAddToStSubName;
    private javax.swing.JTextField jTextFieldMinimumQtyForAlert;
    private javax.swing.JTextField jTextFieldMinimumQtyForAlert1;
    // End of variables declaration//GEN-END:variables

}
