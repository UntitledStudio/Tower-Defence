package td.towers;

import java.awt.image.BufferedImage;
import td.assets.ImageCache;

public enum TowerType {
    BASIC_TOWER(ImageCache.TOWER_BASIC),
    UNKNOWN(ImageCache.UNKNOWN);
    
    private final BufferedImage bi;
    
    private TowerType(BufferedImage bi) {
        this.bi = bi;
    }
    
    public BufferedImage getImage() {
        return bi;
    }
}
