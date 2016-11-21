package td;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import td.screens.ScreenManager;

public class GamePanel extends JPanel {
    private long lastLoopTime = System.nanoTime();
    private final int TARGET_FPS = 60;
    private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private int fps = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ScreenManager.render((Graphics2D)g);
    }
    
    public void startGameLoop() {
        new Thread(() -> {
            gameLoop();
        }).start();
    }
    
    protected void gameLoop() {
        //double lastUpdateTime = System.nanoTime();
        long lastFpsTime = 0;
        
        while(true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double dt = updateLength / ((double)OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if(lastFpsTime >= 1000000000) {
                ScreenManager.reportFPS(fps);
                lastFpsTime = 0;
                fps = 0;
            }
            
            // Update.
            ScreenManager.update(dt);

            // Render.
            repaint();

            try {
                long l = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                
                if(l <= 0) {
                    l = 1;
                }
                Thread.sleep(l);
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
