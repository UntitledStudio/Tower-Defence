package td.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import td.util.Log;

public class ImageCache {
    public static BufferedImage BLOCK_HIGHLIGHT = null;
    
    public static void load() {
        try {
            BLOCK_HIGHLIGHT = Image.BLOCK_HIGHLIGHT.getBufferedImage();
        } catch (IOException ex) {
            Log.error("[ImageCache] Failed to load an image");
            ex.printStackTrace();
        }
    }
}
