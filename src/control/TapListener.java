/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author malit
 */
public abstract class TapListener extends java.awt.event.MouseAdapter {
    private int pressX, pressY;
    private long pressTime;
    private static final int MOVE_THRESHOLD = 5; // pixels of allowed movement

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        pressX = e.getXOnScreen();
        pressY = e.getYOnScreen();
        pressTime = System.currentTimeMillis();
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        int dx = Math.abs(e.getXOnScreen() - pressX);
        int dy = Math.abs(e.getYOnScreen() - pressY);

        if (dx < MOVE_THRESHOLD && dy < MOVE_THRESHOLD) {
            // treat as a tap
            onTap(e);
        }
        // else do nothing; user dragged to scroll
    }

    /**
     * Called when a tap is detected.
     */
    public abstract void onTap(java.awt.event.MouseEvent e);
}
