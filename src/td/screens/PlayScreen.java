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
import td.data.Colors;
import td.data.Fonts;
import td.data.Player;
import td.maps.MapManager;
import td.util.Debug;
import td.util.Hitbox;
import td.util.Input;
import td.util.Log;
import td.util.Util;

public class PlayScreen implements Screen {
    private GameWindow window;
    private Texture infoAreaTexture = null;
    private Player player = null;
    private MenuBar menuBar = null;
    
    @Override
    public void create(GameWindow window) {
        this.window = window;
        
        if(MapManager.getCurrentMap() == null) {
            Log.info("[PlayScreen] No map has been set. Loading default map ..");
            MapManager.setMap(MapManager.getMaps().get(0), true);
        }
        this.player = new Player(100, 1000);
        
        try {
            this.infoAreaTexture = new Texture(Image.INFO_AREA);
            infoAreaTexture.createHitbox(0, 0);
        } catch (IOException ex) {
            Log.error("[PlayScreen] Failed to load textures");
            ex.printStackTrace();
        }
        this.menuBar = new MenuBar(infoAreaTexture);
    }

    @Override
    public void update(double dt) {
        MapManager.getCurrentMap().update(dt);
        menuBar.update(dt);
    }

    @Override
    public void render(Graphics2D g) {
        MapManager.getCurrentMap().render(g, this);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        //g.drawImage(ImageCache.TOWER_BASIC, Rows.ROW_5.getX(), Columns.COLUMN_3.getY(), null);
        
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
            g.setFont(Fonts.INFO_AREA);
            int x1 = getX() + getWidth() + 15;
            int x2 = x1 + 60;
            
            // - Cash
            g.drawString("Cash:", x1, 25);
            g.drawString("$" + player.getCash(), x2, 25);
            
            // - Health
            g.drawString("Health:", x1, 45);
            g.drawString("" + player.getHealth(), x2, 45);
            
            // - Wave
            g.drawString("Wave:", x1, 65);
            g.drawString("" + player.getWave(), x2, 65);
            
            
            /**
             * Menu Bar
             */
            // - Background
            g.drawImage(barTexture.getImage(), getX(), getY(), null);
            
            // - Title
            g.setFont(Fonts.MENU_BAR_TITLE);
            g.setColor(Colors.MENU_BAR_TITLE);
            g.drawString("BUILD MENU", getX() + 30, 40);
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
