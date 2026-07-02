package easyPOS;

import control.ApplicationDataManager;
import control.EventManager;
import dataModels.MenuItemType;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.GregorianCalendar;
import dataModels.Language;
import java.util.Locale;
import java.util.ResourceBundle;
import control.EasyPosLogger;

/**
 *
 * @author malit
 */
public final class HeaderPanel extends javax.swing.JPanel implements control.LanguageChangeListener {

    /**
     * Creates new form HeaderPanel
     */
    public HeaderPanel() {
        initComponents();
        switchLanguage();
        setTodayDate();
        control.EventManager.getInstance().addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChanged() {
        switchLanguage();
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelHeaderBusinessName = new javax.swing.JLabel();
        jTextFieldHeaderDate = new javax.swing.JTextField();
        jTextFieldHeaderTime = new javax.swing.JTextField();
        jLabelHeaderLogo = new javax.swing.JLabel();
        jButtonHeaderHome = new javax.swing.JButton();
        jButtonHeaderLogOut = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelHeaderBusinessName.setFont(new java.awt.Font("Bookman Old Style", 0, 36)); // NOI18N
        jLabelHeaderBusinessName.setForeground(new java.awt.Color(0, 0, 51));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("easyPOS/Bundle"); // NOI18N
        jLabelHeaderBusinessName.setText(bundle.getString("HeaderPanel.jLabelHeaderBusinessName.text")); // NOI18N

        jTextFieldHeaderDate.setEditable(false);
        jTextFieldHeaderDate.setBackground(new java.awt.Color(153, 153, 153));
        jTextFieldHeaderDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextFieldHeaderTime.setEditable(false);
        jTextFieldHeaderTime.setBackground(new java.awt.Color(153, 153, 153));
        jTextFieldHeaderTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabelHeaderLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logoes/pagelogo.png"))); // NOI18N
        jLabelHeaderLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelHeaderLogoMouseClicked(evt);
            }
        });

        jButtonHeaderHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/grid.png"))); // NOI18N
        jButtonHeaderHome.setText(bundle.getString("HeaderPanel.jButtonHeaderHome.text")); // NOI18N
        jButtonHeaderHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHeaderHomeActionPerformed(evt);
            }
        });

        jButtonHeaderLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users.png"))); // NOI18N
        jButtonHeaderLogOut.setText(bundle.getString("HeaderPanel.jButtonHeaderLogOut.text")); // NOI18N
        jButtonHeaderLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHeaderLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelHeaderLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelHeaderBusinessName, javax.swing.GroupLayout.PREFERRED_SIZE, 410, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonHeaderLogOut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonHeaderHome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHeaderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHeaderTime, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelHeaderLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldHeaderDate)
                    .addComponent(jButtonHeaderLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonHeaderHome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldHeaderTime))
                .addContainerGap())
            .addComponent(jLabelHeaderBusinessName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonHeaderHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHeaderHomeActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.HOME);
    }//GEN-LAST:event_jButtonHeaderHomeActionPerformed

    private void jButtonHeaderLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHeaderLogOutActionPerformed
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.LOGOUT);
    }//GEN-LAST:event_jButtonHeaderLogOutActionPerformed

    private void jLabelHeaderLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHeaderLogoMouseClicked
        EventManager.getInstance().notifyMenuItemChanged(MenuItemType.ICON_CLICK);
    }//GEN-LAST:event_jLabelHeaderLogoMouseClicked

    private void switchLanguage() {
        Language appLang = ApplicationDataManager.getInstance().getApplicationLanguage();
        Locale locale = (appLang == Language.SINHALA) ? new Locale("si", "LK") : Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("easyPOS/Bundle", locale);
        if (appLang == Language.SINHALA) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            jLabelHeaderBusinessName.setFont(customFont);
            jButtonHeaderHome.setFont(customFont);
            jButtonHeaderLogOut.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
        }
        jLabelHeaderBusinessName.setText(resourceBundle.getString("HeaderPanel.jLabelHeaderBusinessName.text"));
        jButtonHeaderHome.setText(resourceBundle.getString("HeaderPanel.jButtonHeaderHome.text"));
        jButtonHeaderLogOut.setText(resourceBundle.getString("HeaderPanel.jButtonHeaderLogOut.text"));
    }

    void setTodayDate(){//set Date in text field
        Thread clock;
        clock = new Thread(){
            @Override
            public void run(){
                //infinite loop , this run until the program run
                for(;;){
                    //code for change the date date
                    Calendar calender=new GregorianCalendar();
                    int month=calender.get(Calendar.MONTH);
                    int year=calender.get(Calendar.YEAR);
                    int day=calender.get(Calendar.DAY_OF_MONTH);
                    String curDate=day+"/"+(month+1)+"/"+year; //display date as day/month/year
                    int monthLength=Integer.toString(month+1).length();
                    int dayLength=Integer.toString(day).length();
                    
                   
                    if(monthLength==1 && dayLength==1){
                        curDate="0"+day+"/0"+(month+1)+"/"+year;
                    }else if(monthLength==1){
                        curDate=day+"/0"+(month+1)+"/"+year;
                    }else if(dayLength==1){
                        curDate="0"+day+"/"+(month+1)+"/"+year;
                    }
                                   
                    int second=calender.get(Calendar.SECOND);
                    int minute=calender.get(Calendar.MINUTE);
                    int hour=calender.get(Calendar.HOUR);
                    int amOrPm=calender.get(Calendar.AM_PM);
                    String ampm="";
                    if(amOrPm==1){
                        ampm="PM";
                    }else{
                        ampm="AM";
                    }
                    String sec=Integer.toString(second);
                    String min=Integer.toString(minute);
                    String hou=Integer.toString(hour);
                            
                    int secondLength=sec.length();
                    int minuteLength=min.length();
                    int hourLength=hou.length();
                    if(secondLength==1){
                        sec="0"+(Integer.toString(second));
                    }
                    if(minuteLength==1){
                        min="0"+(Integer.toString(minute));
                    }
                    if(hourLength==1){
                        hou="0"+(Integer.toString(hour));
                    }
                    
                    String curTime=hou+":"+min+":"+sec+" "+ampm; //display as hou:min:sec am/pm
                    
                    
                    jTextFieldHeaderDate.setText(curDate); //display date
                    jTextFieldHeaderTime.setText(curTime); //display time
                    
                    //my payment alert system
//                    if(minute==0 && second==0){
//                        if(showAlertAgain==1){
//                           alertForPayForSuppliers();
//                        }                        
//                    }
///change time for every 1000 miliseconds
try {
    sleep(1000);
} catch (InterruptedException ex) {
    EasyPosLogger.getInstance().error("", ex);
}   
                }
                
            }
        };
        clock.start();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHeaderHome;
    private javax.swing.JButton jButtonHeaderLogOut;
    private javax.swing.JLabel jLabelHeaderBusinessName;
    private javax.swing.JLabel jLabelHeaderLogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldHeaderDate;
    private javax.swing.JTextField jTextFieldHeaderTime;
    // End of variables declaration//GEN-END:variables
}
