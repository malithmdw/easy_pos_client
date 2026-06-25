
package easyPOS.sale;

import control.ApplicationDataManager;
import dataModels.Language;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author MalithWanniarachchi
 */
public class ItemCard extends javax.swing.JPanel {
    
    /**
     * Creates new form ItemCardPanel
     */
    public ItemCard() {
        initComponents();    
        try {
            Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, ApplicationDataManager.getInstance().getSinhalaFontFile()).deriveFont(13f);

            if (Language.SINHALA.equals(ApplicationDataManager.getInstance().getApplicationLanguage())) {
                itemCardNameLabel.setFont(customFont1);
            }
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(ItemCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemCardNameLabel = new javax.swing.JLabel();
        itemCardPriceLabel = new javax.swing.JLabel();
        itemCardImageLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(118, 148));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemCardNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        itemCardNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        itemCardNameLabel.setText("Item Name");
        itemCardNameLabel.setOpaque(true);
        add(itemCardNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 160, 25));

        itemCardPriceLabel.setBackground(new java.awt.Color(255, 255, 255));
        itemCardPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        itemCardPriceLabel.setText("Price");
        add(itemCardPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 110, 30));

        itemCardImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(itemCardImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, -1, 120, 150));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel itemCardImageLabel;
    private javax.swing.JLabel itemCardNameLabel;
    private javax.swing.JLabel itemCardPriceLabel;
    // End of variables declaration//GEN-END:variables

    void setData(String imageName, String itemName, String price) {
        // Always update Swing components on EDT
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> setData(imageName, itemName, price));
            return;
        }

        // Update text immediately
        itemCardNameLabel.setText(" " + itemName);
        itemCardPriceLabel.setText(" " + price);

        // Set transparent label background
        itemCardNameLabel.setOpaque(true);
        itemCardNameLabel.setBackground(new Color(255, 255, 255, 180));
        itemCardPriceLabel.setOpaque(true);
        itemCardPriceLabel.setBackground(new Color(255, 255, 255, 185));

        // Set placeholder icon (optional) so UI doesn't look empty while loading
        itemCardImageLabel.setIcon(null);

        // Load and scale image in a background thread
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                File folderInput = new File(ApplicationDataManager.ITEM_LOCAL_FOLDER_PATH + imageName);
                BufferedImage folderImage = ImageIO.read(folderInput);

                Image newImage = folderImage.getScaledInstance(148, 162, Image.SCALE_SMOOTH);
                return new ImageIcon(newImage);
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    itemCardImageLabel.setIcon(icon); // back on EDT
                } catch (InterruptedException | ExecutionException ex) {
                    System.err.println("Image not found: " + ex.getMessage());
                    itemCardImageLabel.setIcon(null);
                }
            }
        };
        worker.execute();
    }
}
