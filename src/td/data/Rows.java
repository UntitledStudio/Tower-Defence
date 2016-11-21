package td.data;

import td.Configuration;

public enum Rows {
    // x
    ROW_1(0),
    ROW_2(Configuration.BLOCK_SIZE),
    ROW_3(Configuration.BLOCK_SIZE * 2),
    ROW_4(Configuration.BLOCK_SIZE * 3),
    ROW_5(Configuration.BLOCK_SIZE * 4),
    ROW_6(Configuration.BLOCK_SIZE * 5),
    ROW_7(Configuration.BLOCK_SIZE * 6),
    ROW_8(Configuration.BLOCK_SIZE * 7),
    ROW_9(Configuration.BLOCK_SIZE * 8),
    ROW_10(Configuration.BLOCK_SIZE * 9),
    ROW_11(Configuration.BLOCK_SIZE * 10),
    ROW_12(Configuration.BLOCK_SIZE * 11),
    ROW_13(Configuration.BLOCK_SIZE * 12),
    ROW_14(Configuration.BLOCK_SIZE * 13),
    ROW_15(Configuration.BLOCK_SIZE * 14),
    ROW_16(Configuration.BLOCK_SIZE * 15);
    
    private final int x;
    
    private Rows(int x) {
        this.x = x;
    }
    
    public int getX() {
        return x;
    }
}
