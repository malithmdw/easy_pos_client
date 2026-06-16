
package garbage;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author malit
 */
public class InventoryItemForm extends javax.swing.JPanel {

    /**
     * Creates new form StockItemForm
     */
    public InventoryItemForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel14 = new javax.swing.JPanel();
        jButtonAddNewSto = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldAddToStItCode = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldAddToStName = new javax.swing.JTextField();
        jTextFieldAddToStSubName = new javax.swing.JTextField();
        jTextFieldAddToStQty = new javax.swing.JTextField();
        jTextFieldAddToStSellrPrice = new javax.swing.JTextField();
        jComboBoxStokeAdd = new javax.swing.JComboBox();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldAddToStDis = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jTextFieldAddToStComent = new javax.swing.JTextField();
        jButtonChangeStore = new javax.swing.JButton();
        jTextFieldMinimumQtyForAlert = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jButtonAddNewToStoke = new javax.swing.JButton();
        jButtonDeleteStore2 = new javax.swing.JButton();
        jButtonCancl = new javax.swing.JButton();
        jTextFieldExpDte = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldAddToStItCode1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldAddToStName1 = new javax.swing.JTextField();
        jTextFieldAddToStName2 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jComboBoxStokeAdd1 = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jTextFieldAddToStQty1 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextFieldMinimumQtyForAlert1 = new javax.swing.JTextField();
        jComboBoxStokeAdd2 = new javax.swing.JComboBox();
        jComboBoxStokeAdd3 = new javax.swing.JComboBox();

        jButtonAddNewSto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonAddNewSto.setText("Add New Stock");
        jButtonAddNewSto.setToolTipText("You can add stoke here.old stoke will be changed as new prices.");
        jButtonAddNewSto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNewStoActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Barcode:");

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

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Item Name:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Sub Name:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Category:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Qty:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Salling Price of 1 Qty:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Supplier 1:");

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

