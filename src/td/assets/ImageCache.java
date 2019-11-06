package td.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import td.util.Log;

public class ImageCache {
    public static BufferedImage UNKNOWN = null;
    public static BufferedImage BLOCK_HIGHLIGHT = null;
    public static BufferedImage TOWER_MACHINE_GUN = null;
    public static BufferedImage CASH_ICON = null;
    public static BufferedImage HEART_ICON = null;
    public static BufferedImage SWORD_ICON = null;
    public static BufferedImage PLACEMENT_DENIED = null;
    public static BufferedImage PLACEMENT_ALLOWED = null;
    public static BufferedImage ENEMY_BASIC = null;
    public static BufferedImage MACHINE_GUN_PROJECTILE = null;
    
    public static void load() {
        try {
            UNKNOWN                 = Image.UNKNOWN.getBufferedImage();
            BLOCK_HIGHLIGHT         = Image.BLOCK_HIGHLIGHT.getBufferedImage();
            TOWER_MACHINE_GUN       = Image.TOWER_MACHINE_GUN.getBufferedImage();
            CASH_ICON               = Image.CASH_ICON.getBufferedImage();
            HEART_ICON              = Image.HEART_ICON.getBufferedImage();
            PLACEMENT_DENIED        = Image.PLACEMENT_DENIED.getBufferedImage();
            PLACEMENT_ALLOWED       = Image.PLACEMENT_ALLOWED.getBufferedImage();
            SWORD_ICON              = Image.SWORD_ICON.getBufferedImage();
            ENEMY_BASIC             = Image.ENEMY_BASIC.getBufferedImage();
            MACHINE_GUN_PROJECTILE  = Image.MACHINE_GUN_PROJECTILE.getBufferedImage();
        } catch (IOException ex) {
            Log.error("[ImageCache] Failed to load!");
            ex.printStackTrace();
        }
    }
}
