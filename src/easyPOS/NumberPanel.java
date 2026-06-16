package easyPOS;

import control.EventManager;
import control.NumberPadKeyPressListener;

/**
 *
 * @author malit
 */
public class NumberPanel extends javax.swing.JPanel {

    /**
     * Creates new form NumberPanel
     */
    public NumberPanel() {
        initComponents();
        
        jButtonNumPanel1.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel2.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel3.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel4.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel5.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel6.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel7.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel8.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel9.setFocusable(false);  // prevents stealing focus
        jButtonNumPanel0.setFocusable(false);  // prevents stealing focus
        jButtonNumPanelDot.setFocusable(false);  // prevents stealing focus
        jButtonNumPanelBackSpace.setFocusable(false);  // prevents stealing focus
    }
    
      
    
    private void onButtonClick(NumberPadKeyPressListener.NumberPadButton button)
    {
        EventManager.getInstance().notifySalesPanelCustomNumberPadKeyPressed(button);
    }
    
    public static String getText(String existing, NumberPadKeyPressListener.NumberPadButton button)
    {
        switch (button) {
            case BUTTON_1:
                return existing + "1";
            case BUTTON_2:
                return existing + "2";
            case BUTTON_3:
                return existing + "3";
            case BUTTON_4:
                return existing + "4";
            case BUTTON_5:
                return existing + "5";
            case BUTTON_6:
                return existing + "6";
            case BUTTON_7:
                return existing + "7";
            case BUTTON_8:
                return existing + "8";
            case BUTTON_9:
                return existing + "9";
            case BUTTON_0:
                return existing + "0";
            case BUTTON_DOT:
                return existing + ".";
            case BUTTON_BACK:
                return removeLastChar(existing);
            default:
                throw new AssertionError();
        }
    }
    
    public static String getDouble(String existing, NumberPadKeyPressListener.NumberPadButton button)
    {
        if (NumberPadKeyPressListener.NumberPadButton.BUTTON_DOT.equals(button)) {
            if (existing == null || existing.isEmpty()) {
                return "0.";
            }
            else if (existing.contains(".")) {
                return existing;
            }            
        }
        
        return getText(existing, button);
    }
    
    public static String getNum(String existing, NumberPadKeyPressListener.NumberPadButton button)
    {
        if (NumberPadKeyPressListener.NumberPadButton.BUTTON_DOT.equals(button)) {
            return existing;
        }
        
        return getText(existing, button);
    }
    
