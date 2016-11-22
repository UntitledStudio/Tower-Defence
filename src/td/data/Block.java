package td.data;

import java.awt.Graphics2D;
import java.io.IOException;
import td.assets.Image;
import td.assets.Texture;
import td.screens.PlayScreen;
import td.util.Log;

public class Block {
    //private final Rows row;
    //private final Columns column;
    private final int x;
    private final int y;
    private BlockType type;
    private Texture texture = null;
    
    public Block(int x, int y, BlockType type) {
        //this.row = row;
        //this.column = column;
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
    
    /**
     * Automatically attempts to set the texture of this block depending on the block type.
     * @throws java.io.IOException
     */
    public void determineTexture() throws IOException {
        switch(type) {
            case PATH: setTexture(new Texture(Image.BLOCK_PATH)); break;
            case TOWER: setTexture(new Texture(Image.BLOCK_TOWER)); break;
            default: setTexture(new Texture(Image.BLOCK_UNKNOWN)); break;
        }
        texture.createHitbox(x, y);
    }
    
    public void render(Graphics2D g, PlayScreen screen) {
        g.drawImage(texture.getImage(), x, y, null);
    }
}