        jTextFieldAddToStQty.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStQty.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStQtyKeyPressed(evt);
            }
        });

        jTextFieldAddToStSellrPrice.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStSellrPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStSellrPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAddToStSellrPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddToStSellrPriceActionPerformed(evt);
            }
        });
        jTextFieldAddToStSellrPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStSellrPriceKeyPressed(evt);
            }
        });

        jComboBoxStokeAdd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxStokeAdd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));
        jComboBoxStokeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStokeAddActionPerformed(evt);
            }
        });
        jComboBoxStokeAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxStokeAddKeyPressed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("Discount Per 1 Qty:");

        jTextFieldAddToStDis.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStDis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAddToStDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStDisKeyPressed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel57.setText("Supplier 2:");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("Comment:");

        jTextFieldAddToStComent.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStComent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStComent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStComentKeyPressed(evt);
            }
        });

        jButtonChangeStore.setBackground(new java.awt.Color(255, 153, 102));
        jButtonChangeStore.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonChangeStore.setText("Save Changes");
        jButtonChangeStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangeStoreActionPerformed(evt);
            }
        });

        jTextFieldMinimumQtyForAlert.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldMinimumQtyForAlert.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMinimumQtyForAlertKeyPressed(evt);
            }
        });

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel78.setText("Minimum Qty For Alert:");

        jButtonAddNewToStoke.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonAddNewToStoke.setText("Add To Stock As a New Item");
        jButtonAddNewToStoke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNewToStokeActionPerformed(evt);
            }
        });

        jButtonDeleteStore2.setBackground(new java.awt.Color(255, 0, 0));
        jButtonDeleteStore2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonDeleteStore2.setText("Delete Stock");
        jButtonDeleteStore2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteStore2ActionPerformed(evt);
            }
        });

        jButtonCancl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonCancl.setText("Cancel");
        jButtonCancl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCanclActionPerformed(evt);
            }
        });

        jTextFieldExpDte.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldExpDte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldExpDteKeyPressed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel79.setText("Exp date:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Item Code:");

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

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Item Name Sinhala:");

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

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Item Name Tamil:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Measure Unit:");

        jComboBoxStokeAdd1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxStokeAdd1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));
        jComboBoxStokeAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStokeAdd1ActionPerformed(evt);
            }
        });
        jComboBoxStokeAdd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxStokeAdd1KeyPressed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Wholesale Price:");

        jTextFieldAddToStQty1.setBackground(new java.awt.Color(204, 204, 255));
        jTextFieldAddToStQty1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAddToStQty1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAddToStQty1KeyPressed(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel80.setText("Wholesale Minimum Qty:");

        jTextFieldMinimumQtyForAlert1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldMinimumQtyForAlert1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMinimumQtyForAlert1KeyPressed(evt);
            }
        });

        jComboBoxStokeAdd2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxStokeAdd2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));
        jComboBoxStokeAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStokeAdd2ActionPerformed(evt);
            }
        });
        jComboBoxStokeAdd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxStokeAdd2KeyPressed(evt);
            }
        });

        jComboBoxStokeAdd3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxStokeAdd3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tablet", "capsule", "syrup", "drops", "oinment", "cream", "other medicine", "g- milk powder", "g- biscuits", "g-cosmatics", "grocery-other" }));
        jComboBoxStokeAdd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStokeAdd3ActionPerformed(evt);
            }
        });
        jComboBoxStokeAdd3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxStokeAdd3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonAddNewToStoke, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonChangeStore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonDeleteStore2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonCancl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonAddNewSto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldExpDte, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addComponent(jTextFieldAddToStComent)))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxStokeAdd1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAddToStName1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldAddToStName)
                    .addComponent(jTextFieldAddToStSubName)
                    .addComponent(jComboBoxStokeAdd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAddToStQty, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldAddToStItCode)
                    .addComponent(jTextFieldAddToStItCode1)
                    .addComponent(jTextFieldAddToStName2)))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jTextFieldAddToStQty1))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldMinimumQtyForAlert1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldMinimumQtyForAlert, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAddToStSellrPrice)
                            .addComponent(jTextFieldAddToStDis)
                            .addComponent(jComboBoxStokeAdd3, 0, 361, Short.MAX_VALUE)
                            .addComponent(jComboBoxStokeAdd2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddToStItCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddToStItCode1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStSubName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStName1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStName2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxStokeAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxStokeAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStQty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStQty1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStSellrPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAddToStDis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxStokeAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxStokeAdd3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMinimumQtyForAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMinimumQtyForAlert1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldExpDte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddToStComent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonCancl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddNewSto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddNewToStoke, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonChangeStore, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDeleteStore2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddNewStoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNewStoActionPerformed
        addNewStock();
    }//GEN-LAST:event_jButtonAddNewStoActionPerformed

    private void jTextFieldAddToStItCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStItCodeActionPerformed

    private void jTextFieldAddToStItCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCodeKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //            if(stock.checkItemCodeExist(jTextFieldAddToStItCode.getText())==true){
                //                jTextFieldAddToStTotPaid.requestFocus();
                //                try{
                    //                    String[] s=(stock.unitSellingPriceAndDis(jTextFieldAddToStItCode.getText())).split("&");
                    //                    double sellingPrice=Double.parseDouble(s[0]);
                    //                    double dis=Double.parseDouble(s[1]);
                    //                    jTextFieldAddToStName.setText(stock.getItemName(jTextFieldAddToStItCode.getText()));
                    //                    jTextFieldExpDte.setText(dateFormatForUI(stock.getExpDate(jTextFieldAddToStItCode.getText())));
                    //                    jTextFieldAddToStSellrPrice.setText(df.format(sellingPrice));
                    //                    jTextFieldAddToStDis.setText(df.format(dis));
                    //                }catch(Exception e){
                    //                }
                //            }else{
                //                jTextFieldAddToStName.requestFocus();
                //            }
            //        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            //            jTextFieldAddToStName.requestFocus();
            //        }
    }//GEN-LAST:event_jTextFieldAddToStItCodeKeyPressed

    private void jTextFieldAddToStItCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStItCodeKeyTyped
