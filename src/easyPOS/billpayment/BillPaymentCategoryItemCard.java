package easyPOS.billpayment;

import control.ApplicationDataManager;
import dataModels.Language;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import control.EasyPosLogger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author MalithWanniarachchi
 */
public class BillPaymentCategoryItemCard extends javax.swing.JPanel {

    
    /**
     * Creates new form ItemCardPanel
     */
    public BillPaymentCategoryItemCard() {
        initComponents();
        try {
            Font customFontSin = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(Font.BOLD, 14f);

            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())) {
                itemCardNameLabel.setFont(customFontSin);
            }
        } catch (FontFormatException | IOException ex) {
            EasyPosLogger.getInstance().error("", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemCardNameLabel = new javax.swing.JLabel();
        itemCardImageLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(148, 85));
        setMinimumSize(new java.awt.Dimension(148, 85));
        setPreferredSize(new java.awt.Dimension(148, 85));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemCardNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        itemCardNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        itemCardNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemCardNameLabel.setText("Item Name");
        itemCardNameLabel.setOpaque(true);
        add(itemCardNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 150, 40));

        itemCardImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(itemCardImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 90));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel itemCardImageLabel;
    private javax.swing.JLabel itemCardNameLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Loads the category image from a remote URL on a background thread.
     * Used for bill-payment categories where images are served from the EasyReload CDN.
     */
    void setDataFromUrl(String imageUrl, String itemName) {
        itemCardNameLabel.setText(" " + itemName);
        itemCardNameLabel.setOpaque(true);
        itemCardNameLabel.setBackground(new Color(255, 255, 255, 180));
        itemCardImageLabel.setIcon(null);

        if (imageUrl == null || imageUrl.isEmpty()) return;

        new Thread(() -> {
            try {
                String fileName  = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
                java.io.File cacheDir  = new java.io.File(ApplicationDataManager.BILLERS_LOCAL_FOLDER_PATH);
                java.io.File localFile = new java.io.File(cacheDir, fileName);

                BufferedImage img;
                if (localFile.exists()) {
                    img = ImageIO.read(localFile);
                } else {
                    img = ImageIO.read(new URL(imageUrl));
                    if (img != null) {
                        cacheDir.mkdirs();
                        String ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1) : "png";
                        ImageIO.write(img, ext, localFile);
                    }
                }

                if (img != null) {
                    Image scaled = img.getScaledInstance(148, 85, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaled);
                    SwingUtilities.invokeLater(() -> {
                        itemCardImageLabel.setIcon(icon);
                        revalidate();
                        repaint();
                    });
                }
            } catch (IOException ex) {
                EasyPosLogger.getInstance().error("BillPayment category image load error", ex);
            }
        }).start();
    }

    void setData(String imageName, String itemName) {
        itemCardNameLabel.setText(" " + itemName);
        
        // Set transparent label background
        itemCardNameLabel.setOpaque(true);
        itemCardNameLabel.setBackground(new Color(255, 255, 255, 180));
                        
        try {
            // Load image
            File folderInput = new File(ApplicationDataManager.CATEGORY_LOCAL_FOLDER_PATH + imageName);
            BufferedImage folderImage = ImageIO.read(folderInput);
            
            // Resize image
            Image newImage = folderImage.getScaledInstance(148, 85, Image.SCALE_SMOOTH);
            
            // Set image
            ImageIcon icon = new ImageIcon(newImage);
            itemCardImageLabel.setIcon(icon);
        } catch (IOException ex) {
            System.err.println("Image not found");
            EasyPosLogger.getInstance().error("", ex);
        }
    }
}
