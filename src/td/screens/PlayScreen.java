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
import java.awt.image.BufferedImage;
import java.io.IOException;
import td.Configuration;
import td.GameWindow;
import td.assets.Image;
import td.assets.ImageCache;
import td.assets.Texture;
import td.data.Block;
import td.data.BlockType;
import td.data.Colors;
import td.data.Fonts;
import td.data.Player;
import td.entities.EntityRemoveReason;
import td.entities.projectile.ProjectileManager;
import td.maps.MapManager;
import td.screens.buildmenu.BuildMenu;
import td.screens.buildmenu.BuildMenuState;
import td.towers.MachineGunTower;
import td.towers.TowerPlacer;
import td.util.Debug;
import td.util.Input;
import td.util.Log;
import td.util.Util;
import td.waves.WaveManager;

public class PlayScreen implements Screen {
    private GameWindow window;
    private Texture infoAreaTexture = null;
    private Texture waveInfoAreaTexture = null;
    private Player player = null;
    private BuildMenu bmenu = null;
    private ProjectileManager projectileManager = null;
    public static PlayScreen instance;
    
    @Override
    public void create(GameWindow window) {
        this.window = window;
        instance = this;
        
        if(MapManager.getCurrentMap() == null) {
            Log.info("[PlayScreen] No map has been set. Loading default map ..");
            MapManager.setMap(MapManager.getMaps().get(0), true);
        }
        this.player = new Player(100, 1000);
        this.projectileManager = new ProjectileManager();
        
        try {
            this.infoAreaTexture = new Texture(Image.INFO_AREA);
            infoAreaTexture.createHitbox(0, 0);
            
            BufferedImage waveInfoArea = Image.WAVE_INFO_AREA.getBufferedImage();
            this.waveInfoAreaTexture = new Texture(waveInfoArea);
            waveInfoAreaTexture.createHitbox(window.getPanel().getWidth() - waveInfoArea.getWidth(), window.getPanel().getHeight() - waveInfoArea.getHeight());
        } catch (IOException ex) {
            Log.error("[PlayScreen] Failed to load textures");
            ex.printStackTrace();
        }
        this.bmenu = new BuildMenu(this);
        WaveManager.generateNext();
    }

    @Override
    public void update(double dt) {
        MapManager.getCurrentMap().update(dt);
        getBuildMenu().update(dt);
    }

