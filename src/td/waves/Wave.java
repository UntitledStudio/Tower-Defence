package td.waves;

public class Wave {
    private final int id;
    private final boolean bossWave;
    
    public Wave(int id, boolean bossWave) {
        this.bossWave = bossWave;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean isBossWave() {
        return bossWave;
    }
}
