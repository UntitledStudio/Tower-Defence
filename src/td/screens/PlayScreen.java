package td.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import td.Configuration;
import td.GameWindow;
import td.assets.Image;
import td.assets.Texture;
import td.data.Fonts;
import td.data.Player;
import td.util.Debug;
import td.util.Hitbox;
import td.util.Input;
import td.util.Log;
import td.util.Util;

public class PlayScreen implements Screen {
    /**
     * Set to true once the create() method has completed loading.
     * Used to prevent update and render calls while the screen is being loaded.
     */
    protected boolean loaded = false;
    private GameWindow window;
    private Texture infoAreaTexture = null;
    private Player player = null;
    private MenuBar menuBar = null;
    
    @Override
    public void create(GameWindow window) {
        this.window = window;
        this.player = new Player(100, 1000);
        
        try {
            this.infoAreaTexture = new Texture(Image.INFO_AREA);
            infoAreaTexture.createHitbox(0, 0);
        } catch (IOException ex) {
            Log.error("[PlayScreen] Failed to load textures");
            ex.printStackTrace();
        }
        this.menuBar = new MenuBar(infoAreaTexture);
        loaded = true;
    }

    @Override
    public void update(double dt) {
        if(!loaded) return;
        window.getMap().update(dt);
        menuBar.update(dt);
    }

    @Override
    public void render(Graphics2D g) {
        if(!loaded) return;
        window.getMap().render(g, this);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        /**
         * Menu Bar
         */
        menuBar.render(g);
        
        /**
         * Handle debug.
         */
        if(Debug.ENABLED) {
            Debug.render(g, window);
        }
    }
    
    @Override
    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(!menuBar.isOpen() || !menuBar.getState().equalsIgnoreCase("static")) {
                        menuBar.toggle();
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(menuBar.isOpen() || !menuBar.getState().equalsIgnoreCase("static")) {
                        menuBar.toggle();
                    }
                }
            }
        };
    }

    @Override
    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                if(!Util.isWithinArea(x, y, menuBar.getHitbox()) && !Util.isWithinArea(x, y, infoAreaTexture)) {
                    if(menuBar.isOpen() || menuBar.getState().equalsIgnoreCase("opening")) {
                        menuBar.toggle();
                        return;
                    }
                }
                
                if(Util.isWithinArea(x, y, infoAreaTexture)) {
                    if(!menuBar.isOpen() || menuBar.getState().equalsIgnoreCase("closing")) {
                        menuBar.toggle();
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
    public void dispose() {
    }

    @Override
    public String toString() {
        return "PlayScreen";
    }
    
    public Input getInput() {
        return window.getInput();
    }
    
    private class MenuBar {
        protected Texture barTexture = null;
        protected Texture infoAreaTexture = null;
        private boolean isOpen = false;
        private String state = "static"; // States: static, opening, closing;
        
        public MenuBar(Texture infoAreaTexture) {
            this.infoAreaTexture = infoAreaTexture;
            
            try {
                barTexture = new Texture(Image.MENU_BAR);
                barTexture.createHitbox(0, 0);
                barTexture.getHitbox().setX(-barTexture.getHitbox().getWidth());
            } catch (IOException ex) {
                Log.error("[MenuBar] Failed to load texture");
            }
        }
        
        public int getX() {
            return getHitbox().getX();
        }
        
        public int getY() {
            return getHitbox().getY();
        }
        
        public int getWidth() {
            return getHitbox().getWidth();
        }
        
        public int getHeight() {
            return getHitbox().getHeight();
        }
        
        public Hitbox getHitbox() {
            return barTexture.getHitbox();
        }
        
        public boolean isOpen() {
            return isOpen;
        }
        
        public String getState() {
            return state;
        }
        
        public void update(double dt) {
            /**
             * Menu open/close animation.
             */
            if(!state.equalsIgnoreCase("static")) {
                if(state.equalsIgnoreCase("opening")) {
                    // Opening
                    int x = (int)(menuBar.getX() + (25 * dt));
                    
                    if(x >= 0) {
                        x = 0;
                        state = "static";
                        isOpen = true;
                    }
                    menuBar.getHitbox().setX(x);
                } else {
                    // Closing
                    int x = (int)(menuBar.getX() - (25 * dt));
                    
                    if(x <= -menuBar.getWidth()) {
                        x = -menuBar.getWidth();
                        state = "static";
                        isOpen = false;
                    }
                    menuBar.getHitbox().setX(x);
                }
                infoAreaTexture.getHitbox().setX(menuBar.getX() + menuBar.getWidth());
            }
        }
        
        public void render(Graphics2D g) {
            /**
             * Info Area
             */
            infoAreaTexture.draw(g);
            g.setColor(Color.WHITE);
            g.setFont(Fonts.INFO_AREA_FONT);
            int x = menuBar.getX() + menuBar.getWidth() + 15;
            g.drawString("Cash: $" + player.getCash(), x, 25);
            g.drawString("Health: " + player.getHealth(), x, 45);
            g.drawString("Wave: " + player.getWave(), x, 65);
            
            /**
             * Menu Bar
             */
            g.drawImage(barTexture.getImage(), menuBar.getX(), menuBar.getY(), null);
        }
        
        public void toggle() {
            Hitbox hitbox = barTexture.getHitbox();
            
            if(!state.equalsIgnoreCase("static")) {
                if(Configuration.ANIMATIONS_ENABLED) {
                    if(state.equalsIgnoreCase("opening")) {
                        state = "closing";
                    } else {
                        state = "opening";
                    }
                    return;
                }
            }
            
            if(isOpen) {
                /**
                 * Close
                 */
                if(Configuration.ANIMATIONS_ENABLED) {
                    state = "closing";
                } else {
                    hitbox.setX(-hitbox.getWidth());
                    infoAreaTexture.getHitbox().setX(0);
                    isOpen = false;
                }
            } else {
                /**
                 * Open
                 */
                if(Configuration.ANIMATIONS_ENABLED) {
                    state = "opening";
                } else {
                    hitbox.setX(0);
                    infoAreaTexture.getHitbox().setX(hitbox.getWidth());
                    isOpen = true;
                }
            }
        }
    }
}