//        loadStokeTbleFull(5, jTextFieldAddToStItCode.getText(),"");////should load the table search ///////
        if(jTextFieldAddToStItCode.getText().length()>8){
            jLabel18.setText("maximum length=8");
            jLabel18.setForeground(Color.RED);

        }else{
            jLabel18.setText("Item Code:");
            jLabel18.setForeground(Color.BLACK);
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
            jLabel23.setText("max length=32");
            jLabel23.setForeground(Color.RED);

        }else{
            jLabel23.setText("Item Name:");
            jLabel23.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldAddToStNameKeyTyped

    private void jTextFieldAddToStSubNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSubNameKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldAddToStName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jComboBoxStokeAdd.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jComboBoxStokeAdd.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldAddToStSubNameKeyPressed

    private void jTextFieldAddToStSubNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSubNameKeyTyped

        if(jTextFieldAddToStSubName.getText().length()>32){
            jLabel24.setText("max length=32");
            jLabel24.setForeground(Color.RED);

        }else{
            jLabel24.setText("Sub Name:");
            jLabel24.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldAddToStSubNameKeyTyped

    private void jTextFieldAddToStQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStQtyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
//            jTextFieldAddToStTotPaid.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
//            jTextFieldAddToStPurPrice.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            if(jTextFieldAddToStTotPaid.getText().equals("")){
//            }else{
//                try{
//                    double totalPaid=Double.parseDouble(jTextFieldAddToStTotPaid.getText());
//                    double qty=Double.parseDouble(jTextFieldAddToStQty.getText());
//                    double unitPerchPrice=(totalPaid/qty);
//                    jTextFieldAddToStPurPrice.setText(df.format(unitPerchPrice));
//                }catch(Exception e){
//                }
//            }
//            jTextFieldAddToStPurPrice.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldAddToStQtyKeyPressed

    private void jTextFieldAddToStSellrPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSellrPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStSellrPriceActionPerformed

    private void jTextFieldAddToStSellrPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStSellrPriceKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
//            jTextFieldAddToStPurPrice.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldAddToStDis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldAddToStDis.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldAddToStSellrPriceKeyPressed

    private void jComboBoxStokeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStokeAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAddActionPerformed

    private void jComboBoxStokeAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxStokeAddKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_LEFT){
            jTextFieldAddToStSubName.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
//            jTextFieldAddToStTotPaid.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            jTextFieldAddToStTotPaid.requestFocus();
        }
    }//GEN-LAST:event_jComboBoxStokeAddKeyPressed

    private void jTextFieldAddToStDisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStDisKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_UP){
            //            jTextFieldAddToStSellrPrice.requestFocus();
            //        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            //            jTextFieldAddToStSupCode.requestFocus();
            //        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //            if(stock.checkItemCodeExist(jTextFieldAddToStItCode.getText())==true){
                //                jTextFieldAddToStComent.requestFocus();
                //            }else{
                //                jTextFieldAddToStSupCode.requestFocus();
                //            }
            //        }
    }//GEN-LAST:event_jTextFieldAddToStDisKeyPressed

    private void jTextFieldAddToStComentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStComentKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldExpDte.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldAddToStItCode.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldAddToStItCode.requestFocus();
        }else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A){
            //shortcut add stoke (Ctrl+A)
            addNewStock();
        }else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N){
            //shortcut add new item to stoke (Ctrl+N)
            addNewItemToStock();
        }
    }//GEN-LAST:event_jTextFieldAddToStComentKeyPressed

    private void jButtonChangeStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangeStoreActionPerformed
        //        if(jTextFieldAddToStItCode.getText().equals("")|| jTextFieldAddToStName.getText().equals("")||
            //            jTextFieldAddToStQty.getText().equals("") || jTextFieldAddToStPurPrice.getText().equals("") ||
            //            jTextFieldAddToStSellrPrice.getText().equals("")){
            //            JOptionPane.showMessageDialog(null, "Fill the required fields !\n ");
            //
            //        }else{
            //            StockItemDataModel sv=new StockItemDataModel();
            //            try{
                //                sv.setItem_code(jTextFieldAddToStItCode.getText());
                //                sv.setItem_name(jTextFieldAddToStName.getText());
                //                sv.setSub_name(jTextFieldAddToStSubName.getText());
                //                sv.setCategory(jComboBoxStokeAdd.getSelectedItem().toString());
                //                sv.setQty(Integer.parseInt(jTextFieldAddToStQty.getText()));
                //                sv.setPurchase_price(Double.parseDouble(jTextFieldAddToStPurPrice.getText()));
                //                sv.setSelling_price(Double.parseDouble(jTextFieldAddToStSellrPrice.getText()));
                //                sv.setDiscount(Double.parseDouble(jTextFieldAddToStDis.getText()));
                //                sv.setSupp_code1(jTextFieldAddToStSupCode.getText());
                //                sv.setSupp_code2(jTextFieldAddToStSupCode2.getText());
                //                sv.setComment(jTextFieldAddToStComent.getText());
                //                sv.setMinimumQtyForAlert(Integer.parseInt(jTextFieldMinimumQtyForAlert.getText()));
                //                sv.setLast_edit_date(jTextFieldDate2.getText());
                //                sv.setLast_editor(currentUser);
                //                sv.setExp_date(dateFormatForDB(jTextFieldExpDte.getText()));
                //
                //                boolean checkItmCode=stock.checkItemCodeExist(jTextFieldAddToStItCode.getText());
                //                if(checkItmCode==true){
                    //                    boolean resu=stock.changeStoke(sv,jTextFieldAddToStItCode.getText());
                    //                    if(resu==true){
                        //                        clearFieldsStokeAdd();
                        //                        loadStokeTbleFull(0,"","");//Here should load table
                        //                        jButtonChangeStore.setVisible(false);
                        //                        jButtonAddNewSto.setVisible(true);
                        //                        jButtonAddNewToStoke.setVisible(true);
                        //                    }else{
                        //                        JOptionPane.showMessageDialog(null, "Can't do your process !\n ");
                        //                    }
                    //
                    //                }else{
                    //                    JOptionPane.showMessageDialog(null, "Item code does not exist !.\n You can add as a new item.\n ");
                    //                }
                //            }catch(Exception e){
                //                JOptionPane.showMessageDialog(null, "Illegal input method !.\n check and try again.\n ");
                //            }
            //        }
    }//GEN-LAST:event_jButtonChangeStoreActionPerformed

    private void jTextFieldMinimumQtyForAlertKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMinimumQtyForAlertKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
