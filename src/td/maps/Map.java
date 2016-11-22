package td.maps;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;
import td.Configuration;
import td.assets.ImageCache;
import td.data.Block;
import td.data.BlockType;
import td.screens.PlayScreen;
import td.util.Log;
import td.util.Util;

public class Map {
    private final String name;
    private final int[][] structure;
    private boolean userMade = false;
    
    public Map(String name, int[][] structure) {
        this.name = name;
        this.structure = structure;
    }
    private Set<Block> blocks = new HashSet<>();
    
    public void buildMap() {
        Log.info("[Map: " + getName() + "] Building ..");
        long start = System.currentTimeMillis();
        
        for(int row = 0; row < structure.length; row++) {
            for(int column = 0; column < structure[row].length; column++) {
                int id = structure[row][column];
                BlockType type = BlockType.fromId(id);
                int x = MapManager.COLUMNS[column];
                int y = MapManager.ROWS[row];
                blocks.add(new Block(x, y, type));
            }
        }
        Log.info("[Map: " + getName() + "] Built! (" + (System.currentTimeMillis()-start) + "ms)");
    }
    
    public void update(double dt) {
        
    }
    
    public void render(Graphics2D g, PlayScreen screen) {
        /**
         * Render all the blocks in the block list.
         */
        for(Block b : blocks) {
            b.render(g, screen);
        }
        
        if(screen.getInput().isMouseInWindow()) {
            Block b = getBlockAt(screen.getInput().getMouseX(), screen.getInput().getMouseY());
            
            if(b.getType() == BlockType.TOWER) {
                if(Util.isWithinArea(screen.getInput(), b.getTexture().getHitbox())) {
                    g.drawImage(ImageCache.BLOCK_HIGHLIGHT, b.getX(), b.getY(), null);
                }
            } 
        }
    }
    
    /**
     * Returns the block at the given coordinate.
     * @param x
     * @param y
     * @return
     */
    public Block getBlockAt(int x, int y) {
        for(Block b : blocks) {
            int minX = b.getX();
            int maxX = minX + Configuration.BLOCK_SIZE;

            if(x == minX || (x > minX && x <= maxX)) {
                int minY = b.getY();
                int maxY = minY + Configuration.BLOCK_SIZE;

                if(y == minY || (y > minY && y <= maxY)) {
                    return b;
                }
            }
        }
        return null;
    }
    
    public Set<Block> getBlocks() {
        return blocks;
    }
    
    public void setUserMade(boolean userMade) {
        this.userMade = userMade;
    }
    
    public boolean isUserMade() {
        return userMade;
    }
    
    public String getName() {
        return name;
    }
    
    public int[][] getStructure() {
        return structure;
    }
}
