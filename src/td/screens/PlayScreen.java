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
import td.data.Block;
import td.data.Player;
import td.maps.MapManager;
import td.screens.buildmenu.BuildMenu;
import td.screens.buildmenu.BuildMenuState;
import td.towers.TowerPlacer;
import td.util.Debug;
import td.util.Input;
import td.util.Log;
import td.util.Util;

public class PlayScreen implements Screen {
    private GameWindow window;
    private Texture infoAreaTexture = null;
    private Player player = null;
    private BuildMenu bmenu = null;
    
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
        this.bmenu = new BuildMenu(this);
    }

    @Override
    public void update(double dt) {
        MapManager.getCurrentMap().update(dt);
        bmenu.update(dt);
    }

    @Override
    public void render(Graphics2D g) {
        MapManager.getCurrentMap().render(g, this);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        /**
         * Build Menu
         */
        bmenu.render(g);
        
        /**
         * TowerPlacer
         */
        if(TowerPlacer.isActive()) {
            g.drawImage(TowerPlacer.getIcon(), getInput().getMouseX() - Configuration.BLOCK_SIZE / 2, getInput().getMouseY() - Configuration.BLOCK_SIZE / 2, null);
        }
        
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
                    if(!bmenu.isOpen() || bmenu.getState() != BuildMenuState.STATIC) {
                        bmenu.toggle();
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(bmenu.isOpen() || bmenu.getState() != BuildMenuState.STATIC) {
                        bmenu.toggle();
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if(TowerPlacer.isActive()) {
                        TowerPlacer.setActive(false);
                    } else {
                        // pause menu
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if(e.isAltDown() || e.isControlDown()) {
                        Log.info("[~] Performing reset ..");
                        int i = 0;
                        
                        for(Block b : MapManager.getCurrentMap().getBlocks()) {
                            if(b.hasTowerEntity()) {
                                b.getTowerEntity().remove();
                                i++;
                            }
                        }
                        
                        player.setCash(player.getStartingCash());
                        player.setHealth(player.getMaxHealth());
                        Log.info("[~] Reset completed.");
                        Log.info("[~] - Removed " + i + " tower entities.");
                        Log.info("[~] - Refreshed player data.");
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
                
                if(!Util.isWithinArea(x, y, bmenu.getHitbox()) && !Util.isWithinArea(x, y, infoAreaTexture)) {
                    if(bmenu.isOpen() || bmenu.getState() == BuildMenuState.OPENING) {
                        bmenu.toggle();
                        return;
                    }
                }
                
                if(bmenu.isOpen() && bmenu.getState() == BuildMenuState.STATIC) {
                    if(Util.isWithinArea(x, y, bmenu.getTowerSection().getHitbox())) {
                        Log.info("[PlayScreen] Registered mousePress at section: TOWER_SECTION");
                        bmenu.getTowerSection().mousePressed(e);
                        return;
                    }
                }
                
                if(Util.isWithinArea(x, y, infoAreaTexture)) {
                    if(!bmenu.isOpen() || bmenu.getState() == BuildMenuState.CLOSING) {
                        bmenu.toggle();
                        return;
                    }
                }
                
                if(TowerPlacer.isActive()) {
                    if(e.getButton() == MouseEvent.BUTTON3) {
                        TowerPlacer.setActive(false);
                        bmenu.toggle();
                    } else {
                        Block b = MapManager.getCurrentMap().getHighlightedBlock(getInput());
                        
                        if(b != null) {
                            if(b.hasTowerEntity()) {
                                // alert player in some way
                                Log.info("[PlayScreen: TowerPlacer] Player attempted to place at an occupied location.");
                            } else {
                                TowerPlacer.place(b);
                            }
                        } else {
                            // alert player in some way
                            Log.info("[PlayScreen: TowerPlacer] Player attempted to place at an invalid location.");
                        }
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
    
    public Texture getInfoArea() {
        return infoAreaTexture;
    }
    
    public Player getPlayer() {
        return player;
    }
}
