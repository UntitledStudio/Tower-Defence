package td.data;

public enum BlockType {
    PATH(0),
    TOWER(1),
    UNKNOWN(-1);
    
    private final int id;
    
    private BlockType(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}
