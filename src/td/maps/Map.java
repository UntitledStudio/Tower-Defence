package td.maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import td.Configuration;
import td.assets.ImageCache;
import td.data.Block;
import td.data.BlockType;
import td.screens.PlayScreen;
import td.towers.TowerPlacer;
import td.util.Debug;
import td.util.Input;
import td.util.Log;
import td.util.RenderUtil;
import td.util.Util;
import td.waves.WaveManager;

public class Map {
    private final String name;
    private final int[][] structure;
    private boolean userMade = false;
    private final List<Block> blocks = new ArrayList<>();
    private final List<Block> markedBlocks = new ArrayList<>();
    private PathData pathData = null;
    
    public Map(String name, int[][] structure) {
        this.name = name;
        this.structure = structure;
    }
    
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
        pathData = new PathData(this);
        pathData.loadFile();
        pathData.injectIntoBlockList();
        
        Log.info("[Map: " + getName() + "] Built! (" + (System.currentTimeMillis()-start) + "ms)");
    }
    
    public void update(double dt) {
        /**
         * Tick the live wave.
         */
        WaveManager.tickLiveWave();
    }
    
    public void render(Graphics2D g, PlayScreen screen) {
        /**
         * Render all the blocks in the block list.
         */
        for(Block b : blocks) {
            b.render(g, screen);
        }
        
        /**
         * Render all entities on the map.
         */
        WaveManager.renderLiveWave(g);
        //EntityManager.renderLivingEntities(g);
        
        /**
         * Used if any block is in the "blocks" index is marked for range indication.
         */
        if(!markedBlocks.isEmpty()) {
            for(Block b : markedBlocks) {
                if(b.willHighlight()) {
                    TowerPlacer.drawRangeIndicator(screen.getInput(), g, b.getTowerEntity().getRangeIndicator());
                    b.setWillHighlightRange(false);
                }
                //g.drawImage(ImageCache.TOWER_BASIC, b.getX(), b.getY(), null);
                b.getTowerEntity().draw(g);
            }
            markedBlocks.clear();
        }
        
        /**
         * Render the highlighted block.
         */
        if(screen.getInput().isMouseInWindow()) {
            Block b = getBlockAt(screen.getInput().getMouseX(), screen.getInput().getMouseY());
            
            if(b != null && b.getType() == BlockType.TOWER) {
                if(Util.isWithinArea(screen.getInput(), b.getTexture().getHitbox())) {
                    if(b.hasTowerEntity()) {
                        if(!TowerPlacer.isActive()) {
                            b.setWillHighlightRange(true);
                        }
                    } else {
                        g.drawImage(ImageCache.BLOCK_HIGHLIGHT, b.getX(), b.getY(), null);
                    }
                }
            }
            
            if(Debug.ENABLED) {
                RenderUtil.drawString("B xy: " + b.getX() + "," + b.getY(), screen.getInput().getMouseX() - 35, screen.getInput().getMouseY() - 15, Color.WHITE, Debug.bgClr, g);
            }
        }
    }
    
    public void processTowerRender(Block block) {
        markedBlocks.add(block);
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

            if(x >= minX && x < maxX) {
                int minY = b.getY();
                int maxY = minY + Configuration.BLOCK_SIZE;

                if(y >= minY && y < maxY) {
                    return b;
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the currently highlighted block.
     * Returns null if no block is highlighted.
     * @param input
     * @return 
     */
    public Block getHighlightedBlock(Input input) {
        if(input.isMouseInWindow()) {
            Block b = getBlockAt(input.getMouseX(), input.getMouseY());
            
            if(b != null && b.getType() == BlockType.TOWER) {
                if(Util.isWithinArea(input, b.getTexture().getHitbox())) {
                    return b;
                }
            }
        }
        return null;
    }
    
    public List<Block> getBlocks() {
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
    
    public PathData getPathData() {
        return pathData;
    }
}