    @Override
    public void render(Graphics2D g) {
        /**
         * Render map
         */
        MapManager.getCurrentMap().render(g, this);
        
        // Todo: make configurable
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        /**
         * Build Menu
         */
        getBuildMenu().render(g);
        
        /**
         * Wave Info
         */
        drawWaveInfoArea(g);
        
        /**
         * TowerPlacer
         */
        if(TowerPlacer.isActive()) {
            int x = getInput().getMouseX();
            int y = getInput().getMouseY();
            
            // No need to do logic or render anything if the position of mouse is not a valid location for placement.
            if(getInput().isMouseInWindow() && !Util.isWithinArea(getInput(), infoAreaTexture.getHitbox())) {
                
                // Snapping into correct location. This allows the player to correctly see the range reach of the tower.
                if(MapManager.getDefaultMap().getHighlightedBlock(getInput()) != null) {
                    try {
                        x = MapManager.getCurrentMap().getHighlightedBlock(getInput()).getX() + Configuration.BLOCK_SIZE / 2;
                        y = MapManager.getCurrentMap().getHighlightedBlock(getInput()).getY() + Configuration.BLOCK_SIZE / 2;
                    } catch(NullPointerException npe) {} // Ignore the occassional NPE thrown by this. Nothing wrong.
                }
                
                TowerPlacer.drawRangeIndicator(getInput(), g, Util.getEllipseFromCenter(x, y, TowerPlacer.getSelectedTower().getDefaults().RANGE, TowerPlacer.getSelectedTower().getDefaults().RANGE));
                g.drawImage(TowerPlacer.getIcon(), x - Configuration.BLOCK_SIZE / 2, y - Configuration.BLOCK_SIZE / 2, null);

                if(TowerPlacer.drawPlacementDenied) {
                    g.drawImage(ImageCache.PLACEMENT_DENIED, x - Configuration.BLOCK_SIZE / 2, y - Configuration.BLOCK_SIZE / 2, null);
                    TowerPlacer.drawPlacementDenied = false;
                } else if(TowerPlacer.drawPlacementAllowed) {
                    g.drawImage(ImageCache.PLACEMENT_ALLOWED, x - Configuration.BLOCK_SIZE / 2, y - Configuration.BLOCK_SIZE / 2, null);
                    TowerPlacer.drawPlacementAllowed = false;
                }
            }
        }
        
        /**
         * Tick and render all projectiles.
         */
        PlayScreen.instance.getProjectileManager().processAll(g);
        
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
                    if(!getBuildMenu().isOpen() || getBuildMenu().getState() != BuildMenuState.STATIC) {
                        getBuildMenu().toggle();
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(getBuildMenu().isOpen() || getBuildMenu().getState() != BuildMenuState.STATIC) {
                        getBuildMenu().toggle();
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if(TowerPlacer.isActive()) {
                        TowerPlacer.setActive(false);
                    } else {
                        // todo: pause menu
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if(e.isAltDown() || e.isControlDown()) {
                        Log.info("[~] Performing reset ..");
                        int i = 0;
                        
                        getProjectileManager().removeAll();
                        for(Block b : MapManager.getCurrentMap().getBlocks()) {
                            if(b.hasTowerEntity()) {
                                b.getTowerEntity().remove(EntityRemoveReason.RESET);
                                i++;
                            }
                        }
                        WaveManager.reset();
                        player.setCash(player.getStartingCash());
                        player.setHealth(player.getMaxHealth());
                        Log.info("[~] Reset completed.");
                        Log.info("[~] - Removed " + i + " tower entities.");
                        Log.info("[~] - Refreshed player data.");
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_INSERT) {
                    if(e.isAltDown() || e.isControlDown()) {
                        Log.info("[~] Filling map ..");
                        int i = 0;
                        
                        for(Block b : MapManager.getCurrentMap().getBlocks()) {
                            if(!b.hasTowerEntity() && b.getType() == BlockType.TOWER) {
                                b.setTowerEntity(new MachineGunTower(b));
                                i++;
                            }
                        }
                        
                        Log.info("[~] Fill completed. Filled " + i + " available blocks.");
                    }
                } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    WaveManager.launchNext();
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
                
                if(Util.isWithinArea(x, y, waveInfoAreaTexture)) {
                    return;
                }
                
                // If the click is within the BuildMenu hitbox and the BuildMenu is opening/open, close it.
                if(!Util.isWithinArea(x, y, getBuildMenu().getHitbox()) && !Util.isWithinArea(x, y, infoAreaTexture)) {
                    if(getBuildMenu().isOpen() || getBuildMenu().getState() == BuildMenuState.OPENING) {
                        getBuildMenu().toggle();
                        return;
                    }
                }
                
                if(getBuildMenu().isOpen() && getBuildMenu().getState() == BuildMenuState.STATIC) {
                    if(Util.isWithinArea(x, y, getBuildMenu().getTowerSection().getHitbox())) {
                        Log.info("[PlayScreen] Registered mousePress at section: TOWER_SECTION");
                        getBuildMenu().getTowerSection().mousePressed(e);
                        return;
                    }
                }
                
                if(Util.isWithinArea(x, y, infoAreaTexture)) {
                    if(!getBuildMenu().isOpen() || getBuildMenu().getState() == BuildMenuState.CLOSING) {
                        getBuildMenu().toggle();
                        return;
                    }
                }
                
                if(TowerPlacer.isActive()) {
                    if(e.getButton() == MouseEvent.BUTTON3) {
                        TowerPlacer.setActive(false);
                        getBuildMenu().toggle();
                    } else {
                        Block b = MapManager.getCurrentMap().getHighlightedBlock(getInput());
                        
                        if(b != null) {
                            if(b.hasTowerEntity()) {
                                // alert player in some way
                                Log.info("[PlayScreen: TowerPlacer] Player attempted to place at an occupied location.");
                            } else {
                                TowerPlacer.place(b, player);
                            }
                        } else {
                            // alert player in some way
                            Log.info("[PlayScreen: TowerPlacer] Player attempted to place at an invalid location.");
                        }
                    }
                } else {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        Block b = MapManager.getCurrentMap().getHighlightedBlock(getInput());
                        
                        if(b != null && b.getType() == BlockType.TOWER) {
                            if(!b.hasTowerEntity()) {
                                if(!getBuildMenu().isOpen()) {
                                    getBuildMenu().toggle();
                                }
                            }
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
    public GameWindow getGameWindow() {
        return window;
    }

    @Override
    public void dispose() {
    }

    @Override
    public String toString() {
        return "PlayScreen";
    }
    
    public void drawWaveInfoArea(Graphics2D g) {
        getWaveInfoArea().draw(g);
        g.setColor(Colors.INFO_AREA_TEXT);
        g.setFont(Fonts.INFO_AREA);
        g.drawImage(ImageCache.SWORD_ICON, getWaveInfoArea().getX() + 30, getWaveInfoArea().getY() + 10, null);
        String s = WaveManager.isWaveActive() ? WaveManager.getCurrentWaveID()+"" : WaveManager.getCurrentWaveID() + " -> " + WaveManager.getWaveCount();
        g.drawString(s, getWaveInfoArea().getX() + 68, Util.centerStringY(s, getWaveInfoArea().getHeight(), g, getWaveInfoArea().getY() + 2));
        
        if(Debug.ENABLED) {
            g.setColor(Color.RED);
            g.drawRect(getWaveInfoArea().getX(), getWaveInfoArea().getY(), getWaveInfoArea().getWidth(), getWaveInfoArea().getHeight());
        }
    }
    
    public Input getInput() {
        return window.getInput();
    }
    
    public Texture getInfoArea() {
        return infoAreaTexture;
    }
    
    public Texture getWaveInfoArea() {
        return waveInfoAreaTexture;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public BuildMenu getBuildMenu() {
        return bmenu;
    }
    
    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
}
