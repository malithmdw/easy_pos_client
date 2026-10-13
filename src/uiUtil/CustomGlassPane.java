
package uiUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author MalithWanniarachchi
 */
public class CustomGlassPane extends JComponent {
    private final JPanel overlayWindow;

    public CustomGlassPane(JFrame frame) {
        // Use GridBagLayout to perfectly center our internal "window"
        setLayout(new GridBagLayout());

        // Block all mouse events from reaching the components underneath
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Consume the event to stop it from passing through
                e.consume(); 
            }
        });
        
        // Block mouse motion events (like hovering effects on buttons underneath)
        addMouseMotionListener(new MouseAdapter() {});

        // Create the "window" panel that sits on top
        overlayWindow = new JPanel();
        overlayWindow.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        overlayWindow.setPreferredSize(new Dimension(250, 150));
        overlayWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        // Add content to our overlay window
        overlayWindow.add(new JLabel("<html><center><b>Overlay Window</b><br>On top of base frame!</center></html>"));
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> setVisible(false)); // Hide the glass pane
        overlayWindow.add(closeButton);

        // Add the window panel to the centered GridBagLayout
        add(overlayWindow);
    }

    // Dim the background to give a modal light-box effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Semi-transparent gray background (Alpha = 125)
        g2d.setColor(new Color(0, 0, 0, 125)); 
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.dispose();
    }
}
