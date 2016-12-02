package td.screens.buildmenu;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import td.assets.Image;
import td.assets.ImageCache;
import td.assets.Texture;
import td.data.Colors;
import td.data.Fonts;
import td.util.Log;
import td.util.Util;

public class TowerSection {
    private final int y;
    private final BuildMenu menu;
    private Texture texture = null;
    private final String title = "Towers";
    private final List<TowerIcon> icons;
    private final int iconSize = 60;
    
    public TowerSection(BuildMenu menu, int y) {
        this.menu = menu;
        this.y = y;
        this.icons = new ArrayList<>();
        
        try {
            // Load background
            this.texture = new Texture(Image.TOWER_SECTION);
            this.texture.createHitbox(menu.getX(), y);
            
            // Load tower icons
            this.icons.add(new TowerIcon(new Texture(Util.resizeImageHQ(Image.TOWER_BASIC.getBufferedImage(), iconSize, iconSize))));
        } catch (IOException ex) {
            Log.error("[BuildMenu: TowerSection] Failed to load texture");
        }
    }
    
    /**
     * Update this section.
     * @param dt 
     */
    public void update(double dt) {
        getTexture().getHitbox().setX(menu.getX());
    }
    
    /**
     * Render this section.
     * @param g 
     */
    public void render(Graphics2D g) {
        // Background
        getTexture().draw(g);
        
        // Title
        g.setFont(Fonts.BUILD_MENU_SECTIONS);
        g.setColor(Colors.BUILD_MENU_SECTION_TITLES);
        g.drawString(title, Util.centerStringX(title, getWidth(), g, getX()), y + 20);
        
        // Icons
        // !! Consider: Hard coding all towericon variables instead for better performance?
        for(TowerIcon icon : icons) {
            icon.setX(getX() + 10); // todo: put somewhere else
            icon.setY(getY() + 30); // todo: put somewhere else
            icon.draw(g);
        }
    }
    
    public void addTowers() {
        
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    public int getY() {
        return y;
    }
    
    public int getX() {
        return getTexture().getHitbox().getX();
    }
    
    public int getWidth() {
        return getTexture().getHitbox().getWidth();
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
            getTexture().draw(g);
        }
    }
}
