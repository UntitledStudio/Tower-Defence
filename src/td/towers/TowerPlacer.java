package td.towers;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import td.data.Block;
import td.util.Log;
import td.util.Util;

public class TowerPlacer {
    private static JFrame frame = null;
    private static boolean ACTIVE = false;
    private static TowerType SELECTED_TOWER = null;
    private static BufferedImage icon = null;
    
    public static void setFrame(JFrame frame) {
        TowerPlacer.frame = frame;
    }
    
    public static void setSelectedTower(TowerType type) {
        Log.info("[TowerPlacer] Selecting new tower for placement: " + type.name() + " ..");
        SELECTED_TOWER = type;
        icon = SELECTED_TOWER.getImage(); // !wip!
    }
    
    public static void place(Block block) {
        Log.info("[TowerPlacer] Placing a " + SELECTED_TOWER.name() + " at " + block.getX() + "," + block.getY() + " ..");
        
        switch(SELECTED_TOWER) {
            case BASIC_TOWER: {
                BasicTower te = new BasicTower(block);
                block.setTowerEntity(te);
                break;
            }
            default: {
                Log.error("[TowerPlacer] Attempted to place an invalid tower! "  + SELECTED_TOWER.name());
                // alert player somehow
            }
        }
        setActive(false);
        Log.info("[TowerPlacer] Tower placed!");
    }
    
    public static TowerType getSelectedTower() {
        return SELECTED_TOWER;
    }
    
    public static BufferedImage getIcon() {
        return icon;
    }
    
    public static void setActive(boolean active) {
        ACTIVE = active;
        Util.setCursorVisible(!active, frame);
        
        if(!active) {
            clean();
        }
    }
    
    public static boolean isActive() {
        return ACTIVE;
    }
    
    public static void clean() {
        SELECTED_TOWER = null;
        icon = null;
    }
}