package td.screens.buildmenu;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import td.assets.Image;
import td.assets.Texture;
import td.data.Colors;
import td.data.Fonts;
import td.towers.Tower;
import td.towers.TowerPlacer;
import td.towers.TowerType;
import td.util.Debug;
import td.util.Hitbox;
import td.util.Log;
import td.util.Util;

public class TowerSection implements Section {
    private final BuildMenu menu;
    private final Hitbox hitbox;
    private final String title = "Towers";
    private final int iconSize = 60;
    private final TowerIcon TOWER_BASIC;
    
    public TowerSection(BuildMenu menu, int y) {
        this.menu = menu;
        this.hitbox = new Hitbox(menu.getX(), y, menu.getWidth(), 300);
        
        TowerIcon basicTower = null;
        try {
            // Load tower icons
            basicTower = new TowerIcon(new Texture(Util.resizeImageHQ(Image.TOWER_MACHINE_GUN.getBufferedImage(), iconSize, iconSize)), TowerType.MACHINE_GUN);
            basicTower.setY(y + 30);
        } catch (IOException ex) {
            Log.error("[BuildMenu: TowerSection] Failed to load tower icon(s)");
            ex.printStackTrace();
        }
        this.TOWER_BASIC = basicTower;
    }
    
    /**
     * Update this section.
     * @param dt 
     */
    @Override
    public void update(double dt) {
        getHitbox().setX(menu.getX());
    }
    
    /**
     * Render this section.
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        // Title
        g.setFont(Fonts.BUILD_MENU_SECTIONS);
        g.setColor(Colors.BMENU_SECTION_TITLES);
        g.drawString(title, Util.centerStringX(title, getWidth(), g, getX()), getY() + 20);
        
        // Tower icons
        TOWER_BASIC.setX(getX() + 20);
        TOWER_BASIC.draw(g);
        
        // Debug
        if(Debug.ENABLED) {
            g.setColor(Color.RED);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(Util.isWithinArea(x, y, TOWER_BASIC.getTexture())) {
                Log.info("[TowerSection] Registered mousePress at icon: TOWER_BASIC");
                
                menu.toggle(false);
                TowerPlacer.setActive(true);
                TowerPlacer.setSelectedTower(TowerType.MACHINE_GUN);
                
                if(menu.rememberMousePos()) {
                    menu.moveMouse();
                }
            }
        }
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
    
    private class TowerIcon {
        private final Texture texture;
        private final TowerType type;

        public TowerIcon(Texture texture, TowerType type) {
            this.texture = texture;
            texture.createHitbox(0, 0);
            this.type = type;
        }
        
        public int getX() {
            return texture.getHitbox().getX();
        }
        
        public void setX(int x) {
            getTexture().getHitbox().setX(x);
        }
        
        public int getY() {
            return texture.getHitbox().getY();
        }
        
        public void setY(int y) {
            getTexture().getHitbox().setY(y);
        }
        
        public int getWidth() {
            return texture.getHitbox().getWidth();
        }
        
        public int getHeight() {
            return texture.getHitbox().getHeight();
        }
        
        public Texture getTexture() {
            return texture;
        }
        
        public TowerType getType() {
            return type;
        }
        
        public void draw(Graphics2D g) {
            // Icon highlight
            if(Util.isWithinArea(menu.getInput(), texture.getHitbox())) {
                g.setColor(Colors.BMENU_SECTION_ICON_BG_HIGHLIGHTED);
                menu.getInfoSection().setDisplayed(true, type);
            } else {
                g.setColor(Colors.BMENU_SECTION_ICON_BG);
                menu.getInfoSection().setDisplayed(false);
            }
            g.fillRect(getX(), getY(), getWidth(), getHeight());
            
            // Icon image
            getTexture().draw(g);
            
            // Price tag
            g.setFont(Fonts.BUILD_MENU_PRICETAG);
            g.setColor(Colors.BMENU_PRICETAG);
            g.drawString("$" + type.getDefaults().BUY_COST, getX() + 5, getTexture().getHitbox().getBottomY() - 5);
        }
    }
}
