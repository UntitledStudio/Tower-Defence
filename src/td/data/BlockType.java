package td.data;

public enum BlockType {
    PATH(1),
    TOWER(0),
    UNKNOWN(-1);
    
    private final int id;
    
    private BlockType(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public static BlockType fromId(int id) {
        for(BlockType type : values()) {
            if(type.id == id) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
