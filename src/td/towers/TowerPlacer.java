package td.towers;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import td.data.Block;
import td.data.BlockType;
import td.data.Colors;
import td.data.Player;
import td.maps.MapManager;
import td.screens.PlayScreen;
import td.util.Input;
import td.util.Log;
import td.util.Util;

public class TowerPlacer {
    private static boolean ACTIVE = false;
    private static TowerType SELECTED_TOWER = null;
    private static BufferedImage icon = null;
    public static boolean drawPlacementDenied = false;
    public static boolean drawPlacementAllowed = false;
    //public static boolean drawNotEnoughCash = false;

    public static void setSelectedTower(TowerType type) {
        Log.info("[TowerPlacer] Selecting new tower for placement: " + type.name() + " ..");
        SELECTED_TOWER = type;
        icon = SELECTED_TOWER.getImage(); // !wip!
    }
    
    public static void place(Block block, Player player) {
        Log.info("[TowerPlacer] Placing a " + SELECTED_TOWER.name() + " at " + block.getX() + "," + block.getY() + " ..");
        int price = SELECTED_TOWER.getDefaults().BUY_COST;
        
        if(player.getCash() < price) {
            Log.error("[TowerPlacer] Player does not have enough cash for this tower! (" + SELECTED_TOWER.name() + ")");
            return;
        }
        
        switch(SELECTED_TOWER) {
            case MACHINE_GUN: {
                MachineGunTower te = new MachineGunTower(block);
                block.setTowerEntity(te);
                break;
            }
            default: {
                Log.error("[TowerPlacer] Attempted to place an invalid tower! "  + SELECTED_TOWER.name());
                // alert player somehow
                setActive(false);
                return;
            }
        }
        player.takeCash(Defaults.getDefaults(SELECTED_TOWER).BUY_COST);
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
        //Util.setCursorVisible(!active, frame);
        
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
    
    public static void drawRangeIndicator(Input input, Graphics2D g, Ellipse2D ellipse) {
        if(isActive()) {
            Block b = MapManager.getCurrentMap().getHighlightedBlock(input);
            
            if(b != null) {
                if(PlayScreen.instance.getPlayer().getCash() < getSelectedTower().getDefaults().BUY_COST) {
                    g.setColor(Colors.TOWER_RANGE_HIGHLIGHT_NOTOK);
                } else if(b.hasTowerEntity() || b.getType() != BlockType.TOWER) {
                    g.setColor(Colors.TOWER_RANGE_HIGHLIGHT_NOTOK);
                    drawPlacementDenied = true;
                } else {
                    g.setColor(Colors.TOWER_RANGE_HIGHLIGHT_OK);
                    drawPlacementAllowed = true;
                }
            } else {
                g.setColor(Colors.TOWER_RANGE_HIGHLIGHT_NOTOK);
                drawPlacementDenied = true;
            }
        } else {
            g.setColor(Colors.TOWER_RANGE_HIGHLIGHT);
        }
        g.fill(ellipse);
    }
}
