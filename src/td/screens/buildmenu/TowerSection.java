package td.screens.buildmenu;

import java.awt.Graphics2D;
import java.io.IOException;
import td.assets.Image;
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
    
    public TowerSection(BuildMenu menu, int y) {
        this.menu = menu;
        this.y = y;
        
        try {
            this.texture = new Texture(Image.TOWER_SECTION);
            this.texture.createHitbox(menu.getX(), y);
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
}
