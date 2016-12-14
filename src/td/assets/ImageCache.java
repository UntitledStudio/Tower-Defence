package td.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import td.util.Log;

public class ImageCache {
    public static BufferedImage UNKNOWN = null;
    public static BufferedImage BLOCK_HIGHLIGHT = null;
    public static BufferedImage TOWER_BASIC = null;
    public static BufferedImage CASH_ICON = null;
    public static BufferedImage HEART_ICON = null;
    public static BufferedImage PLACEMENT_DENIED = null;
    public static BufferedImage PLACEMENT_ALLOWED = null;
    
    public static void load() {
        try {
            UNKNOWN             = Image.UNKNOWN.getBufferedImage();
            BLOCK_HIGHLIGHT     = Image.BLOCK_HIGHLIGHT.getBufferedImage();
            TOWER_BASIC         = Image.TOWER_BASIC.getBufferedImage();
            CASH_ICON           = Image.CASH_ICON.getBufferedImage();
            HEART_ICON          = Image.HEART_ICON.getBufferedImage();
            PLACEMENT_DENIED    = Image.PLACEMENT_DENIED.getBufferedImage();
            PLACEMENT_ALLOWED   = Image.PLACEMENT_ALLOWED.getBufferedImage();
        } catch (IOException ex) {
            Log.error("[ImageCache] Failed to load!");
            ex.printStackTrace();
        }
    }
}
