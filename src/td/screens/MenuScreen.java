package td.screens;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import td.GameWindow;
import td.assets.Image;
import td.assets.Texture;
import td.util.Debug;
import td.util.Log;
import td.util.Util;

public class MenuScreen implements Screen {
    private GameWindow window = null;
    private Texture playButton = null;
    
    @Override
    public void create(GameWindow window) {
        this.window = window;
        
        try {
            playButton = new Texture(Image.BUTTON_PLAY);
            playButton.createHitbox(Util.centerValue(playButton.getImage().getWidth(), window.getWidth()), Util.centerValue(playButton.getImage().getHeight(), window.getHeight()));
        } catch (IOException ex) {
            Log.error("[Menu] Failed to load a texture!");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(double dt) {
        
    }

    @Override
    public void render(Graphics2D g) {
        playButton.draw(g);
        
        if(Debug.ENABLED) {
            Debug.render(g, window);
        }
    }

    @Override
    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            
        };
    }

    @Override
    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if(Util.isWithinArea(x, y, playButton)) {
                        ScreenManager.setScreen(new PlayScreen());
                    }
                }
            }
        };
    }

    @Override
    public MouseWheelListener getMouseWheelListener() {
        return (MouseWheelEvent e) -> {
            
        };
    }

    @Override
    public GameWindow getGameWindow() {
        return window;
    }
    
    @Override
    public void dispose() {
        window.disposeInputHandlers();
    }
    
    @Override
    public String toString() {
        return "MenuScreen";
    }
}
