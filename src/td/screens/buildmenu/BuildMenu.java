package td.screens.buildmenu;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.IOException;
import td.Configuration;
import td.assets.Image;
import td.assets.ImageCache;
import td.assets.Texture;
import td.data.Colors;
import td.data.Fonts;
import td.data.Player;
import td.screens.PlayScreen;
import td.towers.TowerPlacer;
import td.util.Debug;
import td.util.Hitbox;
import td.util.Input;
import td.util.Log;
import td.util.Util;

public class BuildMenu {
    /**
     * 
     */
    private final PlayScreen playScreen;
    
    /**
     * The texture that is the build menu. This defines the position of all elements placed inside this menu.
     */
    private Texture menuTexture = null;
    
    /**
     * Returns true if the menu is completely open. Meaning that it is open and static.
     */
    private boolean isOpen = false; 
    
    /**
     * The current state of the menu.
     * If the menu is still, the state will always be set to STATIC.
     * In any other case it will be either OPENING or CLOSING.
     */
    private BuildMenuState state = BuildMenuState.STATIC;
    
    /**
     * The current build mode. The build mode defines what tower sections the player sees.
     * There are two build modes; DEFAULT and UPGRADING.
     * DEFAULT: The default mode. Here the player sees the tower section.
     * UPGRADING: Here the tower section is replaced by the upgrade section, displaying upgrades for the currently selected tower.
     */
    private BuildMode mode = BuildMode.DEFAULT;
    
    /**
     * The tower section of the menu.
     */
    private TowerSection tower_section = null;
    
    /**
     * The info section of the menu.
     */
    private InfoSection info_section = null;
    
    /**
     * The remembered position of the mouse.
     * Used if "rememberMousePos" is true when calling the "toggle" function, such as when toggling the build menu by clicking an empty tower block.
     */
    private Point mousePos = new Point(0, 0);
    private Point windowPos = new Point(0, 0);
    private boolean rememberMousePos = false;

    public BuildMenu(PlayScreen playScreen) {
        this.playScreen = playScreen;

        try {
            menuTexture = new Texture(Image.BUILD_MENU);
            menuTexture.createHitbox(0, 0);
            menuTexture.getHitbox().setX(-menuTexture.getHitbox().getWidth());
        } catch (IOException ex) {
            Log.error("[MenuBar] Failed to load texture");
        }
        this.tower_section = new TowerSection(this, 120);
        this.info_section = new InfoSection(this);
        info_section.setMode(InfoSection.BUILD_MODE);
        Debug.feedInstance(this);
    }
    
    /**
     * Returns the current build mode.
     * @return 
     */
    public BuildMode getBuildMode() {
        return mode;
    }
    
