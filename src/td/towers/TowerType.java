package td.towers;

import java.awt.image.BufferedImage;
import td.assets.ImageCache;

public enum TowerType {
    MACHINE_GUN(ImageCache.TOWER_MACHINE_GUN, "Machine Gun"),
    UNKNOWN(ImageCache.UNKNOWN, "Unknown");
    
    private final BufferedImage bi;
    private final String name;
    
    private TowerType(BufferedImage bi, String name) {
        this.bi = bi;
        this.name = name;
    }
    
    public BufferedImage getImage() {
        return bi;
    }
    
    public String getName() {
        return name;
    }
    
    public Defaults getDefaults() {
        return Defaults.getDefaults(this);
    }
}
