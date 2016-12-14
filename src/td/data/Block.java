package td.data;

import java.awt.Graphics2D;
import java.io.IOException;
import td.assets.Image;
import td.assets.ImageCache;
import td.assets.Texture;
import td.maps.MapManager;
import td.screens.PlayScreen;
import td.towers.Tower;
import td.util.Log;

public class Block {
    private final int x;
    private final int y;
    private BlockType type = null;
    private Texture texture = null;
    private Tower towerEntity = null;
    private boolean willHighlight = false;
    
    public Block(int x, int y, BlockType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        
        try {
            determineTexture();
        } catch (IOException ex) {
            Log.error("[Block] Error loading texture. (" + type.name() + ")");
        }
    }    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    public void setTowerEntity(Tower te) {
        this.towerEntity = te;
    }
    
    public Tower getTowerEntity() {
        return towerEntity;
    }
    
    public boolean hasTowerEntity() {
        return getTowerEntity() != null;
    }
    
    public void setWillHighlightRange(boolean highlight) {
        this.willHighlight = highlight;
    }
    
    public boolean willHighlight() {
        return willHighlight;
    }
    
    /**
     * Automatically attempts to set the texture of this block depending on the block type.
     * @throws java.io.IOException
     */
    public void determineTexture() throws IOException {
        switch(type) {
            case PATH: setTexture(new Texture(Image.BLOCK_PATH)); break;
            case TOWER: setTexture(new Texture(Image.BLOCK_TOWER)); break;
            default: setTexture(new Texture(ImageCache.UNKNOWN)); break;
        }
        texture.createHitbox(x, y);
    }
    
    public void render(Graphics2D g, PlayScreen screen) {
        // Texture
        g.drawImage(texture.getImage(), x, y, null);
        
        // Tower Entities
        if(hasTowerEntity()) {
            if(willHighlight) {
                // If there's a block marked for range highlighting then process tower rendering differently. (Heavier)
                // If not, process normally. (Light)
                MapManager.getCurrentMap().processTowerRender(this);
            } else {
                g.drawImage(ImageCache.TOWER_BASIC, x, y, null);
            }
        }
    }
}
