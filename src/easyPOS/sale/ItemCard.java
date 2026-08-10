
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
import control.EasyPosLogger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author MalithWanniarachchi
 */
public class ItemCard extends javax.swing.JPanel {

    // Original NetBeans-designed card size (118x148) and the fixed y-positions
    // the name/price labels were placed at within that design. Kept as
    // offsets-from-bottom so the labels stay correctly anchored when the
    // card is stretched larger by the grid's layout manager.
    private static final int CARD_DESIGN_HEIGHT = 148;
    private static final int NAME_LABEL_Y = 90;
    private static final int NAME_LABEL_HEIGHT = 25;
    private static final int PRICE_LABEL_Y = 110;
    private static final int PRICE_LABEL_HEIGHT = 30;

    // Decoded, unscaled item image and the size it was last scaled/drawn at.
    // The card's real size isn't known until the grid's layout manager has
    // actually run (setData() is called before the card is added to the
    // grid), so the icon is (re)scaled from doLayout() instead of at load
    // time, using whatever the card's actual current size is at that point.
    private BufferedImage rawImage;
    private int lastIconWidth = -1;
    private int lastIconHeight = -1;

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
            EasyPosLogger.getInstance().error("", ex);
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

    /**
     * AbsoluteLayout keeps its children at fixed pixel bounds, but the outer
     * grid (see ItemSelection.loadItemGrid) stretches every card to an equal
     * cell size that can be larger than this card's designed 118x148 size.
     * Recompute child bounds from the card's actual current size on every
     * layout pass so the image and labels always fill the card instead of
     * leaving empty space on the right/bottom.
     */
    @Override
    public void doLayout() {
        int w = getWidth();
        int h = getHeight();

        itemCardImageLabel.setBounds(0, 0, w, h);

        int nameOffsetFromBottom = CARD_DESIGN_HEIGHT - NAME_LABEL_Y;
        int priceOffsetFromBottom = CARD_DESIGN_HEIGHT - PRICE_LABEL_Y;

        itemCardNameLabel.setBounds(0, Math.max(0, h - nameOffsetFromBottom), w, NAME_LABEL_HEIGHT);
        itemCardPriceLabel.setBounds(0, Math.max(0, h - priceOffsetFromBottom), w, PRICE_LABEL_HEIGHT);

        updateImageIcon();
    }

    private void updateImageIcon() {
        if (rawImage == null) {
            return;
        }
        int targetWidth = itemCardImageLabel.getWidth() > 0 ? itemCardImageLabel.getWidth() : getPreferredSize().width;
        int targetHeight = itemCardImageLabel.getHeight() > 0 ? itemCardImageLabel.getHeight() : getPreferredSize().height;

        if (targetWidth == lastIconWidth && targetHeight == lastIconHeight) {
            return;
        }
        lastIconWidth = targetWidth;
        lastIconHeight = targetHeight;

        Image scaled = rawImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        itemCardImageLabel.setIcon(new ImageIcon(scaled));
    }

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
        rawImage = null;
        lastIconWidth = -1;
        lastIconHeight = -1;

        // Decode the image in a background thread; scaling to the card's
        // actual size happens in updateImageIcon(), called from doLayout(),
        // since the card's real size isn't known yet at this point.
        SwingWorker<BufferedImage, Void> worker = new SwingWorker<BufferedImage, Void>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                File folderInput = new File(ApplicationDataManager.ITEM_LOCAL_FOLDER_PATH + imageName);
                return ImageIO.read(folderInput);
            }

            @Override
            protected void done() {
                try {
                    rawImage = get();
                    updateImageIcon(); // back on EDT
                } catch (InterruptedException | ExecutionException ex) {
                    System.err.println("Image not found: " + ex.getMessage());
                    itemCardImageLabel.setIcon(null);
                }
            }
        };
        worker.execute();
    }
}
