package td.screens.buildmenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import td.assets.Image;
import td.assets.ImageCache;
import td.assets.Texture;
import td.data.Colors;
import td.data.Fonts;
import td.util.Hitbox;
import td.util.Log;
import td.util.Util;

public class TowerSection {
    private final int y;
    private final BuildMenu menu;
    private final Hitbox hitbox;
    private final String title = "Towers";
    private final List<TowerIcon> icons;
    private final int iconSize = 60;
    
    public TowerSection(BuildMenu menu, int y) {
        this.menu = menu;
        this.y = y;
        this.icons = new ArrayList<>();
        this.hitbox = new Hitbox(menu.getX(), y, menu.getWidth(), 0); // test
        
        try {
            // Load tower icons
            TowerIcon basicTower = new TowerIcon(new Texture(Util.resizeImageHQ(Image.TOWER_BASIC.getBufferedImage(), iconSize, iconSize)));
            basicTower.setY(y + 30);
            this.icons.add(basicTower);
        } catch (IOException ex) {
            Log.error("[BuildMenu: TowerSection] Failed to load tower icon(s)");
        }
    }
    
    /**
     * Update this section.
     * @param dt 
     */
    public void update(double dt) {
        getHitbox().setX(menu.getX());
    }
    
    /**
     * Render this section.
     * @param g 
     */
    public void render(Graphics2D g) {
        // Title
        g.setFont(Fonts.BUILD_MENU_SECTIONS);
        g.setColor(Colors.BMENU_SECTION_TITLES);
        g.drawString(title, Util.centerStringX(title, getWidth(), g, getX()), y + 20);
        
        // Icons
        // !! Consider: Hard coding all towericon variables instead for better performance?
        for(TowerIcon icon : icons) {
            icon.setX(getX() + 20);
            icon.draw(g);
        }
    }
    
    public void addTowers() {
        
    }
    
    public Hitbox getHitbox() {
        return hitbox;
    }
    
    public int getY() {
        return y;
    }
    
    public int getX() {
        return getHitbox().getX();
    }
    
    public int getWidth() {
        return getHitbox().getWidth();
    }
    
    private class TowerIcon {
        private final Texture texture;

        public TowerIcon(Texture texture) {
            this.texture = texture;
            texture.createHitbox(0, 0);
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
        
        public void draw(Graphics2D g) {
            if(Util.isWithinArea(menu.getInput(), texture.getHitbox())) {
                g.setColor(Colors.BMENU_SECTION_ICON_BG_HIGHLIGHTED);
            } else {
                g.setColor(Colors.BMENU_SECTION_ICON_BG);
            }
            g.fillRect(getX(), getY(), getWidth(), getHeight());
            getTexture().draw(g);
        }
    }
}
