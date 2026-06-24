package easyPOS;

import dbOperations.LoginDBOperation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import easyPOS.localization.ApplicationMessages;
import javax.swing.JOptionPane;
import uiUtil.EasyPOSMessageDialog;


public class Notes extends javax.swing.JFrame {

    LoginDBOperation ls=new LoginDBOperation();
    private String curUser;
    public Notes() {
        initComponents();
        setIcon();
    }
    
    void setIcon(){
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo2.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButtonSave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButtonPrint.setText("Print");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 151, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSave)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButtonPrint)
                    .addComponent(jButtonSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        boolean res=ls.changeNote(curUser, jTextArea1.getText());
        if(res==true){
            EasyPOSMessageDialog.showLocalizedInfo(rootPane, ApplicationMessages.INFO_SAVE_SUCCESS);
            this.dispose();
        }else{
            EasyPOSMessageDialog.showLocalizedError(rootPane, ApplicationMessages.ERROR_NOTES_SAVE_FAILED);
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int ans=JOptionPane.showConfirmDialog(rootPane, "Want you save your changes ?");
        if(ans==0){//save 
            boolean res=ls.changeNote(curUser, jTextArea1.getText());
            if(res==true){
                EasyPOSMessageDialog.showLocalizedInfo(rootPane, ApplicationMessages.INFO_SAVE_SUCCESS);
                this.dispose();
            }else{
                EasyPOSMessageDialog.showLocalizedError(rootPane, ApplicationMessages.ERROR_NOTES_SAVE_FAILED);
            }    
        }else if(ans==2){//cancel
            //close the msg box
        }else{// no or other
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        PrinterJob job2 = PrinterJob.getPrinterJob();
        PageFormat pf=new PageFormat();
                Paper paper = new Paper();
                paper.setSize(760,800); // Large Address Dimension
                paper.setImageableArea( 5, 2, 450, 420);
                pf.setPaper(paper);
                pf.setOrientation(PageFormat.PORTRAIT);
        job2.setPrintable(new UnbelievablySimplePrint2(),pf);
        if (job2.printDialog()) {
            try {
                job2.print();
            }catch (Exception e) {
                        
            }
        }
    }//GEN-LAST:event_jButtonPrintActionPerformed
    void setNote(String userName){ 
        this.setTitle("Note");
        String result=ls.getNote(userName);
        jTextArea1.setText(result);
        jButtonPrint.setVisible(false);
    }
    
    void setPrintPreview(String txt){
        jButtonPrint.setVisible(true);
        jButtonSave.setVisible(false);
        jTextArea1.requestFocus(false);
        jTextArea1.setText(txt);
        this.setBounds(WIDTH, WIDTH, 465, 525);
        jTextArea1.setBounds(WIDTH, WIDTH, 425,452);
        this.setTitle("Preview");
    }
    
    
    
    
    
    public class UnbelievablySimplePrint2 implements Printable{
        private Font sFont = new Font("Arial", Font.PLAIN , 10);
        private Font sFont2=new Font("Times New Roman", Font.BOLD, 9);
        
        public int print(Graphics g, PageFormat Pf, int pageIndex)
            throws PrinterException{
            if (pageIndex > 0) return NO_SUCH_PAGE;
            
            Graphics2D g2 = (Graphics2D)g;
            g2.setPaint(Color.black);             
            //Prepare the rendering
            //split the String by the semicolon character
            String[] bill = jTextArea1.getText().split("\n");
            
            g2.setFont(sFont);
            int y = 10;
            int x = 19;
            //draw each String in a separate line
            for (int i = 0; i < 8; i++) {
                g2.drawString(bill[i], x,y);
                y = y + 15;
            }
            g2.setFont(sFont2);
            for (int i = 8; i < bill.length-2; i++) {
                g2.drawString(bill[i], x,y);
                y = y + 13;
            }
            g2.setFont(sFont);
            g2.drawString(bill[bill.length-2], x,y);
            y = y + 10;
            g2.drawString(bill[bill.length-1], x,y);
            return PAGE_EXISTS;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    
    
    
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
            java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Notes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the curUser
     */
    public String getCurUser() {
        return curUser;
    }

    /**
     * @param curUser the curUser to set
     */
    public void setCurUser(String curUser) {
        this.curUser = curUser;
    }
}