    /**
     * Set the current build mode.
     * @param mode 
     */
    public void setBuildMode(BuildMode mode) {
        this.mode = mode;
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
        return menuTexture.getHitbox();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public BuildMenuState getState() {
        return state;
    }

    public void update(double dt) {
        /**
         * Menu open/close animation.
         */
        if(state != BuildMenuState.STATIC) {
            if(state == BuildMenuState.OPENING) {
                // Opening
                int x = (int)(getX() + (25 * dt));

                if(x >= 0) {
                    x = 0;
                    state = BuildMenuState.STATIC;
                    isOpen = true;
                }
                getHitbox().setX(x);
                
                // - Background overlay
                int a = Colors.BMENU_OUTSIDE_OVERLAY.getAlpha();
                
                if(a >= 100) {
                    a = 100;
                } else {
                    a += 10;
                }
                Colors.BMENU_OUTSIDE_OVERLAY = new Color(1f, 1f, 1f, (float)a/255);
            } else {
                // Closing
                int x = (int)(getX() - (25 * dt));

                if(x <= -getWidth()) {
                    x = -getWidth();
                    state = BuildMenuState.STATIC;
                    isOpen = false;
                }
                getHitbox().setX(x);
                
                // - Background overlay
                int a = Colors.BMENU_OUTSIDE_OVERLAY.getAlpha();
                
                if(a <= 0) {
                    a = 0;
                } else {
                    a -= 10;
                }
                Colors.BMENU_OUTSIDE_OVERLAY = new Color(1f, 1f, 1f, (float)a/255);
            }
            getInfoArea().getHitbox().setX(getX() + getWidth());
            //getWaveInfoArea().getHitbox().setX(getX() + getWidth());
        } else {
            if(!isOpen()) {
                return;
            }
        }
        
        /**
        * Update sections.
        */
        tower_section.update(dt);
        
        if(info_section.isDisplayed()) {
            info_section.update(dt);
        }
    }

    public void render(Graphics2D g) {
        /**
         * Build Menu & Info Area
         * - Draw only the info area if the build menu is completely closed.
         */
        if(getState() == BuildMenuState.STATIC && !isOpen()) {
            // Completely closed.
            drawInfoArea(g);
        } else {
            // Opening or fully open.
             // -- Background overlay (outside menu)
            g.setColor(Colors.BMENU_OUTSIDE_OVERLAY);
            g.fillRect(0, 0, playScreen.getGameWindow().getWidth(), playScreen.getGameWindow().getHeight());
            // -- Menu Background & Info Area
            drawInfoArea(g);
            g.drawImage(menuTexture.getImage(), getX(), getY(), null);

            // - Title
            g.setFont(Fonts.BUILD_MENU_TITLE);
            g.setColor(Colors.BMENU_TITLE);
            g.drawString("BUILD MENU", Util.centerStringX("BUILD MENU", getWidth(), g, getX()), 40);

            // - Render Sections
            tower_section.render(g);

            if(info_section.isDisplayed()) {
                info_section.render(g);
            }
        }
    }
    
    public void drawInfoArea(Graphics2D g) {
        // todo: performance opt.
        
        getInfoArea().draw(g);
        
        // Just a little highlighting if the menu is closed and the mouse if hovering the info area.
        if(!isOpen() && getState() == BuildMenuState.STATIC) {
            if(Util.isWithinArea(getInput(), getInfoArea().getHitbox())) {
                g.setColor(Colors.INFO_AREA_TEXT.brighter().brighter());
            } else {
                g.setColor(Colors.INFO_AREA_TEXT);
            }
        } else {
            g.setColor(Colors.INFO_AREA_TEXT);
        }
        g.setFont(Fonts.INFO_AREA);
        int x1 = getX() + getWidth() + 15;
        
        g.drawImage(ImageCache.CASH_ICON, x1, 12, null);
        g.drawString(Util.cleanNumber(getPlayer().getCash()), x1 + ImageCache.CASH_ICON.getWidth() + 5, 30);
        
        g.drawImage(ImageCache.HEART_ICON, x1, 45, null);
        g.drawString("" + getPlayer().getHealth(), x1 + ImageCache.HEART_ICON.getWidth() + 5, 64);
        
        if(Debug.ENABLED) {
            g.setColor(Color.RED);
            g.drawRect(getInfoArea().getX(), getInfoArea().getY(), getInfoArea().getWidth(), getInfoArea().getHeight());
        }
    }

    public void toggle(boolean rememberMousePos) {
        Hitbox hitbox = menuTexture.getHitbox();
        
        // Whenever we toggle the open/close state of the BuildMenu, we always have to cancel the placing of a tower.
        TowerPlacer.setActive(false);
        
        if(state != BuildMenuState.STATIC) {
            if(Configuration.ANIMATIONS_ENABLED) {
                if(state == BuildMenuState.OPENING) {
                    state = BuildMenuState.CLOSING;
                } else {
                    state = BuildMenuState.OPENING;
                }
                return;
            }
        }

        if(isOpen) {
            /**
             * Close
             */
            Colors.BMENU_OUTSIDE_OVERLAY = new Color(1f, 1f, 1f, (float)100/255);
            
            if(Configuration.ANIMATIONS_ENABLED) {
                state = BuildMenuState.CLOSING;
            } else {
                hitbox.setX(-hitbox.getWidth());
                getInfoArea().getHitbox().setX(0);
                isOpen = false;
            }
        } else {
            /**
             * Open
             */
            this.rememberMousePos = rememberMousePos;
            mousePos = rememberMousePos ? MouseInfo.getPointerInfo().getLocation() : new Point(0, 0);
            windowPos = playScreen.getGameWindow().getPanel().getLocationOnScreen();
            
            Colors.BMENU_OUTSIDE_OVERLAY = new Color(1f, 1f, 1f, (float)0/255);
            
            if(Configuration.ANIMATIONS_ENABLED) {
                state = BuildMenuState.OPENING;
            } else {
                hitbox.setX(0);
                getInfoArea().getHitbox().setX(hitbox.getWidth());
                Colors.BMENU_OUTSIDE_OVERLAY = new Color(1f, 1f, 1f, (float)100/255);
                isOpen = true;
            }
        }
    }
    
    public TowerSection getTowerSection() {
        return tower_section;
    }
    
    public InfoSection getInfoSection() {
        return info_section;
    }
    
    public UpgradeSection getUpgradeSection() {
        return null;
    }
    
    public Texture getInfoArea() {
        return playScreen.getInfoArea();
    }
    
    public Texture getWaveInfoArea() {
        return playScreen.getWaveInfoArea();
    }
    
    public Player getPlayer() {
        return playScreen.getPlayer();
    }
    
    public Input getInput() {
        return playScreen.getInput();
    }
    
    public boolean rememberMousePos() {
        return rememberMousePos;
    }
    
    public Point getMousePos() {
        return mousePos;
    }
    
    public void moveMouse() {
        rememberMousePos = false;
        
        if(!playScreen.getGameWindow().getPanel().getLocationOnScreen().equals(windowPos)) {
            Log.info("[BuildMenu] Failed to move mouse pointer because the location of the game window has changed.");
            mousePos = new Point(0, 0);
            return;
        }
        
        try {
            Robot r = new Robot();
            r.mouseMove(getMousePos().x, getMousePos().y);
        } catch (AWTException ex) {
            Log.error("[BuildMenu] Failed to move mouse pointer!");
            ex.printStackTrace();
        }
        mousePos = new Point(0, 0);
    }
}
