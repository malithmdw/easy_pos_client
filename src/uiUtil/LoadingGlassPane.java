
package uiUtil;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author MalithWanniarachchi
 */
public class LoadingGlassPane extends JComponent {
    private final Timer timer;
    private int angle = 0;

    public LoadingGlassPane() {
        setOpaque(false);

        // Make sure glass pane blocks mouse events
        addMouseListener(new java.awt.event.MouseAdapter() {});
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});
        addKeyListener(new java.awt.event.KeyAdapter() {});
        setFocusTraversalKeysEnabled(false);

        // Rotate animation every 100ms (clockwise)
        timer = new Timer(100, e -> {
            angle -= 15; // clockwise
            if (angle >= 360) angle = 0;
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dark semi-transparent background
        g2d.setColor(new Color(0, 0, 0, 120)); // darker overlay
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Spinner
        int size = 50;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g2d.setStroke(new BasicStroke(6f));
        g2d.setColor(new Color(0, 100, 200));

        g2d.drawArc(x, y, size, size, angle, 270); // spinning arc clockwise

        g2d.dispose();
    }

    public void start() {
        timer.start();
        setVisible(true);
    }

    public void stop() {
        timer.stop();
        setVisible(false);
    }
}

