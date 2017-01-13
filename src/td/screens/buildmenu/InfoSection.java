package td.screens.buildmenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import td.data.Colors;
import td.data.Fonts;
import td.towers.TowerType;
import td.util.Debug;
import td.util.Hitbox;
import td.util.Log;
import td.util.RenderUtil;
import td.util.Util;

public class InfoSection implements Section {
    private final BuildMenu menu;
    private final Hitbox hitbox;
    public TowerType towerType = TowerType.UNKNOWN;
    private boolean displayed = false;
    private int mode = BUILD_MODE;
    public final static int BUILD_MODE = 0;
    public final static int UPGRADE_MODE = 1;
    
    public InfoSection(BuildMenu menu) {
        this.menu = menu;
        this.hitbox = new Hitbox(menu.getX(), menu.getHeight() - 200, menu.getWidth(), 300);
    }
    
    @Override
    public void update(double dt) {
        getHitbox().setX(menu.getX());
    }

    @Override
    public void render(Graphics2D g) {
        // Title
        g.setFont(Fonts.BUILD_MENU_SECTIONS);
        g.setColor(Colors.BMENU_SECTION_TITLES);
        g.drawString(towerType.getName(), Util.centerStringX(towerType.getName(), getWidth(), g, getX()), getY() + 20);
        
        // Info
        if(mode == BUILD_MODE) {
            // optimize.
            int x = 20;
            int y = getY() + 45;
            int yInc = 18;
            
            g.setFont(Fonts.BUILD_MENU_INFOLABELS);
            
            if(towerType == TowerType.MACHINE_GUN) {
                RenderUtil.drawTwoColoredString(x, y, "Damage: ", "" + towerType.getDefaults().DAMAGE, Colors.BMENU_SECTION_TITLES, Color.GREEN, g);
                RenderUtil.drawTwoColoredString(x, y + yInc, "Crit Chance: ", towerType.getDefaults().CRIT_CHANCE + "%", Colors.BMENU_SECTION_TITLES, Color.GREEN, g);
            }
        }
        
        // Debug
        if(Debug.ENABLED) {
            g.setColor(Color.red);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public int getY() {
        return getHitbox().getY();
    }
    
    @Override
    public int getX() {
        return getHitbox().getX();
    }
    
    @Override
    public int getWidth() {
        return getHitbox().getWidth();
    }

    @Override
    public int getHeight() {
        return getHitbox().getHeight();
    }
    
    public void setMode(int mode) {
        Log.info("[InfoSection] Setting mode to " + (mode == 0 ? "BUILD_MODE" : "UPGRADE_MODE") + " (" + mode + ")");
        this.mode = mode;
    }
    
    public int getMode() {
        return mode;
    }
    
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
    
    public void setDisplayed(boolean displayed, TowerType towerType) {
        this.displayed = displayed;
        this.towerType = towerType;
    }
    
    public boolean isDisplayed() {
        return displayed;
    }
}