//            jTextFieldAddToStSupCode2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldExpDte.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldExpDte.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldMinimumQtyForAlertKeyPressed

    private void jButtonAddNewToStokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNewToStokeActionPerformed
        addNewItemToStock();
    }//GEN-LAST:event_jButtonAddNewToStokeActionPerformed

    private void jButtonDeleteStore2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteStore2ActionPerformed
        //        int ans=JOptionPane.showConfirmDialog(rootPane, "Are you sure?\n Do you want to permanently delete this stock data ?");
        //        if(ans==JOptionPane.YES_OPTION){
            //            boolean result=stock.deleteStock(jTextFieldAddToStItCode.getText());
            //            if(result==true){
                //                im.deleteMovementItem(jTextFieldAddToStItCode.getText());//delete this item from item_movement table
                //                JOptionPane.showMessageDialog(rootPane, "Successfully deleted.");
                //                clearFieldsStokeAdd();
                //                loadStokeTbleFull(0,"","");//Here should load table
                //                jButtonDeleteStore2.setVisible(false);
                //                jButtonAddNewSto.setVisible(true);
                //                jButtonAddNewToStoke.setVisible(true);
                //            }else{
                //                JOptionPane.showMessageDialog(rootPane, "Error occur while deleting !");
                //            }
            //        }
    }//GEN-LAST:event_jButtonDeleteStore2ActionPerformed

    private void jButtonCanclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCanclActionPerformed
        clearFieldsStokeAdd();
        jButtonChangeStore.setVisible(false);
        jButtonDeleteStore2.setVisible(false);

        jButtonAddNewSto.setVisible(true);
