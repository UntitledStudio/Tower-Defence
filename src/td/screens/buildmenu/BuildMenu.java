package td.screens.buildmenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import td.Configuration;
import td.assets.Image;
import td.assets.Texture;
import td.data.Colors;
import td.data.Fonts;
import td.data.Player;
import td.util.Hitbox;
import td.util.Log;
import td.util.Util;

public class BuildMenu {
    /**
     * The texture that is the build menu. This defines the position of all elements placed inside this menu.
     */
    private Texture menuTexture = null;
    
    /**
     * The small display box up in the left corner of the screen displaying information about the player and active game.
     */
    private Texture infoAreaTexture = null;
    
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
     * An instance of the player object.
     */
    private final Player player;
    
    private TowerSection tower_section = null;

    public BuildMenu(Texture infoAreaTexture, Player player) {
        this.infoAreaTexture = infoAreaTexture;

        try {
            menuTexture = new Texture(Image.BUILD_MENU);
            menuTexture.createHitbox(0, 0);
            menuTexture.getHitbox().setX(-menuTexture.getHitbox().getWidth());
        } catch (IOException ex) {
            Log.error("[MenuBar] Failed to load texture");
        }
        this.player = player;
        this.tower_section = new TowerSection(this, 110);
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
            } else {
                // Closing
                int x = (int)(getX() - (25 * dt));

                if(x <= -getWidth()) {
                    x = -getWidth();
                    state = BuildMenuState.STATIC;
                    isOpen = false;
                }
                getHitbox().setX(x);
            }
            infoAreaTexture.getHitbox().setX(getX() + getWidth());
        } else {
            if(!isOpen()) {
                return;
            }
        }
        
        /**
        * Update sections.
        */
        tower_section.update(dt);
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
         * Build Menu
         */
        // - Background
        if(getState() == BuildMenuState.STATIC && !isOpen()) return;
        g.drawImage(menuTexture.getImage(), getX(), getY(), null);

        // - Title
        g.setFont(Fonts.BUILD_MENU_TITLE);
        g.setColor(Colors.BUILD_MENU_TITLE);
        g.drawString("BUILD MENU", Util.centerStringX("BUILD MENU", getWidth(), g, getX()), 40);
        
        // - Render Sections
        tower_section.render(g);
    }

    public void toggle() {
        Hitbox hitbox = menuTexture.getHitbox();

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
            if(Configuration.ANIMATIONS_ENABLED) {
                state = BuildMenuState.CLOSING;
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
                state = BuildMenuState.OPENING;
            } else {
                hitbox.setX(0);
                infoAreaTexture.getHitbox().setX(hitbox.getWidth());
                isOpen = true;
            }
        }
    }
}