    private static String removeLastChar(String input) {
        String newString = null;

        if (input != null && input.length() > 0) {
            newString = input.substring(0, input.length() - 1);
        } 
        input = newString;
        return input;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonNumPanel1 = new javax.swing.JButton();
        jButtonNumPanel2 = new javax.swing.JButton();
        jButtonNumPanel3 = new javax.swing.JButton();
        jButtonNumPanel4 = new javax.swing.JButton();
        jButtonNumPanel5 = new javax.swing.JButton();
        jButtonNumPanel6 = new javax.swing.JButton();
        jButtonNumPanel7 = new javax.swing.JButton();
        jButtonNumPanel8 = new javax.swing.JButton();
        jButtonNumPanel9 = new javax.swing.JButton();
        jButtonNumPanelDot = new javax.swing.JButton();
        jButtonNumPanel0 = new javax.swing.JButton();
        jButtonNumPanelBackSpace = new javax.swing.JButton();
        jButtonNumPanelOK = new javax.swing.JButton();

        jButtonNumPanel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel1.setText("1");
        jButtonNumPanel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel1ActionPerformed(evt);
            }
        });

        jButtonNumPanel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel2.setText("2");
        jButtonNumPanel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel2ActionPerformed(evt);
            }
        });

        jButtonNumPanel3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel3.setText("3");
        jButtonNumPanel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel3ActionPerformed(evt);
            }
        });

        jButtonNumPanel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel4.setText("4");
        jButtonNumPanel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel4ActionPerformed(evt);
            }
        });

        jButtonNumPanel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel5.setText("5");
        jButtonNumPanel5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel5ActionPerformed(evt);
            }
        });

        jButtonNumPanel6.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel6.setText("6");
        jButtonNumPanel6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel6ActionPerformed(evt);
            }
        });

        jButtonNumPanel7.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel7.setText("7");
        jButtonNumPanel7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel7ActionPerformed(evt);
            }
        });

        jButtonNumPanel8.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel8.setText("8");
        jButtonNumPanel8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel8ActionPerformed(evt);
            }
        });

        jButtonNumPanel9.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel9.setText("9");
        jButtonNumPanel9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel9ActionPerformed(evt);
            }
        });

        jButtonNumPanelDot.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanelDot.setText(".");
        jButtonNumPanelDot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanelDotActionPerformed(evt);
            }
        });

        jButtonNumPanel0.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanel0.setText("0");
        jButtonNumPanel0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanel0ActionPerformed(evt);
            }
        });

        jButtonNumPanelBackSpace.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanelBackSpace.setText("<");
        jButtonNumPanelBackSpace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanelBackSpaceActionPerformed(evt);
            }
        });

        jButtonNumPanelOK.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jButtonNumPanelOK.setText("OK");
        jButtonNumPanelOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumPanelOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNumPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNumPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNumPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonNumPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonNumPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonNumPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNumPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButtonNumPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNumPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNumPanelDot, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNumPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNumPanelBackSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNumPanelOK, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonNumPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jButtonNumPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jButtonNumPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNumPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonNumPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonNumPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNumPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNumPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNumPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNumPanelDot, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNumPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNumPanelBackSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNumPanelOK, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNumPanel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel1ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_1);
    }//GEN-LAST:event_jButtonNumPanel1ActionPerformed

    private void jButtonNumPanel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel2ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_2);
    }//GEN-LAST:event_jButtonNumPanel2ActionPerformed

    private void jButtonNumPanel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel3ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_3);
    }//GEN-LAST:event_jButtonNumPanel3ActionPerformed

    private void jButtonNumPanel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel4ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_4);
    }//GEN-LAST:event_jButtonNumPanel4ActionPerformed

    private void jButtonNumPanel5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel5ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_5);
    }//GEN-LAST:event_jButtonNumPanel5ActionPerformed

    private void jButtonNumPanel6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel6ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_6);
    }//GEN-LAST:event_jButtonNumPanel6ActionPerformed

    private void jButtonNumPanel7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel7ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_7);
    }//GEN-LAST:event_jButtonNumPanel7ActionPerformed

    private void jButtonNumPanel8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel8ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_8);
    }//GEN-LAST:event_jButtonNumPanel8ActionPerformed

    private void jButtonNumPanel9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel9ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_9);
    }//GEN-LAST:event_jButtonNumPanel9ActionPerformed

    private void jButtonNumPanelDotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanelDotActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_DOT);
    }//GEN-LAST:event_jButtonNumPanelDotActionPerformed

    private void jButtonNumPanel0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanel0ActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_0);
    }//GEN-LAST:event_jButtonNumPanel0ActionPerformed

    private void jButtonNumPanelBackSpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanelBackSpaceActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_BACK);
    }//GEN-LAST:event_jButtonNumPanelBackSpaceActionPerformed

    private void jButtonNumPanelOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumPanelOKActionPerformed
        onButtonClick(NumberPadKeyPressListener.NumberPadButton.BUTTON_OK);
    }//GEN-LAST:event_jButtonNumPanelOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNumPanel0;
    private javax.swing.JButton jButtonNumPanel1;
    private javax.swing.JButton jButtonNumPanel2;
    private javax.swing.JButton jButtonNumPanel3;
    private javax.swing.JButton jButtonNumPanel4;
    private javax.swing.JButton jButtonNumPanel5;
    private javax.swing.JButton jButtonNumPanel6;
    private javax.swing.JButton jButtonNumPanel7;
    private javax.swing.JButton jButtonNumPanel8;
    private javax.swing.JButton jButtonNumPanel9;
    private javax.swing.JButton jButtonNumPanelBackSpace;
    private javax.swing.JButton jButtonNumPanelDot;
    private javax.swing.JButton jButtonNumPanelOK;
    // End of variables declaration//GEN-END:variables
}
