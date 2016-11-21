package td.data;

import td.Configuration;

public enum Columns {
    // y
    COLUMN_1(0),
    COLUMN_2(Configuration.BLOCK_SIZE),
    COLUMN_3(Configuration.BLOCK_SIZE * 2),
    COLUMN_4(Configuration.BLOCK_SIZE * 3),
    COLUMN_5(Configuration.BLOCK_SIZE * 4),
    COLUMN_6(Configuration.BLOCK_SIZE * 5),
    COLUMN_7(Configuration.BLOCK_SIZE * 6),
    COLUMN_8(Configuration.BLOCK_SIZE * 7),
    COLUMN_9(Configuration.BLOCK_SIZE * 8);
    
    private final int y;
    
    private Columns(int y) {
        this.y = y;
    }
    
    public int getY() {
        return y;
    }
}
