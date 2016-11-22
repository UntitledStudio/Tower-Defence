package td.screens;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import td.GameWindow;

public interface Screen {
    /**
     * Called when this screen is initialized and created.
     * @param window
     */
    public void create(GameWindow window);
    
    /**
     * Called whenever this screen is updated.
     * Update calls are always made before render calls.
     * @param dt 
     */
    public void update(double dt);
    
    /**
     * Called whenever this screen is rendered.
     * @param g 
     */
    public void render(Graphics2D g);
    
    /**
     * 
     * @return 
     */
    public KeyAdapter getKeyAdapter();
    
    /**
     * 
     * @return 
     */
    public MouseAdapter getMouseAdapter();
    
    /**
     * 
     * @return 
     */
    public MouseWheelListener getMouseWheelListener();
    
    /**
     * Called when this screen is disposed of.
     * Use this method to get rid of garbage.
     */
    public void dispose();
}
