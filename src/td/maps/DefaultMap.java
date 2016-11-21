package td.maps;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import td.Configuration;
import td.assets.ImageCache;
import td.data.Block;
import td.data.BlockType;
import td.data.Columns;
import td.data.Rows;
import td.screens.PlayScreen;
import td.util.Util;

public class DefaultMap implements Map {
    // Array list of all the blocks in this map. (position, type, ...)
    private final List<Block> blocks;

    public DefaultMap() {
        this.blocks = new ArrayList<>();
    }

    /**
     * Build the map by manually creating all the blocks necessary and adding them to the block list.
     */
    @Override
    public void buildMap() {
        for(Rows row : Rows.values()) {
            for(Columns column : Columns.values()) {
                BlockType type = BlockType.TOWER_BLOCK;
                
                switch(row) {
                    case ROW_1: if(column == Columns.COLUMN_6) type = BlockType.PATH_BLOCK; break;
                    case ROW_2: if(column == Columns.COLUMN_6) type = BlockType.PATH_BLOCK; break;
                    case ROW_3: if(column == Columns.COLUMN_6) type = BlockType.PATH_BLOCK; break;
                    case ROW_4: if(column == Columns.COLUMN_6) type = BlockType.PATH_BLOCK; break;
                    case ROW_5: {
                        switch(column) {
                            case COLUMN_6: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_5: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_4: type = BlockType.PATH_BLOCK; break;
                        }
                        break;
                    }
                    case ROW_6: if(column == Columns.COLUMN_4) type = BlockType.PATH_BLOCK; break;
                    case ROW_7: if(column == Columns.COLUMN_4) type = BlockType.PATH_BLOCK; break;
                    case ROW_8: {
                        switch(column) {
                            case COLUMN_4: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_5: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_6: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_7: type = BlockType.PATH_BLOCK; break;
                        }
                        break;
                    }
                    case ROW_9: if(column == Columns.COLUMN_7) type = BlockType.PATH_BLOCK; break;
                    case ROW_10: if(column == Columns.COLUMN_7) type = BlockType.PATH_BLOCK; break;
                    case ROW_11: {
                        switch(column) {
                            case COLUMN_7: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_6: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_5: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_4: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_3: type = BlockType.PATH_BLOCK; break;
                            case COLUMN_2: type = BlockType.PATH_BLOCK; break;
                        }
                        break;
                    }
                    case ROW_12: if(column == Columns.COLUMN_2) type = BlockType.PATH_BLOCK; break;
                    case ROW_13: if(column == Columns.COLUMN_2) type = BlockType.PATH_BLOCK; break;
                    case ROW_14: if(column == Columns.COLUMN_2) type = BlockType.PATH_BLOCK; break;
                    case ROW_15: if(column == Columns.COLUMN_2) type = BlockType.PATH_BLOCK; break;
                    case ROW_16: if(column == Columns.COLUMN_2) type = BlockType.PATH_BLOCK; break;
                }
                blocks.add(new Block(row, column, type));
            }
        }
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics2D g, PlayScreen screen) {
        /**
         * Render all the blocks in the block list.
         */
        for(Block b : blocks) {
            b.render(g, screen);
        }
        
        if(screen.getInput().isMouseInWindow()) {
            Block b = getBlockAt(screen.getInput().getMouseX(), screen.getInput().getMouseY());
            
            if(b.getType() == BlockType.TOWER_BLOCK) {
                if(Util.isWithinArea(screen.getInput(), b.getTexture().getHitbox())) {
                    g.drawImage(ImageCache.BLOCK_HIGHLIGHT, b.getX(), b.getY(), null);
                }
            } 
        }
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * Returns the block at the given coordinate.
     * @param x
     * @param y
     * @return
     */
    @Override
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
}