//        if(addingStocks==1){
//            jButtonAddNewToStoke.setVisible(true);
//        }else{
//            jButtonAddNewToStoke.setVisible(false);
//        }
    }//GEN-LAST:event_jButtonCanclActionPerformed

    private void jTextFieldExpDteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldExpDteKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            jTextFieldMinimumQtyForAlert.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            jTextFieldAddToStComent.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTextFieldAddToStComent.requestFocus();
        }else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A){
            //shortcut add stoke (Ctrl+A)
            addNewStock();
        }else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N){
            //shortcut add new item to stoke (Ctrl+N)
            addNewItemToStock();
        }
    }//GEN-LAST:event_jTextFieldExpDteKeyPressed

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

    private void jComboBoxStokeAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStokeAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAdd1ActionPerformed

    private void jComboBoxStokeAdd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxStokeAdd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAdd1KeyPressed

    private void jTextFieldAddToStQty1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAddToStQty1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddToStQty1KeyPressed

    private void jTextFieldMinimumQtyForAlert1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMinimumQtyForAlert1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMinimumQtyForAlert1KeyPressed

    private void jComboBoxStokeAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStokeAdd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAdd2ActionPerformed

    private void jComboBoxStokeAdd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxStokeAdd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAdd2KeyPressed

    private void jComboBoxStokeAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStokeAdd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAdd3ActionPerformed

    private void jComboBoxStokeAdd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxStokeAdd3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxStokeAdd3KeyPressed

    void addNewStock(){
//        if(jTextFieldAddToStItCode.getText().equals("")||
//            jTextFieldAddToStQty.getText().equals("") || jTextFieldAddToStPurPrice.getText().equals("") ||
//            jTextFieldAddToStSellrPrice.getText().equals("")){
//            JOptionPane.showMessageDialog(null, "Fill the required fields !\n ");
//
//        }else{
//            StockItemDataModel sv=new StockItemDataModel();
//            try{
//                sv.setItem_code(jTextFieldAddToStItCode.getText());
//                sv.setItem_name(jTextFieldAddToStName.getText());
//                sv.setSub_name(jTextFieldAddToStSubName.getText());
//                sv.setCategory(jComboBoxStokeAdd.getSelectedItem().toString());
//                sv.setQty(Integer.parseInt(jTextFieldAddToStQty.getText()));
//                sv.setPurchase_price(Double.parseDouble(jTextFieldAddToStPurPrice.getText()));
//                sv.setSelling_price(Double.parseDouble(jTextFieldAddToStSellrPrice.getText()));
//                sv.setDiscount(Double.parseDouble(jTextFieldAddToStDis.getText()));
//                sv.setSupp_code1(jTextFieldAddToStSupCode.getText());
//                sv.setSupp_code2(jTextFieldAddToStSupCode2.getText());
//                sv.setComment(jTextFieldAddToStComent.getText());
//                sv.setLast_edit_date(todayDATE);
//                sv.setLast_editor(currentUser);
//                sv.setExp_date(dateFormatForDB(jTextFieldExpDte.getText()));
//
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(null, "Illegal input.\n check and try again !\n ");
//            }
//
//            boolean checkItmCode=stock.checkItemCodeExist(jTextFieldAddToStItCode.getText());
//            if(checkItmCode==true){
//                boolean resu=stock.addStoke(sv,jTextFieldAddToStItCode.getText());
//                if(resu==true){
//                    if(startAutoCreateInv==1){
//                        int qt=Integer.parseInt(jTextFieldAddToStQty.getText());
//                        double unitPerPri=Double.parseDouble(jTextFieldAddToStPurPrice.getText());
//                        boolean res=supp.editTemp(qt*unitPerPri, "0.00", "0.00", currentUser);
//                        if(res==false){
//                            JOptionPane.showMessageDialog(null, "Auto create invoice process is not working!\n ");
//                        }
//                    }                    
//                    clearFieldsStokeAdd();
//                    jTextFieldAddToStItCode.requestFocus();
//                    loadStokeTbleFull(0,"","");//Here should load table
//                }else{
//                    JOptionPane.showMessageDialog(null, "Can not do your process !\n ");
//                }
//            }else{
//                JOptionPane.showMessageDialog(null, "Item code does not exist in stock.\n Enter as a new item ?\n ");
//            }
//        }
    }
    void addNewItemToStock(){
//        if(jTextFieldAddToStItCode.getText().equals("")|| jTextFieldAddToStName.getText().equals("")||
//            jTextFieldAddToStQty.getText().equals("") || jTextFieldAddToStPurPrice.getText().equals("") ||
//            jTextFieldAddToStSellrPrice.getText().equals("")){
//            JOptionPane.showMessageDialog(null, "Fill the required fields !\n ");
//
//        }else{
//            StockItemDataModel sv=new StockItemDataModel();
//            try{
//                sv.setItem_code(jTextFieldAddToStItCode.getText());
//                sv.setItem_name(jTextFieldAddToStName.getText());
//                sv.setSub_name(jTextFieldAddToStSubName.getText());
//                sv.setCategory(jComboBoxStokeAdd.getSelectedItem().toString());
//                sv.setQty(Integer.parseInt(jTextFieldAddToStQty.getText()));
//                sv.setPurchase_price(Double.parseDouble(jTextFieldAddToStPurPrice.getText()));
//                sv.setSelling_price(Double.parseDouble(jTextFieldAddToStSellrPrice.getText()));
//                sv.setDiscount(Double.parseDouble(jTextFieldAddToStDis.getText()));
//                sv.setSupp_code1(jTextFieldAddToStSupCode.getText());
//                sv.setSupp_code2(jTextFieldAddToStSupCode2.getText());
//                sv.setComment(jTextFieldAddToStComent.getText());
//                sv.setMinimumQtyForAlert(Integer.parseInt(jTextFieldMinimumQtyForAlert.getText()));
//                sv.setLast_edit_date(jTextFieldDate2.getText());
//                sv.setLast_editor(currentUser);
//                sv.setExp_date(dateFormatForDB(jTextFieldExpDte.getText()));
//                
//                boolean checkItmCode=stock.checkItemCodeExist(jTextFieldAddToStItCode.getText());
//                if(checkItmCode==true){
//                    JOptionPane.showMessageDialog(null, "Item code already exist.\n change the Item Code !\n ");
//                }else{
//                    boolean resu=stock.addNewItem(sv);
//                    if(resu==true){
//                        im.addMovementItem(jTextFieldAddToStItCode.getText(),dateFormatForDB(jTextFieldDate2.getText()));//add this new item to movement table
//                        if(startAutoCreateInv==1){
//                            int qt=Integer.parseInt(jTextFieldAddToStQty.getText());
//                            double unitPerPri=Double.parseDouble(jTextFieldAddToStPurPrice.getText());
//                            boolean res=supp.editTemp(qt*unitPerPri, "0.00", "0.00", currentUser);
//                            if(res==false){
//                                JOptionPane.showMessageDialog(null, "Auto create invoice process is not working!\n ");
//                            }
//                        }
//                        clearFieldsStokeAdd();//clear fields
//                        jTextFieldAddToStItCode.requestFocus();//move curser
//                        loadStokeTbleFull(0,"","");//Here should load table
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Can't do your process !\n ");
//                    }
//                }
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(null, "Illegal input.\n check and try again !\n ");
//            }            
//        }
    }
     
    void clearFieldsStokeAdd(){
        jTextFieldAddToStItCode.setText("");
        jTextFieldAddToStName.setText("");
        jTextFieldAddToStSubName.setText("");
        jTextFieldAddToStQty.setText("");
//        jTextFieldAddToStPurPrice.setText("");
        jTextFieldAddToStSellrPrice.setText("");
        jTextFieldAddToStDis.setText("0");
//        jTextFieldAddToStSupCode.setText("");
//        jTextFieldAddToStSupCode2.setText("");
        jTextFieldAddToStComent.setText("");
        jTextFieldMinimumQtyForAlert.setText("0");
        jTextFieldExpDte.setText("");
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddNewSto;
    private javax.swing.JButton jButtonAddNewToStoke;
    private javax.swing.JButton jButtonCancl;
    private javax.swing.JButton jButtonChangeStore;
    private javax.swing.JButton jButtonDeleteStore2;
    private javax.swing.JComboBox jComboBoxStokeAdd;
    private javax.swing.JComboBox jComboBoxStokeAdd1;
    private javax.swing.JComboBox jComboBoxStokeAdd2;
    private javax.swing.JComboBox jComboBoxStokeAdd3;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JTextField jTextFieldAddToStComent;
    private javax.swing.JTextField jTextFieldAddToStDis;
    private javax.swing.JTextField jTextFieldAddToStItCode;
    private javax.swing.JTextField jTextFieldAddToStItCode1;
    private javax.swing.JTextField jTextFieldAddToStName;
    private javax.swing.JTextField jTextFieldAddToStName1;
    private javax.swing.JTextField jTextFieldAddToStName2;
    private javax.swing.JTextField jTextFieldAddToStQty;
    private javax.swing.JTextField jTextFieldAddToStQty1;
    private javax.swing.JTextField jTextFieldAddToStSellrPrice;
    private javax.swing.JTextField jTextFieldAddToStSubName;
    private javax.swing.JTextField jTextFieldExpDte;
    private javax.swing.JTextField jTextFieldMinimumQtyForAlert;
    private javax.swing.JTextField jTextFieldMinimumQtyForAlert1;
    // End of variables declaration//GEN-END:variables
}
